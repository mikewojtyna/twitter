package pl.sdacademy.twitter.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataSourceFactory {
	public static DataSource createDataSource() throws SQLException {
		return h2();
		// return mysql();
	}

	private static DataSource mysql() throws SQLException {
		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setUser("root");
		mysqlDataSource.setURL("jdbc:mysql://localhost/twitter");
		mysqlDataSource.setUseSSL(false);
		return mysqlDataSource;
	}

	private static DataSource h2() {
		JdbcDataSource jdbcDataSource = new JdbcDataSource();
		jdbcDataSource.setUser("sa");
		jdbcDataSource.setPassword("sa");
		jdbcDataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		return jdbcDataSource;
	}
}
