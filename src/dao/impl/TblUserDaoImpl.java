/**
 * Copyright(C) 2017 Luvina
 * TblUserDaoIml.java, 16/10/2017 Đinh Anh Tú
 */
package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.TblUserDao;
import entity.MstGroup;
import entity.UserInfo;

/**
 * class implements interface TblUserDao
 * 
 * @author AnhTu
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	@Override
	public List<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate) {
		Connection con = connectDB();
		// thông tin mỗi user hiển thị trên màn ADM002
		UserInfo userInfo = new UserInfo();
		// danh sách các user với thông tin từng user
		List<UserInfo> lstUserInfo = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		//replace để search % không show all user
		fullName = fullName.trim().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");

		sql.append(
				"select u.user_id,u.full_name,u.birthday,g.group_name,u.email,u.tel,j.name_level,tdu.end_date,tdu.total ");
		sql.append("from tbl_user u ");
		sql.append("inner join mst_group g on u.group_id = g.group_id ");
		sql.append("left join tbl_detail_user_japan tdu on u.user_id = tdu.user_id ");
		sql.append("inner join mst_japan j on tdu.code_level = j.code_level ");
		sql.append("where u.full_name like ? ");
		// nếu mặc định vào trang là search thì thêm group id
		if (groupId != 0) {
			sql.append("and u.group_id = ? ");
		}
		sql.append("order by u.full_name ");
		sql.append(sortByFullname);
		sql.append(", tdu.code_level ");
		sql.append(sortByCodeLevel);
		sql.append(",tdu.end_date ");
		sql.append(sortByEndDate);

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql.toString());

			ps.setString(1, "%" + fullName + "%");
			if (groupId != 0) {
				ps.setInt(2, groupId);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt("u.user_id");
				String fullname = rs.getString("u.full_name");
				Date birthday = rs.getDate("u.birthday");
				String groupname = rs.getString("g.group_name");
				String email = rs.getString("u.email");
				String tel = rs.getString("u.tel");
				String nameLevel = rs.getString("j.name_level");
				Date endDate = rs.getDate("tdu.end_date");
				int total = rs.getInt("tdu.total");
				userInfo = new UserInfo(userId, fullname, birthday, groupname, email, tel, nameLevel, endDate, total);
				lstUserInfo.add(userInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(con);
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lstUserInfo;
	}

	@Override
	public int getTotalUser(int groupId, String fullName) {
		Connection con = connectDB();
		StringBuffer sql = new StringBuffer();
		fullName = fullName.trim().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		//biến đếm số bản ghi
		int countTotal = 0;
		sql.append("select count(*) ");
		sql.append("from tbl_user u ");
		sql.append("inner join mst_group g on u.group_id = g.group_id ");
		sql.append("where full_name like ? ");
		// nếu mặc định vào trang là search thì thêm group id
		if (groupId != 0) {
			sql.append("and group_id = ? ");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql.toString());

			ps.setString(1, "%" + fullName + "%");
			if (groupId != 0) {
				ps.setInt(2, groupId);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				countTotal++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(con);
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return countTotal;
	}

}
