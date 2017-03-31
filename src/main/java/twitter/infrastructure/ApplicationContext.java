package twitter.infrastructure;

import twitter.Tweet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class ApplicationContext implements Context {

    private final Config config;
    private final HashMap<String, Object> beanStore = new HashMap<>();

    public ApplicationContext(Config config) {
        this.config = config;
    }

    @Override
    public <T> T getBean(String beanName) throws Exception {
       /* T bean = (T)( beanStore.get(beanName));
        if (bean != null) {
            Class<?> clazz = config.getImpl(beanName);
            if (clazz == null) {
                throw new RuntimeException("Bean not found");
            }
            bean = (T) clazz.newInstance();
            beanStore.put(beanName, bean);
        }*/

       /*T bean = null;
        if (beanStore.containsKey(beanName)){
            bean = (T)(beanStore.get(beanName));
        } else {
            Class<?> clazz = config.getImpl(beanName);
            if (clazz == null) {
                throw new RuntimeException("Bean not found");
            }
            bean = (T) clazz.newInstance();
            beanStore.put(beanName, bean);
        }*/

        T bean = null;
        if (beanStore.containsKey(beanName)){
            bean = (T)(beanStore.get(beanName));
        } else {
            Class<?> clazz = config.getImpl(beanName);
            if (clazz == null) {
                throw new RuntimeException("Bean not found");
            }
            bean = (T) clazz.newInstance();

            bean = createProxy(bean);

            beanStore.put(beanName, bean);
            //callInitMethod(bean);
            //callAnnotatedBean(bean);
        }

        return bean;
    }

    private <T> T createProxy(T bean) {
        T newBean = (T)Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("proxy enter for method " + method.getName());
                    System.out.println("proxy exit for method " + method.getName());
                    System.out.println(method.getName());
                    return method.invoke(bean, args);
                });


       /* T newBean = (T)Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("proxy enter for method " + method.getName());
                    System.out.println("proxy exit for method " + method.getName());
                    String metName = method.getName();
//                    System.out.println(metName);
//                    System.out.println(bean.getClass().getMethod(method.getName()));
//                    System.out.println(bean.getClass().getMethods()[1]);
                    String[] realargs = Arrays.stream(args)
                            .map(e -> e.getClass().getName())
                            .collect(Collectors.toList())
                            .toArray(new String[args.length]);
                    System.out.println("Real args: "+ realargs.toString());

//                    System.out.println(bean.getClass().getMethod(metName, null));
                    return method.invoke(bean, args);
                });*/

        return newBean;
    }

    private void callAnnotatedBean(Object bean) {
        Class<?> clazz = bean.getClass();
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(PostConstructAnnotation.class)) {
                try {
                    m.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void callInitMethod(Object bean) {
        Class<?> clazz = bean.getClass();
        Optional<Method> initMethod = Arrays.stream(clazz.getMethods())
                .filter(m -> "init".equals(m.getName()))
                .findFirst();
        initMethod.ifPresent(m -> {
            try {
                m.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
