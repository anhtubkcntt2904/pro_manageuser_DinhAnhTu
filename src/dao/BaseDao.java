/**
 * Copyright(C) 2017 Luvina
 * BaseDao.java, 16/10/2017 Đinh Anh Tú
 */
package dao;

import java.sql.Connection;

/**
 * Interface chứa các phương thức kết nối và đóng kết nối tới database
 * 
 * @author AnhTu
 *
 */
public interface BaseDao {

	/**
	 * Phương thức kết nối đến DB
	 * 
	 * @return true hoặc false
	 */
	public boolean connectDB();

	/**
	 * Phương thức đóng kết nối
	 */
	public void closeDB();

	/**
	 * phương thức setAutoCommit về false
	 * 
	 * @param valueCommit
	 */
	public void setAutoCommit(boolean valueCommit);

	/**
	 * phương thức roll back sql
	 */
	public void rollBack();

	/**
	 * phương thức commit sql
	 */
	public void commit();
}