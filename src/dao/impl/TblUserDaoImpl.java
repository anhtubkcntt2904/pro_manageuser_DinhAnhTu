/**
 * Copyright(C) 2017 Luvina
 * TblUserDaoIml.java, 16/10/2017 Đinh Anh Tú
 */
package dao.impl;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.TblUserDao;
import entity.MstGroup;
import entity.TblUser;
import entity.UserInfo;
import entity.UserInfor;

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
	// biến đếm để set param cho PreparedStatement getUserByEmail
	static int count3 = 0;

	@Override
	public List<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate) {
		connectDB();
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
			ps = conn.prepareStatement(sql.toString());

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
			closeDB(conn);
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
		connectDB();
		StringBuffer sql = new StringBuffer();
		// biến đếm số bản ghi
		int countTotal = 0;
		sql.append("select u.user_id ");
		sql.append("from tbl_user u ");
		sql.append("inner join mst_group g on u.group_id = g.group_id ");
		sql.append("where 1 = 1 ");

		// nếu vào trường hợp tìm kiếm có full name
		if (fullName != null) {
			sql.append("and u.full_name like ? ");
		}
		// nếu vào trường hợp tìm kiếm có group id
		if (groupId != 0) {
			sql.append("and u.group_id = ? ");
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql.toString());

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
			closeDB(conn);
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return countTotal;
	}

	@Override
	public TblUser getUserByEmail(Integer userId, String email) {
		connectDB();
		StringBuffer sql = new StringBuffer();
		TblUser tblUser = new TblUser();
		sql.append("select u.user_id ");
		sql.append("from tbl_user u ");
		sql.append("where u.email = ? ");
		if (userId != null) {
			if (userId != 0) {
				sql.append(" and user_id = ?");
			}
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			int i = 0;
			ps.setString(++i, email);
			if (userId != null) {
				if (userId != 0) {
					ps.setInt(++i, userId);
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				/*
				 * Integer userID = rs.getInt("u.user_id"); Integer groupID =
				 * rs.getInt("u.group_id"); String loginName = rs.getString("u.login_name");
				 * String password = rs.getString("u.passwords"); String fullName =
				 * rs.getString("u.full_name"); String fullNameKana =
				 * rs.getString("u.full_name_kana"); String emailUser = rs.getString("u.email");
				 * String tel = rs.getString("u.tel"); Date birthday = rs.getDate("u.birthday");
				 * String salt = rs.getString("u.salt"); tblUser = new TblUser(userID, groupID,
				 * loginName, password, fullName, fullNameKana, emailUser, tel, birthday, salt);
				 */
				tblUser.setUserId(rs.getInt("u.user_id"));
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

		return tblUser;
	}

	@Override
	public TblUser checkExistedLoginName(Integer userId, String loginName) {
		connectDB();
		StringBuffer sql = new StringBuffer();
		TblUser tblUser = new TblUser();
		sql.append("select u.user_id ");
		sql.append("from tbl_user u ");
		sql.append("where u.login_name = ? ");
		if (userId != null) {
			if (userId != 0) {
				sql.append(" and u.user_id = ?");
			}
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			int i = 0;
			ps.setString(++i, loginName);
			if (userId != null) {
				if (userId != 0) {
					ps.setInt(++i, userId);
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				tblUser.setUserId(rs.getInt("user_id"));
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
		return tblUser;
	}

	@Override
	public int insertUser(TblUser tblUser) throws SQLException {
		// Connection conn = BaseDaoImpl.conn;
		StringBuffer sql = new StringBuffer();
		int userid = 0;
		sql.append(
				"INSERT INTO tbl_user (group_id, login_name, passwords, full_name, full_name_kana, email, tel, birthday,salt) ");
		sql.append("VALUES(?,?,?,?,?,?,?,?,?)");
		PreparedStatement ps = null;
		ResultSet rs = null;
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, tblUser.getGroupId());
			ps.setString(2, tblUser.getLoginName());
			ps.setString(3, tblUser.getPassword());
			ps.setString(4, tblUser.getFullname());
			ps.setString(5, tblUser.getFullnamekana());
			ps.setString(6, tblUser.getEmail());
			ps.setString(7, tblUser.getTel());
			ps.setDate(8, java.sql.Date.valueOf(dt1.format(tblUser.getBirthday())));
			ps.setString(9, tblUser.getSalt());
			

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			while (rs.next()) {
				// tblUser.setUserId(rs.getInt(1));
				userid = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw e;
			// e.printStackTrace();
		}
		return userid;
	}

	@Override
	public TblUser getUserById(int userId) {
		connectDB();
		TblUser tblUser = new TblUser();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from tbl_user ");
		sql.append("where user_id = ?");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				tblUser.setUserId(rs.getInt("user_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(conn);
		}
		return tblUser;
	}

	@Override
	public UserInfor getUserInforById(int userId) {
		connectDB();
		UserInfor userInfor = new UserInfor();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select u.login_name, u.group_id, u.full_name, u.full_name_kana, u.birthday, u.email, u.tel, duj.code_level, duj.start_date, duj.end_date, duj.total ");
		sql.append("from tbl_user u ");
		sql.append("inner join tbl_detail_user_japan duj on ");
		sql.append("u.user_id = duj.user_id ");
		sql.append("where u.user_id = ? ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				userInfor.setUserId(userId);
				userInfor.setLoginName(rs.getString("u.login_name"));
				userInfor.setGroupId(rs.getInt("u.group_id"));
				userInfor.setFullName(rs.getString("u.full_name"));
				userInfor.setFullNameKana(rs.getString("u.full_name_kana"));
				userInfor.setBirthday(rs.getDate("u.birthday"));
				userInfor.setEmail(rs.getString("u.email"));
				userInfor.setTel(rs.getString("u.tel"));
				userInfor.setCodeLevel(rs.getString("duj.code_level"));
				userInfor.setStartDate(rs.getDate("duj.start_date"));
				userInfor.setEndDate(rs.getDate("duj.end_date"));
				userInfor.setTotal(Integer.toString(rs.getInt("duj.total")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(conn);
		}
		return userInfor;
	}

}
