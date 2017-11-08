/**
 * Copyright(C) 2017 Luvina
 * UserLogicImpl.java, 16/10/2017 Đinh Anh Tú
 */
package logic.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.impl.BaseDaoImpl;
import dao.impl.TblDetailUserJapanDaoImpl;
import dao.impl.TblUserDaoImpl;
import entity.TblDetailUserJapan;
import entity.TblUser;
import entity.UserInfo;
import entity.UserInfor;
import logic.TblUserLogic;
import validate.Validate;

/**
 * class để implement các phương thức của interface userLogic
 * 
 * @author AnhTu
 *
 */
public class TblUserLogicImpl implements TblUserLogic {
	// Danh sách các lỗi khi dăng nhập
	public static List<String> lstErr = new ArrayList<>();

	@Override
	public boolean ExistLogin(String loginName, String password) {
		Validate validate = new Validate();
		// xác thực thông tin đăng nhập
		lstErr = validate.validateLogin(loginName, password);
		// nếu danh sách lỗi rỗng
		if (lstErr.isEmpty()) {
			// trả về true
			return true;
		} else
			// nếu không rỗng thì trả về false
			return false;
	}

	@Override
	public List<UserInfo> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		if (fullName != null) {
			fullName = fullName.trim().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		return tblUserDao.getListUser(offset, limit, groupId, fullName, sortType, sortByFullname, sortByCodeLevel,
				sortByEndDate);
	}

	@Override
	public int getTotalUser(int groupId, String fullName) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		if (fullName != null) {
			fullName = fullName.trim().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		return tblUserDao.getTotalUser(groupId, fullName);
	}

	@Override
	public boolean checkExistedEmail(Integer userId, String email) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		TblUser tblUser = tblUserDao.getUserByEmail(userId, email);
		if (tblUser != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkExistedLoginName(Integer userId, String loginName) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		TblUser tblUser = tblUserDao.checkExistedLoginName(userId, loginName);
		if (tblUser != null) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean createUser(UserInfor userInfor) throws SQLException {
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
		Boolean check = false;
		int userid;
		try {
			TblUser tblUser = new TblUser();
			TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
			TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
			TblDetailUserJapanDaoImpl tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
			baseDaoImpl.connectDB();
			// int userId = userInfor.getUserId();
			int groupId = userInfor.getGroupId();
			String loginName = userInfor.getLoginName();
			String password = userInfor.getPassword();
			String fullname = userInfor.getFullName();
			String fullnamekana = userInfor.getFullNameKana();
			String email = userInfor.getEmail();
			String tel = userInfor.getTel();
			Date birthday = userInfor.getBirthday();
			String salt = "";

			String codeLevel = userInfor.getCodeLevel();
			Date startDate = userInfor.getStartDate();
			Date endDate = userInfor.getEndDate();
			String total = userInfor.getTotal();

			// tblUser.setUserId(userId);
			tblUser.setGroupId(groupId);
			tblUser.setLoginName(loginName);
			tblUser.setPassword(password);
			tblUser.setFullname(fullname);
			tblUser.setFullnamekana(fullnamekana);
			tblUser.setEmail(email);
			tblUser.setTel(tel);
			tblUser.setBirthday(birthday);
			tblUser.setSalt(salt);

			BaseDaoImpl.conn.setAutoCommit(false);

			userid = tblUserDao.insertUser(tblUser);
			if (userid != 0) {
				tblUser.setUserId(userid);

				tblDetailUserJapan.setUserId(userid);
				tblDetailUserJapan.setCodeLevel(codeLevel);
				tblDetailUserJapan.setStartDate(startDate);
				tblDetailUserJapan.setEndDate(endDate);
				tblDetailUserJapan.setTotal(total);

				check = tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan);
			}
			if (check) {
				BaseDaoImpl.conn.commit();
				System.out.println("đã commit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BaseDaoImpl.conn.rollback();
			System.out.println("đã roll back");
		} finally {
			baseDaoImpl.closeDB(BaseDaoImpl.conn);
		}
		return check;
	}
}
