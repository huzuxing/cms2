package com.gold.action.admin;

import com.gold.entity.News;
import com.gold.service.NewsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by huzuxing on 2016/9/22.
 */
@Controller
public class NewsAct {
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String title, Integer id, Integer pageNo, Integer pageSize) {
        model.addAttribute("pager", newsService.getNewsList(title, id, pageNo, pageSize));
        return "admin/news/news";
    }
    @RequestMapping(value = "/news/detail", method = RequestMethod.GET)
    public String add(Integer id, boolean readOnly, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (null != id) {
            News bean = newsService.getNewsById(id);
            model.addAttribute("bean", bean);
        }
        if (readOnly) {
            model.addAttribute("readOnly", readOnly);
        }
        return "admin/news/detail";
    }
    @RequestMapping(value = "/news/check", method = RequestMethod.GET)
    public String check(Integer id, boolean readOnly, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (null != id) {
            News bean = newsService.getNewsById(id);
            model.addAttribute("bean", bean);
        }
        return "admin/news/check";
    }
    @RequestMapping(value = "/news/save", method = RequestMethod.POST)
    public String save(News bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        newsService.save(bean);
        return "redirect:/admin/news";
    }
    @RequestMapping(value = "/news/delete", method = RequestMethod.POST)
    public void delete(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        try {
            newsService.delete(id);
            obj.addProperty("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("msg", "failure");
        }
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setPrettyPrinting().setVersion(1.0).create();
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(gson.toJson(obj));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out)
                out.close();
        }
    }

    @Autowired
    private NewsService newsService;
}
