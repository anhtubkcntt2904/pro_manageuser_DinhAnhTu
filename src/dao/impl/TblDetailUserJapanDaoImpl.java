/**
 * Copyright(C) 2017 Luvina
 * TblDetailUserJapanDaoImpl.java , Nov 7, 2017, Anh Tu
 */
package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import dao.TblDetailUserJapanDao;
import entity.TblDetailUserJapan;

/**
 * class implements các phương thức cảu class TblDetailUserJapanDao
 * @author Anh Tu
 *
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	@Override
	public Boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException {
		StringBuffer sql = new StringBuffer();
		Boolean isSuccess = false;
		int total = 0;
		sql.append("insert into tbl_detail_user_japan");
		sql.append("(user_id,code_level,start_date,end_date,total) ");
		sql.append("values(?,?,?,?,?) ");
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, tblDetailUserJapan.getUserId());
			ps.setString(2, tblDetailUserJapan.getCodeLevel());
			ps.setDate(3, java.sql.Date.valueOf(dt1.format(tblDetailUserJapan.getStartDate())));
			ps.setDate(4, java.sql.Date.valueOf(dt1.format(tblDetailUserJapan.getEndDate())));
			if (tblDetailUserJapan.getTotal() != "" || tblDetailUserJapan.getTotal() != null) {
				ps.setInt(5, Integer.parseInt(tblDetailUserJapan.getTotal()));
			} else {
				ps.setInt(5, total);
			}

			isSuccess = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw e;
		}
		return isSuccess;
	}

	@Override
	public Boolean updateDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException {
		StringBuffer sql = new StringBuffer();
		Boolean isSuccess = false;
		int total = 0;
		sql.append("update tbl_detail_user_japan ");
		sql.append("set code_level = ?,start_date = ?,end_date = ?,total = ? ");
		sql.append("where user_id = ?");
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			int i = 0;
			ps.setString(++i, tblDetailUserJapan.getCodeLevel());
			ps.setDate(++i, java.sql.Date.valueOf(dt1.format(tblDetailUserJapan.getStartDate())));
			ps.setDate(++i, java.sql.Date.valueOf(dt1.format(tblDetailUserJapan.getEndDate())));
			if (tblDetailUserJapan.getTotal() != "" || tblDetailUserJapan.getTotal() != null) {
				ps.setInt(++i, Integer.parseInt(tblDetailUserJapan.getTotal()));
			} else {
				ps.setInt(++i, total);
			}
			System.out.println("dao update detail user id : " + tblDetailUserJapan.getUserId());
			if (tblDetailUserJapan.getUserId() != 0) {
				ps.setInt(++i, tblDetailUserJapan.getUserId());
			}
			isSuccess = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw e;
		}
		return isSuccess;
	}

	@Override
	public TblDetailUserJapan getDetailUserJapanById(int userId) {
		StringBuffer sql = new StringBuffer();
		TblDetailUserJapan tblUserJapan = new TblDetailUserJapan();
		sql.append("select code_level from tbl_detail_user_japan ");
		sql.append("where user_id = ?");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			int i = 0;
			ps.setInt(++i, userId);

			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("dao code level : " + rs.getString("code_level"));
				tblUserJapan.setCodeLevel(rs.getString("code_level"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// throw e;
		}
		return tblUserJapan;
	}

	@Override
	public Boolean deleteDetailUserJapan(int userId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		Boolean isSuccess = false;
		sql.append("delete from tbl_detail_user_japan ");
		sql.append("where user_id = ?");
		PreparedStatement ps = null;
		try {
			//System.out.println(code_level);
			System.out.println(userId);
			ps = conn.prepareStatement(sql.toString());
			int i = 0;
			//ps.setString(++i, code_level);
			if (userId != 0) {
				ps.setInt(++i, userId);
			}
			isSuccess = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw e;
		}
		return isSuccess;
	}

}
