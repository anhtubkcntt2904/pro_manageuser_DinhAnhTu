/**
 * Copyright(C) 2017 Luvina
 * BaseDao.java, 16/10/2017 Đinh Anh Tú
 */
package dao;

import java.sql.Connection;

/**
 * Interface chứa các phương thức kết nối và đóng kết nối tới database
 * @author AnhTu
 *
 */
public interface BaseDao {
	
	/**
	 * Phương thức kết nối đến DB
	 * @return Connection
	 */
	public void connectDB();
	
	/**
	 * Phương thức đóng kết nối
	 * @param connection
	 */
	public void closeDB(Connection connection);
}