package com.gold.dao.staff;

import com.gold.common.Finder;
import com.gold.dao.HibernateTempDao;
import com.gold.entity.Staff;
import com.gold.util.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huzuxing on 2016/10/8.
 */
@Repository("staffDao")
public class StaffDaoImpl extends HibernateTempDao<Staff> implements StaffDao {
    @Override
    public Class<Staff> getClazz() {
        return Staff.class;
    }

    public Staff getById(Integer id) {
        if (null == id)
            return null;
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from Staff o where o.id=:id", session);
            finder.setParameter("id", id);
            Staff bean = (Staff) finder.uniqueResult();
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public List<Staff> getStaffList(Staff bean, Integer pageNo, Integer pageSize) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from Staff o where 1=1", session);
            addParams(bean, finder);
            finder.append(" order by o.createTime desc");
            finder.setPageNo(pageNo);
            finder.setPageSize(pageSize);
            List<Staff> list = finder.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public Integer getTotalCount(Staff bean) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select count(o) from Staff o where 1=1", session);
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

    private void addParams(Staff bean, Finder finder) {
        if (null != bean) {
            if (null != bean.getId()) {
                finder.append(" and o.id=:id");
                finder.setParameter("id", bean.getId());
            }
            if (!StringUtils.isNullOrEmpty(bean.getName())) {
                finder.append(" and o.name like :name");
                finder.setParameter("name", "%" + bean.getName() + "%");
            }
            if (null != bean.getTeamId()) {
                finder.append(" and o.teamId=:teamId");
                finder.setParameter("teamId", bean.getTeamId());
            }
        }
    }
}
