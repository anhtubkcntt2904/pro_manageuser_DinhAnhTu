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
	public static final String LOGIN_REQUEST_URI = "/manage_user/Login.do";
	public static final String CONTEXT_ROOT = "/manage_user/";
	public static final String ADM002_SERVLET = "/ListUser.do";
	public static final String ADM001_SERVLET = "/Login.do";
	public static final String ERROR_SERVLET = "/Error.do";
	public static final String INDEX = "index.jsp";
	public static final String ADM002 = "/jsp/ADM002.jsp";
	public static final String ADM003 = "/jsp/ADM003.jsp";
	
	
	public static final String GROUP_ID_DEFAULT = "0";
	public static final String NAME_DEFAULT = null;
	
	
	public static final String SORTTYPE_DEFAULT = "full_name";
	public static final String SORTBYFULLNAME_DEFAULT = "asc";
	public static final String SORTBYCODELEVEL_DEFAULT = "asc";
	public static final String SORTBYENDDATE_DEFAULT = "desc";
	
	public static final int CURRENTPAGE_DEFAULT = 1;
	public static final int OFFSET_DEFAULT = 1;
	public static final int TOTALPAGE_DEFAULT = 1;
	
	public static final String MESS_ADM002_NORECORD = "MSG005_ADM002";
	
	
	public static final String SEARCH_TYPE = "search";
	public static final String SORT_TYPE = "sort";
	public static final String PAGING_TYPE = "paging";
	
	
	public static final String FULLNAME_SORT = "full_name";
	public static final String CODELEVEL_SORT = "code_level";
	public static final String ENDDATE_SORT = "end_date";
	
	
	public static final int START_YEAR = 1980;
	
	public static final String DRIVER_CONST = "driver";
	public static final String URL_CONST = "url";
	public static final String USER_CONST = "user";
	public static final String PASS_CONST = "pass";
}
