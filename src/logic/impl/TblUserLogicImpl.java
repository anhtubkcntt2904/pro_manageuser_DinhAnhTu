/**
 * Copyright(C) 2017 Luvina
 * UserLogicImpl.java, 16/10/2017 Đinh Anh Tú
 */
package logic.impl;

import java.util.ArrayList;
import java.util.List;

import dao.impl.TblUserDaoImpl;
import entity.UserInfo;
import logic.TblUserLogic;
import validate.Validate;

/**
 * class để implement các phương thức của interface userLogic
 * @author AnhTu
 *
 */
public class TblUserLogicImpl implements TblUserLogic {
	//Danh sách các lỗi khi dăng nhập
	public static List<String> lstErr = new ArrayList<>();

	@Override
	public boolean ExistLogin(String loginName, String password) {
		Validate validate = new Validate();
		//xác thực thông tin đăng nhập
		lstErr = validate.validateLogin(loginName, password);
		//nếu danh sách lỗi  rỗng
		if (lstErr.isEmpty()) {
			//trả về true
			return true;
		} else
			//nếu không rỗng thì trả về false
			return false;
	}
	
	@Override
	public List<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		return tblUserDao.getListUser(offset, limit, groupId, fullName, sortType, sortByFullname, sortByCodeLevel, sortByEndDate);
	}

	@Override
	public int getTotalUser(int groupId, String fullName) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		return tblUserDao.getTotalUser(groupId, fullName);
	}
}