package com.my.mylogin.service.impl;

import com.my.mylogin.entity.SysUser;
import com.my.mylogin.mapper.SysUserMapper;
import com.my.mylogin.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SysUserServiceImpl
 *
 * @ClassName com.my.mylogin.service.impl.SysUserServiceImpl
 * @Author ll
 **/
@Service
public class SysUserServiceImpl implements ISysUserService {
    @Resource
    private SysUserMapper userMapper;

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }
}
