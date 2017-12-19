/**
 * Copyright(C) 2017 Luvina
 * TblDetailUserJapanDaoImpl.java , Nov 7, 2017, Anh Tu
 */
package dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import dao.TblDetailUserJapanDao;
import entity.TblDetailUserJapan;

/**
 * class implements các phương thức cảu class TblDetailUserJapanDao
 * 
 * @author Anh Tu
 *
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	/**
	 * @see dao.TblDetailUserJapanDao#insertDetailUserJapan(TblDetailUserJapan,Connection)
	 */
	@Override
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan)
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		PreparedStatement ps = null;
		// biến set param cho prepared statement
		int index = 0;
		boolean isSuccess = false;
		sql.append("insert into tbl_detail_user_japan");
		sql.append("(user_id,code_level,start_date,end_date,total) ");
		sql.append("values(?,?,?,?,?) ");
		try {
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString());
				ps.setInt(++index, tblDetailUserJapan.getUserId());
				ps.setString(++index, tblDetailUserJapan.getCodeLevel());
				ps.setDate(++index, java.sql.Date.valueOf(dt1.format(tblDetailUserJapan.getStartDate())));
				ps.setDate(++index, java.sql.Date.valueOf(dt1.format(tblDetailUserJapan.getEndDate())));
				if (!"".equals(tblDetailUserJapan.getTotal()) || tblDetailUserJapan.getTotal() != null) {
					ps.setInt(++index, Integer.parseInt(tblDetailUserJapan.getTotal()));
				} else {
					ps.setInt(++index, 0);
				}
				// update thành công thì biến isSuccess bằng true
				isSuccess = ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return isSuccess;
	}

	/**
	 * @see dao.TblDetailUserJapanDao#updateDetailUserJapan(TblDetailUserJapan,Connection)
	 */
	@Override
	public boolean updateDetailUserJapan(TblDetailUserJapan tblDetailUserJapan)
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		boolean isSuccess = false;	
		// biến set giá trị cho total của detail user japan
		int total = 0;
		// biến set param cho prepared statement
		int i = 0;
		sql.append("update tbl_detail_user_japan ");
		sql.append("set code_level = ?,start_date = ?,end_date = ?,total = ? ");
		sql.append("where user_id = ?");
		try {
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString());
				ps.setString(++i, tblDetailUserJapan.getCodeLevel());
				ps.setDate(++i, java.sql.Date.valueOf(dt1.format(tblDetailUserJapan.getStartDate())));
				ps.setDate(++i, java.sql.Date.valueOf(dt1.format(tblDetailUserJapan.getEndDate())));
				if (tblDetailUserJapan.getTotal() != "" || tblDetailUserJapan.getTotal() != null) {
					ps.setInt(++i, Integer.parseInt(tblDetailUserJapan.getTotal()));
				} else {
					ps.setInt(++i, total);
				}
				if (tblDetailUserJapan.getUserId() != 0) {
					ps.setInt(++i, tblDetailUserJapan.getUserId());
				}
				isSuccess = ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return isSuccess;
	}

	/**
	 * @see dao.TblDetailUserJapanDao#getDetailUserJapanById(int)
	 */
	@Override
	public TblDetailUserJapan getDetailUserJapanById(int userId) {
		TblDetailUserJapan tblUserJapan = new TblDetailUserJapan();
		try {
			connectDB();
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				PreparedStatement ps = null;
				ResultSet rs = null;
				sql.append("select code_level from tbl_detail_user_japan ");
				sql.append("where user_id = ?");
				ps = conn.prepareStatement(sql.toString());
				int i = 0;
				ps.setInt(++i, userId);

				rs = ps.executeQuery();
				if (rs.next()) {
					tblUserJapan.setCodeLevel(rs.getString("code_level"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tblUserJapan;
	}

	/**
	 * @see dao.TblDetailUserJapanDao#deleteDetailUserJapan(int,Connection)
	 */
	@Override
	public boolean deleteDetailUserJapan(int userId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		sql.append("delete from tbl_detail_user_japan ");
		sql.append("where user_id = ?");
		boolean isSuccess = false;
		try {
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString());
				int i = 0;
				if (userId != 0) {
					ps.setInt(++i, userId);
				}
				isSuccess = ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			throw e;
		}
		return isSuccess;
	}

	@Override
	public boolean isExistDetailUserJapan(int userid) {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select * from tbl_detail_user_japan ");
		sql.append("where user_id = ?");
		try {
			//nếu kết nối db thành công
			if (connectDB()) {
				ps = conn.prepareStatement(sql.toString());
				int i = 0;
				ps.setInt(++i, userid);
				rs = ps.executeQuery();
				if (rs.next()) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return false;
	}

}
