package com.gold.app;

import com.gold.entity.SceneWork;
import com.gold.service.SceneWorkService;
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
public class AppSceneWorkAct {

    @RequestMapping(value = "/app/scenework", method = RequestMethod.POST)
    public void material(HttpServletRequest request, HttpServletResponse response,
                           String name, Date workTime, Integer id, Integer pageNo, Integer pageSize) {
        JsonObject obj = new JsonObject();
        try {
            List<SceneWork> lists = sceneWorkService.getSceneWorkList(name, workTime, id, pageNo, pageSize).getList();
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.sceneWorkListsToJsonArray(lists));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/dayscenework", method = RequestMethod.POST)
    public void dayscenework(HttpServletRequest request, HttpServletResponse response,Date workTime) {
        JsonObject obj = new JsonObject();
        try {
            List<SceneWork> lists = sceneWorkService.getSceneWorkList(null, workTime, null, null, null).getList();
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.daySceneWorkListsToJsonObject(lists));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/dayscenework/check", method = RequestMethod.POST)
    public void daysceneworkcheck(HttpServletRequest request, HttpServletResponse response,Date workTime) {
        JsonObject obj = new JsonObject();
        try {
            List<SceneWork> lists = sceneWorkService.getSceneWorkList(null, workTime, null, null, null).getList();
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            JsonObject object = new JsonObject();
            if (null != lists && lists.size() > 0) {
                SceneWork entity = lists.get(0);
                object.addProperty("persons", entity.getPersons());
                object.addProperty("attention", entity.getAttention());
            }
            obj.add("data", object);
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/scenework/check", method = RequestMethod.GET)
    public String check(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id) {
        if (null != id) {
            model.addAttribute("bean", sceneWorkService.getSceneWorkById(id));
        }
        return "admin/scenework/check";
    }

    @RequestMapping(value = "/app/scenework/detail", method = RequestMethod.POST)
    public void detail(HttpServletRequest request, HttpServletResponse response, Integer id) {
        JsonObject obj = new JsonObject();
        if (null != id) {
            try {
                obj.addProperty("code", 200);
                obj.addProperty("message", "success");
                obj.add("data", JsonUtils.sceneWorkToJsonObject(sceneWorkService.getSceneWorkById(id)));
            } catch (Exception e) {
                e.printStackTrace();
                obj.addProperty("code", 0);
                obj.addProperty("message", "failure");
            }
        }
        ResponseUtils.sendResponseJson(response, obj);
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

    @RequestMapping(value = "/app/scenework/save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response, SceneWork bean) {
        JsonObject obj = new JsonObject();
        try {
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
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/scenework/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Integer id) {
        JsonObject obj = new JsonObject();
        try {
            sceneWorkService.delete(id);
            obj.addProperty("status", 200);
            obj.addProperty("message", "success");
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("msg", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @Autowired
    private SceneWorkService sceneWorkService;
}
