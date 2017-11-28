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
	TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
	Common common = new Common();
	TblUser tblUser = new TblUser();
	BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
	TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
	TblDetailUserJapanDaoImpl tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();

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
		tblUserDao = new TblUserDaoImpl();
		List<UserInfor> lstUser = new ArrayList<>();

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
		tblUserDao = new TblUserDaoImpl();

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
		tblUserDao = new TblUserDaoImpl();

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
		tblUserDao = new TblUserDaoImpl();
		tblUser = new TblUser();

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
		baseDaoImpl = new BaseDaoImpl();

		// Biến kiểm tra insert có thành công không
		Boolean check = true;
		//biến lấy user id của user vừa mới insert
		int userid;
		Connection conn = null;
		try {
			// thông tin để insert vào bảng user
			tblUser = new TblUser();
			// thông tin để insert vào bảng detail user japan
			tblDetailUserJapan = new TblDetailUserJapan();
			tblUserDao = new TblUserDaoImpl();
			tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();

			// Lấy thông tin nhập vào
			String salt = common.createSalt();
			String password = common.encryptPassword((salt + userInfor.getPassword()));
			// Lấy thông tin TĐTN user nhập vào
			String codeLevel = userInfor.getCodeLevel();

			// set giá trị cho user
			tblUser = common.setTblUser(userInfor);
			tblUser.setSalt(salt);
			tblUser.setPassword(password);

			conn = baseDaoImpl.connectDB();
			conn.setAutoCommit(false);

			// thêm thông tin user và lấy ra user id
			userid = tblUserDao.insertUser(tblUser);

			// Nếu insert thông tin user thành công và lấy được user id
			// Xét xem admin có thêm TĐTN cho user không
			if (userid != 0 && !"0".equals(codeLevel)) {

				// set giá trị trình độ tiếng nhật cho user
				tblDetailUserJapan = common.setTblDetailUserJapan(userInfor);
				tblDetailUserJapan.setUserId(userid);

				// Nếu có thực hiện insert TĐTN cho user
				check = tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan, conn);
			}

			conn.commit();
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
		tblUserDao = new TblUserDaoImpl();
		tblUser = new TblUser();

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
		tblUserDao = new TblUserDaoImpl();
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
		baseDaoImpl = new BaseDaoImpl();

		// biến kiểm tra update có thành công không
		Boolean check = false;
		Connection conn = null;
		try {
			tblDetailUserJapan = new TblDetailUserJapan();
			tblUser = new TblUser();
			tblUserDao = new TblUserDaoImpl();
			tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
			// biến kiểm tra user có tồn tại code level không
			Boolean ExistedUserCodeLevel = false;

			// Lấy thông in user
			int userId = userInfor.getUserId();
			String codeLevel = userInfor.getCodeLevel();
			String password = userInfor.getPassword();

			// set giá trị cho user
			tblUser = common.setTblUser(userInfor);
			// set password cho user
			tblUser.setPassword(password);
			// set giá trị cho detail
			tblDetailUserJapan.setUserId(userId);
			System.out.println(tblDetailUserJapan.getUserId());
//			tblDetailUserJapan = common.setTblDetailUserJapan(userInfor);
			Date startDate = userInfor.getStartDate();
			Date endDate = userInfor.getEndDate();
			String total = userInfor.getTotal();
			// Lấy thông tin detail user japan
			tblDetailUserJapan.setCodeLevel(codeLevel);
			tblDetailUserJapan.setStartDate(startDate);
			tblDetailUserJapan.setEndDate(endDate);
			tblDetailUserJapan.setTotal(total);
			System.out.println(tblDetailUserJapan.toString());
			// Kiểm tra user có trình độ tiếng nhật không
			ExistedUserCodeLevel = checkUserCodeLevel(userId);
			System.out.println(tblDetailUserJapan.getCodeLevel());
			conn = baseDaoImpl.connectDB();
			conn.setAutoCommit(false);
			System.out.println(tblDetailUserJapan.getCodeLevel());
			check = tblUserDao.updateUser(tblUser, conn);
//			if (check) {
				
//				System.out.println("total tyty: " + tblDetailUserJapan.getTotal());
//			}
			// nếu trong db, user có trình độ tiếng nhật
			if (ExistedUserCodeLevel) {
				// nếu người dùng xóa TĐTN
				if ("0".equals(codeLevel)) {
					check = tblDetailUserJapanDao.deleteDetailUserJapan(tblDetailUserJapan.getUserId(), conn);

					// nếu người dùng chỉnh sửa TĐTN hoặc không thay đổi
				} else {
					check = tblDetailUserJapanDao.updateDetailUserJapan(tblDetailUserJapan, conn);
				}
				// nếu trong db, user không có trình độ tiếng nhật
			} else {

				// nếu người dùng thêm TĐTN
				if (!"0".equals(codeLevel)) {
					check = tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan, conn);
				}
				// Nếu người dùng không thay đổi thì không thực hiện gì cả
			}
			conn.commit();
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
		tblDetailUserJapan = new TblDetailUserJapan();
		tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();

		// Kiểm tra trong database,user có trình độ tiếng nhật không
		tblDetailUserJapan = tblDetailUserJapanDao.getDetailUserJapanById(userId);
		
		// Nếu có trả về true
		if (tblDetailUserJapan.getCodeLevel() != null) {
			return true;
		}
		else {
			// nếu không có trả về false
			return false;
		}
	}
	/**
	 * @see logic.TblUserLogic#updatePass(String,int)
	 */
	@Override
	public Boolean updatePass(String pass, int userId) {
		tblUserDao = new TblUserDaoImpl();

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
		baseDaoImpl = new BaseDaoImpl();
		// Biến kiểm tra việc delete user có thành công hay không
		Boolean check = false;
		Connection conn = null;
		try {
			TblUserDaoImpl tblUserDao = new TblUserDaoImpl();
			TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
			// Biến kiểm tra user có trình độ tiếng nhật không
			Boolean ExistedUserCodeLevel = false;

			// Mở kết nối đến database
			conn = baseDaoImpl.connectDB();
			conn.setAutoCommit(false);

			// Kiểm tra user có TĐTN không
			ExistedUserCodeLevel = checkUserCodeLevel(userId);
			// nếu user có TĐTN thì xóa TĐTN của user
			if (ExistedUserCodeLevel) {
				check = tblDetailUserJapanDaoImpl.deleteDetailUserJapan(userId, conn);
			}

			// xóa thông tin của user
			check = tblUserDao.deleteUser(userId, conn);

			conn.commit();
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
