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
import entity.UserInfor;

/**
 * class implements interface TblUserDao
 * 
 * @author AnhTu
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {
	/**
	 * @see dao.TblUserDao#getListUser()
	 */
	@Override
	public List<UserInfor> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate) {
		// thông tin mỗi user hiển thị trên màn ADM002
		UserInfor userInfor = new UserInfor();
		// danh sách các user với thông tin từng user
		List<UserInfor> lstUserInfor = new ArrayList<>();
		// biến đếm để set param cho PreparedStatement getTotalUser
		int i = 0;
		try {
			connectDB();
			if (conn != null) {
				StringBuffer sql = new StringBuffer();
				PreparedStatement ps = null;
				ResultSet rs = null;
				sql.append(
						"SELECT u.USER_ID,u.full_name,u.birthday,g.group_name,u.email,u.tel,j.name_level,tdu.end_date,tdu.total ");
				sql.append("FROM tbl_user AS u ");
				sql.append("INNER JOIN mst_group AS g ");
				sql.append("ON u.group_id = g.group_id ");
				sql.append("LEFT JOIN ");
				sql.append("( tbl_detail_user_japan tdu INNER JOIN mst_japan j ON tdu.code_level = j.code_level) ");
				sql.append("ON u.user_id = tdu.user_id ");
				sql.append("WHERE 1=1 ");

				// nếu vào trường hợp tìm kiếm có full name
				if (fullName != null) {
					sql.append("and u.full_name like ? ");
				}
				// nếu vào trường hợp tìm kiếm có group id
				if (groupId != 0) {
					sql.append("and u.group_id = ? ");
				}

				// Trường hợp ưu tiên sắp xếp ưu tiên theo full name
				// Mặc định ban đầu vào trường hợp này
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

				// giới hạn số bản ghi lấy ra
				sql.append(" limit ");
				sql.append(offset + ",");
				sql.append(limit);
				ps = conn.prepareStatement(sql.toString());

				// nếu vào trường hợp tìm kiếm có full name
				if (fullName != null) {
					i++;
					ps.setString(i, "%" + fullName + "%");
				}
				// nếu vào trường hợp tìm kiếm có group id
				if (groupId != 0) {
					i++;
					ps.setInt(i, groupId);
				}

				rs = ps.executeQuery();
				while (rs.next()) {
					userInfor = new UserInfor();
					int userId = rs.getInt("u.user_id");
					String fullname = rs.getString("u.full_name");
					Date birthday = rs.getDate("u.birthday");
					String groupname = rs.getString("g.group_name");
					String email = rs.getString("u.email");
					String tel = rs.getString("u.tel");
					String nameLevel = rs.getString("j.name_level");
					Date endDate = rs.getDate("tdu.end_date");
					int total = rs.getInt("tdu.total");
					// set giá trị cho user infor
					userInfor.setUserId(userId);
					userInfor.setFullName(fullname);
					userInfor.setBirthday(birthday);
					userInfor.setGroupName(groupname);
					userInfor.setEmail(email);
					userInfor.setTel(tel);
					userInfor.setNameLevel(nameLevel);
					userInfor.setEndDate(endDate);
					if (total == 0) {
						userInfor.setTotal("");
					} else {
						userInfor.setTotal(total + "");
					}
					lstUserInfor.add(userInfor);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return lstUserInfor;

	}

	/**
	 * @see dao.TblUserDao#getTotalUser(int,String)
	 */
	@Override
	public int getTotalUser(int groupId, String fullName) {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select count(*) as totaluser ");
		sql.append("from tbl_user u ");
		sql.append("inner join mst_group g on u.group_id = g.group_id ");
		sql.append("where 1 = 1 ");
		// biến đếm số bản ghi
		int countTotal = 0;
		// biến đếm để set param cho PreparedStatement getTotalUser
		int i = 0;
		try {
			connectDB();
			if (conn != null) {
				// nếu vào trường hợp tìm kiếm có full name
				if (fullName != null) {
					sql.append("and u.full_name like ? ");
				}
				// nếu vào trường hợp tìm kiếm có group id
				if (groupId != 0) {
					sql.append("and u.group_id = ? ");
				}
				ps = conn.prepareStatement(sql.toString());
				// nếu vào trường hợp tìm kiếm có full name
				if (fullName != null) {
					ps.setString(++i, "%" + fullName + "%");
				}
				// nếu vào trường hợp tìm kiếm có group id
				if (groupId != 0) {
					ps.setInt(++i, groupId);
				}
				rs = ps.executeQuery();
				if (rs.next()) {
					// đếm tổng số user
					countTotal = rs.getInt("totaluser");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return countTotal;

	}

	/**
	 * @see dao.TblUserDao#getUserByEmail(Integer,String)
	 */
	@Override
	public TblUser getUserByEmail(Integer userId, String email) {
		TblUser tblUser = new TblUser();
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select u.user_id ");
		sql.append("from tbl_user u ");
		sql.append("where u.email = ? ");
		// biến đếm để set param cho PreparedStatement getUserByEmail
		int i = 0;
		try {
			connectDB();
			if (conn != null) {
				// nếu user id không bằng null thì thêm điều kiện user id vào tìm kiếm
				if (userId != null) {
					if (userId != 0) {
						sql.append(" and user_id != ?");
					}
				}
				ps = conn.prepareStatement(sql.toString());
				ps.setString(++i, email);
				// nếu user id không bằng null thì thêm điều kiện user id vào tìm kiếm
				if (userId != null) {
					if (userId != 0) {
						ps.setInt(++i, userId);
					}
				}
				rs = ps.executeQuery();
				if (rs.next()) {
					// Lấy ra user có email trùng với email truyền vào và khác user id
					tblUser.setUserId(rs.getInt("u.user_id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return tblUser;

	}

	/**
	 * @see dao.TblUserDao#checkExistedLoginName(Integer,String)
	 */
	@Override
	public TblUser checkExistedLoginName(Integer userId, String loginName) {
		// thông tin user cần lấy
		TblUser tblUser = new TblUser();
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select u.user_id ");
		sql.append("from tbl_user u ");
		sql.append("where u.login_name = ? ");
		try {
			connectDB();
			if (conn != null) {
				// nếu user id không bằng null thì thêm điều kiện user id vào tìm kiếm
				if (userId != null) {
					if (userId != 0) {
						sql.append(" and u.user_id = ?");
					}
				}
				ps = conn.prepareStatement(sql.toString());
				int i = 0;
				ps.setString(++i, loginName);
				// nếu user id không bằng null thì thêm điều kiện user id vào tìm kiếm
				if (userId != null) {
					if (userId != 0) {
						ps.setInt(++i, userId);
					}
				}
				rs = ps.executeQuery();
				if (rs.next()) {
					// lấy ra user với user id và login name trùng với param đầu vào
					tblUser.setUserId(rs.getInt("user_id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return tblUser;
	}

	/**
	 * @see dao.TblUserDao#insertUser(TblUser)
	 */
	@Override
	public int insertUser(TblUser tblUser) throws SQLException {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		sql.append(
				"INSERT INTO tbl_user (group_id, login_name, passwords, full_name, full_name_kana, email, tel, birthday,salt) ");
		sql.append("VALUES(?,?,?,?,?,?,?,?,?)");
		int userid = 0;
		try {
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				int i = 0;
				ps.setInt(++i, tblUser.getGroupId());
				ps.setString(++i, tblUser.getLoginName());
				ps.setString(++i, tblUser.getPassword());
				ps.setString(++i, tblUser.getFullname());
				ps.setString(++i, tblUser.getFullnamekana());
				ps.setString(++i, tblUser.getEmail());
				ps.setString(++i, tblUser.getTel());
				ps.setDate(++i, java.sql.Date.valueOf(dt1.format(tblUser.getBirthday())));
				ps.setString(++i, tblUser.getSalt());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					userid = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return userid;
	}

	/**
	 * @see dao.TblUserDao#getUserById(int)
	 */
	@Override
	public TblUser getUserById(int userId) {
		TblUser tblUser = new TblUser();
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select * from tbl_user ");
		sql.append("where user_id = ?");
		try {
			connectDB();
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString());
				ps.setInt(1, userId);
				rs = ps.executeQuery();
				if (rs.next()) {
					tblUser.setUserId(rs.getInt("user_id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return tblUser;
	}

	/**
	 * @see dao.TblUserDao#getUserInforById(int)
	 */
	@Override
	public UserInfor getUserInforById(int userId) {
		UserInfor userInfor = new UserInfor();
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append(
				"select u.login_name, u.group_id, u.full_name, u.full_name_kana, u.birthday, u.email, u.tel, duj.code_level, duj.start_date, duj.end_date, duj.total ");
		sql.append("from tbl_user u ");
		sql.append("left join tbl_detail_user_japan duj on ");
		sql.append("u.user_id = duj.user_id ");
		sql.append("where u.user_id = ? ");
		try {
			connectDB();
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString());
				ps.setInt(1, userId);
				rs = ps.executeQuery();
				if (rs.next()) {
					// set các thông tin lấy được cho user infor
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return userInfor;
	}

	/**
	 * @see dao.TblUserDao#updateUser(TblUser,Connection)
	 */
	@Override
	public boolean updateUser(TblUser tblUser) throws SQLException {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		boolean check = false;
		sql.append("update tbl_user ");
		sql.append(
				"set group_id = ? , login_name = ?, full_name = ?, full_name_kana = ?, email = ?, tel = ?, birthday = ?");
		sql.append("where user_id = ?");
		try {
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString());
				int index = 0;
				ps.setInt(++index, tblUser.getGroupId());
				ps.setString(++index, tblUser.getLoginName());
				ps.setString(++index, tblUser.getFullname());
				ps.setString(++index, tblUser.getFullnamekana());
				ps.setString(++index, tblUser.getEmail());
				ps.setString(++index, tblUser.getTel());
				ps.setDate(++index, java.sql.Date.valueOf(dt1.format(tblUser.getBirthday())));
				if (tblUser.getUserId() != 0) {
					ps.setInt(++index, tblUser.getUserId());
				}
				int record = ps.executeUpdate();
				// nếu update thành công
				if (record > 0) {
					// trả về true
					check = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return check;
	}

	/**
	 * @see dao.TblUserDao#updatePass(String,String,int)
	 */
	@Override
	public boolean updatePass(String pass, String salt, int userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("update tbl_user ");
		sql.append("set passwords = ?, salt = ?");
		sql.append("where user_id = ?");
		PreparedStatement ps = null;
		boolean check = false;
		try {
			connectDB();
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString());
				int i = 0;
				ps.setString(++i, pass);
				ps.setString(++i, salt);
				// nếu user id không bằng null thì thêm điều kiện user id vào tìm kiếm
				if (userId != 0) {
					ps.setInt(++i, userId);
				}
				check = ps.executeUpdate() > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return check;
	}

	/**
	 * @see dao.TblUserDao#deleteUser(int,Connection)
	 */
	@Override
	public boolean deleteUser(int userId) throws SQLException {
		Boolean isSuccess = false;
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		sql.append("delete from tbl_user ");
		sql.append("where user_id = ?");
		try {
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString());
				int i = 0;
				// nếu user id không bằng null thì thêm điều kiện user id vào tìm kiếm
				if (userId != 0) {
					ps.setInt(++i, userId);
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
	 * @see dao.TblUserDao#isExistedUser(int)
	 */
	@Override
	public boolean isExistedUser(int userid) {
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select * from tbl_user ");
		sql.append("where user_id = ?");
		boolean check = false;
		try {
			connectDB();
			if (conn != null) {
				ps = conn.prepareStatement(sql.toString());
				ps.setInt(1, userid);
				rs = ps.executeQuery();
				if (rs.next()) {
					check = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return check;
	}
}
