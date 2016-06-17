package utils;

import java.io.IOException;
import java.util.Properties;
/**
 * 
 * @author DLHT
 * 解析配置文件信息
 *
 */
public class PropertyUtils {
    private Properties pro = new Properties();
    private static PropertyUtils propertyUtils = new PropertyUtils();

    private PropertyUtils() {
        try {
            pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mongodb.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
        }
    }

    public static PropertyUtils getInstance(){
           return propertyUtils;
    }

    public static  String getValue(String key) {
            return (String)getInstance(). pro.get(key);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getValue("port"));
    }

}
