package twitter.repository;


import twitter.Tweet;
import twitter.infrastructure.Benchmark;

import java.util.ArrayList;
import java.util.List;

public class TweetRepositoryImpl implements TweetRepository {

    private List<Tweet> tweetRepo = new ArrayList<>();

    @Override
    public void save(Tweet tweet) {
        tweetRepo.add(tweet);
    }

    @Override
    @Benchmark(value = true)
    public List<Tweet> findAll() {
        return new ArrayList<>(tweetRepo);
    }
}
