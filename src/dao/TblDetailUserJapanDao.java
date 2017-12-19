/**
 * Copyright(C) 2017 Luvina
 * TblDetailUserJapanDao.java , Nov 7, 2017, Anh Tu
 */
package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import entity.TblDetailUserJapan;

/**
 * interface chứa các thao tác với bảng TblDetailUserJapanDao
 * 
 * @author Anh Tú
 *
 */
public interface TblDetailUserJapanDao {

	/**
	 * phương thức insert detail user japan
	 * 
	 * @param tblDetailUserJapan
	 * @return true hoặc false
	 * @throws SQLException
	 */
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan)
			throws SQLException;

	/**
	 * phương thức update detail user japan
	 * 
	 * @param tblDetailUserJapan
	 * @param connection
	 * @return true hoặc false
	 */
	public boolean updateDetailUserJapan(TblDetailUserJapan tblDetailUserJapan)
			throws SQLException;

	/**
	 * Lấy thông tin detail user japan bằng user id
	 * 
	 * @param userId
	 * @return thông tin detail user japan
	 */
	public TblDetailUserJapan getDetailUserJapanById(int userId);

	/**
	 * xóa detail user japan theo code level
	 * 
	 * @param code_level
	 * @return true hoặc false
	 * @throws SQLException
	 */
	public boolean deleteDetailUserJapan(int userId) throws SQLException;
	
	/**
	 * phương thức kiểm tra user có tồn tại detail user japan không
	 * @param userid
	 * @return true hoặc false
	 */
	public boolean isExistDetailUserJapan(int userid);

}
