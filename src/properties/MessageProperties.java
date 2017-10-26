/**
 * Copyright(C) 2017 Luvina
 * MessageProperties.java, 16/10/2017 Đinh Anh Tú
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
 * class chứa phương thức đọc file message properties 
 * @author AnhTu
 *
 */
public class MessageProperties {
	//tạo map lưu các key và value
		static private Map<String, String> dataMessage = new HashMap<String, String>();
		//tạo set lưu các key
		static Set messageStates;
		
		 static {
			 	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				// Lấy file properties
				InputStream input = classLoader.getResourceAsStream("message.properties");
		        Properties prop = new Properties();
		        try {
		            prop.load(input);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        messageStates = prop.keySet();
		        Iterator itr = messageStates.iterator();
		        while(itr.hasNext()) { //xét từng key trong set
		        	String key = (String) itr.next();
		        	//thêm key và value tương ứng vào trong map data
		        	dataMessage.put(key, prop.getProperty(key));
		        }	        
		    }
		 
		 /**
		  * phương thức lấy ra value tương ứng với key 
		  * @param key
		  * @return String value
		  */
		public String getMessageProperties(String key) {
			String string = "";
	        if (dataMessage.containsKey(key)) { // nếu  map có chứa key truyền vào
	            string = dataMessage.get(key); // lấy value theo key đó
	        }
	        return string;
		}
}
