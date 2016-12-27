package com.gold.common;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.support.json.JSONUtils;
import com.gold.util.FileUtils;
import com.gold.util.ResponseUtils;
import com.google.gson.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by huzuxing on 2016/9/24.
 */
@MultipartConfig
@WebServlet(name="fileupload", urlPatterns = {"/fileupload"})
public class FileUpload extends HttpServlet{
    private static Log log = LogFactory.getLog(FileUpload.class);
    private static String upload = File.separator + "upload";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String dir = req.getParameter("dir");
        String path = req.getServletContext().getRealPath(upload) + File.separator + dir;
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        Collection<Part> parts = req.getParts();
        JsonArray imgs = new JsonArray();
        for(Part part : parts) {
            String header = part.getHeader("Content-Disposition");
            String[] headers = header.split(";");
            if (headers.length == 3) {
                String fileName = UUID.randomUUID() + FileUtils.getFileExt(headers[2].split("=")[1]);
                part.write(file + File.separator + fileName);
                imgs.add(upload + File.separator + dir + File.separator + fileName);
            }
        }
       JsonObject object = new JsonObject();
        object.addProperty("status", 200);
        object.add("imgs", imgs);
        ResponseUtils.sendResponseJson(resp, object);
    }
}
