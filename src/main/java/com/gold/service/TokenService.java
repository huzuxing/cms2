package com.gold.service;

import com.gold.dao.apptoken.TokenDao;
import com.gold.entity.AdminUser;
import com.gold.entity.AppToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Created by huzuxing on 2016/10/23.
 */
@Component("tokenService")
public class TokenService {

    public String generateToken(Integer userId) {
        AppToken token = new AppToken();
        String t = UUID.randomUUID().toString();
        token.setToken(t);
        token.setCreateTime(new Date());
        token.setExpireTime(new Date());
        token.setUserId(userId);
        tokenDao.save(token);
        return t;
    }

    public void delete(String token) {
        AppToken entity = tokenDao.getToken(token);
        tokenDao.delete(entity);
    }

    public AppToken getToken(Integer userId) {
        return tokenDao.getToken(userId);
    }

    public AppToken getToken(String token) {
        return tokenDao.getToken(token);
    }

    @Autowired
    private TokenDao tokenDao;
}


