package com.gold.action.admin;

import com.gold.entity.Staff;
import com.gold.entity.Tools;
import com.gold.entity.ToolsLog;
import com.gold.service.StaffService;
import com.gold.service.ToolsService;
import com.gold.util.ResponseUtils;
import com.gold.util.StringUtils;
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
public class ToolsAct {

    @RequestMapping(value = "/tools", method = RequestMethod.GET)
    public String tools(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                        String name, Integer status, Integer id, Integer pageNo, Integer pageSize) {
        model.addAttribute("pager", toolsService.getToolsList(name, status, id, pageNo, pageSize));
        model.addAttribute("name", name);
        return "admin/tools/tools";
    }

    @RequestMapping(value = "/tools/log", method = RequestMethod.GET)
    public String toollog(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                          String name, Integer cate, Integer id, Integer status, Integer pageNo, Integer pageSize) {
        model.addAttribute("pager", toolsService.getToolsLogPager(name, null == cate ? -1 : cate, status, id, pageNo, pageSize));
        model.addAttribute("name", name);
        if (null != status)
            model.addAttribute("status", status);
        model.addAttribute("cate", cate);
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
            List<Staff> staffs = staffService.getStaffs();
            model.addAttribute("staff", staffs);
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
        } else if (null != bean && null != bean.getId()) {
            Tools entity = toolsService.getToolsById(bean.getId());
            if (null != entity) {
                entity.setName(bean.getName());
                toolsService.update(entity);
            }
        } else {
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
            List<Tools> list = toolsService.getTools(status == 1 ? 0 : 1);
            model.addAttribute("tools", list);
            List<Staff> staffs = staffService.getStaffs();
            model.addAttribute("staff", staffs);
        }
        return "admin/tools/batch";
    }

    @RequestMapping(value = "/tools/batch", method = RequestMethod.POST)
    public void batchsave(ToolsLog bean, String tools, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        if (null == bean || StringUtils.isNullOrEmpty(tools)) {
            obj.addProperty("status", 0);
            obj.addProperty("msg", "bean attrs not defined ...");
        } else {
            String[] toolsNames = tools.split(",");
            List<Tools> list = new ArrayList<>();
            for (String name : toolsNames) {
                Tools entity = toolsService.getToolsByName(name.trim());
                if (null == entity) {
                    break;
                }
                list.add(entity);
            }
            if (toolsNames.length != list.size()) {
                obj.addProperty("status", 0);
                obj.addProperty("msg", "some of the tools not exist ...");
            } else {
                List<ToolsLog> toolsLogs = new ArrayList<>(list.size());//  批量处理，保证两个list大小一致
                for (Tools t : list) {
                    t.setStatus(bean.getStatus());
                    bean.setToolsName(t.getName());
                    bean.setToolsId(t.getId());
                    ToolsLog tl = new ToolsLog();
                    tl.setToolsId(t.getId());
                    tl.setPhone(bean.getPhone());
                    tl.setStatus(bean.getStatus());
                    tl.setToolsName(t.getName());
                    tl.setTime(bean.getTime());
                    tl.setCreateTime(new Date());
                    tl.setAuditor(bean.getAuditor());
                    tl.setOperator(bean.getOperator());
                    tl.setReason(bean.getReason());
                    toolsLogs.add(tl);
                }
                try {
                    toolsService.toolsOutOrInBatch(list, toolsLogs);
                    obj.addProperty("status", 200);
                } catch (Exception e) {
                    e.printStackTrace();
                    obj.addProperty("status", 0);
                    obj.addProperty("msg", e.getMessage());
                }
            }
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/tools/log/copy", method = RequestMethod.POST)
    public void copy(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        JsonObject obj = new JsonObject();
        if (null == id) {
            obj.addProperty("status", 0);
            obj.addProperty("msg", "id not exist ...");
        } else {
            ToolsLog bean = toolsService.getToolsLogById(id);
            if (null == bean) {
                obj.addProperty("status", 0);
                obj.addProperty("msg", "bean not exist ...");
            } else {
                Tools tools = toolsService.getToolsById(bean.getToolsId());
                if (tools.getStatus() == bean.getStatus()) {// 两个状态相同，说明不能做此操作
                    obj.addProperty("status", 0);
                    obj.addProperty("msg", "该工具已" + (tools.getStatus() == 0 ? "在库" : "借出"));
                } else {
                    try {
                        tools.setStatus(bean.getStatus());//做复制操作，同时修改tools状态
                        tools.setTime(new Date());
                        tools.setPhone(bean.getPhone());
                        tools.setReason(bean.getReason());
                        tools.setOperator(bean.getOperator());
                        tools.setAuditor(bean.getAuditor());
                        toolsService.updateToolsAndAddToolsLog(tools, bean.getStatus());
                        obj.addProperty("status", 200);
                    } catch (Exception e) {
                        e.printStackTrace();
                        obj.addProperty("status", 0);
                        obj.addProperty("msg", e.getMessage());
                    }
                }
            }
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @Autowired
    private ToolsService toolsService;
    @Autowired
    private StaffService staffService;
}
