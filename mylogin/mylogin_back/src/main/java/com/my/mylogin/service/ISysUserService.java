package com.my.mylogin.service;

import com.my.mylogin.entity.SysUser;

/**
 * ISysUserService
 *
 * @ClassName com.my.mylogin.service.ISysUserService
 * @Author ll
 **/
public interface ISysUserService {
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser selectUserByUserName(String userName);
}
