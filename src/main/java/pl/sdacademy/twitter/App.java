package pl.sdacademy.twitter;

import pl.sdacademy.twitter.db.DataSourceFactory;
import pl.sdacademy.twitter.db.SqlTweetRepository;
import pl.sdacademy.twitter.db.executor.ClasspathSqlScriptExecutor;
import pl.sdacademy.twitter.model.Dashboard;

import javax.sql.DataSource;
import java.sql.SQLException;

public class App {
	public static void main(String[] args) throws SQLException {
		DataSource dataSource = DataSourceFactory.createDataSource();
		createSchema(dataSource);
		Dashboard dashboard = new Dashboard(new SqlTweetRepository
			(dataSource));
		dashboard.create("hello", "goobar");
		dashboard.load().forEach(System.out::println);
	}

	private static void createSchema(DataSource dataSource) {
		new ClasspathSqlScriptExecutor(dataSource).execute
			("db/twitter-0.sql");
	}
}
