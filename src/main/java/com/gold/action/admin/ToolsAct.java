package com.gold.action.admin;

import com.gold.entity.Staff;
import com.gold.entity.Tools;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by huzuxing on 2016/9/30.
 */
@Controller
public class ToolsAct {

    @RequestMapping(value = "/tools", method = RequestMethod.GET)
    public String tools(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           String name,Integer status, Integer id, Integer pageNo, Integer pageSize) {
        model.addAttribute("pager", toolsService.getToolsList(name, status, id, pageNo, pageSize));
        model.addAttribute("name", name);
        return "admin/tools/tools";
    }

    @RequestMapping(value = "/tools/log", method = RequestMethod.GET)
    public String toollog(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           String name, Integer id, Integer status, Integer pageNo, Integer pageSize) {
        model.addAttribute("pager", toolsService.getToolsLogPager(name, status, id, pageNo, pageSize));
        model.addAttribute("name", name);
        if (null != status)
            model.addAttribute("status", status);
        return "admin/tools/tools_log";
    }

    @RequestMapping(value = "/tools/check", method = RequestMethod.GET)
    public String check(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id, Integer status) {
        if (null != id) {
            model.addAttribute("bean", toolsService.getToolsById(id));
        }
        if (null != status) {
            model.addAttribute("status", status);
        }
        return "admin/tools/check";
    }

    @RequestMapping(value = "/tools/detail", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id, Integer status) {
        if (null != id) {
            model.addAttribute("bean", toolsService.getToolsById(id));
        }
        if (null != status) {
            model.addAttribute("status", status);
            List<Staff> list = staffService.getStaffList(null, null, null, null).getList();
            model.addAttribute("staff", list);
        }
        return "admin/tools/detail";
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

    @RequestMapping(value = "/tools/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Tools bean, Integer status) {
        if (null != status) {
            toolsService.updateToolsAndAddToolsLog(bean, status);
        }
        else if (null != bean && null != bean.getId()) {
            Tools entity = toolsService.getToolsById(bean.getId());
            if (null != entity) {
                entity.setName(bean.getName());
                toolsService.update(entity);
            }
        }
        else {
            toolsService.save(bean);
        }
        return "redirect:/admin/tools";
    }

    @RequestMapping(value = "/tools/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Integer id) {
        JsonObject obj = new JsonObject();
        try {
            toolsService.delete(id);
            obj.addProperty("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("msg", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @RequestMapping(value = "/tools/batch", method = RequestMethod.GET)
    public String batch(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer status) {
        if (null != status) {
            model.addAttribute("status", status);
            // 当status==1,说明是借出，则查询在库的工器具，归还相反。
            List<Tools> list = toolsService.getToolsList(null, status == 1 ? 0 : status, null, null, null).getList();
            model.addAttribute("tools", list);
            List<Staff> staffs = staffService.getStaffList(null, null, null, null).getList();
            model.addAttribute("staff", staffs);
        }
        return "admin/tools/batch";
    }
    @RequestMapping(value = "/tools/batch", method = RequestMethod.POST)
    public void batchsave(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer status) {
        if (null != status) {
            model.addAttribute("status", status);
            List<Staff> list = staffService.getStaffList(null, null, null, null).getList();
            model.addAttribute("staff", list);
        }
    }
    @Autowired
    private ToolsService toolsService;
    @Autowired
    private StaffService staffService;
}
