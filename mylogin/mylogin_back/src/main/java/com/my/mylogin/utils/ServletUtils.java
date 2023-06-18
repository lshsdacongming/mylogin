package com.my.mylogin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 客户端工具类
 * 
 * @author gq
 */
public class ServletUtils
{
    private static final Logger log = LoggerFactory.getLogger(ServletUtils.class);
    /**
     * 获取String参数
     */
    public static String getParameter(String name)
    {
        return getRequest().getParameter(name);
    }


    /**
     * 获取request
     */
    public static HttpServletRequest getRequest()
    {
        try
        {
            return getRequestAttributes().getRequest();
        } catch (NullPointerException ne){
            return getCurrentRequest();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 获取currentRequest
     */
    public static HttpServletRequest getCurrentRequest()
    {
        try
        {
            return currentRequestAttributes().getRequest();
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse()
    {
        try
        {
            return getRequestAttributes().getResponse();
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 获取session
     */
    public static HttpSession getSession()
    {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes()
    {
        try
        {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            return (ServletRequestAttributes) attributes;
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    public static ServletRequestAttributes currentRequestAttributes() {
        try {
            RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
            return (ServletRequestAttributes) attributes;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    public static Map<String, String> getHeaders(HttpServletRequest request)
    {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null)
        {
            while (enumeration.hasMoreElements())
            {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 是否是Ajax异步请求
     * 
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1)
        {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
        {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml"))
        {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.inStringIgnoreCase(ajax, "json", "xml"))
        {
            return true;
        }
        return false;
    }

    /**
     * 内容编码
     * 
     * @param str 内容
     * @return 编码后的内容
     */
    public static String urlEncode(String str)
    {
        try
        {
            return URLEncoder.encode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage(),e);
            return "";
        }
    }

    /**
     * 内容解码
     * 
     * @param str 内容
     * @return 解码后的内容
     */
    public static String urlDecode(String str)
    {
        try
        {
            if(str==null){
                return "";
            }
            return URLDecoder.decode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage(),e);
            return "";
        }
    }

    /**
     * 线程间共享request，在调用异步方法的外面将RequestAttributes对象设置为子线程共享
     */
    public static void setRequestAttributesInheritable(){
        //ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //RequestContextHolder.setRequestAttributes(sra, true);
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
    }
}
