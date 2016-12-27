package com.gold.app;

import com.gold.entity.Staff;
import com.gold.entity.Tools;
import com.gold.entity.ToolsLog;
import com.gold.service.StaffService;
import com.gold.service.ToolsService;
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
import java.util.Date;
import java.util.List;

/**
 * Created by huzuxing on 2016/9/30.
 */
@Controller
public class AppToolsAct {

    @RequestMapping(value = "/app/tools", method = RequestMethod.POST)
    public void tools(HttpServletRequest request, HttpServletResponse response,
                           String name, Integer id, Integer status, Integer pageNo, Integer pageSize) {
        JsonObject obj = new JsonObject();
        try {
            List<Tools> lists = toolsService.getToolsList(name, status, id, pageNo, pageSize).getList();
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.toolsListsToJsonArray(lists));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/tools/log", method = RequestMethod.POST)
    public void toollog(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           String name, Integer id, Integer status, Integer pageNo, Integer pageSize) {
        JsonObject obj = new JsonObject();
        try {
            List<ToolsLog> lists = toolsService.getToolsLogPager(name, status, id, pageNo, pageSize).getList();
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.toolsLogListsToJsonArray(lists));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/toolslog/detail", method = RequestMethod.POST)
    public void logdetail(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id) {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.toolsLogToJsonObject(toolsService.getToolsLogById(id)));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/tools/check", method = RequestMethod.GET)
    public String check(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id, Integer status) {
        if (null != id) {
            model.addAttribute("bean", toolsService.getToolsById(id));
        }
        if (null != status) {
            model.addAttribute("status", status);
        }
        return "admin/tools/check";
    }

    @RequestMapping(value = "/app/tools/detail", method = RequestMethod.POST)
    public void detail(HttpServletRequest request, HttpServletResponse response, Integer id) {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.toolsToJsonObject(toolsService.getToolsById(id)));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/app/tools/save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response,
                       Tools bean, Integer status) {
        JsonObject obj = new JsonObject();
        try {
            if (null != status) {
                toolsService.updateToolsAndAddToolsLog(bean, status);
            }
            else if (null != bean && null != bean.getId()) {
                toolsService.update(bean);
            }
            else {
                toolsService.save(bean);
            }
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/tools/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Integer id) {
        JsonObject obj = new JsonObject();
        try {
            toolsService.delete(id);
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @Autowired
    private ToolsService toolsService;
    @Autowired
    private StaffService staffService;
}
