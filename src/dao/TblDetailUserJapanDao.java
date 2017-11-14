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
	 *  @throws SQLException
	 */
	public Boolean updateDetailUserJpan(TblDetailUserJapan tblDetailUserJapan) throws SQLException;
}
