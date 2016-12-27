package com.gold.dao.apptoken;

import com.gold.common.Finder;
import com.gold.dao.HibernateTempDao;
import com.gold.entity.AppToken;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Created by huzuxing on 2016/10/23.
 */
@Repository("tokenDao")
public class TokenDaoImpl extends HibernateTempDao<AppToken> implements TokenDao {
    @Override
    public Class<AppToken> getClazz() {
        return AppToken.class;
    }

    @Override
    public AppToken getToken() {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from AppToken o", session);
            AppToken token = (AppToken) finder.uniqueResult();
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
        return null;
    }
}
