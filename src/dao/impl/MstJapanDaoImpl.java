/**
 * Copyright(C) 2017 Luvina
 * MstJapanDaoImpl.java, Nov 2, 2017 Đinh Anh Tú
 */
package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.MstJapanDao;
import entity.MstGroup;
import entity.MstJapan;

/**
 * class implement các phương thức trong interface MstJapanDao
 * 
 * @author AnhTu
 *
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao{

	@Override
	public List<MstJapan> getAllMstJapan() {
		// TODO Auto-generated method stub
		MstJapan mstJapan = new MstJapan();
		List<MstJapan> lstMstJapan = new ArrayList<>();
		String sql = "select j.code_level, j.name_level from mst_japan j order by j.code_level desc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connectDB();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String codeLevel = rs.getString("j.code_level");
				String nameLevel = rs.getString("j.name_level");
				mstJapan = new MstJapan(codeLevel, nameLevel);
				lstMstJapan.add(mstJapan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeDB(conn);
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lstMstJapan;
	}
}
