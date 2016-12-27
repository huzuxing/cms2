package com.gold.service;

import com.gold.common.Pager;
import com.gold.dao.scenework.ToolsDao;
import com.gold.dao.tools.SceneWorkDao;
import com.gold.entity.SceneWork;
import com.gold.entity.Tools;
import com.gold.util.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by huzuxing on 2016/9/24.
 */
@Component("sceneWorkService")
public class SceneWorkService {
    @Autowired
    private SceneWorkDao sceneWorkDao;

    public void save(SceneWork bean) {
        bean.setCreateTime(new Date());
        sceneWorkDao.save(bean);
    }

    public Pager<SceneWork> getSceneWorkList(String name, Date date, Integer id, Integer pageNo, Integer pageSize) {
        SceneWork bean = new SceneWork();
        bean.setChargePerson(name);
        bean.setId(id);
        if (null != date) {
            bean.setWorkTime(date);
        }
        Pager pager = new Pager(QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize));
        pager.setTotalCount(sceneWorkDao.getTotalCount(bean));
        pager.setList(sceneWorkDao.getSceneWorkList(bean, QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize)));
        return pager;
    }

    public void delete(Integer id) {
        SceneWork bean = sceneWorkDao.getById(id);
        if (null != bean) {
            sceneWorkDao.delete(bean);
        }
    }

    public SceneWork getSceneWorkById(Integer id) {
        return sceneWorkDao.getById(id);
    }

    public void update(SceneWork bean) {
        sceneWorkDao.update(bean);
    }
}
