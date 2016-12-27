package com.gold.dao.news;

import com.gold.common.Finder;
import com.gold.dao.HibernateTempDao;
import com.gold.entity.News;
import com.gold.util.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huzuxing on 2016/9/24.
 */
@Repository("newsDao")
public class NewsDaoImpl extends HibernateTempDao<News> implements NewsDao {

    @Override
    public Class<News> getClazz() {
        return News.class;
    }

    public News getById(Integer id) {
        if (null == id)
            return null;
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from News o where o.id=:id", session);
            finder.setParameter("id", id);
            News bean = (News) finder.uniqueResult();
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public List<News> getNewsList(News bean, Integer pageNo, Integer pageSize) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from News o where 1=1", session);
            addParams(bean, finder);
            finder.append(" order by o.createTime desc");
            finder.setPageNo(pageNo);
            finder.setPageSize(pageSize);
            List<News> list = finder.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public Integer getTotalCount(News bean) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select count(o) from News o where 1=1", session);
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

    private void addParams(News bean, Finder finder) {
        if (null != bean) {
            if (null != bean.getId()) {
                finder.append(" and o.id=:id");
                finder.setParameter("id", bean.getId());
            }
            if (!StringUtils.isNullOrEmpty(bean.getTitle())) {
                finder.append(" and o.title like :title");
                finder.setParameter("title", "%"+ bean.getTitle() +"%");
            }
        }
    }

}
