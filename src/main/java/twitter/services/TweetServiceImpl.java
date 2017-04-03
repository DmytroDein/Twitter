package twitter.services;


import twitter.Tweet;
import twitter.repository.TweetRepository;

import java.util.List;

public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public List<Tweet> findAll() {
        return null;
    }
}
