/**
 * Copyright(C) 2017 Luvina
 * TblDetailUserJapanDao.java , Nov 7, 2017, Anh Tu
 */
package dao;

import java.sql.SQLException;

import entity.TblDetailUserJapan;

/**
 * @author LA-AM
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
	public Boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException;

	/**
	 * phương thức update detail user japan
	 * 
	 * @param tblDetailUserJapan
	 * @return true hoặc false
	 */
	public Boolean updateDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException;

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
	 */
	public Boolean deleteDetailUserJapan(int userId) throws SQLException;

}
