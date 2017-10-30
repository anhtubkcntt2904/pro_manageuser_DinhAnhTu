/**
 * Copyright(C) 2017 Luvina
 * Constant.java, 16/10/2017 Đinh Anh Tú
 */
package common;
/**
 * class chứa các hằng số
 * @author AnhTu
 *
 */
public class Constant {
	public static final String LOGIN_REQUEST_URI = "/manage_user/doLogin";
	public static final String CONTEXT_ROOT = "/manage_user/";
	public static final String ADM002_SERVLET = "/doListUser";
	public static final String ADM001_SERVLET = "/doLogin";
	public static final String INDEX = "index.jsp";
	public static final String ADM002 = "/jsp/ADM002.jsp";
	
	
	public static final String GROUP_ID_DEFAULT = "0";
	public static final String NAME_DEFAULT = null;
	
	
	public static final String SORTTYPE_DEFAULT = "full_name";
	public static final String SORTBYFULLNAME_DEFAULT = "asc";
	public static final String SORTBYCODELEVEL_DEFAULT = "asc";
	public static final String SORTBYENDDATE_DEFAULT = "desc";
	
	public static final int CURRENTPAGE_DEFAULT = 1;
	public static final int OFFSET_DEFAULT = 1;
	public static final int TOTALPAGE_DEFAULT = 1;
}
