package com.gold.app;

import com.gold.entity.SceneWork;
import com.gold.entity.Staff;
import com.gold.service.SceneWorkService;
import com.gold.service.StaffService;
import com.gold.service.TeamGroupService;
import com.gold.util.JsonUtils;
import com.gold.util.ResponseUtils;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huzuxing on 2016/9/30.
 */
@Controller
public class AppStaffAct {

    @RequestMapping(value = "/app/staff", method = RequestMethod.POST)
    public void staff(HttpServletRequest request, HttpServletResponse response,
                           String name, Integer id, Integer pageNo, Integer pageSize) {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");

            List<Staff> staffs = staffService.getStaffList(name, id, pageNo, pageSize).getList();
            List<String> teamGroups = new ArrayList<>(staffs.size());
            for (Staff bean : staffs) {
                teamGroups.add(null != teamGroupService.getTeamGroupById(bean.getTeamId()) ? teamGroupService.getTeamGroupById(bean.getTeamId()).getName() : "");
            }
            obj.add("data", JsonUtils.staffListsToJsonArray(staffService.getStaffList(name, id, pageNo, pageSize).getList(), teamGroups));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @RequestMapping(value = "/app/staffs", method = RequestMethod.POST)
    public void staffs(HttpServletResponse response) {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.staffListsToJsonArray(staffService.getStaffs()));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @RequestMapping(value = "/app/staff/update", method = RequestMethod.POST)
    public void update(Integer id, Staff bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
    @RequestMapping(value = "/app/staff/delete", method = RequestMethod.POST)
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
    @RequestMapping(value = "/app/staff/save", method = RequestMethod.POST)
    public void save(Staff bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        try {
            if (null != bean.getId() && bean.getId() > 0)
                staffService.update(bean);
            else
                staffService.save(bean);
            obj.addProperty("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("msg", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @RequestMapping(value = "/app/staff/detail", method = RequestMethod.POST)
    public void detail(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("status", 200);
            obj.add("data", JsonUtils.staffToJsonObject(staffService.getStaffById(id)));
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
