package com.gold.dao.banner;

import com.gold.entity.Banner;

import java.util.List;

/**
 * Created by huzuxing on 2016/12/27.
 */
public interface BannerDao {

    public void saveAll(List<Banner> list);

    public List<Banner> getBanners(Banner bean);
}
