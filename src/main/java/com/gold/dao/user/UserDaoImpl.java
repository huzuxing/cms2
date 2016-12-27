package com.gold.dao.user;

import com.gold.common.Finder;
import com.gold.dao.HibernateTempDao;
import com.gold.entity.AdminUser;
import com.gold.util.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Created by huzuxing on 2016/9/29.
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateTempDao<AdminUser> implements UserDao {
    @Override
    public Class<AdminUser> getClazz() {
        return AdminUser.class;
    }

    @Override
    public AdminUser getAdminUser(String username, String password) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from AdminUser o where 1=1", session);
            if (!StringUtils.isNullOrEmpty(username) && !StringUtils.isNullOrEmpty(password)) {
                finder.append(" and o.username=:username");
                finder.setParameter("username", username);
                finder.append(" and o.password=:password");
                finder.setParameter("password", password);
            }
            AdminUser user = (AdminUser) finder.uniqueResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    @Override
    public AdminUser getAdminUser(String username) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from AdminUser o where o.username=:username", session);
            finder.setParameter("username", username);
            AdminUser user = (AdminUser) finder.uniqueResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }
}
