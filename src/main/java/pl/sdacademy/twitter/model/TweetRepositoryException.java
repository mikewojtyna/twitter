package pl.sdacademy.twitter.model;

public class TweetRepositoryException extends RuntimeException {
	public TweetRepositoryException(String message) {
		super(message);
	}

	public TweetRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}
}
