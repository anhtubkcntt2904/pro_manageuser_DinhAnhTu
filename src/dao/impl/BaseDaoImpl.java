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
	protected static Connection conn = null;

	/**
	 * @see dao.BaseDao#connectDB()
	 */
	@Override
	public boolean connectDB() {
		// thông tin kết nối database
		String driver = DatabaseProperties.getDBProperties(Constant.DRIVER_CONST);
		String url = DatabaseProperties.getDBProperties(Constant.URL_CONST);
		String user = DatabaseProperties.getDBProperties(Constant.USER_CONST);
		String pass = DatabaseProperties.getDBProperties(Constant.PASS_CONST);
		try {
			// kết nối database
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			if (conn != null) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see dao.BaseDao#closeDB(Connection)
	 */
	@Override
	public void closeDB() {
		try {
			// kiểm tra connection truyền vào có null không
			if (conn != null) {
				// nếu ko null thì đóng connection
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see dao.BaseDao#setAutoCommit()
	 */
	@Override
	public void setAutoCommit(boolean valueCommit) {
		if (conn != null) {
			try {
				conn.setAutoCommit(valueCommit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see dao.BaseDao#rollBack()
	 */
	@Override
	public void rollBack() {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see dao.BaseDao#commit()
	 */
	@Override
	public void commit() {
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
