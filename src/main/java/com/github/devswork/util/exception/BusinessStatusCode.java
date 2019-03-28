package com.github.devswork.util.exception;

/**
 * @author tian bowen
 * @class BusinessStatusCode
 * @date 2019-02-27 11:22
 * @description 开发中作为判断数据库是否执行成功的状态码
 */

public interface BusinessStatusCode {

    // 保存单条数据到数据库成功（通用）
    int SAVE_SINGLE_DATABASE_SUCCEED = 1;

    // 删除单条数据成功（通用）
    int DELETE_SINGLE_DATABASE_SUCCEED = 1;

    // 更新单条数据成功（通用）
    int UPDATE_SINGLE_DATABASE_SUCCEED = 1;


}
