/**
 * Copyright(C) 2017 Luvina
 * Validate.java, 16/10/2017 Đinh Anh Tú
 */
package validate;

import java.util.ArrayList;
import java.util.List;

import common.Common;
import entity.UserInfor;
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;
import logic.impl.TblUserLogicImpl;
import properties.AdminProperties;
import properties.MessageProperties;

/**
 * class chứa phương thức validate thông tin đăng nhập
 * 
 * @author AnhTu
 *
 */
public class Validate {
	/**
	 * phương thức xác thực thông tin đăng nhập
	 * 
	 * @param loginName
	 *            tên đăng nhập
	 * @param password
	 *            mật khẩu
	 * @return Danh sách các lỗi
	 */
	public static List<String> validateLogin(String loginName, String password) {
		List<String> lstErr = new ArrayList<>();
		// nếu thông tin nhập vào login là rỗng
		if (loginName.length() == 0 || password.length() == 0) {
			// thêm thông báo lỗi tương ứng
			lstErr.add(MessageProperties.getMessageProperties("ER001_LOGIN"));
		}
		// nếu nhập đầy đủ
		else {
			// xét xem thông tin nhập vào có đúng không
			if (!AdminProperties.getAdminProperties("loginName").equals(loginName)
					|| !AdminProperties.getAdminProperties("password").equals(password)) {
				// nếu không đúng thì add lỗi vào list lỗi
				lstErr.add(MessageProperties.getMessageProperties("ER004_LOGIN"));
			}
		}
		return lstErr;
	}

