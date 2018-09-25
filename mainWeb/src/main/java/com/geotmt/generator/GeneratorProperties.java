package com.geotmt.generator;

import com.geotmt.generator.util.PropertiesHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;



/**
 * 用于装载generator.properties文件，
 * <br/>sgenerator.properties中的配置信息可以在全局使用
 * 
 * @author badQiu
 */
@SuppressWarnings({"rawtypes"})
public class GeneratorProperties {

	/**
	 * 说明：获取配置文件的名称
	 */
	static final String PROPERTIES_FILE_NAME = "generator.properties";
	
	/**
	 * 说明：Properties配置文件操作类
	 */
	static PropertiesHelper props;
	private GeneratorProperties(){}//空构造函数
	
	/**
	 * 说明：加载配置文件
	 */
	private static void loadProperties() {
		try {
			System.out.println("加载配置文件 [generator.properties] from classpath");
			props = new PropertiesHelper(loadAllPropertiesByClassLoader(PROPERTIES_FILE_NAME));
			
			String basepackage = getRequiredProperty("basepackage");
			String basepackage_dir = basepackage.replace('.', '/');
			props.setProperty("basepackage_dir", basepackage_dir);
			
			for(Iterator it = props.entrySet().iterator();it.hasNext();) {
				Map.Entry entry = (Map.Entry)it.next();
				System.out.println("[Property] "+entry.getKey()+"="+entry.getValue());
			}
			
			System.out.println();
			
		}catch(IOException e) {
			throw new RuntimeException("加载 Properties配置文件 异常！",e);
		}
	}
	
	public static Properties getProperties() {
		return getHelper().getProperties();
	}
	
	private static PropertiesHelper getHelper() {
		if(props == null)
			loadProperties();
		return props;
	}
	
	public static String getProperty(String key, String defaultValue) {
		return getHelper().getProperty(key, defaultValue);
	}
	
	public static String getProperty(String key) {
		return getHelper().getProperty(key);
	}
	
	public static String getRequiredProperty(String key) {
		return getHelper().getRequiredProperty(key);
	}
	
	public static int getRequiredInt(String key) {
		return getHelper().getRequiredInt(key);
	}
	
	public static boolean getRequiredBoolean(String key) {
		return getHelper().getRequiredBoolean(key);
	}
	
	public static String getNullIfBlank(String key) {
		return getHelper().getNullIfBlank(key);
	}
	
	public static void setProperty(String key,String value) {
		getHelper().setProperty(key, value);
	}
	
	public static void setProperties(Properties v) {
		props = new PropertiesHelper(v);
	}

	/**
	 * 说明：加载所有配置文件
	 * @param resourceName
	 * @return
	 * @throws IOException
	 */
	public static Properties loadAllPropertiesByClassLoader(String resourceName) throws IOException {
		Properties properties = new Properties();
		Enumeration urls = GeneratorProperties.class.getClassLoader().getResources(resourceName);
		while (urls.hasMoreElements()) {
			URL url = (URL) urls.nextElement();
			InputStream input = null;
			try {
				URLConnection con = url.openConnection();
				con.setUseCaches(false);
				input = con.getInputStream();
				properties.load(input);
			}
			finally {
				if (input != null) {
					input.close();
				}
			}
		}
		return properties;
	}
}
