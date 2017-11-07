/**
 * Copyright(C) 2017 Luvina
 * MstJapanDao.java, Nov 2, 2017 Đinh Anh Tú
 */
package dao;

import java.util.List;

import entity.MstJapan;

/**
 * interface chứa các phương thức thao tác với bảng mst_japan
 * 
 * @author AnhTu
 *
 */
public interface MstJapanDao {

	/**
	 * phương thức lấy ra các record của mst_japan
	 * 
	 * @return danh sách MstJapan
	 */
	public List<MstJapan> getAllMstJapan();
	
}
