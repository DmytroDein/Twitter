package twitter.domain.repository;


import org.springframework.stereotype.Repository;
import twitter.domain.Tweet;
import twitter.infrastructure.annotations.Benchmark;
import twitter.infrastructure.annotations.PostConstructAnnotation;

import java.util.ArrayList;
import java.util.List;

@Repository("TweeterRepository")
public class TweetRepositoryImpl implements TweetRepository {

    private List<Tweet> tweetRepo = new ArrayList<>();

    @Override
    @Benchmark
    public void save(Tweet tweet) {
        tweetRepo.add(tweet);
    }

    @Override
    @Benchmark(value = true)
    public List<Tweet> findAll() {
        return new ArrayList<>(tweetRepo);
    }

    public void init(){
        System.out.println("TweetRepositoryImpl: init() called.");
    }

    @PostConstructAnnotation
    public void postConstructMethod(){
        System.out.println("postConstructMethod() called over annotation '@PostConstructAnnotation'");
    }

}
