package com.gold.util;

import com.gold.entity.*;
import com.google.gson.*;

import java.util.List;

/**
 * Created by huzuxing on 2016/10/7.
 */
public class JsonUtils {

    public static Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().setVersion(1.0).create();
    }

    public static JsonElement setStringProperty(JsonObject obj, String key, String value) {
        if (null == obj)
            obj = new JsonObject();
        obj.addProperty(key, value);
        return obj;
    }

    public static JsonElement setNumberProperty(JsonObject obj, String key, Number value) {
        if (null == obj)
            obj = new JsonObject();
        obj.addProperty(key, value);
        return obj;
    }

    public static JsonElement setStringProperty(String key, String value) {
        JsonObject obj = new JsonObject();
        obj.addProperty(key, value);
        return obj;
    }

    public static JsonElement setNumberProperty(String key, Number value) {
        JsonObject obj = new JsonObject();
        obj.addProperty(key, value);
        return obj;
    }

    public static JsonArray materialListsToJsonArray(List<Material> lists) {
        JsonArray array = new JsonArray();
        if (null == lists || lists.size() == 0) {
            return array;
        }
        lists.forEach(bean -> {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", bean.getId());
            obj.addProperty("name", bean.getName());
            obj.addProperty("techParam", bean.getTechParam());
            array.add(JsonUtils.materialToJsonObject(bean, null));
        });
        return array;
    }
    public static JsonArray staffListsToJsonArray(List<Staff> lists) {
        JsonArray array = new JsonArray();
        if (null == lists || lists.size() == 0) {
            return array;
        }
        lists.forEach(bean -> {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", bean.getId());
            obj.addProperty("name", bean.getName());
            obj.addProperty("phone", bean.getPhone());
            array.add(obj);
        });
        return array;
    }

    public static JsonArray materialLogListsToJsonArray(List<MaterialLog> lists) {
        JsonArray array = new JsonArray();
        if (null == lists || lists.size() == 0) {
            return array;
        }
        lists.forEach(bean -> {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", bean.getId());
            obj.addProperty("materialName", bean.getMaterialName());
            obj.addProperty("location", bean.getLocation());
            obj.addProperty("count", bean.getCount());
            obj.addProperty("time", commonUtils.dateFormat("yyyy-MM-dd  HH:mm", bean.getTime()));
            array.add(obj);
        });
        return array;
    }

    public static JsonArray toolsListsToJsonArray(List<Tools> lists) {
        JsonArray array = new JsonArray();
        if (null == lists || lists.size() == 0) {
            return array;
        }
        lists.forEach(bean -> {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", bean.getId());
            obj.addProperty("name", bean.getName());
            obj.addProperty("status", bean.getStatus());
            array.add(obj);
        });
        return array;
    }

    public static JsonArray toolsLogListsToJsonArray(List<ToolsLog> lists) {
        JsonArray array = new JsonArray();
        if (null == lists || lists.size() == 0) {
            return array;
        }
        lists.forEach(bean -> {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", bean.getId());
            obj.addProperty("name", bean.getToolsName());
            obj.addProperty("status", bean.getStatus());
            obj.addProperty("time", commonUtils.dateFormat("yyyy-MM-dd HH:mm", bean.getTime()));
            array.add(obj);
        });
        return array;
    }

    public static JsonObject toolsLogToJsonObject(ToolsLog bean) {
        JsonObject obj = new JsonObject();
        if (null != bean) {
            obj.addProperty("id", bean.getId());
            obj.addProperty("name", bean.getToolsName());
            obj.addProperty("auditor", bean.getAuditor());
            obj.addProperty("status", bean.getStatus());
            obj.addProperty("operator", bean.getOperator());
            obj.addProperty("phone", bean.getPhone());
            obj.addProperty("reason", bean.getReason());
            obj.addProperty("toolsId", bean.getToolsId());
            obj.addProperty("time", commonUtils.dateFormat("yyyy-MM-dd HH:mm", bean.getTime()));
        }
        return obj;
    }

    public static JsonObject toolsToJsonObject(Tools bean) {
        JsonObject obj = new JsonObject();
        if (null != bean) {
            obj.addProperty("id", bean.getId());
            obj.addProperty("name", bean.getName());
            obj.addProperty("status", bean.getStatus());
            obj.addProperty("phone", bean.getPhone());
            obj.addProperty("operator", bean.getOperator());
            obj.addProperty("auditor", bean.getAuditor());
            obj.addProperty("reason", bean.getReason());
            obj.addProperty("time", commonUtils.dateFormat("yyyy-MM-dd HH:mm", bean.getTime()));
        }
        return obj;
    }

    public static JsonArray sceneWorkListsToJsonArray(List<SceneWork> lists) {
        JsonArray array = new JsonArray();
        if (null == lists || lists.size() == 0) {
            return array;
        }
        lists.forEach(bean -> {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", bean.getId());
            obj.addProperty("chargePerson", bean.getChargePerson());
            obj.addProperty("member", bean.getMember());
            obj.addProperty("workTime", commonUtils.dateFormat("yyyy-MM-dd HH:mm", bean.getWorkTime()));
            array.add(obj);
        });
        return array;
    }

    public static JsonObject daySceneWorkListsToJsonObject(List<SceneWork> lists) {
        JsonObject object = new JsonObject();
        if (null != lists && lists.size() > 0) {
            JsonArray array = new JsonArray();
            lists.forEach(bean -> {
                JsonObject obj = new JsonObject();
                obj.addProperty("id", bean.getId());
                obj.addProperty("chargePerson", bean.getChargePerson());
                obj.addProperty("workTime", commonUtils.dateFormat("yyyy-MM-dd HH:mm", bean.getWorkTime()));
                obj.addProperty("sort", bean.getSort());
                array.add(obj);
            });
            SceneWork entity = lists.get(0);
            object.addProperty("persons", entity.getPersons());
            object.addProperty("attention", entity.getAttention());
            object.add("list", array);
        }
        return object;
    }

    public static JsonObject sceneWorkToJsonObject(SceneWork bean) {
        JsonObject obj = new JsonObject();
        if (null != bean) {
            obj.addProperty("id", bean.getId());
            obj.addProperty("chargePerson", bean.getChargePerson());
            obj.addProperty("member", bean.getMember());
            obj.addProperty("context", bean.getContext());
            obj.addProperty("workTime", commonUtils.dateFormat("yyyy-MM-dd HH:mm", bean.getWorkTime()));
            obj.addProperty("persons", bean.getPersons());
            obj.addProperty("attention", bean.getAttention());
            obj.addProperty("workRecord", bean.getWorkRecord());
            obj.addProperty("sort", bean.getSort());
        }
        return obj;
    }

    public static JsonObject materialToJsonObject(Material bean, List<MaterialPicture> lists) {
        JsonObject obj = new JsonObject();
        if (null == bean)
            return obj;
        obj.addProperty("id", bean.getId());
        obj.addProperty("name", bean.getName());
        obj.addProperty("techParam", bean.getTechParam());
        obj.addProperty("storeroom1", bean.getStoreroom1());
        obj.addProperty("storeroom2", bean.getStoreroom2());
        obj.addProperty("system", bean.getSystem());
        obj.addProperty("room1rest", bean.getRoom1Rest());
        obj.addProperty("room2rest", bean.getRoom2Rest());
        obj.addProperty("systemrest", bean.getSystemRest());
        if (null != lists && lists.size() > 0) {
            JsonArray array = new JsonArray();
            lists.forEach(list -> {
                array.add(list.getUrl());
            });
            obj.add("pictures", array);
        }
        return obj;
    }

    public static JsonObject materialLogToJsonObject(MaterialLog bean) {
        JsonObject obj = new JsonObject();
        if (null == bean)
            return obj;
        obj.addProperty("id", bean.getId());
        obj.addProperty("materialId", bean.getMaterialId());
        obj.addProperty("materialName", bean.getMaterialName());
        obj.addProperty("count", bean.getCount());
        obj.addProperty("location", bean.getLocation());
        obj.addProperty("cate", bean.getCate());
        obj.addProperty("time", commonUtils.dateFormat("yyyy-MM-dd HH:mm", bean.getTime()));
        obj.addProperty("auditor", bean.getAuditor());
        obj.addProperty("operator", bean.getOperator());
        return obj;
    }

}
