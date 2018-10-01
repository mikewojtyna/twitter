package pl.sdacademy.twitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sdacademy.twitter.db.DataSourceFactory;
import pl.sdacademy.twitter.db.SqlTweetRepository;
import pl.sdacademy.twitter.db.executor.ClasspathSqlScriptExecutor;
import pl.sdacademy.twitter.model.Dashboard;
import pl.sdacademy.twitter.model.Tweet;

import javax.sql.DataSource;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DashboardTest {

	private Dashboard dashboard;

	@BeforeEach
	void beforeEach() throws Exception {
		DataSource dataSource = DataSourceFactory.createDataSource();
		new ClasspathSqlScriptExecutor(dataSource).execute
			("db/twitter-0.sql");
		dashboard = new Dashboard(new SqlTweetRepository(dataSource));
	}

	@DisplayName("author should be able to create a new tweet")
	@Test
	void test0() {
		// given
		String msg = "content";
		String author = "goobar";

		// when
		Tweet tweet = dashboard.create(msg, author);

		// then
		assertThat(tweet.getAuthor()).isEqualTo(author);
		assertThat(tweet.getMessage()).isEqualTo(msg);
	}

	@DisplayName("should load created tweet from the dashboard")
	@Test
	void test1() {
		// given
		String msg = "content";
		String author = "goobar";
		Tweet tweet = dashboard.create(msg, author);

		// when
		Stream<Tweet> allTweets = dashboard.load();

		// then
		assertThat(allTweets).containsExactlyInAnyOrder(tweet);
	}
}
