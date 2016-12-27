package com.gold.action.admin;

import com.gold.entity.News;
import com.gold.entity.TeamGroup;
import com.gold.service.NewsService;
import com.gold.service.TeamGroupService;
import com.gold.util.ResponseUtils;
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
public class TeamGroupAct {
    @RequestMapping(value = "/teamgroup", method = RequestMethod.GET)
    public String teamgroup(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String name, Integer id, Integer pageNo, Integer pageSize) {
        model.addAttribute("pager", teamGroupService.getTeamGroupList(name, id, pageNo, pageSize));
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        return "admin/teamgroup/teamgroup";
    }
    @RequestMapping(value = "/teamgroup/detail", method = RequestMethod.GET)
    public String add(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (null != id)
            model.addAttribute("bean", teamGroupService.getToolsById(id));
        return "admin/teamgroup/detail";
    }
    @RequestMapping(value = "/teamgroup/check", method = RequestMethod.GET)
    public String check(Integer id, boolean readOnly, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (null != id)
            model.addAttribute("bean", teamGroupService.getToolsById(id));
        return "admin/teamgroup/check";
    }
    @RequestMapping(value = "/teamgroup/save", method = RequestMethod.POST)
    public String save(TeamGroup bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (null != bean.getId() && bean.getId() > 0)
            teamGroupService.update(bean);
        else
            teamGroupService.save(bean);
        return "redirect:/admin/teamgroup";
    }
    @RequestMapping(value = "/teamgroup/delete", method = RequestMethod.POST)
    public void delete(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        try {
            teamGroupService.delete(id);
            obj.addProperty("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("msg", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @Autowired
    private TeamGroupService teamGroupService;
}
