package com.gold.action.admin;

import com.gold.common.Pager;
import com.gold.entity.Material;
import com.gold.entity.MaterialLog;
import com.gold.entity.Staff;
import com.gold.service.MaterialService;
import com.gold.service.StaffService;
import com.gold.util.JsonUtils;
import com.gold.util.ResponseUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class MaterialAct {

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

    @RequestMapping(value = "/material", method = RequestMethod.GET)
    public String material(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           String name, String techParam, Integer id, Integer pageNo, Integer pageSize) {
        model.addAttribute("pager", materialService.getMaterialPager(name, id, pageNo, pageSize));
        model.addAttribute("name", name);
        return "admin/material/material";
    }

    @RequestMapping(value = "/material/check", method = RequestMethod.GET)
    public String check(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id) {
        if (null != id) {
            model.addAttribute("bean", materialService.getMaterialById(id));
            model.addAttribute("pictures", materialService.getMaterialPictures(id));
        }
        return "admin/material/check";
    }

    @RequestMapping(value = "/material/detail", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer id) {
        if (null != id) {
            model.addAttribute("bean", materialService.getMaterialById(id));
            model.addAttribute("pictures", materialService.getMaterialPictures(id));
        }
        return "admin/material/detail";
    }

    @RequestMapping(value = "/material/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Material bean, String[] pictures) {
        if (null != bean && null != bean.getId()) {
            materialService.update(bean, pictures);
        }
        else
            materialService.save(bean, pictures);
        return "redirect:/admin/material";
    }

    @RequestMapping(value = "/material/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       Integer id) {
        JsonObject obj = new JsonObject();
        try {
            materialService.delete(id);
            obj.addProperty("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("status", 0);
            obj.addProperty("msg", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/material/manage", method = RequestMethod.GET)
    public String manage(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                              Integer cate) {
        model.addAttribute("cate", cate);
        model.addAttribute("materialList", materialService.getMaterialList(null, null, null, null, null));
        List<Staff> staffList = staffService.getStaffList(null, null, null, null).getList();
        model.addAttribute("staff", staffList);
        return "admin/material/material_out_in";
    }

    @RequestMapping(value = "/material/manage", method = RequestMethod.POST)
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
                    obj.addProperty("status", 0);
                    flag = false;
                }
                else {
                    material.setRoom1Rest(material.getRoom1Rest() - bean.getCount());
                    material.setStoreroom1(material.getStoreroom1() - bean.getCount());
                }

            }
            else if (TWO == bean.getLocation()) {
                if (material.getRoom2Rest() - bean.getCount() < 0){
                    obj.addProperty("status", 0);
                    flag = false;
                }
                else {
                    material.setRoom2Rest(material.getRoom2Rest() - bean.getCount());
                    material.setStoreroom2(material.getStoreroom2() - bean.getCount());
                }

            }
            else if (THREE == bean.getLocation()) {
                if (material.getSystemRest() - bean.getCount() < 0){
                    obj.addProperty("status", 0);
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
            obj.addProperty("status", 200);
        }
        else
            obj.addProperty("status", 0);
        ResponseUtils.sendResponseJson(response, obj);
    }

    @RequestMapping(value = "/material/log", method = RequestMethod.GET)
    public String materialLog(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           String name, Integer id,Integer status, Integer pageNo, Integer pageSize) {
        model.addAttribute("pager", materialService.getMaterialLog(name, id, status, pageNo, pageSize));
        model.addAttribute("name", name);
        if (null != status)
            model.addAttribute("status", status);
        return "admin/material/material_log";
    }
    @Autowired
    private MaterialService materialService;

    @Autowired
    private StaffService staffService;
}
