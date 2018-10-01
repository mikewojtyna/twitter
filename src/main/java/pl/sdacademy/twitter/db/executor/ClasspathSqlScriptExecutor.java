package pl.sdacademy.twitter.db.executor;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ClasspathSqlScriptExecutor implements SqlScriptExecutor {
	private DataSource dataSource;

	public ClasspathSqlScriptExecutor(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void execute(String classpathScript) throws
		SqlScriptExecutorException {
		try (Connection connection = dataSource.getConnection();
		     Statement statement = connection.createStatement()) {
			InputStream sqlScriptInputStream = getClass()
				.getResourceAsStream("/" + classpathScript);
			boolean previousAutoCommitMode = connection
				.getAutoCommit();
			try {
				// start transaction
				connection.setAutoCommit(false);

				Scanner scanner = new Scanner
					(sqlScriptInputStream, "UTF-8");
				scanner.useDelimiter(";");
				while (scanner.hasNext()) {
					String sqlStatement = scanner.next()
						.trim();
					if (!sqlStatement.isEmpty()) {
						statement.execute
							(sqlStatement);
					}
				}

				// end transaction
				connection.commit();
			}
			catch (SQLException e) {
				connection.rollback();
				throw wrapEx(e, classpathScript);
			}
			finally {
				connection.setAutoCommit
					(previousAutoCommitMode);
			}
		}
		catch (SQLException e) {
			throw wrapEx(e, classpathScript);
		}
	}

	private SqlScriptExecutorException wrapEx(SQLException e, String
		classpath) {
		return new SqlScriptExecutorException(String.format("Failed "
			+ "to" + " execute script %s", classpath), e);
	}
}
