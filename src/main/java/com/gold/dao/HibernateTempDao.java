package com.gold.dao;

import com.gold.common.Finder;
import com.gold.entity.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by huzuxing on 2016/9/24.
 */
public abstract class HibernateTempDao<T>{

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.openSession();
    }

    public abstract Class<T> getClazz();

    public void save(T bean) {
        getSession().save(bean);
    }

    public void delete(T bean) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            session.delete(bean);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (null != tx)
                tx.rollback();
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public void update(T bean) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            session.update(bean);
            tx.commit();
        } catch (Exception e) {
            if (null != tx)
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public List queryList(Finder finder) {
        Session session = null;
        try {
            session = getSession();
            finder.setSession(session);
            return finder.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public Object queryObject(Finder finder) {
        Session session = null;
        try {
            session = getSession();
            finder.setSession(session);
            return finder.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }
}
