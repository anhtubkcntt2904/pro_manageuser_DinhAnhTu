/**
 * Copyright(C) 2017 Luvina
 * TblUserDaoIml.java, 16/10/2017 Đinh Anh Tú
 */
package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

	// biến đếm để set param cho PreparedStatement getListUser
	static int count1 = 0;
	// biến đếm để set param cho PreparedStatement getTotalUser
	static int count2 = 0;

	@Override
	public List<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate) {
		Connection con = connectDB();
		// thông tin mỗi user hiển thị trên màn ADM002
		UserInfo userInfo = new UserInfo();
		// danh sách các user với thông tin từng user
		List<UserInfo> lstUserInfo = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select u.user_id,u.full_name,u.birthday,g.group_name,u.email,u.tel,j.name_level,tdu.end_date,tdu.total ");
		sql.append("from tbl_user u ");
		sql.append("inner join mst_group g on u.group_id = g.group_id ");
		sql.append("left join tbl_detail_user_japan tdu on u.user_id = tdu.user_id ");
		sql.append("inner join mst_japan j on tdu.code_level = j.code_level ");
		sql.append("where 1 = 1 ");

		// nếu vào trường hợp tìm kiếm có full name
		if (fullName != null) {
			fullName = fullName.trim().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
			sql.append("and u.full_name like ? ");
		}
		// nếu vào trường hợp tìm kiếm có group id
		if (groupId != 0) {
			sql.append("and u.group_id = ? ");
		}

		// Trường hợp ưu tiên sắp xếp ưu tiên theo full name
		if ("full_name".equals(sortType)) {
			sql.append("order by u.full_name ");
			sql.append(sortByFullname);
			sql.append(", tdu.code_level ");
			sql.append(sortByCodeLevel);
			sql.append(",tdu.end_date ");
			sql.append(sortByEndDate);
			// trường hợp ưu tiên sắp xếp ưu tiên theo code level
		} else if ("code_level".equals(sortType)) {
			sql.append("order by tdu.code_level ");
			sql.append(sortByCodeLevel);
			sql.append(", u.full_name  ");
			sql.append(sortByFullname);
			sql.append(",tdu.end_date ");
			sql.append(sortByEndDate);
			// trường hợp ưu tiên sắp xếp ưu tiên theo end date
		} else if ("end_date".equals(sortType)) {
			sql.append("order by tdu.end_date ");
			sql.append(sortByEndDate);
			sql.append(", u.full_name  ");
			sql.append(sortByFullname);
			sql.append(",tdu.code_level ");
			sql.append(sortByCodeLevel);
		}
		
		sql.append(" limit ");
		sql.append(offset + ",");
		sql.append(limit);

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql.toString());

			// nếu vào trường hợp tìm kiếm có full name
			if (fullName != null) {
				count1++;
				ps.setString(count1, "%" + fullName + "%");
			}
			// nếu vào trường hợp tìm kiếm có group id
			if (groupId != 0) {
				count1++;
				ps.setInt(count1, groupId);
			}
			// set lại giá trị cho biến count
			count1 = 0;
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
		// biến đếm số bản ghi
		int countTotal = 0;
		sql.append("select u.user_id ");
		sql.append("from tbl_user u ");
		sql.append("inner join mst_group g on u.group_id = g.group_id ");
		sql.append("where 1 = 1 ");

		// nếu vào trường hợp tìm kiếm có full name
		if (fullName != null) {
			fullName = fullName.trim().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
			sql.append("and u.full_name like ? ");
		}
		// nếu vào trường hợp tìm kiếm có group id
		if (groupId != 0) {
			sql.append("and u.group_id = ? ");
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql.toString());

			// nếu vào trường hợp tìm kiếm có full name
			if (fullName != null) {
				count2++;
				ps.setString(count2, "%" + fullName + "%");
			}
			// nếu vào trường hợp tìm kiếm có group id
			if (groupId != 0) {
				count2++;
				ps.setInt(count2, groupId);
			}

			// set lại giá trị cho biến count
			count2 = 0;
			rs = ps.executeQuery();
			while (rs.next()) {
				countTotal++;
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
		return countTotal;
	}

}
