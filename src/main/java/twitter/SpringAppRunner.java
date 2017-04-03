package twitter;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import twitter.repository.TweetRepository;

import java.util.Arrays;

public class SpringAppRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"spring.xml"}, true);
        TweetRepository repository = (TweetRepository) ctx.getBean("TweeterRepository");

        ConfigurableApplicationContext ctx2 = new ClassPathXmlApplicationContext(new String[]{"service.xml"}, ctx);

        User user = new User("Douglas");
        repository.save(new Tweet(user, "Some text №1!"));
        repository.save(new Tweet(user, "Some text №2!"));
        repository.save(new Tweet(user, "Some text №3!"));
        repository.findAll().forEach(System.out::println);

        System.out.println("-------------CTX-----------");
        System.out.println(Arrays.toString(ctx.getBeanDefinitionNames()));

        System.out.println("-------------CTX2-----------");
        System.out.println(Arrays.toString(ctx2.getBeanDefinitionNames()));
        

        BeanDefinition beanDefinition = ctx.getBeanFactory().getBeanDefinition("TweeterRepository");
        System.out.println("----------- ctx ------------");
        System.out.println(beanDefinition);

        beanDefinition.setScope("prototype");
        //ctx.refresh();
        beanDefinition = ctx.getBeanFactory().getBeanDefinition("TweeterRepository");
        System.out.println(beanDefinition);

        ctx.close();

    }
}
