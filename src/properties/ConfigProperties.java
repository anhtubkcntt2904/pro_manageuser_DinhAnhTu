/**
 * Copyright(C) 2017 Luvina
 * ConfigProperties.java, 16/10/2017 Đinh Anh Tú
 */
package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * class đọc các thông tin cấu hình
 * @author Anh Tú
 *
 */
public class ConfigProperties {
	//tạo map lưu các key và value
			static private Map<String, String> dataConfig = new HashMap<String, String>();
			//tạo set lưu các key
			static Set configStates;
			
			 static {
				 	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					// Lấy file properties
					InputStream input = classLoader.getResourceAsStream("database.properties");
			        Properties prop = new Properties();
			        try {
			            prop.load(input);
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			        configStates = prop.keySet();
			        Iterator itr = configStates.iterator();
			        while(itr.hasNext()) { //xét từng key trong set
			        	String key = (String) itr.next();
			        	//thêm key và value tương ứng vào trong map data
			        	dataConfig.put(key, prop.getProperty(key));
			        }	        
			    }
			 
			 /**
			  * phương thức lấy ra value tương ứng với key 
			  * @param key
			  * @return String value
			  */
			public String getConfigProperties(String key) {
				String string = "";
		        if (dataConfig.containsKey(key)) { // nếu  map có chứa key truyền vào
		            string = dataConfig.get(key); // lấy value theo key đó
		        }
		        return string;
			}
}
