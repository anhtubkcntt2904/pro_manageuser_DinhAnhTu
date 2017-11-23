/**
 * Copyright(C) 2017 Luvina
 * Common.java, 16/10/2017 Đinh Anh Tú
 */
package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import entity.UserInfor;
import properties.ConfigProperties;

/**
 * class chứa các hàm common
 * 
 * @author Anh Tú
 *
 */
public class Common {

	/**
	 * Phương thức lấy list paging cần hiển thị
	 * 
	 * @param totalUser
	 *            tổng số bản ghi
	 * @param limit
	 *            số bản ghi tối đa trên 1 trang
	 * @param currentPage
	 *            trang hiện tại
	 * 
	 * @return danh sách paging
	 */
	public static List<Integer> getListPaging(int totalUser, int limit, int currentPage) {
		List<Integer> lstPaging = new ArrayList<>();
		int totalPage = getTotalPage(totalUser, limit);
		int currentRange = getCurrentRange(currentPage);
		int start = getStart(currentRange);
		int end = getEnd(currentRange);
		if (end > totalPage) {
			end = totalPage;
		}
		for (int i = start; i <= end; i++) {
			lstPaging.add(i);
		}
		return lstPaging;
	}

	/**
	 * phương thức tính ra vùng paging
	 * 
	 * @param currentPage
	 *            trang hiện tại
	 * @return vùng paging
	 */
	public static int getCurrentRange(int currentPage) {
		return (int) Math.ceil((double) currentPage / 3);
	}

	/**
	 * phương thức lấy ra start của một vùng paging
	 * 
	 * @param currentRange
	 *            vùng paging hiện tại
	 * @return start của vùng paging hiện tại
	 */
	public static int getStart(int currentRange) {
		return (currentRange - 1) * 3 + 1;
	}

	/**
	 * phương thức lấy ra end của một vùng paging
	 * 
	 * @param currentRange
	 *            vùng paging hiện tại
	 * @return end của vùng paging hiện tại
	 */
	public static int getEnd(int currentRange) {
		return currentRange * 3;
	}

	/**
	 * phương thức lấy số lượng hiển thị bản ghi trên 1 trang
	 * 
	 * @return số lượng bản ghi records cần lấy
	 */
	public static int getLimit() {
		ConfigProperties configProperties = new ConfigProperties();
		int limit = Integer.parseInt(configProperties.getConfigProperties("limit"));
		return limit;
	}

	/**
	 * phương thức tính tổng số trang để hiển thị màn hình
	 * 
	 * @param totalUser
	 * @param limit
	 * @return tổng số trang
	 */
	public static int getTotalPage(int totalUser, int limit) {
		return (int) Math.ceil((double) totalUser / limit);
	}

	/**
	 * phương thức lấy ra vị trí data cần lấy
	 * 
	 * @param currentPage
	 *            page hiện tại
	 * @param limit
	 *            số bản ghi trên 1 trang
	 * @return vị trí data cần lấy
	 */
	public static int getOffset(int currentPage, int limit) {
		int offSet = (currentPage - 1) * limit;
		return offSet;
	}

	/**
	 * Phương thức thay đổi type sort
	 * 
	 * @param type
	 * @return string sort đã thay đổi
	 */
	public static String changeType(String type) {
		if ("asc".equals(type)) {
			return "desc";
		} else
			return "asc";
	}

