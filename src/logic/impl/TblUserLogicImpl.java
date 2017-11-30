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
	public static List<String> lstErr = new ArrayList<>();

	/**
	 * @see logic.TblUserLogic#existLogin(String,String)
	 */
	@Override
	public boolean existLogin(String loginName, String password) {
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

	/**
	 * @see logic.TblUserLogic#getListUser()
	 */
	@Override
	public List<UserInfor> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullname, String sortByCodeLevel, String sortByEndDate) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		List<UserInfor> lstUser = new ArrayList<>();
		Common common = new Common();

		// Nếu full name không null
		if (fullName != null) {
			fullName = common.replaceWildCard(fullName);
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
		Common common = new Common();

		int totalUser = 0;
		// Nếu full name không null
		if (fullName != null) {
			fullName = common.replaceWildCard(fullName);
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
	public Boolean createUser(UserInfor userInfor) throws SQLException {
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
		// Biến kiểm tra insert có thành công không
		Boolean check = true;
		Connection conn = null;
		
		try {
			conn = baseDaoImpl.connectDB();
			// nếu connect đến db thành công
			if (conn != null) {		
				// thông tin để insert vào bảng user
				TblUser tblUser = new TblUser();
				Common common = new Common();
				// thông tin để insert vào bảng detail user japan
				TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
				TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
				TblDetailUserJapanDaoImpl tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
				int userid;
				
				// Lấy thông tin nhập vào
				int userId = userInfor.getUserId();
				String salt = common.createSalt();
				String password = common.encryptPassword((salt + userInfor.getPassword()));
				// Lấy thông tin TĐTN user nhập vào
				String codeLevel = userInfor.getCodeLevel();

				// set giá trị cho user
				tblUser = common.setTblUser(userInfor);
				tblUser.setSalt(salt);
				tblUser.setPassword(password);

				conn.setAutoCommit(false);
				// thêm thông tin user và lấy ra user id
				userid = tblUserDao.insertUser(tblUser, conn);
				// Nếu insert thông tin user thành công và lấy được user id
				// Xét xem admin có thêm TĐTN cho user không
				if (userid != 0 && !"0".equals(codeLevel)) {
					tblUser.setUserId(userid);

					// set giá trị trình độ tiếng nhật cho user
					tblDetailUserJapan = common.setTblDetailUserJapan(userInfor);
					tblDetailUserJapan.setUserId(userid);

					// Nếu có thực hiện insert TĐTN cho user
					check = tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan, conn);
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			baseDaoImpl.closeDB(conn);
		}
		return check;
	}

	/**
	 * @see logic.TblUserLogic#isExistedUser(int)
	 */
	@Override
	public boolean isExistedUser(int userId) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		TblUser tblUser = new TblUser();
		Common common = new Common();

		// biến kiểm tra user có tồn tại không
		boolean existedUser = false;
		// Lấy user theo user id
		tblUser = tblUserDao.getUserById(userId);
		// nếu tồn tại user thì trả về true
		if (tblUser.getUserId() != 0) {
			existedUser = true;
		}
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
	public Boolean updateUserInfor(UserInfor userInfor) throws SQLException {
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
		Common common = new Common();
		// biến kiểm tra update có thành công không
		Boolean check = false;
		Connection conn = null;
		
		try {
			conn = baseDaoImpl.connectDB();
			// nếu connect đến db thành công
			if (conn != null) {			
				TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
				TblUser tblUser = new TblUser();
				TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
				TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
				// Kiểm tra user có trình độ tiếng nhật không
				Boolean ExistedUserCodeLevel = false;
				
				// Lấy thông tin người dùng nhập vào
				int userId = userInfor.getUserId();
				// Lấy thông tin TĐTN người dùng nhập vào
				String codeLevel = userInfor.getCodeLevel();

				// Lấy thông tin user
				tblUser = common.setTblUser(userInfor);
				// Lấy thông tin detail user japan
				tblDetailUserJapan = common.setTblDetailUserJapan(userInfor);
				ExistedUserCodeLevel = checkUserCodeLevel(userId);
				
				conn.setAutoCommit(false);
				check = tblUserDao.updateUser(tblUser, conn);
				//nếu update user thành công thì set user id cho tblDetailUserJapan
				if (check) {
					tblDetailUserJapan.setUserId(userId);
				}
				// nếu trong db, user có trình độ tiếng nhật
				if (ExistedUserCodeLevel) {
					// nếu người dùng xóa TĐTN
					if ("0".equals(codeLevel)) {
						check = tblDetailUserJapanDaoImpl.deleteDetailUserJapan(tblDetailUserJapan.getUserId(), conn);
						// nếu người dùng chỉnh sửa TĐTN hoặc không thay đổi
					} else {
						check = tblDetailUserJapanDaoImpl.updateDetailUserJapan(tblDetailUserJapan, conn);
					}
					// nếu trong db, user không có trình độ tiếng nhật
				} else {
					// nếu người dùng thêm TĐTN
					if (!"0".equals(codeLevel)) {
						check = tblDetailUserJapanDaoImpl.insertDetailUserJapan(tblDetailUserJapan, conn);
					}
					// Nếu người dùng không thay đổi thì không thực hiện gì cả
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// roll back nếu gặp lỗi
			conn.rollback();
		} finally {
			conn.setAutoCommit(true);
			baseDaoImpl.closeDB(conn);
		}
		return check;
	}

	/**
	 * @see logic.TblUserLogic#checkUserCodeLevel(int)
	 */
	@Override
	public Boolean checkUserCodeLevel(int userId) {
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
	public Boolean updatePass(String pass, int userId) {
		TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
		Common common = new Common();

		// Biến kiểm tra việc update pass có thành công không
		Boolean check = false;
		// tạo chuỗi salt mã hóa mật khẩu
		String salt = common.createSalt();
		// tạo mật khẩu đã có chuỗi salt mã hóa
		String password = common.encryptPassword((salt + pass));

		// thay đổi pass người dùng
		check = tblUserDao.updatePass(password, salt, userId);
		return check;
	}

	/**
	 * @see logic.TblUserLogic#deleteUser(int)
	 */
	@Override
	public Boolean deleteUser(int userId) throws SQLException {
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();	
		// Biến kiểm tra việc delete user có thành công hay không
		Boolean check = false;
		Connection conn = null;
		
		try {
			// Mở kết nối đến database
			conn = baseDaoImpl.connectDB();
			// nếu connect đến db thành công
			if (conn != null) {
				
				TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
				TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
				// Biến kiểm tra user có trình độ tiếng nhật không
				Boolean ExistedUserCodeLevel = false;
				// Kiểm tra user có TĐTN không
				ExistedUserCodeLevel = checkUserCodeLevel(userId);

				conn.setAutoCommit(false);
				// nếu user có TĐTN thì xóa TĐTN của user
				if (ExistedUserCodeLevel) {
					check = tblDetailUserJapanDaoImpl.deleteDetailUserJapan(userId, conn);
				}
				// xóa thông tin của user
				check = tblUserDao.deleteUser(userId, conn);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.setAutoCommit(true);
			baseDaoImpl.closeDB(conn);
		}
		return check;
	}
}
