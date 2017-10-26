/**
 * Copyright(C) 2017 Luvina
 * MstGroupDaoImpl.java, 16/10/2017 Đinh Anh Tú
 */
package entity;

import java.util.Date;

/**
 * class chứa các thông tin của user
 * @author AnhTu
 *
 */
public class UserInfo {
	private int userId;
	private String fullName;
	private Date birthDay;
	private String groupName;
	private String email;
	private String tel;
	private String nameLevel;
	private Date endDate;
	private int total;
	/**
	 * @param userId
	 * @param fullName
	 * @param birthDay
	 * @param groupName
	 * @param email
	 * @param tel
	 * @param nameLevel
	 * @param endDate
	 * @param total
	 */
	public UserInfo(int userId, String fullName, Date birthDay, String groupName, String email, String tel,
			String nameLevel, Date endDate, int total) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.birthDay = birthDay;
		this.groupName = groupName;
		this.email = email;
		this.tel = tel;
		this.nameLevel = nameLevel;
		this.endDate = endDate;
		this.total = total;
	}
	/**
	 * 
	 */
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the birthDay
	 */
	public Date getBirthDay() {
		return birthDay;
	}
	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}
	/**
	 * @param nameLevel the nameLevel to set
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
