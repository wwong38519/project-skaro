package project.skaro.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class PropertyUtils {

    public static synchronized PropertyUtils getInstance() {
        if (instance == null) {
            instance = new PropertyUtils();
            instance.initResources();
        }
        return instance;
    }

    private PropertyUtils() {}

    private static PropertyUtils instance = null;
    private static Map<String, Properties> propertiesMap = null;
    private static final String APPLICATION = "applications.properties";
    
    public Object getAll(String key) {
        Map<String, String> keyValueMap = new TreeMap<String, String>();
        for (String collection : propertiesMap.keySet()) {
            String value = get(collection, key);
            if (StringUtils.isNotBlank(value))
                keyValueMap.put(collection, value); 
        }
        return keyValueMap.size() > 0 ? keyValueMap : null;
    }
    
    public String get(String key) {
        for (String collection : propertiesMap.keySet()) {
            return get(collection, key);
        }
        return null;
    }
    
    public String get(String collection, String key) {
        Properties prop = propertiesMap.get(collection);
        return prop == null ? null : prop.getProperty(key, null);
    }
    
    private void initResources() {
        if (propertiesMap == null) propertiesMap = new TreeMap<String, Properties>();
        propertiesMap.put(APPLICATION, loadProperties(APPLICATION));
    }
    
    public Properties loadProperties(String collection) {
        Properties prop = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(collection);
            prop.load(inputStream);
        }
        catch (IOException e) {
//            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
//                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

}
