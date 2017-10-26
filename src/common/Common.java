/**
 * Copyright(C) 2017 Luvina
 * Common.java, 16/10/2017 Đinh Anh Tú
 */
package common;

import properties.ConfigProperties;

/**
 * class chứa các hàm common 
 * @author Anh Tú
 *
 */
public class Common {
	
	/**
	 * phương thức lấy số lượng hiển thị bản ghi trên 1 trang
	 * @return số lượng bản ghi records cần lấy
	 */
	public static int getLimit() {
		ConfigProperties configProperties = new ConfigProperties(); 
		int limit = Integer.parseInt(configProperties.getConfigProperties("limit"));
		return limit;
	}
	
	/**
	 * phương thức tính tổng số trang để hiển thị màn hình
	 * @param totalUser
	 * @param limit
	 * @return tổng số trang
	 */
	public static int getTotalPage(int totalUser, int limit) {
		return totalUser/limit;
	}
}
