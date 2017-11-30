/**
 * Copyright(C) 2017 Luvina
 * MstJapan.java, Nov 2, 2017 Đinh Anh Tú
 */
package entity;

/**
 * Bean chứa các thuộc tính và phương thức của đối tượng MstJapan
 * 
 * @author AnhTu
 *
 */
public class MstJapan {
	private String code_level;
	private String name_level;

	/**
	 * 
	 */
	public MstJapan() {
		super();
	}

	/**
	 * @return the code_level
	 */
	public String getCode_level() {
		return code_level;
	}

	/**
	 * @param code_level
	 *            the code_level to set
	 */
	public void setCode_level(String code_level) {
		this.code_level = code_level;
	}

	/**
	 * @return the name_level
	 */
	public String getName_level() {
		return name_level;
	}

	/**
	 * @param name_level
	 *            the name_level to set
	 */
	public void setName_level(String name_level) {
		this.name_level = name_level;
	}

}
