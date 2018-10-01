package pl.sdacademy.twitter.model;

import java.util.stream.Stream;

public interface TweetRepository {
	/**
	 * Saves a tweet inside the database.
	 *
	 * @param tweet a tweet to save
	 * @throws TweetRepositoryException if fails to add a tweet
	 */
	void save(Tweet tweet) throws TweetRepositoryException;


	/**
	 * Returns a stream containing all tweets in the database.
	 *
	 * @return a stream containing all tweets
	 * @throws TweetRepositoryException if fails to obtain a stream
	 */
	Stream<Tweet> findAll() throws TweetRepositoryException;
}
