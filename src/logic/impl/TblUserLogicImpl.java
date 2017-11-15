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
		// Nếu full name không null
		if (fullName != null) {
			fullName = fullName.trim().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		// trả về danh sách user
		return tblUserDao.getListUser(offset, limit, groupId, fullName, sortType, sortByFullname, sortByCodeLevel,
				sortByEndDate);
	}

	@Override
	public int getTotalUser(int groupId, String fullName) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		// Nếu full name không null
		if (fullName != null) {
			fullName = fullName.trim().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		// trả về tổng số user
		return tblUserDao.getTotalUser(groupId, fullName);
	}

	@Override
	public boolean checkExistedEmail(Integer userId, String email) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		// Lấy thông tin user theo user id và email
		TblUser tblUser = tblUserDao.getUserByEmail(userId, email);
		// Nếu có user trả về true
		if (tblUser.getUserId() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkExistedLoginName(Integer userId, String loginName) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		TblUser tblUser = new TblUser();
		// Lấy thông tin user theo user id và login name
		tblUser = tblUserDao.checkExistedLoginName(userId, loginName);
		// Nếu có user trả vê true
		if (tblUser.getUserId() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean createUser(UserInfor userInfor) throws SQLException {
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
		// Biến kiểm tra insert có thành công không
		Boolean check = true;
		int userid;
		try {
			TblUser tblUser = new TblUser();
			TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
			TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
			TblDetailUserJapanDaoImpl tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
			baseDaoImpl.connectDB();

			// Lấy thông tin nhập vào
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

			// Lấy thông tin TĐTN user nhập vào
			String codeLevel = userInfor.getCodeLevel();
			Date startDate = userInfor.getStartDate();
			Date endDate = userInfor.getEndDate();
			String total = userInfor.getTotal();

			// set giá trị cho user
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

			BaseDaoImpl.conn.setAutoCommit(false);

			userid = tblUserDao.insertUser(tblUser);

			// Nếu insert thông tin user thành công và lấy được user id
			// Xét xem admin có thêm TĐTN cho user không
			if (userid != 0 && !"0".equals(codeLevel)) {
				tblUser.setUserId(userid);

				// set giá trị trình độ tiếng nhật cho user
				/* tblDetailUserJapan.setUserId(userid); */
				tblDetailUserJapan.setCodeLevel(codeLevel);
				tblDetailUserJapan.setStartDate(startDate);
				tblDetailUserJapan.setEndDate(endDate);
				tblDetailUserJapan.setTotal(total);

				// Nếu có thực hiện insert TĐTN cho user
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
		// Lấy user theo user id
		tblUser = tblUserDao.getUserById(userId);
		// nếu tồn tại user thì trả về true
		if (tblUser != null) {
			existedUser = true;
		}
		return existedUser;
	}

	@Override
	public UserInfor getUserInforById(int userId) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		UserInfor userInfor = new UserInfor();
		// Lấy thông tin user theo user id
		userInfor = tblUserDao.getUserInforById(userId);
		return userInfor;
	}

	@Override
	public Boolean updateUserInfor(UserInfor userInfor) throws SQLException {
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
		Boolean check = false;
		try {
			TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
			TblUser tblUser = new TblUser();
			TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
			TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
			baseDaoImpl.connectDB();

			// Lấy thông tin người dùng nhập vào
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

			// Lấy thông tin TĐTN người dùng nhập vào
			String codeLevel = userInfor.getCodeLevel();
			Date startDate = userInfor.getStartDate();
			Date endDate = userInfor.getEndDate();
			String total = userInfor.getTotal();

			// Lấy thông tin user
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

			// Lấy thông tin detail user japan
			tblDetailUserJapan.setCodeLevel(codeLevel);
			tblDetailUserJapan.setStartDate(startDate);
			tblDetailUserJapan.setEndDate(endDate);
			tblDetailUserJapan.setTotal(total);

			// Kiểm tra user có trình độ tiếng nhật không
			Boolean ExistedUserCodeLevel = false;
			ExistedUserCodeLevel = checkUserCodeLevel(userId);

			BaseDaoImpl.conn.setAutoCommit(false);
			// tblUserDao.updateUser(tblUser);
			if (tblUserDao.updateUser(tblUser)) {
				tblDetailUserJapan.setUserId(userId);
			}
			// nếu trong db, user có trình độ tiếng nhật
			if (ExistedUserCodeLevel) {

				// nếu người dùng xóa TĐTN
				if ("0".equals(codeLevel)) {
					System.out.println("come to delete");
					System.out.println(tblDetailUserJapan.getUserId());
					check = tblDetailUserJapanDaoImpl.deleteDetailUserJapan(tblDetailUserJapan.getUserId());

					// nếu người dùng chỉnh sửa TĐTN hoặc không thay đổi
				} else {
					System.out.println("come to update");
					check = tblDetailUserJapanDaoImpl.updateDetailUserJapan(tblDetailUserJapan);
				}
				// nếu trong db, user không có trình độ tiếng nhật
			} else {

				// nếu người dùng thêm TĐTN
				if (!"0".equals(codeLevel)) {
					System.out.println("come to insert");
					check = tblDetailUserJapanDaoImpl.insertDetailUserJapan(tblDetailUserJapan);
				}
				// Nếu người dùng không thay đổi thì không thực hiện gì cả
			}
			BaseDaoImpl.conn.commit();
		} catch (Exception e) {
			System.out.println("come to exception");
			e.printStackTrace();
			BaseDaoImpl.conn.rollback();
			System.out.println("roll back in exception");
		} finally {
			baseDaoImpl.closeDB(BaseDaoImpl.conn);
		}
		return check;
	}

	@Override
	public Boolean checkUserCodeLevel(int userId) {
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
		// Kiểm tra trong database,user có trình độ tiếng nhật không
		tblDetailUserJapan = tblDetailUserJapanDaoImpl.getDetailUserJapanById(userId);
		// Nếu có trả về true
		if (tblDetailUserJapan.getCodeLevel() != null) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean updatePass(String pass, int userId) {
		TblUserDaoImpl tblUserDaoImpl = new TblUserDaoImpl();
		return tblUserDaoImpl.updatePass(pass, userId);
	}

	@Override
	public Boolean deleteUser(UserInfor userInfor) throws SQLException {
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
		Boolean check = false;
		try {
			TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
			TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();

			int userId = userInfor.getUserId();
			baseDaoImpl.connectDB();

			// Kiểm tra user có trình độ tiếng nhật không
			Boolean ExistedUserCodeLevel = false;
			ExistedUserCodeLevel = checkUserCodeLevel(userId);

			BaseDaoImpl.conn.setAutoCommit(false);

			if (ExistedUserCodeLevel) {
				tblDetailUserJapanDaoImpl.deleteDetailUserJapan(userId);
			}

			tblUserDao.deleteUser(userId);

			BaseDaoImpl.conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BaseDaoImpl.conn.rollback();
		} finally {
			baseDaoImpl.closeDB(BaseDaoImpl.conn);
		}
		return check;
	}
}