	/**
	 * phương thức xác thực thông tin nhập vào màn hình ADM003
	 * 
	 * @param userInfor
	 *            thông tin user
	 * @return danh sách lỗi
	 */
	public static List<String> validateUserInfor(UserInfor userInfor) {
		List<String> lstError = new ArrayList<>();
		TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
		MstGroupLogicImpl mstGroupLogic = new MstGroupLogicImpl();
		MstJapanLogicImpl mstJapanLogic = new MstJapanLogicImpl();

		// format check loginname
		String loginformat = "^[^0-9][a-zA-Z_0-9]+";
		boolean checkLoginName = userInfor.getLoginName().matches(loginformat);

		// format check kana
		String kanaformat = "[ァ-・ヽヾ゛゜ー]+";
		boolean checkKana = userInfor.getFullNameKana().matches(kanaformat);

		// check birthday
		String dateBirthday = Common.convertToString(userInfor.getYearbirthday(), userInfor.getMonthbirthday(),
				userInfor.getDaybirthday());
		boolean checkBirthday = Common.isValidDate(dateBirthday);

		// check email
		String emailformat = "[a-zA-Z_.0-9]+@[a-zA-Z_.0-9]+";
		boolean checkEmail = userInfor.getEmail().matches(emailformat);

		boolean checkPass = true;
		if (userInfor.getPassword() != null) {
			// check password
			checkPass = Common.checkByte(userInfor.getPassword());
		}

		// check existed login name
		boolean existedLoginName = tblUserLogic.checkExistedLoginName(userInfor.getUserId(), userInfor.getLoginName());

		// check existed email
		boolean existedEmail = tblUserLogic.checkExistedEmail(userInfor.getUserId(), userInfor.getEmail());

		// check group id
		boolean checkGroupId = mstGroupLogic.existedGroupId(userInfor.getGroupId());

		// check tel
		String telformat = "[0-9]{1,4}-[0-9]{1,4}-[0-9]{1,4}";
		boolean checkTel = userInfor.getTel().matches(telformat);

		// Nếu user đang xét có trình độ tiếng nhật
		if (!"0".equals(userInfor.getCodeLevel())) {

			// check code level
			boolean checkCodeLevel = mstJapanLogic.existedCodelevel(userInfor.getCodeLevel());

			// check start date
			String startDate = Common.convertToString(userInfor.getYearvalidate(), userInfor.getMonthvalidate(),
					userInfor.getDayvalidate());
			boolean checkStartDate = Common.isValidDate(startDate);

			// check end date
			String endDate = Common.convertToString(userInfor.getYearinvalidate(), userInfor.getMonthinvalidate(),
					userInfor.getDayinvalidate());
			boolean checkEndDate = Common.isValidDate(endDate);

			// check total
			String totalhalfsize = "[0-9]+";
			boolean checkTotal = userInfor.getTotal().matches(totalhalfsize);

			// validate start date
			if (!checkStartDate) {
				// ngày không hợp lệ
				lstError.add(MessageProperties.getMessageProperties("ER011_STARTDATE"));
			}

			// validate code level
			if (!checkCodeLevel) {
				// code level không tồn tại
				lstError.add(MessageProperties.getMessageProperties("ER004_CODELEVEL"));
			}

			// validate end date
			if (!checkEndDate) {
				// ngày không hợp lệ
				lstError.add(MessageProperties.getMessageProperties("ER011_ENDDATE"));
			} else if (userInfor.getEndDate().before(userInfor.getStartDate())
					|| userInfor.getEndDate().equals(userInfor.getStartDate())) {
				// ngày hết hạn nhỏ hơn ngày cấp chứng chỉ
				lstError.add(MessageProperties.getMessageProperties("ER012_ENDDATE"));
			}

			// validate total
			if (userInfor.getTotal().trim().length() == 0) {
				// không nhập
				lstError.add(MessageProperties.getMessageProperties("ER001_TOTAL"));
			} else if (!checkTotal) {
				lstError.add(MessageProperties.getMessageProperties("ER0018_TOTAL"));
			}
		}

		// trường hợp add thì validate login
		if (userInfor.getUserId() == 0) {
			// validate loginName
			if (userInfor.getLoginName().trim().length() == 0) {
				// thêm thông báo lỗi không nhập
				lstError.add(MessageProperties.getMessageProperties("ER001_LOGINNAME"));
			} else if (userInfor.getLoginName().trim().length() < 4 || userInfor.getLoginName().trim().length() > 15) {
				// thêm thông báo độ dài nhập không hợp lệ
				lstError.add(MessageProperties.getMessageProperties("ER007_LOGINNAME"));
			} else if (checkLoginName != true) {
				// format login name không hợp lệ
				lstError.add(MessageProperties.getMessageProperties("ER019_LOGINNAME"));
			} else if (existedLoginName) {
				// tên đăng nhập đã tồn tại
				lstError.add(MessageProperties.getMessageProperties("ER003_LOGINNAME"));
			}
		}

		// validate group id
		if (userInfor.getGroupId() == 0) {
			// không chọn group id
			lstError.add(MessageProperties.getMessageProperties("ER002_GROUPID"));
		} else if (!checkGroupId) {
			// group id không tồn tại
			lstError.add(MessageProperties.getMessageProperties("ER004_GROUPID"));
		}

		// validate fullname
		if (userInfor.getFullName().trim().length() == 0) {
			// thêm thông báo lỗi không nhập
			lstError.add(MessageProperties.getMessageProperties("ER001_FULLNAME"));
		} else if (userInfor.getFullName().trim().length() > 255) {
			// nhập lớn hơn maxlength
			lstError.add(MessageProperties.getMessageProperties("ER006_FULLNAME"));
		}

		// validate fullname kana
		if (userInfor.getFullNameKana().trim().length() > 255) {
			// thêm thông báo lỗi không nhập
			lstError.add(MessageProperties.getMessageProperties("ER006_FULLNAMEKANA"));
		} else if (!Common.checkKana(userInfor.getFullNameKana())) {
			lstError.add(MessageProperties.getMessageProperties("ER009_FULLNAMEKANA"));
		}

		// validate ngày sinh
		if (!checkBirthday) {
			// ngày sinh không hợp lệ
			lstError.add(MessageProperties.getMessageProperties("ER011_BIRTHDAY"));
		}

		// validate email
		if (userInfor.getEmail().trim().length() == 0) {
			// không nhập
			lstError.add(MessageProperties.getMessageProperties("ER001_EMAIL"));
		} else if (!checkEmail) {
			// sai format
			lstError.add(MessageProperties.getMessageProperties("ER005_EMAIL"));
		} else if (existedEmail) {
			// email đã tồn tại
			lstError.add(MessageProperties.getMessageProperties("ER003_EMAIL"));
		} else if (userInfor.getEmail().trim().length() > 255) {
			// maxlength
			lstError.add(MessageProperties.getMessageProperties("ER006_EMAIL"));
		}

		// validate tel
		if (userInfor.getTel().trim().length() == 0) {
			// không nhập
			lstError.add(MessageProperties.getMessageProperties("ER001_TEL"));
		} else {
			if (!checkTel)
				// không đúng định dạng
				lstError.add(MessageProperties.getMessageProperties("ER005_TEL"));
			else {
				if (userInfor.getTel().trim().length() > 14) {
					// maxlength
					lstError.add(MessageProperties.getMessageProperties("ER006_TEL"));
				}
			}
		}

		// xét trường hợp có validate password
		if (userInfor.getPassword() != null) {
			// validate pass
			if (userInfor.getPassword().trim().length() == 0) {
				// không nhập
				lstError.add(MessageProperties.getMessageProperties("ER001_PASS"));
			} else if (!checkPass) {
				// nhập vào kí tự > 1byte
				lstError.add(MessageProperties.getMessageProperties("ER008_PASS"));
			} else if (userInfor.getPassword().trim().length() < 5 || userInfor.getPassword().trim().length() > 15) {
				lstError.add(MessageProperties.getMessageProperties("ER007_PASS"));
			}

			// validate confirm pass
			if (!userInfor.getPassword().equals(userInfor.getConfirmpass())) {
				// pass không trùng
				lstError.add(MessageProperties.getMessageProperties("ER017_CONFIRMPASS"));
			}

		}

		return lstError;
	}

	public static List<String> validatePass(String newpass, String confirmpass) {
		List<String> lstError = new ArrayList<>();
		// validate pass
		if (newpass.length() == 0 || newpass == null) {
			// không nhập
			lstError.add(MessageProperties.getMessageProperties("ER001_PASS"));
		} else if (!Common.checkByte(newpass)) {
			// nhập vào kí tự > 1byte
			lstError.add(MessageProperties.getMessageProperties("ER008_PASS"));
		} else if (newpass.length() < 5 || newpass.length() > 15) {
			lstError.add(MessageProperties.getMessageProperties("ER007_PASS"));
		}
		// validate confirm pass
		if (!newpass.equals(confirmpass)) {
			// pass không trùng
			lstError.add(MessageProperties.getMessageProperties("ER017_CONFIRMPASS"));
		}

		return lstError;
	}
}
