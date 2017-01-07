package com.gold.action.admin;

import com.gold.entity.Banner;
import com.gold.service.BannerService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huzuxing on 2016/12/29.
 */
@Controller
public class BannerAct {

    @RequestMapping(value = "/banner", method = RequestMethod.GET)
    public String banner(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        return "admin/banner/banner";
    }

    @RequestMapping(value = "/banner", method = RequestMethod.POST)
    public void bannersave(String[] urls, String[] imgs, Integer cate, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        JsonObject obj = new JsonObject();
        if (null == cate) {
            obj.addProperty("status", 0);
            obj.addProperty("msg", "cate not defined ...");
        }
        else {
            if (urls.length != imgs.length) {
                obj.addProperty("status", 0);
                obj.addProperty("msg", "please submit all datas  ...");
            }
            else {
                try {
                    List<Banner> banners = new ArrayList<>();
                    for(int i = 0; i < urls.length; i++) {
                        Banner banner = new Banner();
                        banner.setCate(cate);
                        banner.setUrl(urls[i]);
                        banner.setImg(imgs[i]);
                        banners.add(banner);
                    }
                    bannerService.save(banners);
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

    @RequestMapping(value = "/banner/list", method = RequestMethod.POST)
    public void getBanners(Integer cate, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        JsonObject obj = new JsonObject();
        if (null == cate) {
            obj.addProperty("status", 0);
            obj.addProperty("msg", "cate not defined ...");
        }
        else {
            try {
                Banner banner = new Banner();
                banner.setCate(cate);
                obj.addProperty("status", 200);
                obj.add("data", JsonUtils.bannerListToJsonArray(bannerService.getBanners(banner)));
            } catch (Exception e) {
                e.printStackTrace();
                obj.addProperty("status", 0);
                obj.addProperty("msg", e.getMessage());
            }
        }
        ResponseUtils.sendResponseJson(response, obj);
    }

    @Autowired
    private BannerService bannerService;
}
