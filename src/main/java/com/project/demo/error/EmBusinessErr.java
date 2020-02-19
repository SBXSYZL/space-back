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
    SEARCH_USER_ERROR(2007, "搜索用户失败"),
    GET_INFO_ERROR(2008, "获取用户信息失败"),
    //excel文件错误
    EXCEL_FORMAT_ERROR(4001, "Excel文件格式错误"),
    PHONENUMBER_FORMAT_ERROR(4002, "手机号格式错误"),
    USER_IMPORT_ERROR(4003, "用户导入失败"),
    CREATE_EXCEL_ERROR(4004, "生成Excel文件失败"),
    //文件目录错误
    ROOT_JAR_PATH_ERROR(5001, "根目录文件路径错误"),
    CREATE_DIR_ERROR(5002, "创建目录失败"),
    DIR_EXISTS_ERROR(5003, "文件目录已存在"),
    DIR_DELETE_ERROR(5004, "文件目录删除失败"),
    //文件操作相关
    FILE_UPLOAD_ERROR(6001, "文件上传失败"),
    FILE_SAVE_ERROR(6002, "文件保存失败"),
    FILE_NOT_FOUND(6003, "找不到该文件"),
    GET_FILES_UNDER_FOLDER_ID_ERROR(6004, "获取目录下文件失败"),
    FILE_DELETE_ERROR(6005, "删除文件失败"),

    DELETE_FILE_ERROR(6005, "删除文件失败"),

    //课程相关
    COURSE_LIST_GET_ERROR(7001, "课程列表获取失败"),
    LESSON_LIST_GET_ERROR(7002, "课时列表获取失败"),
    CREATE_COURSE_ERROR(7003, "创建课程失败"),
    DELETE_COURSE_ERROR(7004, "删除课程失败"),
    GET_ELECTIVE_LIST_ERROR(7005, "获取选中该课程学生列表失败"),
    SEARCH_COURSE_ERROR(7006, "搜索课程出错"),
    CREATE_WORK_ERROR(7007, "创建课时失败"),
    GET_SUBMIT_WORK_ERROR(7008, "获取作业列表失败"),
    SUBMIT_EVAL_ERROR(7009, "提交评分失败"),
    SEARCH_WORK_ERROR(7010, "搜索课时失败"),
    COURSE_GRADING_ERROR(7011, "课程评分失败"),
    GET_SELECTED_COURSE_LIST_ERROR(7012, "获取已选择课程列表失败 "),
    GET_COURSE_SCORE_ERROR(7013, "获取成绩失败"),
    JOIN_COURSE_ERROR(7014, "加入课程失败"),
    GET_OPTIONAL_COURSE_LIST(7015, "获取可选课程列表失败"),
    GET_WORK_SCORE_ERROR(7016, "获取作业成绩失败"),
    SUBMIT_WORK_ERROR(7017, "提交作业失败"),
    DELETE_WORK_ERROR(7018, "删除作业失败"),

    //消息相关
    POST_MSG_ERROR(8001, "发送消息失败"),
    GET_MSG_LIST_ERROR(8002, "获取消息列表失败");
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
