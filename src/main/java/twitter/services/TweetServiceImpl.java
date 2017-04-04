package twitter.services;


import twitter.Tweet;
import twitter.repository.TweetRepository;
import twitter.repository.TweetRepositoryImpl;

import java.util.List;

public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    public TweetServiceImpl(TweetRepositoryImpl tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public void addTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }
}
