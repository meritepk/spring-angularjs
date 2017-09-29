package pk.merite.webapp.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "webapp")
public class WebAppProps {

    private Map<String, String> configs = new HashMap<>();

    public void setConfigs(Map<String, String> config) {
        this.configs = config;
    }

    public Map<String, String> getConfigs() {
        return configs;
    }

    public String get(String name, String defaultValue) {
        if (configs.containsKey(name)) {
            return get(name);
        }
        return defaultValue;
    }

    public int intValue(String name, int defaultValue) {
        if (configs.containsKey(name)) {
            return Integer.parseInt(get(name));
        }
        return defaultValue;
    }

    public String get(String name) {
        return configs.get(name);
    }

}
