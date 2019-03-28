package com.github.devswork.util.exception;

public interface ResponseStatusCode {

    // 验证手机号时，手机号格式非法
    int MOBILE_CHECK_ERROR = 101;

    // 验证手机号时，传入的业务类型错误
    int VERIFICATION_TYPE_CHECK_ERROR = 102;

    // 手机号已被绑定错误
    int MOBILE_ALREDAY_BIND_ERROR = 103;

    // 无效的 token
    int TOKEN_ERROR = 201;

    // 无效的refresh code
    int REFRESH_CODE_ERROR = 202;

    // 未登录错误，需要登录
    int NEED_LOGIN_ERROR = 203;

    // 微信调用错误
    int WX_ERROR_CALL = 204;

    // header 请求头设置错误
    int HEADER_ERROR = 205;

    // 数据库找不到此记录（通用）
    int NO_DATABASE_ERROR = 301;

    // 保存到数据库失败（通用）
    int SAVE_DATABASE_FAILED = 302;

    // 操作成功（通用）
    int OPERATE_SUCCEED = 400;

    // 操作失败（通用）
    int OPERATE_FAILED = 401;

    // 验证码错误
    int VERIFICATION_CODE_ERROR_DIED = 501;

    // 发生异常了，就返回繁忙
    int SERVER_BUSY = 600;

    // 不是直播的嘉宾
    int NOT_LIVE_GUEST = 701;

    // 没有权限开启直播
    int LIVE_NO_PERMISSION = 700;

    // 邀请码已过期
    int GUEST_CODE_FAILED = 801;

    // 邀请码已被使用
    int GUEST_CODE_USED = 802;

}
