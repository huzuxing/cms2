package com.gold.app;

import com.gold.entity.AdminUser;
import com.gold.entity.AppToken;
import com.gold.service.TokenService;
import com.gold.service.UserService;
import com.gold.util.ResponseUtils;
import com.gold.util.StringUtils;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huzuxing on 2016/10/25.
 */
@Controller
public class AppLoginAct {

    @RequestMapping(value = "/app/login", method = RequestMethod.POST)
    public void app_login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        JsonObject obj = new JsonObject();
        AppToken token = tokenService.getToken();
        AdminUser bean = userService.getAdminUser(username);
        if (null != token) {
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            JsonObject userObj = new JsonObject();
            userObj.addProperty("username", bean.getUsername());
            userObj.addProperty("userid", bean.getId());
            userObj.addProperty("token", token.getToken());
            obj.add("data", userObj);
        }
        else if (null == token && null != bean && password.equals(bean.getPassword())) {
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            JsonObject userObj = new JsonObject();
            userObj.addProperty("username", bean.getUsername());
            userObj.addProperty("userid", bean.getId());
            userObj.addProperty("token", tokenService.generateToken());
            obj.add("data", userObj);
        }
        else {
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/logout", method = RequestMethod.POST)
    public void app_logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        JsonObject obj = new JsonObject();
        if (!StringUtils.isNullOrEmpty(token)) {
            AppToken entity = tokenService.getToken();
            if (null != entity) {
                tokenService.delete();
                obj.addProperty("code", 200);
                obj.addProperty("message", "success");
            }
            else {
                obj.addProperty("code", 0);
                obj.addProperty("message", "already logout");
            }
        }
        else {
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        response.setHeader("token", "");
        ResponseUtils.sendResponseJson(response, obj);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;
}
