package twitter;


import twitter.infrastructure.*;
import twitter.repository.TweetRepository;
import twitter.repository.TweetRepositoryImpl;

public class AppRunner {
    public static void main(String[] args) throws Exception {
       /* TimeLine timeLine = new TimeLine();
        User user = new User("Douglas");
        timeLine.put(new Tweet(user, "Some text!"));*/

        /*TweetRepository tweetRepository = new TweetRepositoryImpl();
        User user = new User("Douglas");
        tweetRepository.save(new Tweet(user, "Some text №1!"));
        tweetRepository.save(new Tweet(user, "Some text №2!"));

        tweetRepository.findAll().forEach(System.out::println);*/
//---------------------------------------------------------------------------

        /*Config config = new JavaConfig();
        InitialContext initialContext = new InitialContext(config);
        TweetRepository repository = (TweetRepository) initialContext.lookup("TweetRepo");
        User user = new User("Douglas");
        repository.save(new Tweet(user, "Some text №1!"));
        repository.save(new Tweet(user, "Some text №2!"));
        repository.findAll().forEach(System.out::println);*/
//---------------------------------------------------------------------------
        Config config = new JavaConfig();
        Context context = new ApplicationContext(config);
        TweetRepository repository = context.getBean("TweetRepo");
        User user = new User("Douglas");
        repository.save(new Tweet(user, "Some text №1!"));
        repository.save(new Tweet(user, "Some text №2!"));
        repository.save(new Tweet(user, "Some text №3!"));
        repository.findAll().forEach(System.out::println);

        System.out.println("Done!");
    }
}