package twitter.infrastructure;

import twitter.repository.TweetRepositoryImpl;

import java.util.HashMap;
import java.util.Map;


public class JavaConfig implements Config {

    private final Map<String, Class<?>> classes = new HashMap<>();

    {
        classes.put("TweetRepo", TweetRepositoryImpl.class);
    }

    @Override
    public Class<?> getImpl(String name) {
        return classes.get(name);
    }
}
