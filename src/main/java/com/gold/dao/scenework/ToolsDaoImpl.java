package com.gold.dao.scenework;

import com.gold.common.Finder;
import com.gold.dao.HibernateTempDao;
import com.gold.entity.Tools;
import com.gold.entity.ToolsLog;
import com.gold.util.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huzuxing on 2016/9/30.
 */
@Repository("toolsDao")
public class ToolsDaoImpl extends HibernateTempDao<Tools> implements ToolsDao {
    @Override
    public Class<Tools> getClazz() {
        return Tools.class;
    }

    public Tools getById(Integer id) {
        if (null == id)
            return null;
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from Tools o where o.id=:id", session);
            finder.setParameter("id", id);
            Tools bean = (Tools) finder.uniqueResult();
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public List<Tools> getToolsList(Tools bean, Integer pageNo, Integer pageSize) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from Tools o where 1=1", session);
            addParams(bean, finder);
            finder.append(" order by o.createTime desc");
            finder.setPageNo(pageNo);
            finder.setPageSize(pageSize);
            List<Tools> list = finder.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public Integer getTotalCount(Tools bean) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select count(o) from Tools o where 1=1", session);
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

    @Override
    public void updateToolsAndAddToolsLog(Tools bean, ToolsLog logBean) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            session.update(bean);
            session.save(logBean);
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

    @Override
    public List<ToolsLog> getToolsLogList(ToolsLog bean, Integer pageNo, Integer pageSize) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from ToolsLog o where 1=1", session);
            addToolsLogParams(bean, finder);
            finder.append(" order by o.createTime desc");
            finder.setPageNo(pageNo);
            finder.setPageSize(pageSize);
            List<ToolsLog> list = finder.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    @Override
    public Integer getToolsLogTotalCount(ToolsLog bean) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select count(o) from ToolsLog o where 1=1", session);
            addToolsLogParams(bean, finder);
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

    @Override
    public ToolsLog getToolsLogById(Integer id) {
        if (null == id)
            return null;
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from ToolsLog o where o.id=:id", session);
            finder.setParameter("id", id);
            ToolsLog bean = (ToolsLog) finder.uniqueResult();
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    @Override
    public Tools getToolsByName(String name) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from Tools o where o.name=:name", session);
            finder.setParameter("name", name);
            Tools bean = (Tools) finder.uniqueResult();
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    @Override
    public void toolsOutOrInBatch(List<Tools> toolses, List<ToolsLog> toolsLogs) throws Exception {
        if (toolses.size() != toolsLogs.size())
            throw new Exception("toolses size not equal toolsLogs size ...");
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            int size = toolses.size();
            for (int i = 0;i < size;i++) {
                session.update(toolses.get(i));
                session.save(toolsLogs.get(i));
            }
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

    private void addToolsLogParams(ToolsLog bean, Finder finder) {
        if (null != bean) {
            if (null != bean.getId()) {
                finder.append(" and o.id=:id");
                finder.setParameter("id", bean.getId());
            }
            if (!StringUtils.isNullOrEmpty(bean.getToolsName())) {
                finder.append(" and o.toolsName like :toolsName");
                finder.setParameter("toolsName", "%"+ bean.getToolsName() +"%");
            }
            if (null != bean.getStatus() && bean.getStatus() >= 0) {
                finder.append(" and o.status=:status");
                finder.setParameter("status", bean.getStatus());
            }
            if (!StringUtils.isNullOrEmpty(bean.getOperator())) {
                finder.append(" and o.operator like :operator");
                finder.setParameter("operator", "%"+ bean.getOperator() +"%");
            }
        }
    }

    private void addParams(Tools bean, Finder finder) {
        if (null != bean) {
            if (null != bean.getId()) {
                finder.append(" and o.id=:id");
                finder.setParameter("id", bean.getId());
            }
            if (!StringUtils.isNullOrEmpty(bean.getName())) {
                finder.append(" and o.name like :name");
                finder.setParameter("name", "%"+ bean.getName() +"%");
            }
            if (null != bean.getStatus() && bean.getStatus() > -1) {
                finder.append(" and o.status=:status");
                finder.setParameter("status", bean.getStatus());
            }
        }
    }
}
