package com.project.demo.error;

public enum EmBusinessErr implements CommonError {

    //测试用无意义报错,一般用于测试回滚等机制使用
    TEST_ONLY_ERROR(0, "测试用报错"),


    //常规状态错误
    PARAMETER_INVALIDATION_ERROR(1001, "参数不合法"),
    UNKNOWN_ERROR(1002, "未知错误"),
    PERMISSION_DENIED(1003, "无权限"),
    NOT_LOGIN_USER(1004, "未登录"),
    //
    //用户相关错误
    USER_LOGIN_ERROR(2001, "登录失败"),
    USER_NOT_EXISTS(2002, "用户不存在"),
    USER_PSD_MODIFY_ERROR(2003, "用户密码修改失败"),
    USER_REGISTERED_ERROR(2004, "用户注册失败"),
    USER_ACCOUNT_EXISTS_ALREADY(2005, "该账号已存在"),
    USER_LIST_GET_ERROR(2006, "用户列表获取失败"),
    //excel文件错误
    EXCEL_FORMAT_ERROR(4001, "Excel文件格式错误"),
    PHONENUMBER_FORMAT_ERROR(4002, "手机号格式错误"),
    USER_IMPORT_ERROR(4003, "用户导入失败"),
    CREATE_EXCEL_ERROR(4004, "生成Excel文件失败"),
    //文件目录错误
    ROOT_JAR_PATH_ERROR(5001, "根目录文件路径错误"),
    //文件操作相关
    FILE_UPLOAD_ERROR(6001, "文件上传失败"),
    FILE_SAVE_ERROR(6002, "文件保存失败"),
    FILE_NOT_FOUND(6003, "找不到该文件"),
    CREATE_DIR_ERROR(6004, "创建目录失败"),
    DELETE_FILE_ERROR(6005, "删除文件失败"),

    //课程相关
    COURSE_LIST_GET_ERROR(7001, "课程列表获取失败"),
    LESSON_LIST_GET_ERROR(7002, "课时列表获取失败"),
    CREATE_COURSE_ERROR(7003, "创建课程失败"),
    DELETE_COURSE_ERROR(7004, "删除课程失败"),
    GET_ELECTIVE_LIST_ERROR(7005, "获取选中该课程学生列表失败"),
    SEARCH_COURSE_ERROR(7006, "搜索课程出错");
//


    private int errCode;
    private String msg;

    EmBusinessErr(int errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.msg;
    }

    @Override
    public void setErrMsg(String msg) {
        this.msg = msg;
    }
}
