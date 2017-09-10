/**
 * 
 */
package com.caijia.rest.consts;

/**
 * @author lxy
 *
 */
public interface ErrorMessage {
	public static String SQL_ERROR = "数据库错误";
	public static String LOGOUT_DATABASE_ERROR="用户logout系统时发生数据库错误";
	public static String NO_TOKEN_PROVIDED_ERROR = "未提供身份认证令牌";
	public static String UPLOAD_TO_CE_ERROR = "上传文档到CE时发生错误";
	public static String UPLOAD_DATABASE_ERROR = "上传文档到CE时发生数据库错误";
	public static String GROOVY_ERROR = "解析groovy脚本时出错";
	public static String NO_RULE_FOUND_ERROR = "没有找到相关的属性解析规则";
	public static String DOC_DUPLICATION_ERROR = "所上传的文档已经在服务器上存在";
	public static String FILE_UPLOADTYPE_RULE_NOT_FOUND_ERROR = "未定义CE中的目录创建规则";
	public static String NO_PROPERTY_DEFINED = "没有找到上传文档所需要的文档属性值";
	public static String DELETE_FILE_ERROR="删除文档时错误";
	public static String VERSION_NUMBER_INCOMPATIBLE = "不支持客户端版本号";
	public static String TOO_MANY_LOGON_ATTEMP = "输入密码错误次数超过一天内的允许范围";
	public static String USERCODE_PASSWORD_ERROR = "员工号或密码不正确, 24小时内最多输入错误3次，您今天已经输入错误";
	public static String NO_RULE_OPER="该用户现在无权操作,请刷新界面,获取最新记录";

}
