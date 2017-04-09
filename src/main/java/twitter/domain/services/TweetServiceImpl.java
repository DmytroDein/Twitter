package twitter.domain.services;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import twitter.domain.Tweet;
import twitter.domain.User;
import twitter.infrastructure.annotations.Benchmark;
import twitter.domain.repository.TweetRepository;

import java.util.List;

public class TweetServiceImpl implements TweetService, ApplicationContextAware{

    @Autowired
    private final TweetRepository tweetRepository;
    ApplicationContext serviceContext;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.serviceContext = applicationContext;
    }

    public Tweet createTweet(String tweetText, User user){
        Tweet tweet = createNewTweet();
        tweet.setUser(user);
        tweet.setText(tweetText);
        return tweet;
    }

    private Tweet createNewTweet() {
//        throw new UnsupportedOperationException();
        return (Tweet) serviceContext.getBean("tweet");
    }

    @Lookup("Tweet")
    public Tweet creatEmptyTweet(){
        return null;
    }
}
