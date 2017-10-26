/**
 * Copyright(C) 2017 Luvina
 * TblUserDao.java, 16/10/2017 Đinh Anh Tú
 */
package dao;

import java.util.List;

import entity.User;
import entity.UserInfo;

/**
 * Interface chứa các phương thức thao tác với bảng user
 * 
 * @author AnhTu
 *
 */
public interface TblUserDao {
	/**
	 * Phương thức lấy ra list thông tin user
	 * @param offset
	 * @param limit
	 * @param groupId
	 * @param fullName
	 * @param sortType
	 * @param sortByFullname
	 * @param sortByCodeLevel
	 * @param sortByEndDate
	 * @return List User
	 */
	public List<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate);
	
	/**
	 * Phương thức lấy ra tổng số user
	 * @param groupId
	 * @param fullName
	 * @return tổng số user
	 */
	public int getTotalUser(int groupId,String fullName);
}
