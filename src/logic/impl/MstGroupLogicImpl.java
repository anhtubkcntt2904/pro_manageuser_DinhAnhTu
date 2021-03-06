/**
 * Copyright(C) 2017 Luvina
 * MstGroupLogicImpl.java, 16/10/2017 Đinh Anh Tú
 */
package logic.impl;

import java.util.List;

import dao.impl.MstGroupDaoImpl;
import entity.MstGroup;
import logic.MstGroupLogic;

/**
 * class implement các phương thức của interface MstGroupLogic
 * 
 * @author AnhTu
 *
 */
public class MstGroupLogicImpl implements MstGroupLogic {

	/**
	 * @see logic.MstGroupLogic#getAllGroup()
	 */
	@Override
	public List<MstGroup> getAllGroup() {
		MstGroupDaoImpl mstGroupDaoImpl = new MstGroupDaoImpl();
		List<MstGroup> lstGroup = mstGroupDaoImpl.getAllMstGroup();
		return lstGroup;
	}

	/**
	 * @see logic.MstGroupLogic#existedGroupId(int)
	 */
	@Override
	public boolean existedGroupId(int groupid) {
		MstGroupDaoImpl mstGroupDao = new MstGroupDaoImpl();
		// Lấy danh sách các group
		List<MstGroup> lstGroup = mstGroupDao.getAllMstGroup();
		for (int i = 0; i < lstGroup.size(); i++) {
			// nếu tồn tại group với group id truyền vào
			if (groupid == lstGroup.get(i).getGroupId()) {
				// trả về true
				return true;
			}
		}
		// trả về false
		return false;
	}

}
