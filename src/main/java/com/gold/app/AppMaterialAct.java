package com.gold.app;

import com.gold.common.Pager;
import com.gold.entity.Material;
import com.gold.entity.MaterialLog;
import com.gold.entity.Staff;
import com.gold.service.MaterialService;
import com.gold.service.StaffService;
import com.gold.util.JsonUtils;
import com.gold.util.ResponseUtils;
import com.gold.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
 * Created by huzuxing on 2016/10/26.
 */
@Controller
public class AppMaterialAct {

    public static int ONE = 1;

    public static int TWO = 2;

    public static int THREE = 3;

    public static int PLUS = 1;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/app/material", method = RequestMethod.POST)
    public void app_material(HttpServletRequest request, HttpServletResponse response,
                           String name, Integer id, String techParam, Integer pageNo, Integer pageSize) {
        JsonObject obj = new JsonObject();
        try {
            List<Material> lists = materialService.getMaterialList(name, techParam, id, pageNo, pageSize);
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.materialListsToJsonArray(lists));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/material/check", method = RequestMethod.GET)
    public String check(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id) {
        if (null != id) {
            model.addAttribute("bean", materialService.getMaterialById(id));
            model.addAttribute("pictures", materialService.getMaterialPictures(id));
        }
        return "admin/material/check";
    }

    @RequestMapping(value = "/app/material/detail", method = RequestMethod.POST)
    public void detail(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id) {
        JsonObject obj = new JsonObject();
        if (null == id) {
            obj.addProperty("status", 0);
            obj.addProperty("message", "failure");
            model.addAttribute("bean", materialService.getMaterialById(id));
            model.addAttribute("pictures", materialService.getMaterialPictures(id));
        }
        else {
            try {
                obj.add("data", JsonUtils.materialToJsonObject(materialService.getMaterialById(id), materialService.getMaterialPictures(id)));
                obj.addProperty("code", 200);
                obj.addProperty("message", "success");
            } catch (Exception e) {
                e.printStackTrace();
                obj.addProperty("status", 0);
                obj.addProperty("message", "failure");
            }
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/material/save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Material bean, String pictures) {
        JsonObject obj = new JsonObject();
        String[] pics = null;
        if (!StringUtils.isNullOrEmpty(pictures)) {
            pics = pictures.split(";");
        }
        try {
            if (null != bean && null != bean.getId()) {
                materialService.update(bean, pics);
            }
            else
                materialService.save(bean, pics);
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/material/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Integer id) {
        JsonObject obj = new JsonObject();
        try {
            materialService.delete(id);
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/material/manage", method = RequestMethod.POST)
    public void managesave(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           MaterialLog bean) {
        Material material = materialService.getMaterialById(bean.getMaterialId());
        JsonObject obj = new JsonObject();
        boolean flag = true;
        if (PLUS == bean.getCate()) {
            if (ONE == bean.getLocation()) {
                material.setRoom1Rest(material.getRoom1Rest() + bean.getCount());
                material.setStoreroom1(material.getStoreroom1() + bean.getCount());
            }
            else if (TWO == bean.getLocation()) {
                material.setRoom2Rest(material.getRoom2Rest() + bean.getCount());
                material.setStoreroom2(material.getStoreroom2() + bean.getCount());
            }
            else {
                material.setSystemRest(material.getSystemRest() + bean.getCount());
                material.setSystem(material.getSystem() + bean.getCount());
            }
        }
        else {
            if (ONE == bean.getLocation()) {
                if (material.getRoom1Rest() - bean.getCount() < 0) {
                    obj.addProperty("code", 0);
                    obj.addProperty("message", "failure");
                    flag = false;
                }
                else {
                    material.setRoom1Rest(material.getRoom1Rest() - bean.getCount());
                    material.setStoreroom1(material.getStoreroom1() - bean.getCount());
                }

            }
            else if (TWO == bean.getLocation()) {
                if (material.getRoom2Rest() - bean.getCount() < 0){
                    obj.addProperty("code", 0);
                    obj.addProperty("message", "failure");
                    flag = false;
                }
                else {
                    material.setRoom2Rest(material.getRoom2Rest() - bean.getCount());
                    material.setStoreroom2(material.getStoreroom2() - bean.getCount());
                }

            }
            else if (THREE == bean.getLocation()) {
                if (material.getSystemRest() - bean.getCount() < 0){
                    obj.addProperty("code", 0);
                    obj.addProperty("message", "failure");
                    flag = false;
                }
                else {
                    material.setSystemRest(material.getSystemRest() - bean.getCount());
                    material.setSystem(material.getSystem() - bean.getCount());
                }
            }
        }
        if (flag) {
            bean.setMaterialName(material.getName());
            materialService.saveMaterialLog(bean, material);
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
        }
        else {
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/material/log", method = RequestMethod.POST)
    public void materialLog(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                              String name, Integer id,Integer status, Integer pageNo, Integer pageSize) {
        JsonObject obj = new JsonObject();
        try {
            JsonObject data = new JsonObject();
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.materialLogListsToJsonArray(materialService.getMaterialLog(name, id, status, pageNo, pageSize).getList()));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/app/materiallog/detail", method = RequestMethod.POST)
    public void materialLogdetail(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id) {
        JsonObject obj = new JsonObject();
        try {
            JsonObject data = new JsonObject();
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.materialLogToJsonObject(materialService.getMaterialLogById(id)));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @Autowired
    private MaterialService materialService;

    @Autowired
    private StaffService staffService;
}