	/**
	 * phương thức trả về danh sách các tháng trong năm
	 * 
	 * @return danh sách các tháng trong năm
	 */
	public static List<Integer> getListMonth() {
		List<Integer> lstMonth = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			lstMonth.add(i);
		}
		return lstMonth;
	}

	/**
	 * phương thức lấy ra các ngày trong tháng
	 * 
	 * @return danh sách các ngày trong tháng
	 */
	public static List<Integer> getListDay() {
		List<Integer> lstDay = new ArrayList<>();
		for (int i = 1; i <= 31; i++) {
			lstDay.add(i);
		}
		return lstDay;
	}

	/**
	 * phương thức lấy ra các năm
	 * 
	 * @param yearStart
	 *            năm bắt đầu
	 * @param yearNow
	 *            năm hiện tại
	 * @return danh sách các năm tương ứng
	 */
	public static List<Integer> getListYear(int yearStart, int yearNow) {
		List<Integer> lstYear = new ArrayList<>();
		for (int i = yearStart; i <= yearNow; i++) {
			lstYear.add(i);
		}
		return lstYear;
	}

	/**
	 * phương thức lấy ra năm hiện tại
	 * 
	 * @return năm hiện tại
	 */
	public static int getYearNow() {
		int yearNow = Calendar.getInstance().get(Calendar.YEAR);
		return yearNow;
	}

	/**
	 * phương thức convert các số năm. Tháng ngày thành 1 ngày tháng có format
	 * yyyy/mm/dd
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return ngày tháng có format yyyy/mm/dd
	 */
	public static Date toDate(int year, int month, int day) {
		String date = convertToString(year, month, day);
		DateFormat sm = new SimpleDateFormat("yyyy/MM/dd");
		Date dateFmt = null;
		try {
			dateFmt = sm.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateFmt;
	}

	/**
	 * phương thức Convert các số năm. Tháng ngày thành 1 chuỗi ngày tháng có format
	 * yyyy/mm/dd
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return 1 chuỗi ngày tháng có format yyyy/mm/dd
	 */
	public static String convertToString(int year, int month, int day) {
		return String.format("%02d/%02d/%02d", year, month, day);
	}

	/**
	 * phương thức Convert date input to Year,Month,Day store in ArrayList
	 * 
	 * @param date
	 *            date cần convert
	 * @return list store year,month và day
	 */
	public static ArrayList<Integer> toArrayInteger(Date date) {
		ArrayList<Integer> lstDate = new ArrayList<>();
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));
		lstDate.add(year);
		lstDate.add(month);
		lstDate.add(day);
		return lstDate;
	}

	/**
	 * phương thức chuyển một String sang int
	 * 
	 * @param string
	 *            chuỗi cần parse
	 * @return số kiểu int
	 */
	public static int convertStringToInt(String string) {
		return Integer.parseInt(string);
	}

	/**
	 * phương thức kiếm tra ngày hợp lệ
	 * 
	 * @param dateValidate
	 *            ngày cần kiểm tra
	 * @return true or false
	 */
	public static boolean isValidDate(String dateValidate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		// Thông thường dữ liệu đầu vào nếu không hợp lệ,
		// Java Date sẽ tự đông chuyển đổi lại cho hợp lệ,
		// Vì vây ta cần tắt chức năng này đi để dữ liệu được kiểm tra đúng đắn.
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(dateValidate);

		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Phương thức kiểm tra kí tự 1 byte
	 * 
	 * @param string
	 *            chuỗi cần kiểm tra các kí tự
	 * @return true hoặc false
	 */
	public static boolean checkByte(String string) {
		boolean checkByte = true;
		// kiểm tra từng kí tự
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			int ascii = (int) c;
			// nếu kí tự đó có mã decimal tương ứng > 127
			// thì kí tự đó cần lớn hơn 1 byte để mã hóa
			if (ascii > 127) {
				checkByte = false;
				break;
			}

		}
		return checkByte;
	}

	/**
	 * Tạo salt bằng cách lấy thời gian hiện tại
	 * 
	 * @return chuỗi salt đã được tạo
	 */
	public static String createSalt() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	/**
	 * ER009 Kiểm tra chuỗi có phải là chuỗi kana hay không
	 * 
	 * @param str
	 *            chuỗi cần kiểm tra
	 * @return true nếu tất cả các kí tự trong chuỗi đều là kí tự kana
	 */
	public static boolean checkKana(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!isKana(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Kiểm tra kí tự có phải là kí tự kana hay không
	 * 
	 * @param c
	 *            kí tự cần kiểm tra
	 * @return true nếu là kí tự kana
	 */
	public static boolean isKana(char c) {
		return (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.KATAKANA);
	}

	/**
	 * hàm mã hóa password
	 * 
	 * @param password
	 * @return chuỗi đã được mã hóa
	 */
	public static String encryptPassword(String password) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	/**
	 * Hãm format từ byte sang string
	 * 
	 * @param hash
	 * @return string đã theo format
	 */
	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
