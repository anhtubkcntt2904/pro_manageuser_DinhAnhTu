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
	private int userId;
	private String loginName;
	private int groupId;
	private String fullName;
	private String fullNameKana;
	private Date birthday;
	private int yearbirthday, monthbirthday, daybirthday;
	private String email;
	private String tel;
	private String password;
	private String confirmpass;
	private String codeLevel;
	private Date startDate;
	private int yearvalidate, monthvalidate, dayvalidate;
	private Date endDate;
	private int yearinvalidate, monthinvalidate, dayinvalidate;
	private String total;
	private String nameLevel;
	private String groupName;

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

	/**
	 * @return the nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}

	/**
	 * @param nameLevel
	 *            the nameLevel to set
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}

	/**
	 * 
	 */
	public UserInfor() {
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
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
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
	 * @return the yearbirthday
	 */
	public int getYearbirthday() {
		return yearbirthday;
	}

	/**
	 * @param yearbirthday
	 *            the yearbirthday to set
	 */
	public void setYearbirthday(int yearbirthday) {
		this.yearbirthday = yearbirthday;
	}

	/**
	 * @return the monthbirthday
	 */
	public int getMonthbirthday() {
		return monthbirthday;
	}

	/**
	 * @param monthbirthday
	 *            the monthbirthday to set
	 */
	public void setMonthbirthday(int monthbirthday) {
		this.monthbirthday = monthbirthday;
	}

	/**
	 * @return the daybirthday
	 */
	public int getDaybirthday() {
		return daybirthday;
	}

	/**
	 * @param daybirthday
	 *            the daybirthday to set
	 */
	public void setDaybirthday(int daybirthday) {
		this.daybirthday = daybirthday;
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
	 * @return the confirmpass
	 */
	public String getConfirmpass() {
		return confirmpass;
	}

	/**
	 * @param confirmpass
	 *            the confirmpass to set
	 */
	public void setConfirmpass(String confirmpass) {
		this.confirmpass = confirmpass;
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
	 * @return the yearvalidate
	 */
	public int getYearvalidate() {
		return yearvalidate;
	}

	/**
	 * @param yearvalidate
	 *            the yearvalidate to set
	 */
	public void setYearvalidate(int yearvalidate) {
		this.yearvalidate = yearvalidate;
	}

	/**
	 * @return the monthvalidate
	 */
	public int getMonthvalidate() {
		return monthvalidate;
	}

	/**
	 * @param monthvalidate
	 *            the monthvalidate to set
	 */
	public void setMonthvalidate(int monthvalidate) {
		this.monthvalidate = monthvalidate;
	}

	/**
	 * @return the dayvalidate
	 */
	public int getDayvalidate() {
		return dayvalidate;
	}

	/**
	 * @param dayvalidate
	 *            the dayvalidate to set
	 */
	public void setDayvalidate(int dayvalidate) {
		this.dayvalidate = dayvalidate;
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
	 * @return the yearinvalidate
	 */
	public int getYearinvalidate() {
		return yearinvalidate;
	}

	/**
	 * @param yearinvalidate
	 *            the yearinvalidate to set
	 */
	public void setYearinvalidate(int yearinvalidate) {
		this.yearinvalidate = yearinvalidate;
	}

	/**
	 * @return the monthinvalidate
	 */
	public int getMonthinvalidate() {
		return monthinvalidate;
	}

	/**
	 * @param monthinvalidate
	 *            the monthinvalidate to set
	 */
	public void setMonthinvalidate(int monthinvalidate) {
		this.monthinvalidate = monthinvalidate;
	}

	/**
	 * @return the dayinvalidate
	 */
	public int getDayinvalidate() {
		return dayinvalidate;
	}

	/**
	 * @param dayinvalidate
	 *            the dayinvalidate to set
	 */
	public void setDayinvalidate(int dayinvalidate) {
		this.dayinvalidate = dayinvalidate;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

}
