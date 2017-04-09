package twitter.domain.services;


import twitter.domain.Tweet;
import twitter.domain.User;

import java.util.List;

public interface TweetService {

    List<Tweet> findAll();
    void addTweet(Tweet tweet);
    Tweet createTweet(String tweetText, User user);
    Tweet creatEmptyTweet();

}
