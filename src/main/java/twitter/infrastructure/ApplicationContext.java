package twitter.infrastructure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

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
            callAnnotatedBean(bean);
        }

        return bean;
    }

    private <T> T createProxy(T bean) {
        T newBean = (T)Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });

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
        Method method;

        try {
            method = clazz.getMethod("init");
            if (method != null) {
                method.invoke(bean);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }



}
