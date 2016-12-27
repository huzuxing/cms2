package com.gold.app;

import com.gold.entity.SceneWork;
import com.gold.service.SceneWorkService;
import com.gold.service.StaffService;
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
public class AppStaffAct {

    @RequestMapping(value = "/app/staff", method = RequestMethod.POST)
    public void staff(HttpServletRequest request, HttpServletResponse response,
                           String name, Integer id, Integer pageNo, Integer pageSize) {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("code", 200);
            obj.addProperty("message", "success");
            obj.add("data", JsonUtils.staffListsToJsonArray(staffService.getStaffList(name, null, null, null).getList()));
        } catch (Exception e) {
            e.printStackTrace();
            obj.addProperty("code", 0);
            obj.addProperty("message", "failure");
        }
        ResponseUtils.sendResponseJson(response, obj);
    }
    @Autowired
    private StaffService staffService;
}
