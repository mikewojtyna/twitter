package pl.sdacademy.twitter.db;

import pl.sdacademy.twitter.model.Tweet;
import pl.sdacademy.twitter.model.TweetRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class InMemoryTweetRepository implements TweetRepository {
	private Collection<Tweet> tweets;

	public InMemoryTweetRepository() {
		tweets = new ArrayList<>();
	}

	@Override
	public void save(Tweet tweet) {
		tweets.add(tweet);
	}

	@Override
	public Stream<Tweet> findAll() {
		return tweets.stream();
	}
}
