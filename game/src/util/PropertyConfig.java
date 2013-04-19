package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertyConfig {
	private static final  Logger logger = LoggerFactory.getLogger( PropertyConfig.class );
	protected  String configName;
	private  Properties p = null;
	
//	public String getConfigName() {
//		return configName;
//	}
	

	protected   PropertyConfig(String configName) {
		p = new Properties();
		//String configPath = ConfigFileUtil.getPath( PropertyConfig.class,configName );
		
			try {
				InputStream input = new FileInputStream( configName );
				p.load( input);
				this.configName = configName;
			} catch (FileNotFoundException e) {
				logger.error("",e);
				this.configName = null;
			} catch (IOException e) {
				logger.error("",e);
				this.configName = null;
			}			
		
	}
	
	/**
	 * 根据配置文件中的键，返回其字符串类型的值
	 * 
	 * @param key the key
	 * 
	 * @return the value
	 */
	public  String getValue(String key) {
		String value = p.getProperty(key);
		return value;
	}
	
	/**
	 * 根据配置文件中的键，返回其整数类型的值，如果不能转化为整数，返回0.
	 * 
	 * @param key the key
	 * 
	 * @return the int
	 */
	public  int getInt(String key) {
		String str = getValue(key);
		int valueInt = 0;
		if (str != null) {
			try {
				valueInt = Integer.parseInt(str);
			} catch (Exception e) {
				logger.debug( e.toString() );
			}
		}
		logger.debug(key + "->" + valueInt);
		return valueInt;
	}
	
	public  void traceInfo(String key) {
		logger.info(key + "->" + p.getProperty(key));
	}

	public Properties getProperties() {
		return p;
	}
	
	
}

