/**
 * Copyright(C) 2017 Luvina
 * Validate.java, 16/10/2017 Đinh Anh Tú
 */
package validate;

import java.util.ArrayList;
import java.util.List;

import common.Common;
import entity.UserInfor;
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
	private List<String> validateUserInfor(UserInfor userInfor) {
		List<String> lstError = new ArrayList<>();
		MessageProperties messProp = new MessageProperties();
		Common common = new Common();
		
		//format check loginname
		String loginformat = "^[^0-9][a-zA-Z_0-9]+";
		boolean checkLoginName = userInfor.getloginName().matches(loginformat);
		
		//format check kana
		String kanaformat = "[ァ-・ヽヾ゛゜ー]";
		boolean checkKana = userInfor.getFullNameKana().matches(kanaformat);
		
		//check birthday
		List<Integer> lstBirthday = common.toArrayInteger(userInfor.getBirthday());
		String dateBirthday = common.convertToString(lstBirthday.get(0), lstBirthday.get(1), lstBirthday.get(1));
		boolean checkBirthday = common.isValidDate(dateBirthday);
		 
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
		}

		// validate group id
		if (userInfor.getGroupId() == 0) {
			lstError.add(messProp.getMessageProperties("ER007_LOGIN"));
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
		}else if(!checkKana) {
			//full name không phải kí tự kana
			lstError.add(messProp.getMessageProperties("ER009_FULLNAMEKANA"));
		}
		
		//validate ngày sinh
		if(!checkBirthday) {
			//ngày sinh không hợp lệ
			lstError.add(messProp.getMessageProperties("ER011_BIRTHDAY"));
		}
		return lstError;
	}
}
