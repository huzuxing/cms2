package com.gold.service;

import com.gold.dao.apptoken.TokenDao;
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

    public String generateToken() {
        AppToken token = new AppToken();
        String t = UUID.randomUUID().toString();
        token.setToken(t);
        token.setCreateTime(new Date());
        token.setExpireTime(new Date());
        tokenDao.save(token);
        return t;
    }

    public void delete() {
        AppToken token = tokenDao.getToken();
        tokenDao.delete(token);
    }

    public AppToken getToken() {
        return tokenDao.getToken();
    }

    @Autowired
    private TokenDao tokenDao;
}


