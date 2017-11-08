/**
 * Copyright(C) 2017 Luvina
 * TblDetailUserJapanDaoImpl.java , Nov 7, 2017, Anh Tu
 */
package dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import dao.TblDetailUserJapanDao;
import entity.TblDetailUserJapan;

/**
 * @author LA-AM
 *
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	@Override
	public Boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException {
		StringBuffer sql = new StringBuffer();
		Boolean isSuccess = false;
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
			ps.setInt(5, Integer.parseInt(tblDetailUserJapan.getTotal()));

			isSuccess = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw e;
		}
		return isSuccess;
	}

}
