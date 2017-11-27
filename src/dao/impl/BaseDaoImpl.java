/**
 * Copyright(C) 2017 Luvina
 * BaseDaoImpl.java, 16/10/2017 Đinh Anh Tú
 */
package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import common.Constant;
import dao.BaseDao;
import properties.DatabaseProperties;

/**
 * class implements các phương thức của interface BaseDao
 * 
 * @author AnhTu
 *
 */
public class BaseDaoImpl implements BaseDao {
	protected Connection conn = null;

	@Override
	public void connectDB() {
		DatabaseProperties dbProp = new DatabaseProperties();
		//thông tin kết nối database
		String driver = dbProp.getDBProperties(Constant.DRIVER_CONST);
		String url = dbProp.getDBProperties(Constant.URL_CONST);
		String user = dbProp.getDBProperties(Constant.USER_CONST);
		String pass = dbProp.getDBProperties(Constant.PASS_CONST);
		try {
			//kết nối database
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//return conn;
	}

	@Override
	public void closeDB(Connection connection) {
		try {
			//kiểm tra connection truyền vào có null không
			if (connection != null) {
				//nếu ko null thì đóng connection
				connection.close();
				//nếu null thì không thực hiện gì cả
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
