package com.gold.action.admin;

import com.gold.common.Pager;
import com.gold.entity.Staff;
import com.gold.entity.TeamGroup;
import com.gold.service.StaffService;
import com.gold.service.TeamGroupService;
import com.gold.util.ResponseUtils;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huzuxing on 2016/9/22.
 */
@Controller
public class StaffAct {
    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    public String staff(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String name, Integer id, Integer pageNo, Integer pageSize) {
        Pager<Staff> pager = staffService.getStaffList(name, id, pageNo, pageSize);
        List<String> teamgroups = new ArrayList<>();
        for(Staff bean : pager.getList()) {
            TeamGroup entity = teamGroupService.getTeamGroupById(bean.getTeamId());
            String team = (null == entity) ? null : entity.getName();
            teamgroups.add(team);
        }
        model.addAttribute("pager", pager);
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        model.addAttribute("teams", teamgroups);
        return "admin/staff/staff";
    }
    @RequestMapping(value = "/staff/detail", method = RequestMethod.GET)
    public String add(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (null != id)
            model.addAttribute("bean", staffService.getStaffById(id));
        model.addAttribute("teamgroup", teamGroupService.getTeamGroups());
        return "admin/staff/detail";
    }
    @RequestMapping(value = "/staff/check", method = RequestMethod.GET)
    public String check(Integer id, boolean readOnly, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (null != id) {
            Staff bean = staffService.getStaffById(id);
            model.addAttribute("bean", bean);
            TeamGroup entity = teamGroupService.getTeamGroupById(bean.getTeamId());
            model.addAttribute("teamgroup", null == entity ? null : entity.getName());
        }
        return "admin/staff/check";
    }
    @RequestMapping(value = "/staff/save", method = RequestMethod.POST)
    public String save(Staff bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (null != bean.getId() && bean.getId() > 0)
            staffService.update(bean);
        else
            staffService.save(bean);
        return "redirect:/admin/staff";
    }
    @RequestMapping(value = "/staff/delete", method = RequestMethod.POST)
    public void delete(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        try {
            staffService.delete(id);
            obj.addProperty("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("msg", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/staff/update", method = RequestMethod.POST)
    public void update(Integer id,Staff bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        try {
            if (null != id) {
                Staff entity = staffService.getStaffById(id);
                if (null != entity && null != bean) {
                    if (null != bean.getTeamId()) {
                        entity.setTeamId(bean.getTeamId());
                    }
                }
                staffService.update(entity);
                obj.addProperty("status", 200);
            }
            else {
                obj.addProperty("status", 0);
                obj.addProperty("msg", "id not exist ...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("msg", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @Autowired
    private StaffService staffService;

    @Autowired
    private TeamGroupService teamGroupService;
}
