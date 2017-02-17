package com.gold.common;

import com.gold.entity.AdminUser;
import com.gold.entity.AppToken;
import com.gold.service.TokenService;
import com.gold.util.ResponseUtils;
import com.gold.util.StringUtils;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huzuxing on 2016/9/30.
 */
public class WebInterceptor implements HandlerInterceptor {

    private final static String ADMIN_LOGIN_URL = "/admin/login";

    private final static String APP_PATH = "/admin/app";

    private final static String APP_LOGIN = "/admin/app/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        if (requestUrl.contains(APP_PATH)) {
            if (requestUrl.contains(APP_LOGIN)) {
                return true;
            }
            else {
                String token = request.getHeader("token");
                boolean unlogin = true;
                if(!StringUtils.isNullOrEmpty(token)) {
                    AppToken entity = tokenService.getToken(token);
                    if (null != entity)
                        unlogin = false;
                }
                if (unlogin) {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("code", 0);
                    obj.addProperty("message", "unlogin");
                    ResponseUtils.sendResponseJson(response, obj);
                    return false;
                }
            }
            return true;
        }
        else {
            String base = request.getSession().getServletContext().getContextPath();
            request.setAttribute("base", base);
            if (requestUrl.contains(ADMIN_LOGIN_URL))
                return true;
            String context = request.getContextPath();
            String adminUrl = null;
            if (-1 != requestUrl.indexOf(context)) {
                adminUrl = requestUrl.substring(context.length(), requestUrl.length());
            }
            AdminUser adminUser = null;
            if (adminUrl.startsWith("/admin") || adminUrl.startsWith("admin")) {
                adminUser = (AdminUser) request.getSession().getAttribute("adminUser");
            }
            if (null == adminUser) {
                response.sendRedirect("login");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Autowired
    private TokenService tokenService;
}
