package twitter.repository;


import twitter.Tweet;
import twitter.User;

import java.util.List;

public interface TweetRepository {

    void save(Tweet tweet);
    List<Tweet> findAll();

}
