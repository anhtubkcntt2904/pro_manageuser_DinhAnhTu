/**
 * Copyright(C) 2017 Luvina
 * Group.java, 16/10/2017 Đinh Anh Tú
 */
package entity;

/**
 * Bean chứa các thuộc tính của bảng mst_group
 * 
 * @author AnhTu
 *
 */
public class MstGroup {
	private int groupId;
	private String groupName;

	/**
	 * @param groupId
	 * @param groupName
	 */
	public MstGroup(int groupId, String groupName) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
	}

	/**
	 * 
	 */
	public MstGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
