package com.gold.action.admin;

import com.gold.entity.AdminUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huzuxing on 2016/9/21.
 */
@Controller
public class IndexAct
{
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        AdminUser adminUser = (AdminUser) request.getSession().getAttribute("adminUser");
        model.addAttribute("user", adminUser);
        return "admin/index";
    }
    @RequestMapping("/head")
    public String head(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "admin/head";
    }
    @RequestMapping("/left")
    public String left(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "admin/left";
    }
    @RequestMapping("/right")
    public String right(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "admin/right";
    }
    @RequestMapping("/welcome")
    public String welcome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "admin/welcome";
    }
}
