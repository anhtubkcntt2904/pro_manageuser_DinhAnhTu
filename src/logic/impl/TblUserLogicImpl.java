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
		if (tblUser.getUserId() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkExistedLoginName(Integer userId, String loginName) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		TblUser tblUser = new TblUser();
		tblUser = tblUserDao.checkExistedLoginName(userId, loginName);
		if (tblUser.getUserId() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean createUser(UserInfor userInfor) throws SQLException {
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
		Boolean check = true;
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
			if (userid != 0 && !"0".equals(codeLevel)) {
				tblUser.setUserId(userid);

				/* tblDetailUserJapan.setUserId(userid); */
				tblDetailUserJapan.setCodeLevel(codeLevel);
				tblDetailUserJapan.setStartDate(startDate);
				tblDetailUserJapan.setEndDate(endDate);
				tblDetailUserJapan.setTotal(total);
				check = tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan);
			}

			BaseDaoImpl.conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BaseDaoImpl.conn.rollback();
			System.out.println("roll back in exception");
		} finally {
			baseDaoImpl.closeDB(BaseDaoImpl.conn);
		}
		return check;
	}

	@Override
	public boolean isExistedUser(int userId) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		TblUser tblUser = new TblUser();
		boolean existedUser = false;
		tblUser = tblUserDao.getUserById(userId);
		if (tblUser != null) {
			existedUser = true;
		}
		return existedUser;
	}

	@Override
	public UserInfor getUserInforById(int userId) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		UserInfor userInfor = new UserInfor();
		userInfor = tblUserDao.getUserInforById(userId);
		return userInfor;
	}

	@Override
	public Boolean updateUserInfor(UserInfor userInfor) throws SQLException {
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
		Boolean check = true;
		try {
			TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
			TblUser tblUser = new TblUser();
			TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
			TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
			baseDaoImpl.connectDB();
			
			//Lấy thông tin người dùng nhập vào
			int userId = userInfor.getUserId();
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
			
			tblUser.setUserId(userId);
			tblUser.setGroupId(groupId);
			tblUser.setLoginName(loginName);
			tblUser.setPassword(password);
			tblUser.setFullname(fullname);
			tblUser.setFullnamekana(fullnamekana);
			tblUser.setEmail(email);
			tblUser.setTel(tel);
			tblUser.setBirthday(birthday);
			tblUser.setSalt(salt);
			
			tblDetailUserJapan.setCodeLevel(codeLevel);
			tblDetailUserJapan.setStartDate(startDate);
			tblDetailUserJapan.setEndDate(endDate);
			tblDetailUserJapan.setTotal(total);

			Boolean ExistedUserCodeLevel = false;
			ExistedUserCodeLevel = checkUserCodeLevel(userId);
			
			BaseDaoImpl.conn.setAutoCommit(false);
//			tblUserDao.updateUser(tblUser);
			if(tblUserDao.updateUser(tblUser)) {
				tblDetailUserJapan.setUserId(userId);
			}
			//nếu trong db, user có trình độ tiếng nhật
			if (ExistedUserCodeLevel) {
				//nếu người dùng xóa TĐTN
				if ("0".equals(codeLevel)) {
					System.out.println("come to delete");
					System.out.println(tblDetailUserJapan.getUserId());
					check = tblDetailUserJapanDaoImpl.deleteDetailUserJapan(tblDetailUserJapan.getUserId());
					System.out.println("code level :" + codeLevel);
				//nếu người dùng chỉnh sửa TĐTN hoặc không thay đổi
				} else {
					System.out.println("come to update");
					check = tblDetailUserJapanDaoImpl.updateDetailUserJapan(tblDetailUserJapan);
				}
			//nếu trong db, user không có trình độ tiếng nhật
			} else {
				//nếu người dùng thêm TĐTN
				if (!"0".equals(codeLevel)) {
					System.out.println("come to insert");
					check = tblDetailUserJapanDaoImpl.insertDetailUserJapan(tblDetailUserJapan);
				}
				//Nếu người dùng không thay đổi thì không thực hiện gì cả
			}
			BaseDaoImpl.conn.commit();
		} catch (Exception e) {
			System.out.println("come to exception");
			e.printStackTrace();
			BaseDaoImpl.conn.rollback();
			System.out.println("roll back in exception");
		}finally {
			baseDaoImpl.closeDB(BaseDaoImpl.conn);
		}
		return check;
	}

	@Override
	public Boolean checkUserCodeLevel(int userId) {
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
		tblDetailUserJapan = tblDetailUserJapanDaoImpl.getDetailUserJapanById(userId);
		if (tblDetailUserJapan.getCodeLevel() != null) {
			return true;
		}
		return false;
	}
}
