package com.gold.app;

import com.gold.entity.Staff;
import com.gold.entity.TeamGroup;
import com.gold.service.StaffService;
import com.gold.service.TeamGroupService;
import com.gold.util.JsonUtils;
import com.gold.util.ResponseUtils;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by huzuxing on 2016/9/22.
 */
@Controller
public class AppTeamGroupAct {
    @RequestMapping(value = "/app/teamgroup", method = RequestMethod.POST)
    public void teamgroup(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       String name, Integer id, Integer pageNo, Integer pageSize) {
        JsonObject obj = new JsonObject();
        try {
            List<TeamGroup> lists = teamGroupService.getTeamGroupList(name, id, pageNo, pageSize).getList();
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.teamgroupListToJsonArray(lists));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @RequestMapping(value = "/app/teamgroup/detail", method = RequestMethod.POST)
    public void add(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.teamgroupListToJsonObject(teamGroupService.getTeamGroupById(id)));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/teamgroup/save", method = RequestMethod.POST)
    public void save(TeamGroup bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        try {
            if (null != bean.getId() && bean.getId() > 0)
                teamGroupService.update(bean);
            else
                teamGroupService.save(bean);
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @RequestMapping(value = "/app/teamgroup/delete", method = RequestMethod.POST)
    public void delete(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        try {
            List<Staff> staffs = staffService.getStaffsByTeamGroupId(id);
            if (null != staffs && staffs.size() > 0) {
                obj.addProperty("status", 600);
            }
            else {
                teamGroupService.delete(id);
                obj.addProperty("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("msg", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/teamgroup/member", method = RequestMethod.POST)
    public void member(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        JsonObject data = new JsonObject();
        try {
            data.add("members", JsonUtils.staffListsToJsonArray(staffService.getStaffsByTeamGroupId(id)));
            data.addProperty("teamGroupName", teamGroupService.getTeamGroupById(id).getName());
            obj.addProperty("code", 200);
            obj.add("data", data);
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @Autowired
    private TeamGroupService teamGroupService;

    @Autowired
    private StaffService staffService;
}
