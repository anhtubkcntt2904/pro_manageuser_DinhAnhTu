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
 * @author AnhTu
 *
 */
public class MstGroupLogicImpl implements MstGroupLogic{

	@Override
	public List<MstGroup> getAllGroup() {
		// TODO Auto-generated method stub
		MstGroupDaoImpl mstGroupDaoImpl = new MstGroupDaoImpl();
		return mstGroupDaoImpl.getAllMstGroup();
	}

	@Override
	public boolean existedGroupId(int groupid) {
		MstGroupDaoImpl mstGroupDao = new MstGroupDaoImpl();
		List<MstGroup> lstGroup = mstGroupDao.getAllMstGroup();
		for(int i = 0; i< lstGroup.size(); i++) {
			if(groupid == lstGroup.get(i).getGroupId()) {
				return true;
			}
		}
		return false;
	}

}
