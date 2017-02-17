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
    public AppToken getToken(Integer userId) {
        Finder finder = new Finder("select o from AppToken o where userId=:userId");
        finder.setParameter("userId", userId);
        AppToken token = (AppToken) queryObject(finder);
        return token;
    }

    @Override
    public AppToken getToken(String token) {
        Finder finder = new Finder("select o from AppToken o where token=:token");
        finder.setParameter("token", token);
        AppToken entity = (AppToken) queryObject(finder);
        return entity;
    }
}
