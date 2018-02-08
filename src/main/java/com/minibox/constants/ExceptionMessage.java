package com.minibox.constants;

public class ExceptionMessage {

    /*ALL*/
    public static final String PARAMETER_IS_NOT_FULL = "检查信息是否填写完整";
    public static final String PHONE_NUMBER_IS_NOT_TRUE = "手机号格式不正确";
    public static final String TIME_PATTERN_IS_WRONG = "时间格式有误,按照 2018-01-01 12:00:00形式输入";
    public static final String TIME_IS_WRONG = "不能填写以前的时间";
    public static final String RESOURCE_NOT_FOUND= "资源不存在";

    /*userService*/
    public static final String USER_NAME_IS_USED = "用户名已经被注册过了";
    public static final String PHONE_NUMBER_IS_USED ="手机号已经被注册过了";
    public static final String USER_NAME_IS_TOO_LONG = "用户名不要超过十个字符";
    public static final String PASSWORD_IS_TOO_SHORT = "密码不要小于五个字符";
    public static final String PASSWORD_IS_TOO_LONG = "密码不要超过二十个字符";
    public static final String SEX_NOT_TRUE = "性别只能是男或者女";
    public static final String AUTHENTICATION_FAILURE = "认证错误";
    public static final String PASSWORD_IS_WRONG = "密码输入错误";

    /*reservationService*/
    public static final String NO_BOX = "箱子已经用完了";

    /*orderService*/
    public static final String BOX_SIZE_NOT_TRUE = "size 参数只能够是 \"大\" 或者 \"小\"";
}
