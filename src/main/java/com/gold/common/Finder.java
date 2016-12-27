package com.gold.common;

/**
 * Created by huzuxing on 2016/9/25.
 */

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.*;

/**
 * @author huzuxing
 * @Description 查询封装器，以后可以不断丰富该封装器
 * @date 2016年6月22日 下午2:51:32
 */
public class Finder {

    private String query;

    private StringBuilder builder = new StringBuilder();

    private Session session;

    private Integer pageNo;

    private Integer pageSize;

    private Map<String, Object> params = new HashMap<>();

    @SuppressWarnings("rawtypes")
    private Map<String, Collection> listParams = new HashMap<>();

    @SuppressWarnings("rawtypes")
    private Class entity;

    // 默认HQL查询
    private boolean isHql = true;

    public Finder() {
    }

    public Finder(String query) {
        this.query = query;
        builder.append(query);
    }

    public Finder(String query, Session session) {
        this.query = query;
        this.session = session;
        builder.append(query);
    }

    public Finder(String query, Session session, int pageNo, int pageSize) {
        this.query = query;
        this.session = session;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        builder.append(query);
    }

    public void append(String statement) {
        builder.append(statement);
    }

    public void setParameter(String key, Object value) {
        params.put(key, value);
    }

    @SuppressWarnings("rawtypes")
    public void setParameterList(String key, Collection value) {
        listParams.put(key, value);
    }

    // 返回sql语句
    public SQLQuery getSQLQuery() throws Exception {
        SQLQuery sql = null;
        if (null == session)
            throw new Exception("session is not defined ...");
        if (builder.length() > 0) {
            sql = session.createSQLQuery(builder.toString());
            setParams(sql);
        }
        if (null != getEntity()) {
            sql.addEntity(getEntity());
        }
        return sql;
    }

    //参数设置
    private void setParams(Query query) {
        Set<String> keys = params.keySet();
        if (keys.size() > 0) {
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                query.setParameter(key, params.get(key));
            }
        }
        Set<String> listKeys = listParams.keySet();
        if (listKeys.size() > 0) {
            Iterator<String> list = listKeys.iterator();
            while (list.hasNext()) {
                String key = list.next();
                query.setParameterList(key, listParams.get(key));
            }
        }
        if (null != pageNo && null != pageSize && pageNo > 0 && pageSize > 0) {
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
    }

    //返回hql语句
    public Query getQueryHQL() throws Exception {
        Query query = null;
        if (null == session)
            throw new Exception("session is not defined ...");
        if (builder.length() > 0) {
            query = session.createQuery(builder.toString());
            setParams(query);
        }
        return query;
    }

    //获取查询器
    public Query loadQuery() throws Exception {
        Query query = null;
        if (isHql) {
            query = getQueryHQL();
        } else {
            query = getSQLQuery();
        }
        return query;
    }

    // 查询单条数据
    public Object uniqueResult() throws Exception {
        Query query = loadQuery();
        if (null == query) {
            return new Object();
        }
        return query.uniqueResult();
    }

    // 获取结果列表集
    @SuppressWarnings({"rawtypes"})
    public List list() throws Exception {
        Query query = loadQuery();
        if (null == query) {
            return new ArrayList<>();
        }
        List list = null;
        try {
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList();
        }
        return list;
    }

    // 执行增加或者删除或者更新

    public int executeUpdate() throws Exception {
        return loadQuery().executeUpdate();
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @SuppressWarnings("rawtypes")
    public Class getEntity() {
        return entity;
    }

    @SuppressWarnings("rawtypes")
    public void setEntity(Class entity) {
        this.entity = entity;
    }

    public boolean isHql() {
        return isHql;
    }

    public void setHql(boolean isHql) {
        this.isHql = isHql;
    }

}
