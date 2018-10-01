package pl.sdacademy.twitter.model;

import java.util.stream.Stream;

public class Dashboard {
	private TweetRepository repository;

	public Dashboard(TweetRepository repository) {
		this.repository = repository;
	}

	public Tweet create(String msg, String author) {
		Tweet newTweet = new Tweet(msg, author);
		repository.save(newTweet);
		return newTweet;
	}

	public Stream<Tweet> load() {
		return repository.findAll();
	}
}
