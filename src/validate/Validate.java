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
	public List<String> validateLogin(String loginName, String password) {
		List<String> lstErr = new ArrayList<>();
		AdminProperties adminProp = new AdminProperties();
		MessageProperties messProp = new MessageProperties();
		// nếu thông tin nhập vào login là rỗng
		if (loginName.trim().length() == 0 || password.trim().length() == 0) {
			// thêm thông báo lỗi tương ứng
			lstErr.add(messProp.getMessageProperties("ER001_LOGIN"));
		}
		// nếu nhập đầy đủ
		if (loginName.trim().length() != 0 && password.trim().length() != 0) {
			// xét xem thông tin nhập vào có đúng không
			if (!adminProp.getAdminProperties("loginName").equals(loginName)
					|| !adminProp.getAdminProperties("password").equals(password)) {
				// nếu không đúng thì add lỗi vào list lỗi
				lstErr.add(messProp.getMessageProperties("ER004_LOGIN"));
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
	public List<String> validateUserInfor(UserInfor userInfor) {
		List<String> lstError = new ArrayList<>();
		MessageProperties messProp = new MessageProperties();
		Common common = new Common();
		TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
		MstGroupLogicImpl mstGroupLogic = new MstGroupLogicImpl();	
		
		// format check loginname
		String loginformat = "^[^0-9][a-zA-Z_0-9]+";
		boolean checkLoginName = userInfor.getloginName().matches(loginformat);

		// format check kana
		String kanaformat = "[ァ-・ヽヾ゛゜ー]";
		boolean checkKana = userInfor.getFullNameKana().matches(kanaformat);

		// check birthday
		List<Integer> lstBirthday = common.toArrayInteger(userInfor.getBirthday());
		String dateBirthday = common.convertToString(lstBirthday.get(0), lstBirthday.get(1), lstBirthday.get(2));
		boolean checkBirthday = common.isValidDate(dateBirthday);

		// check email
		String emailformat = "[a-zA-Z_0-9]+@[a-zA-Z_0-9]+";
		boolean checkEmail = userInfor.getEmail().matches(emailformat);

		// check password
		boolean checkPass = common.checkByte(userInfor.getPassword());

		// check start date
		List<Integer> lstStartDate = common.toArrayInteger(userInfor.getStartDate());
		String startDate = common.convertToString(lstStartDate.get(0), lstStartDate.get(1), lstStartDate.get(2));
		boolean checkStartDate = common.isValidDate(startDate);

		// check end date
		List<Integer> lstEndDate = common.toArrayInteger(userInfor.getEndDate());
		String endDate = common.convertToString(lstEndDate.get(0), lstEndDate.get(1), lstEndDate.get(2));
		boolean checkEndDate = common.isValidDate(endDate);

		// check existed login name
		boolean existedLoginName = tblUserLogic.checkExistedLoginName(userInfor.getloginName());

		// check existed email
		boolean existedEmail = tblUserLogic.checkExistedEmail(userInfor.getEmail());
		
		//check group id
		boolean checkGroupId = mstGroupLogic.existedGroupId(userInfor.getGroupId());

		// validate loginName
		if (userInfor.getloginName().trim().length() == 0) {
			// thêm thông báo lỗi không nhập
			lstError.add(messProp.getMessageProperties("ER001_LOGIN"));
		} else if (userInfor.getloginName().trim().length() < 4 || userInfor.getloginName().trim().length() > 15) {
			// thêm thông báo độ dài nhập không hợp lệ
			lstError.add(messProp.getMessageProperties("ER007_LOGIN"));
		} else if (checkLoginName != true) {
			// format login name không hợp lệ
			lstError.add(messProp.getMessageProperties("ER019_LOGIN"));
		} else if (existedLoginName) {
			// tên đăng nhập đã tồn tại
			lstError.add(messProp.getMessageProperties("ER003_LOGIN"));
		}

		// validate group id
		if (userInfor.getGroupId() == 0) {
			lstError.add(messProp.getMessageProperties("ER002_GROUPID"));
		}else if(!checkGroupId) {
			lstError.add(messProp.getMessageProperties("ER004_GROUPID"));
		}

		// validate fullname
		if (userInfor.getFullName().trim().length() == 0) {
			// thêm thông báo lỗi không nhập
			lstError.add(messProp.getMessageProperties("ER001_FULLNAME"));
		} else if (userInfor.getFullName().trim().length() > 255) {
			// nhập lớn hơn maxlength
			lstError.add(messProp.getMessageProperties("ER006_FULLNAME"));
		}

		// validate fullname kana
		if (userInfor.getFullNameKana().trim().length() > 255) {
			// thêm thông báo lỗi không nhập
			lstError.add(messProp.getMessageProperties("ER006_FULLNAMEKANA"));
		} else if (!checkKana) {
			// full name không phải kí tự kana
			lstError.add(messProp.getMessageProperties("ER009_FULLNAMEKANA"));
		}

		// validate ngày sinh
		if (!checkBirthday) {
			// ngày sinh không hợp lệ
			lstError.add(messProp.getMessageProperties("ER011_BIRTHDAY"));
		}

		// validate email
		if (userInfor.getEmail().trim().length() == 0) {
			// không nhập
			lstError.add(messProp.getMessageProperties("ER001_EMAIL"));
		} else if (!checkEmail) {
			// sai format
			lstError.add(messProp.getMessageProperties("ER005_EMAIL"));
		} else if (existedEmail) {
			//email đã tồn tại
			lstError.add(messProp.getMessageProperties("ER003_EMAIL"));
		} else if (userInfor.getEmail().trim().length() > 255) {
			// maxlength
			lstError.add(messProp.getMessageProperties("ER006_EMAIL"));
		}

		// validate tel
		if (userInfor.getTel().trim().length() == 0) {
			// không nhập
			lstError.add(messProp.getMessageProperties("ER001_TEL"));
		} else if (userInfor.getTel().trim().length() > 255) {
			// maxlength
			lstError.add(messProp.getMessageProperties("ER006_TEL"));
		}

		// validate pass
		if (userInfor.getPassword().trim().length() == 0) {
			// không nhập
			lstError.add(messProp.getMessageProperties("ER001_PASS"));
		} else if (!checkPass) {
			// nhập vào kí tự > 1byte
			lstError.add(messProp.getMessageProperties("ER008_PASS"));
		}

		// validate start date
		if (!checkStartDate) {
			// ngày không hợp lệ
			lstError.add(messProp.getMessageProperties("ER011_STARTDATE"));
		}

		// validate end date
		if (!checkEndDate) {
			// ngày không hợp lệ
			lstError.add(messProp.getMessageProperties("ER011_ENDDATE"));
		} else if (userInfor.getEndDate().before(userInfor.getStartDate())) {
			// ngày hết hạn nhỏ hơn ngày cấp chứng chỉ
			lstError.add(messProp.getMessageProperties("ER012_ENDDATE"));
		}

		// validate total
		if (Integer.toString(userInfor.getTotal()).trim().length() == 0) {
			// không nhập
			lstError.add(messProp.getMessageProperties("ER001_TOTAL"));
		}
		return lstError;
	}
}
