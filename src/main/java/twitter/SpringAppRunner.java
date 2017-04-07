package twitter;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import twitter.infrastructure.Temp;
import twitter.repository.TweetRepository;
import twitter.services.TweetService;

import java.util.Arrays;

public class SpringAppRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring.xml"}, true);
        TweetRepository repository = (TweetRepository) context.getBean("TweeterRepository");

        ConfigurableApplicationContext childContext = new ClassPathXmlApplicationContext(new String[]{"service.xml"}, context);

        /*User user = new User("Douglas");
        repository.save(new Tweet(user, "Some text №1!"));
        repository.save(new Tweet(user, "Some text №2!"));
        repository.save(new Tweet(user, "Some text №3!"));
        repository.findAll().forEach(System.out::println);*/

        TweetService tweetService = (TweetService)childContext.getBean("TweetService");
//        User user = new User("Douglas");
        User user = (User) childContext.getBean("User");
        tweetService.addTweet(new Tweet(user, "Some text №1!"));
        tweetService.addTweet(new Tweet(user, "Some text №2!"));
        tweetService.addTweet(new Tweet(user, "Some text №3!"));
        tweetService.findAll().forEach(System.out::println);

        System.out.println("------------- context -----------");
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));

        System.out.println("------------- childContext -----------");
        System.out.println(Arrays.toString(childContext.getBeanDefinitionNames()));

        /*BeanDefinition beanDefinition = context.getBeanFactory().getBeanDefinition("TweeterRepository");
        System.out.println("----------- context ------------");
        System.out.println(beanDefinition);

        beanDefinition.setScope("prototype");
        //ctx.refresh();
        beanDefinition = context.getBeanFactory().getBeanDefinition("TweeterRepository");
        System.out.println(beanDefinition);*/

        /*Temp temp = (Temp)ctx2.getBean("temp");

        System.out.println("-------------CTX2-----------");
        System.out.println(Arrays.toString(ctx2.getBeanDefinitionNames()));

        System.out.println(ctx2.getBeanFactory().getBeanDefinition("tempable"));*/

        Tweet tweet = tweetService.creatEmptyTweet();

        context.close();

    }
}
