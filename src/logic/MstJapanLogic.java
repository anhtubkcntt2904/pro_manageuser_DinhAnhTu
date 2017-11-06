/**
 * Copyright(C) 2017 Luvina
 * MstJapanLogic.java , Nov 2, 2017, Anh Tu
 */
package logic;

import java.util.List;

import entity.MstJapan;

/**
 * interface chứa các phương thức thao tác với bảng mst_japan
 * 
 * @author Anh Tu
 *
 */
public interface MstJapanLogic {
	/**
	 * phương thức lấy ra các record của mst_japan
	 * 
	 * @return danh sách MstJapan
	 */
	public List<MstJapan> getAllMstJapan();

	/**
	 * Phương thức kiểm tra tồn tại của code level
	 * 
	 * @param codelevel
	 * @return true hoặc false
	 */
	public boolean existedCodelevel(String codelevel);
}
