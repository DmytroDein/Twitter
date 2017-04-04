package twitter.infrastructure;


public interface Config {

    Class<?> getImpl(String name);
    //String[] getImplParameters(String name);
}
