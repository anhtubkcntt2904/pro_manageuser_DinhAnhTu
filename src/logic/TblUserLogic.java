/**
 * Copyright(C) 2017 Luvina
 * UserLogic.java, 16/10/2017 Đinh Anh Tú
 */
package logic;

import java.util.List;

import entity.UserInfo;

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
	public List<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
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
	public boolean checkExistedEmail(Integer userId,String email);

	/**
	 * kiểm tra login name đã tồn tại trong bảng tbl user chưa
	 * 
	 * @param userId
	 * @param loginName
	 * @return true hoặc false
	 */
	public boolean checkExistedLoginName(Integer userId,String loginName);
}
