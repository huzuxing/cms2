package com.gold.service;

import com.gold.common.Pager;
import com.gold.dao.material.MaterialDao;
import com.gold.dao.news.NewsDao;
import com.gold.entity.Material;
import com.gold.entity.MaterialLog;
import com.gold.entity.MaterialPicture;
import com.gold.entity.News;
import com.gold.util.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by huzuxing on 2016/9/24.
 */
@Component("materialService")
public class MaterialService {
    @Autowired
    private MaterialDao materialDao;

    public void save(Material bean, String[] pictures) {
        bean.setCreateTime(new Date());
        bean.setRoom1Rest(bean.getStoreroom1());
        bean.setRoom2Rest(bean.getStoreroom2());
        bean.setSystemRest(bean.getSystem());
        bean.setTotalCount(bean.getStoreroom1() + bean.getStoreroom2() + bean.getSystem());
        materialDao.save(bean, pictures);
    }

    public Pager<Material> getMaterialPager(String name, Integer id, Integer pageNo, Integer pageSize) {
        Material bean = new Material();
        bean.setName(name);
        bean.setId(id);
        Pager pager = new Pager(QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize));
        pager.setTotalCount(materialDao.getTotalCount(bean));
        pager.setList(materialDao.getMaterialList(bean, QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize)));
        return pager;
    }

    public List<Material> getMaterialList(String name, String techParam, Integer id, Integer pageNo, Integer pageSize) {
        Material bean = new Material();
        bean.setName(name);
        bean.setId(id);
        bean.setTechParam(techParam);
        return materialDao.getMaterialList(bean, QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize));
    }

    public void delete(Integer id) {
        Material bean = materialDao.getById(id);
        if (null != bean) {
            materialDao.delete(bean);
        }
    }

    public Material getMaterialById(Integer id) {
        return materialDao.getById(id);
    }

    public void update(Material bean, String[] pictures) {
        materialDao.update(bean, pictures);
    }

    public Pager<MaterialLog> getMaterialLog(String name, Integer id, Integer status, Integer pageNo, Integer pageSize) {
        MaterialLog bean = new MaterialLog();
        bean.setMaterialName(name);
        bean.setMaterialId(id);
        if (null != status)
            bean.setCate(status);
        Pager pager = new Pager(QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize));
        pager.setTotalCount(materialDao.getMaterialLogTotalCount(bean));
        pager.setList(materialDao.getMaterialLogList(bean, QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize)));
        return pager;
    }

    public void saveMaterialLog(MaterialLog bean, Material entity) {
        materialDao.saveMaterialLog(bean, entity);
    }

    public List<MaterialPicture> getMaterialPictures(Integer materialId) {
       return materialDao.getMaterialPictures(materialId);
    }

    public MaterialLog getMaterialLogById(Integer id) {
        return materialDao.getMaterialLogById(id);
    }
}
