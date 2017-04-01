package twitter.infrastructure;

import twitter.Tweet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;
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
            callInitMethod(bean);
            callAnnotatedBean(bean);

            bean = createProxy(bean);
            beanStore.put(beanName, bean);
        }
        return bean;
    }

    private <T> T createProxy(T bean) {

        /*T newBean = (T)Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    T result = null;
                    String methodName = method.getName();
                    Method[] metods = bean.getClass().getMethods();
                    for (Method m : metods) {
                        if (m.getName().equals(methodName) && m.isAnnotationPresent(Benchmark.class) && m.getAnnotation(Benchmark.class).value()) {
                            System.out.println("Benchmark  annotation found and it is 'true'");
                            long start = System.nanoTime();
                            result = (T) method.invoke(bean, args);
                            long difference = System.nanoTime() - start;
                            System.out.format("Method %s executed during %f seconds.\n", methodName, difference / 1.0E+9);
                            break;
                        }
                    }
                    return result;
                });*/

        T newBean = (T)Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                (proxy, method, args) -> benchmarkMethod(bean, method, args)
                );
        return newBean;
    }

    private <T> T benchmarkMethod(T bean, Method method, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        T result;
        String methodName = method.getName();
        System.out.println(methodName + "()");
        Class<?>[] argsTypes = getClassesArray(args);
        Method checkedMethod = bean.getClass().getMethod(methodName, argsTypes);

        if (checkedMethod.isAnnotationPresent(Benchmark.class) && checkedMethod.getAnnotation(Benchmark.class).value()) {
            System.out.println("'Benchmark' annotation found and it is 'true'");
            long start = System.nanoTime();
            result = (T) method.invoke(bean, args);
            long difference = System.nanoTime() - start;
            System.out.format("Method %s executed during %f seconds.\n", methodName, difference / 1.0E+9);
        } else {
            result = (T) method.invoke(bean, args);
        }
        return result;
    }

    private Class<?>[] getClassesArray(Object[] args) {
        Class<?>[] argsTypes = null;
        if (args != null) {
            argsTypes = Arrays.stream(args)
                    .map(e -> e.getClass())
                    .toArray(e -> new Class<?>[args.length]);
        }
        return argsTypes;
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
