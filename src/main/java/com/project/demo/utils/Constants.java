package com.project.demo.utils;

public final class Constants {
    //是否测试，是则自动填充session的必要信息,仅开发中测试时为true
    public static final boolean TEST = true;

    //session键名
    public static final String SESSION_USER_ID = "SESSION_USER_ID";
    public static final String SESSION_MEMBER_ID = "SESSION_MEMBER_ID";
    public static final String SESSION_MEMBER_ORGANIZATION_ID = "SESSION_MEMBER_ORGANIZATION_ID";
    public static final String SESSION_MEMBER_PERMISSION = "SESSION_MEMBER_PERMISSION";

    public static final String SESSION_ADMIN_ID = "SESSION_ADMIN_ID";


    //pageSize默认值
    public static final String DEFAULT_PAGE_SIZE = "10";

    //权限，LEVEL_1 表示一级权限，对应数据库中值为3，数据库中的值越大表示等级越高权限越大
    public static final Byte PERMISSION_LEVEL_1 = 3;
    public static final Byte PERMISSION_LEVEL_2 = 2;
    public static final Byte PERMISSION_LEVEL_3 = 1;
    public static final Byte PERMISSION_LEVEL_4 = 0;

    //图片验证码有效时间
    public static final Long CAPTCHA_TIME_OUT = 2 * 1000 * 60L; // 2分钟

    //成员识别码有效时间
    public static final Long MEMBER_ID_CODE_TIME_OUT = 30 * 1000 * 60L; // 30分钟

    public static final String USER = "user";
    public static final String ADMIN = "admin";

    //用户登记默认密码
    public static final String DEFAULT_PASSWORD = "123456";
    //用户默认角色
    public static final String DEFAULT_ROLE = "user";
}
