package twitter.domain;


import java.util.ArrayList;
import java.util.List;

public class TimeLine {

    private final List<Tweet> tweets = new ArrayList<>();

    public void put(Tweet tweet){
        tweets.add(tweet);
    }

    public Iterable<Tweet> tweets(){
        return new ArrayList<>(tweets);
    }

}
