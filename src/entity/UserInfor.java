/**
 * Copyright(C) 2017 Luvina
 * UserInfor.java , Nov 2, 2017, Anh Tu
 */
package entity;

import java.util.Date;

/**
 * @author LA-AM
 *
 */
public class UserInfor {
	private String loginName;
	private int groupId;
	private String fullName;
	private String fullNameKana;
	private Date birthday;
	private String email;
	private String tel;
	private String password;
	private String codeLevel;
	private Date startDate;
	private Date endDate;
	private int total;

	/**
	 * @param loginName
	 * @param groupId
	 * @param fullName
	 * @param fullNameKana
	 * @param birthday
	 * @param email
	 * @param tel
	 * @param password
	 * @param codeLevel
	 * @param startDate
	 * @param endDate
	 * @param total
	 */
	public UserInfor(String loginName, int groupId, String fullName, String fullNameKana, Date birthday, String email,
			String tel, String password, String codeLevel, Date startDate, Date endDate, int total) {
		super();
		this.loginName = loginName;
		this.groupId = groupId;
		this.fullName = fullName;
		this.fullNameKana = fullNameKana;
		this.birthday = birthday;
		this.email = email;
		this.tel = tel;
		this.password = password;
		this.codeLevel = codeLevel;
		this.startDate = startDate;
		this.endDate = endDate;
		this.total = total;
	}

	/**
	 * 
	 */
	public UserInfor() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the loginName
	 */
	public String getloginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setloginName(String loginName) {
		this.loginName = loginName;
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
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the fullNameKana
	 */
	public String getFullNameKana() {
		return fullNameKana;
	}

	/**
	 * @param fullNameKana
	 *            the fullNameKana to set
	 */
	public void setFullNameKana(String fullNameKana) {
		this.fullNameKana = fullNameKana;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
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
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}

	/**
	 * @param codeLevel
	 *            the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
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
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

}
