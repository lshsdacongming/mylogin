package com.my.mylogin.controller;

import com.my.mylogin.domain.LoginBody;
import com.my.mylogin.domain.LoginUser;
import com.my.mylogin.domain.R;
import com.my.mylogin.entity.SysUser;
import com.my.mylogin.service.ISysUserService;
import com.my.mylogin.service.TokenService;
import com.my.mylogin.utils.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * LoginController
 *
 * @ClassName com.my.mylogin.controller.LoginController
 * @Author ll
 **/
@RestController
public class LoginController {
    @Resource
    ISysUserService sysUserService;
    @Resource
    TokenService tokenService;

    @PostMapping("/login")
    public R<?> login(@RequestBody LoginBody form){
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(form.getUsername(), form.getPassword()))
        {
            throw new RuntimeException("用户/密码必须填写");
        }
        // 用户登录
        SysUser sysUser = sysUserService.selectUserByUserName(form.getUsername());
        LoginUser sysUserVo = new LoginUser();
        sysUserVo.setSysUser(sysUser);
        if(StringUtils.isNull(sysUser)){
            throw new RuntimeException("登录用户：" + form.getUsername() + " 不存在");
        }
        if(!sysUser.getPassword().equals(form.getPassword())){
            throw new RuntimeException("用户不存在/密码错误");
        }

        // 获取登录token
        return R.ok(tokenService.createToken(sysUserVo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            //String username = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
        }
        return R.ok();
    }
}
