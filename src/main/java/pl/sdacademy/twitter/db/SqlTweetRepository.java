package pl.sdacademy.twitter.db;

import pl.sdacademy.twitter.model.Tweet;
import pl.sdacademy.twitter.model.TweetRepository;
import pl.sdacademy.twitter.model.TweetRepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class SqlTweetRepository implements TweetRepository {
	private DataSource dataSource;

	public SqlTweetRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(Tweet tweet) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement insertStatement = connection
			     .prepareStatement("INSERT INTO tweet(message, " +
				     "author) VALUES (?, ?)")) {

			insertStatement.setString(1, tweet.getMessage());
			insertStatement.setString(2, tweet.getAuthor());

			insertStatement.execute();
		}
		catch (SQLException e) {
			throw wrapEx(e, "Failed to add a tweet");
		}
	}

	private TweetRepositoryException wrapEx(SQLException e, String msg) {
		throw new TweetRepositoryException(msg, e);
	}

	@Override
	public Stream<Tweet> findAll() {
		try (Connection connection = dataSource.getConnection();
		     Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT "
				+ "*" + " FROM tweet");
			Collection<Tweet> allTweets = new ArrayList<>();
			while (resultSet.next()) {
				allTweets.add(new Tweet(resultSet.getString
					("message"), resultSet.getString
					("author")));
			}
			return allTweets.stream();
		}
		catch (SQLException e) {
			throw wrapEx(e, "Failed to get all tweets");
		}
	}
}
