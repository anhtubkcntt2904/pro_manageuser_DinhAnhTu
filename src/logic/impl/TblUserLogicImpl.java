/**
 * Copyright(C) 2017 Luvina
 * UserLogicImpl.java, 16/10/2017 Đinh Anh Tú
 */
package logic.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Common;
import dao.impl.BaseDaoImpl;
import dao.impl.TblDetailUserJapanDaoImpl;
import dao.impl.TblUserDaoImpl;
import entity.TblDetailUserJapan;
import entity.TblUser;
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

	/**
	 * @see logic.TblUserLogic#existLogin(String,String)
	 */
	@Override
	public List<String> checkLogin(String loginName, String password) {
		// xác thực thông tin đăng nhập
		List<String> lstErr = Validate.validateLogin(loginName, password);
		// trả về danh sách lỗi
		return lstErr;
	}

	/**
	 * @see logic.TblUserLogic#getListUser()
	 */
	@Override
	public List<UserInfor> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		List<UserInfor> lstUser = new ArrayList<>();
		// Nếu full name không null
		if (fullName != null) {
			fullName = Common.replaceWildCard(fullName);
		}
		// Lấy ra danh sách user
		lstUser = tblUserDao.getListUser(offset, limit, groupId, fullName, sortType, sortByFullname, sortByCodeLevel,
				sortByEndDate);
		// trả về danh sách user
		return lstUser;
	}

	/**
	 * @see logic.TblUserLogic#getTotalUser(int,String)
	 */
	@Override
	public int getTotalUser(int groupId, String fullName) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		int totalUser = 0;
		// Nếu full name không null
		if (fullName != null) {
			fullName = Common.replaceWildCard(fullName);
		}
		totalUser = tblUserDao.getTotalUser(groupId, fullName);
		// trả về tổng số user
		return totalUser;
	}

	/**
	 * @see logic.TblUserLogic#checkExistedEmail(Integer,String)
	 */
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

	/**
	 * @see logic.TblUserLogic#checkExistedLoginName(Integer,String)
	 */
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

	/**
	 * @see logic.TblUserLogic#createUser(UserInfor)
	 */
	@Override
	public boolean createUser(UserInfor userInfor) throws SQLException {
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		TblDetailUserJapanDaoImpl tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
		TblUser tblUser = new TblUser();
		// Biến kiểm tra insert có thành công không
		boolean check = true;
		try {
			// nếu connect đến db thành công
			if (tblUserDao.connectDB()) {
				// thông tin để insert vào bảng detail user japan
				int userid;
				// Lấy thông tin nhập vào
				String salt = Common.createSalt();
				String password = Common.encryptPassword((salt + userInfor.getPassword()));
				// Lấy thông tin TĐTN user nhập vào
				String codeLevel = userInfor.getCodeLevel();
				// set giá trị cho user
				tblUser = Common.setTblUser(userInfor);
				tblUser.setSalt(salt);
				tblUser.setPassword(password);
				tblUserDao.setAutoCommit(false);
				// thêm thông tin user và lấy ra user id
				userid = tblUserDao.insertUser(tblUser);
				// Nếu insert thông tin user thành công và lấy được user id
				// Xét xem admin có thêm TĐTN cho user không
				if (userid != 0 && !"0".equals(codeLevel)) {
					tblUser.setUserId(userid);

					// set giá trị trình độ tiếng nhật cho user
					tblDetailUserJapan = Common.setTblDetailUserJapan(userInfor);
					tblDetailUserJapan.setUserId(userid);

					// Nếu có thực hiện insert TĐTN cho user
					check = tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan);
				}
				tblUserDao.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			tblUserDao.rollBack();
		} finally {
			tblUserDao.closeDB();
		}
		return check;
	}

	/**
	 * @see logic.TblUserLogic#isExistedUser(int)
	 */
	@Override
	public boolean isExistedUser(int userId) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		// biến kiểm tra user có tồn tại không
		boolean existedUser = tblUserDao.isExistedUser(userId);
		return existedUser;
	}

	/**
	 * @see logic.TblUserLogic#getUserInforById(int)
	 */
	@Override
	public UserInfor getUserInforById(int userId) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		UserInfor userInfor = new UserInfor();
		// Lấy thông tin user theo user id
		userInfor = tblUserDao.getUserInforById(userId);
		return userInfor;
	}

	/**
	 * @see logic.TblUserLogic#updateUserInfor(UserInfor)
	 */
	@Override
	public boolean updateUserInfor(UserInfor userInfor) throws SQLException {
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		TblUser tblUser = new TblUser();
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
		// biến kiểm tra update có thành công không
		boolean check = false;
		// Lấy thông tin người dùng nhập vào
		int userId = userInfor.getUserId();
		// Kiểm tra user có trình độ tiếng nhật không
		boolean ExistedUserCodeLevel = tblDetailUserJapanDaoImpl.isExistDetailUserJapan(userId);
		// Lấy thông tin TĐTN người dùng nhập vào
		String codeLevel = userInfor.getCodeLevel();
		try {
			// nếu connect đến db thành công
			if (tblUserDao.connectDB()) {
				// Lấy thông tin user
				tblUser = Common.setTblUser(userInfor);
				// Lấy thông tin detail user japan
				tblDetailUserJapan = Common.setTblDetailUserJapan(userInfor);
				tblUserDao.setAutoCommit(false);
				if (tblUserDao.updateUser(tblUser)) {
					// nếu thông tin user truyền vào có code level
					if (!"0".equals(codeLevel)) {
						tblDetailUserJapan.setUserId(userId);
						// nếu có code level đó trong db
						if (ExistedUserCodeLevel) {
							// update thông tin trình độ tiếng nhật
							check = tblDetailUserJapanDaoImpl.updateDetailUserJapan(tblDetailUserJapan);
						} else {
							// nếu không thì thêm mới trình độ tiếng nhật
							check = tblDetailUserJapanDaoImpl.insertDetailUserJapan(tblDetailUserJapan);
						}
						// nếu thông tin user truyền bào không có code level
					} else {
						// nếu trong db có code level của user đó
						if (ExistedUserCodeLevel) {
							// xóa thông tin trình độ tiếng nhật của user đó
							check = tblDetailUserJapanDaoImpl.deleteDetailUserJapan(tblDetailUserJapan.getUserId());
							// nếu trong db không có code level đó
						} else {
							// trả về true
							check = true;
						}
					}
				}
				tblUserDao.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// roll back nếu gặp lỗi
			tblUserDao.rollBack();
		} finally {
			tblUserDao.setAutoCommit(true);
			tblUserDao.closeDB();
		}
		return check;
	}

	/**
	 * @see logic.TblUserLogic#checkUserCodeLevel(int)
	 */
	@Override
	public boolean checkUserCodeLevel(int userId) {
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		TblDetailUserJapanDaoImpl tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
		// Kiểm tra trong database,user có trình độ tiếng nhật không
		tblDetailUserJapan = tblDetailUserJapanDao.getDetailUserJapanById(userId);
		// Nếu có trả về true
		if (tblDetailUserJapan.getCodeLevel() != null) {
			return true;
		} else {
			// nếu không có trả về false
			return false;
		}
	}

	/**
	 * @see logic.TblUserLogic#updatePass(String,int)
	 */
	@Override
	public boolean updatePass(String pass, int userId) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		// tạo chuỗi salt mã hóa mật khẩu
		String salt = Common.createSalt();
		// tạo mật khẩu đã có chuỗi salt mã hóa
		String password = Common.encryptPassword((salt + pass));
		// thay đổi pass người dùng
		boolean check = tblUserDao.updatePass(password, salt, userId);
		return check;
	}

	/**
	 * @see logic.TblUserLogic#deleteUser(int)
	 */
	@Override
	public boolean deleteUser(int userId) throws SQLException {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
		// Biến kiểm tra việc delete user có thành công hay không
		boolean check = false;
		// Biến kiểm tra user có trình độ tiếng nhật không
		boolean existedUserCodeLevel = tblDetailUserJapanDaoImpl.isExistDetailUserJapan(userId);
		try {
			// nếu connect đến db thành công
			if (tblUserDao.connectDB()) {
				tblUserDao.setAutoCommit(false);
				// nếu user có TĐTN thì xóa TĐTN của user
				if (existedUserCodeLevel) {
					check = tblDetailUserJapanDaoImpl.deleteDetailUserJapan(userId);
				}
				if (check) {
					// xóa thông tin của user
					check = tblUserDao.deleteUser(userId);
				}
				tblUserDao.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			tblUserDao.rollBack();
		} finally {
			tblUserDao.setAutoCommit(true);
			tblUserDao.closeDB();
		}
		return check;
	}
}
