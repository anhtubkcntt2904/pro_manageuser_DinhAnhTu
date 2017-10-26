/**
 * Copyright(C) 2017 Luvina
 * MstGroupLogic.java, 16/10/2017 Đinh Anh Tú
 */
package logic;

import java.util.List;

import entity.MstGroup;

/**
 * interface chứa các thao tác với bảng mst_group
 * @author AnhTu
 *
 */
public interface MstGroupLogic {
	
	/**
	 * phương thức lấy tất cả các group trong bảng mst_group
	 * @return danh sách các group
	 */
	public List<MstGroup> getAllGroup();
}
