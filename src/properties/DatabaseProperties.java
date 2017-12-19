/**
 * Copyright(C) 2017 Luvina
 * DatabaseProperties.java, 16/10/2017 Đinh Anh Tú
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
 * class để đọc các thông tin cấu hình cho database
 * @author Anh Tú
 *
 */
public class DatabaseProperties {
	//tạo map lưu các key và value
		static private Map<String, String> dataDB = new HashMap<String, String>();
		//tạo set lưu các key
		static Set dbStates;
		
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
		        dbStates = prop.keySet();
		        Iterator itr = dbStates.iterator();
		        while(itr.hasNext()) { //xét từng key trong set
		        	String key = (String) itr.next();
		        	//thêm key và value tương ứng vào trong map data
		        	dataDB.put(key, prop.getProperty(key));
		        }	        
		    }
		 
		 /**
		  * phương thức lấy ra value tương ứng với key 
		  * @param key
		  * @return String value
		  */
		public static String getDBProperties(String key) {
			String string = "";
	        if (dataDB.containsKey(key)) { // nếu  map có chứa key truyền vào
	            string = dataDB.get(key); // lấy value theo key đó
	        }
	        return string;
		}
}
