package com.my.mylogin.mapper;

import com.my.mylogin.entity.SysUser;

/**
 * SysUserMapper
 *
 * @ClassName com.my.mylogin.mapper.SysUserMapper
 * @Author ll
 **/
public interface SysUserMapper {
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser selectUserByUserName(String userName);
}
