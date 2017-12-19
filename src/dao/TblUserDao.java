/**
 * Copyright(C) 2017 Luvina
 * TblUserDao.java, 16/10/2017 Đinh Anh Tú
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entity.TblUser;
import entity.UserInfor;

/**
 * Interface chứa các phương thức thao tác với bảng user
 * 
 * @author AnhTu
 *
 */
public interface TblUserDao {
	/**
	 * Phương thức lấy ra list thông tin user
	 * 
	 * @param offset
	 *            vị trí lấy data
	 * @param limit
	 *            giới hạn số bản ghi
	 * @param groupId
	 *            tên nhóm
	 * @param fullName
	 *            tên user
	 * @param sortType
	 *            kiểu sort ưu tiên
	 * @param sortByFullname
	 *            loại sort của full name
	 * @param sortByCodeLevel
	 *            loại sort của code level
	 * @param sortByEndDate
	 *            loại sort của end date
	 * @return List User
	 */
	public List<UserInfor> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate);

	/**
	 * Phương thức lấy ra tổng số user
	 * 
	 * @param groupId
	 * @param fullName
	 * @return tổng số user
	 */
	public int getTotalUser(int groupId, String fullName);

	/**
	 * phương thức lấy ra thông tin của 1 user với điều kiện email và userId
	 * 
	 * @param userId
	 * @param email
	 * @return thông tin của 1 user
	 */
	public TblUser getUserByEmail(Integer userId, String email);

	/**
	 * Phương thức lấy ra thông tin của 1 user theo login name và userId
	 * 
	 * @param userId
	 * @param loginName
	 * @return thông tin của 1 user
	 */
	public TblUser checkExistedLoginName(Integer userId, String loginName);

	/**
	 * Phương thức insert user vào database
	 * 
	 * @param tblUser
	 * @return user id của user vừa insert
	 * @throws SQLException
	 */
	public int insertUser(TblUser tblUser) throws SQLException;

	/**
	 * phương thức lấy thông tin user bằng user id
	 * 
	 * @param userId
	 * @return thông tin user
	 */
	public TblUser getUserById(int userId);

	/**
	 * Phương thức lấy thông tin user bằng user id
	 * 
	 * @param userId
	 * @return thông tin user
	 */
	public UserInfor getUserInforById(int userId);

	/**
	 * Phương thức update thông tin của user
	 * 
	 * @param tblUser
	 * @return true hoặc false
	 * @throws SQLException
	 */
	public boolean updateUser(TblUser tblUser) throws SQLException;

	/**
	 * Phương thức update password của người dùng
	 * 
	 * @param pass
	 * @param salt
	 * @param userId
	 * @return true hoặc false
	 */
	public boolean updatePass(String pass, String salt, int userId);

	/**
	 * Xóa thông tin user theo user id
	 * 
	 * @param userId
	 * @return true hoặc false
	 * @throws SQLException
	 */
	public boolean deleteUser(int userId) throws SQLException;

	/**
	 * Phương thức kiểm tra user có tồn tại hay không
	 * 
	 * @param userid
	 *            user id cần kiểm tra
	 * @return true hoặc false
	 */
	public boolean isExistedUser(int userid);
}
