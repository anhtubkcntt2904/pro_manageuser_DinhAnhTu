/**
 * Copyright(C) 2017 Luvina
 * Validate.java, 16/10/2017 Đinh Anh Tú
 */
package validate;

import java.util.ArrayList;
import java.util.List;

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
	 * @param loginName tên đăng nhập
	 * @param password mật khẩu
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
}
