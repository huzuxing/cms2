package com.gold.dao.apptoken;

import com.gold.entity.AppToken;

/**
 * Created by huzuxing on 2016/10/23.
 */
public interface TokenDao {

    public void save(AppToken bean);

    public void delete(AppToken bean);

    public AppToken getToken();
}
