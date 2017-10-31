/**
 * Copyright(C) 2017 Luvina
 * Common.java, 16/10/2017 Đinh Anh Tú
 */
package common;

import java.util.ArrayList;
import java.util.List;

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
		return (int) Math.ceil((double)totalUser / limit);
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
}
