package twitter.infrastructure;

import twitter.User;
import twitter.repository.TweetRepositoryImpl;
import twitter.services.TweetServiceImpl;

import java.util.HashMap;
import java.util.Map;


public class JavaConfig implements Config {

    private final Map<String, Class<?>> classes = new HashMap<>();

    {
        classes.put("tweetRepository", TweetRepositoryImpl.class);
        classes.put("tweetService", TweetServiceImpl.class);
//        classes.put("user", User.class);
    }

    @Override
    public Class<?> getImpl(String name) {
        return classes.get(name);
    }

    /*@Override
    public String[] getImplParameters(String name) {
        return beansConstructorParameters.get(name);
    }*/
}
