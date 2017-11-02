/**
 * Copyright(C) 2017 Luvina
 * MstJapanLogicImpl.java , Nov 2, 2017, Anh Tu
 */
package logic.impl;

import java.util.List;

import dao.impl.MstJapanDaoImpl;
import entity.MstJapan;
import logic.MstJapanLogic;

/**
 * class implement các phương thức của interface MstJapanLogic
 * 
 * @author Anh Tu
 *
 */
public class MstJapanLogicImpl implements MstJapanLogic {

	@Override
	public List<MstJapan> getAllMstJapan() {
		// TODO Auto-generated method stub
		MstJapanDaoImpl mstJapanDao = new MstJapanDaoImpl();
		return mstJapanDao.getAllMstJapan();
	}

}
