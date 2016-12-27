package com.gold.action.admin;

import com.gold.entity.SceneWork;
import com.gold.entity.Tools;
import com.gold.service.SceneWorkService;
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
public class SceneWorkAct {

    @RequestMapping(value = "/scenework", method = RequestMethod.GET)
    public String material(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           String name, Date workTime, Integer id, Integer pageNo, Integer pageSize) {
        model.addAttribute("pager", sceneWorkService.getSceneWorkList(name, workTime, id, pageNo, pageSize));
        model.addAttribute("name", name);
        if (null != workTime)
             model.addAttribute("workTime", new SimpleDateFormat("yyyy-MM-dd").format(workTime));
        return "admin/scenework/scenework";
    }

    @RequestMapping(value = "/scenework/check", method = RequestMethod.GET)
    public String check(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id) {
        if (null != id) {
            model.addAttribute("bean", sceneWorkService.getSceneWorkById(id));
        }
        return "admin/scenework/check";
    }

    @RequestMapping(value = "/scenework/detail", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id, Integer cate) {
        if (null != id) {
            model.addAttribute("bean", sceneWorkService.getSceneWorkById(id));
        }
        List<SceneWork> lists = sceneWorkService.getSceneWorkList(null, new Date(), null, null, null).getList();
        if (null != lists && lists.size() > 0) {
            model.addAttribute("timeBean", lists.get(lists.size() - 1));
        }
        model.addAttribute("cate", cate);
        return "admin/scenework/detail";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/scenework/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model, SceneWork bean) {
        // 查询当天数据
        List<SceneWork> lists = sceneWorkService.getSceneWorkList(null, new Date(), null, null, null).getList();
        if (null == lists || lists.size() == 0) {// 说明当天没有添加过现场工作
            //当天第一次添加
            bean.setSort(1);
        }
        if (null != bean && null != bean.getId()) {
            sceneWorkService.update(bean);
        }
        else
            sceneWorkService.save(bean);
        return "redirect:/admin/scenework";
    }

    @RequestMapping(value = "/scenework/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Integer id) {
        JsonObject obj = new JsonObject();
        try {
            sceneWorkService.delete(id);
            obj.addProperty("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("msg", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @Autowired
    private SceneWorkService sceneWorkService;
}
