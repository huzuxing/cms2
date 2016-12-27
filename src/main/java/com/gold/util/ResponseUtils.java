package com.gold.util;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by huzuxing on 2016/10/11.
 */
public class ResponseUtils {

    public static void sendResponseJson(HttpServletResponse response, JsonObject obj){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JsonUtils.getGson().toJson(obj));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out)
                out.close();
        }
    }
}
