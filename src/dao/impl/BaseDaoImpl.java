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
 * @author AnhTu
 *
 */
public class BaseDaoImpl implements BaseDao{
	@Override
	public Connection connectDB() {
		Connection conn = null;
		DatabaseProperties dbProp = new DatabaseProperties();
		String driver = dbProp.getDBProperties(Constant.DRIVER_CONST);
		String url = dbProp.getDBProperties(Constant.URL_CONST);
		String user = dbProp.getDBProperties(Constant.USER_CONST);
		String pass = dbProp.getDBProperties(Constant.PASS_CONST);
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public void closeDB(Connection connection) {
		try {
			connection.close();
		} catch (Exception e) {
			//System.out.println("Close connect error :" + e.getMessage());
			
		}
	}
}
