package com.gold.action.admin;

import com.gold.entity.AdminUser;
import com.gold.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huzuxing on 2016/9/29.
 */
@Controller
public class LoginAct {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(Boolean status, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (null != status)
            model.addAttribute("status", status);
        return "admin/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        AdminUser adminUser = userService.login(username, password);
        if (null != adminUser) {
            request.getSession().setAttribute("adminUser", adminUser);
            return "redirect:/admin/index";
        }
        else {
            model.addAttribute("status", true);
            return "redirect:/admin/login";
        }
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().invalidate();
        return "redirect:/admin/login";
    }

    @Autowired
    private UserService userService;
}
