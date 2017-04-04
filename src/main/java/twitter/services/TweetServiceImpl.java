package twitter.services;


import twitter.Tweet;
import twitter.infrastructure.Benchmark;
import twitter.repository.TweetRepository;
import twitter.repository.TweetRepositoryImpl;

import java.util.List;

public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    @Benchmark
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    @Benchmark(value = true)
    public void addTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }
}
