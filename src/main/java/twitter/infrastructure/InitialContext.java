package twitter.infrastructure;

/**
 * Created by Dmytro_Deinichenko on 3/30/2017.
 */
public class InitialContext {
    private Config config;

    public InitialContext(Config config) {
        this.config = config;
    }

    public Object lookup(String name) throws Exception {
        return config.getImpl(name).newInstance();
    }
}
