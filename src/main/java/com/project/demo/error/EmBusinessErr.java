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
    USER_FOCUS_ENTER_ERROR(2004, "用户关注问题类型录入失败"),
    USER_FOCUS_GET_ERROR(2005, "获取用户关注问题类型成功"),
    USER_LIST_GET_ERROR(2005, "用户列表获取失败"),


    //文章相关
    ARTICLE_SEARCH_ERROR(3003, "文章查找出错"),
    ARTICLE_RELEASE_ERROR(3004, "文章发布失败"),
    ARTICLE_DETAIL_GET_ERROR(3005, "文章详情获取失败"),
    COMMENT_GET_ERROR(3006, "评论获取失败"),
    ARTICLE_DETAIL_GET_COMBINE_ERROR(3007, "文章获取整理失败"),
    COMMENT_POST_ERROR(3008, "评论失败"),
    EVAL_ARTICLE_POST_ERROR(3009, "评价失败"),
    EVAL_ARTICLE_GET_ERROR(3010, "获取评价失败"),
    ARTICLE_SHARE_ERROR(3011, "转发文章失败"),
    ARTICLE_MODIFY_ERROR(3012, "修改文章失败"),
    ARTICLE_GET_LIST_ERROR(3013, "获取文章列表失败"),
    ARTICLE_DELETE_ERROR(3014, "删除文章失败"),
    ARTICLE_REVIEW_ERROR(3015, "文章审核失败"),

    //excel文件错误
    EXCEL_FORMAT_ERROR(4001, "Excel文件格式错误"),
    PHONENUMBER_FORMAT_ERROR(4002, "手机号格式错误"),
    USER_IMPORT_ERROR(4003, "用户导入失败"),
    CREATE_EXCEL_ERROR(4004, "生成Excel文件失败"),


    //文件目录错误
    ROOT_JAR_PATH_ERROR(5001, "根目录文件路径错误"),

    //新闻相关
    NEWS_LIST_GET_ERROR(6001, "新闻列表获取失败"),
    NEWS_DETAIL_GET_ERROR(6002, "新闻详情获取失败"),
    NEWS_SEARCH_ERROR(6003, "搜索新闻失败"),
    NEWS_RELEASE_ERROR(6004, "新闻发布失败"),
    NEWS_DELETE_ERROR(6005, "删除新闻失败"),

    //食谱相关
    RECIPE_LIST_GET_ERROR(7001, "食谱列表获取失败"),
    RECIPE_DETAIL_GET_ERROR(7002, "食谱详情获取失败"),
    RECIPE_TYPE_GET_ERROR(7003, "食谱类型获取失败"),
    RECITE_WRITE_ERROR(7004, "食谱发布失败"),
    RECIPE_EVAL_ERROR(7005, "评价食谱失败"),
    RECIPE_EVAL_GET_ERROR(7006, "食谱评价获取失败"),
    RECIPE_COLLECT_ERROR(7007, "收藏食谱失败"),
    RECIPE_REVIEW_ERROR(7008, "食谱审核失败"),

    //食品图鉴相关
    FOOD_TYPE_GET_ERROR(8001, "获取食品类型失败"),
    FOOD_TYPE_POST_ERROR(8002, "设置食品类型失败"),
    FOOD_TYPE_DELETE_ERROR(8003, "删除食品类型失败"),
    FOOD_GET_ERROR(8004, "获取食品图鉴列表失败"),
    FOOD_DETAIL_GET_ERROR(8005, "食品图鉴详细信息获取失败"),
    FOOD_SEARCH_ERROR(8006, "食品图鉴搜索失败"),
    FOOD_WRITE_ERROR(8007, "编写食品图鉴失败"),
    FOOD_DELETE_ERROR(8008, "删除食品图鉴失败");
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
