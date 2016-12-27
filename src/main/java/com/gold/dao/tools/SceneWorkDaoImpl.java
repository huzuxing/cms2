package com.gold.dao.tools;

import com.gold.common.Finder;
import com.gold.dao.HibernateTempDao;
import com.gold.entity.SceneWork;
import com.gold.entity.Tools;
import com.gold.util.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by huzuxing on 2016/10/7.
 */
@Repository("sceneWorkDao")
public class SceneWorkDaoImpl extends HibernateTempDao<SceneWork> implements SceneWorkDao{
    @Override
    public Class<SceneWork> getClazz() {
        return SceneWork.class;
    }

    public SceneWork getById(Integer id) {
        if (null == id)
            return null;
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from SceneWork o where o.id=:id", session);
            finder.setParameter("id", id);
            SceneWork bean = (SceneWork) finder.uniqueResult();
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public List<SceneWork> getSceneWorkList(SceneWork bean, Integer pageNo, Integer pageSize) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from SceneWork o where 1=1", session);
            addParams(bean, finder);
            finder.append(" order by o.createTime desc");
            finder.setPageNo(pageNo);
            finder.setPageSize(pageSize);
            List<SceneWork> list = finder.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public Integer getTotalCount(SceneWork bean) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select count(o) from SceneWork o where 1=1", session);
            addParams(bean, finder);
            finder.append(" order by o.createTime desc");
            Long count = (Long) finder.uniqueResult();
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    private void addParams(SceneWork bean, Finder finder) {
        if (null != bean) {
            if (null != bean.getId()) {
                finder.append(" and o.id=:id");
                finder.setParameter("id", bean.getId());
            }
            if (!StringUtils.isNullOrEmpty(bean.getChargePerson())) {
                finder.append(" and o.chargePerson like :chargePerson");
                finder.setParameter("chargePerson", "%" + bean.getChargePerson() + "%");
            }
            if (null != bean.getWorkTime()) {
                finder.append(" and Date(o.workTime)=:workTime");
                finder.setParameter("workTime", bean.getWorkTime());
            }
        }
    }
}
