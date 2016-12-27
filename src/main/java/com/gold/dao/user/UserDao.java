package com.gold.dao.user;

import com.gold.entity.AdminUser;

/**
 * Created by huzuxing on 2016/9/29.
 */
public interface UserDao {

    public AdminUser getAdminUser(String username, String password);

    public AdminUser getAdminUser(String username);
}
