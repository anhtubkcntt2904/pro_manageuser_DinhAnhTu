/**
 * Copyright(C) 2017 Luvina
 * MstGroupDaoImpl.java, 16/10/2017 Đinh Anh Tú
 */
package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.MstGroupDao;
import entity.MstGroup;

/**
 * class thao tác với bảng mst group trong database
 * @author AnhTu
 *
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {

	@Override
	public List<MstGroup> getAllMstGroup() {
		// TODO Auto-generated method stub

		MstGroup mstGroup = new MstGroup();
		List<MstGroup> lstMstGroup = new ArrayList<>();
		String sql = "select g.group_id, g.group_name from mst_group g";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connectDB();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int groupId = rs.getInt("g.group_id");
				String groupName = rs.getString("g.group_name");
				mstGroup = new MstGroup(groupId, groupName);
				lstMstGroup.add(mstGroup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(conn);
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lstMstGroup;
	}

}
