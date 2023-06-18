package com.my.mylogin.service;

import com.my.mylogin.domain.LoginUser;
import com.my.mylogin.utils.IdUtils;
import com.my.mylogin.utils.IpUtils;
import com.my.mylogin.utils.ServletUtils;
import com.my.mylogin.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService
{
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    @Resource
    private RedisService redisService;

    /** 60*60 */
    private final static long EXPIRE_TIME = 3600;

    private final static String ACCESS_TOKEN = "login_tokens:";

    protected static final long MILLIS_SECOND = 1000;

    public static final String HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUser loginUser)
    {
        // 生成token
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        loginUser.setUserid(loginUser.getSysUser().getUserId());
        loginUser.setUsername(loginUser.getSysUser().getUserName());
        loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        refreshToken(loginUser);

        // 保存或更新用户token
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("expires_in", EXPIRE_TIME);
        map.put("loginUser",loginUser);
        redisService.setCacheObject(ACCESS_TOKEN + token, loginUser, EXPIRE_TIME, TimeUnit.SECONDS);
        return map;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request)
    {
        if(request==null){
            return null;
        }
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String userKey = getTokenKey(token);
            LoginUser user = redisService.getCacheObject(userKey);
            return user;
        }
        return null;
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request)
    {
        if(request==null){
            return null;
        }
        //String token = ServletUtils.getRequest().getHeader(HEADER);
        String token = request.getHeader(HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX))
        {
            token = token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }

    public void delLoginUser(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String userKey = getTokenKey(token);
            redisService.deleteObject(userKey);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + EXPIRE_TIME * MILLIS_SECOND);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }
}
