package twitter.services;


import twitter.Tweet;
import twitter.User;
import twitter.repository.TweetRepository;

import java.util.List;

public interface TweetService {

    List<Tweet> findAll();
    void addTweet(Tweet tweet);
    Tweet createTweet(String tweetText, User user);

}
