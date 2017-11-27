/**
 * Copyright(C) 2017 Luvina
 * UserLogic.java, 16/10/2017 Đinh Anh Tú
 */
package logic;

import java.sql.SQLException;
import java.util.List;

import entity.UserInfor;

/**
 * interface chứa các phương thức của user
 * 
 * @author AnhTu
 *
 */
public interface TblUserLogic {
	/**
	 * phương thức kiểm tra thông tin login
	 * 
	 * @param loginName
	 * @param password
	 * @return true or false
	 */
	public boolean ExistLogin(String loginName, String password);

	/**
	 * phương thức lấy danh sách user
	 * 
	 * @param offset
	 * @param limit
	 * @param groupId
	 * @param fullName
	 * @param sortType
	 * @param sortByFullname
	 * @param sortByCodeLevel
	 * @param sortByEndDate
	 * @return danh sách user
	 */
	public List<UserInfor> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate);

	/**
	 * Phương thức lấy tổng số user theo điều kiện tìm kiếm
	 * 
	 * @param groupId
	 * @param fullName
	 * @return tổng số user
	 */
	public int getTotalUser(int groupId, String fullName);

	/**
	 * Phương thức kiểm tra email có tồn tại hay chưa
	 * 
	 * @param userId
	 * @param email
	 * @return true hoặc false
	 */
	public boolean checkExistedEmail(Integer userId, String email);

	/**
	 * kiểm tra login name đã tồn tại trong bảng tbl user chưa
	 * 
	 * @param userId
	 * @param loginName
	 * @return true hoặc false
	 */
	public boolean checkExistedLoginName(Integer userId, String loginName);

	/**
	 * Insert data user vào bảng tbl_user và tbl_detail_user_japan Hàm này có dùng
	 * transaction,nếu insert thông tin user vào tbl_user hoặc bảng
	 * detail_user_japan có lỗi thì sẽ rollback
	 * 
	 * @param userInfor
	 * @return true hoặc false
	 */
	public Boolean createUser(UserInfor userInfor) throws SQLException;

	/**
	 * phương thức kiểm tra user có tồn tại hay không
	 * 
	 * @param userId
	 * @return true hoặc false
	 */
	public boolean isExistedUser(int userId);

	/**
	 * Phương thức lấy thông tin user bằng user id
	 * 
	 * @param userId
	 * @return thông tin user
	 */
	public UserInfor getUserInforById(int userId);

	/**
	 * Phương thức update thông tin user infor Hàm này có dùng transaction,nếu
	 * update user vào tbl_user hoặc detail_user_japan mà gặp lỗi thì sẽ rollback
	 * 
	 * @param userInfor
	 * @return true hoặc false
	 */
	public Boolean updateUserInfor(UserInfor userInfor) throws SQLException;

	/**
	 * phương thức kiểm tra user có code level không
	 * 
	 * @param userId
	 * @return true hoặc false
	 */
	public Boolean checkUserCodeLevel(int userId);

	/**
	 * update password của người dùng
	 * 
	 * @param pass
	 * @return true hoặc false
	 */
	public Boolean updatePass(String pass, int userId);

	/**
	 * phương thức delete thông tin của một user dựa vào user id Hàm này có dùng
	 * transaction,nếu delete user trong tbl_user hoặc detail_user_japan mà gặp lỗi
	 * thì sẽ rollback
	 * 
	 * @param userId
	 * @return true hoặc false
	 */
	public Boolean deleteUser(int userId) throws SQLException;
}
