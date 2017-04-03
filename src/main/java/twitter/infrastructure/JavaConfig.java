package twitter.infrastructure;

import twitter.User;
import twitter.repository.TweetRepositoryImpl;
import twitter.services.TweetServiceImpl;

import java.util.HashMap;
import java.util.Map;


public class JavaConfig implements Config {

    private final Map<String, Class<?>> classes = new HashMap<>();
    private final Map<String, String[]> beansConstructorParameters = new HashMap<>();

    {
        classes.put("TweetRepo", TweetRepositoryImpl.class);
        classes.put("User", User.class);
        beansConstructorParameters.put("User", new String[]{"String"});
        classes.put("TweetService", TweetServiceImpl.class);
        beansConstructorParameters.put("TweetService", new String[]{"TweetServiceImpl"});
    }

    @Override
    public Class<?> getImpl(String name) {
        return classes.get(name);
    }

    @Override
    public String[] getImplParameters(String name) {
        return beansConstructorParameters.get(name);
    }
}
