/**
 * Copyright(C) 2017 Luvina
 * AdminProperties.java, 16/10/2017 Đinh Anh Tú
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
 * class đọc file properties đăng nhập của admin
 * @author AnhTu
 *
 */
public class AdminProperties {
	//tạo map lưu các key và value
	static private Map<String, String> data = new HashMap<String, String>();
	//tạo set lưu các key
	static Set adminStates;
	
	 static {
		 	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			// Lấy file properties
			InputStream input = classLoader.getResourceAsStream("admin.properties");
	        Properties prop = new Properties();
	        try {
	            prop.load(input);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        adminStates = prop.keySet();
	        Iterator itr = adminStates.iterator();
	        while(itr.hasNext()) { //xét từng key trong set
	        	String key = (String) itr.next();
	        	//thêm key và value tương ứng vào trong map data
	        	data.put(key, prop.getProperty(key));
	        }	        
	    }
	 
	 /**
	  * phương thức lấy ra value tương ứng với key 
	  * @param key
	  * @return String value
	  */
	public static String getAdminProperties(String key) {
		String string = "";
        if (data.containsKey(key)) { // nếu  map có chứa key truyền vào
            string = data.get(key); // lấy value theo key đó
        }
        return string;
	}
}
