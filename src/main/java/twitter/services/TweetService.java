package twitter.services;


import twitter.Tweet;
import twitter.repository.TweetRepository;

import java.util.List;

public interface TweetService {

    List<Tweet> findAll();
}
