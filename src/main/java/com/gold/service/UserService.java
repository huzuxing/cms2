package com.gold.service;

import com.gold.dao.user.UserDao;
import com.gold.entity.AdminUser;
import com.gold.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huzuxing on 2016/9/30.
 */
@Component("/userService")
public class UserService {

    private static String prefix = "abcd1234";
    @Autowired
    private UserDao userDao;

    public AdminUser login(String username, String password) {
        return userDao.getAdminUser(username, EncryptUtils.originDigest(prefix + password));
    }

    public AdminUser getAdminUser(String username) {
        return userDao.getAdminUser(username);
    }
}
