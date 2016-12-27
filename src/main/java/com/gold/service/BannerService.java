package com.gold.service;

import com.gold.dao.banner.BannerDao;
import com.gold.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huzuxing on 2016/12/27.
 */
@Component("bannerService")
public class BannerService {

    public void save(List<Banner> banners) {
        banenrDao.saveAll(banners);
    }

    public List<Banner> getBanners(Banner banner) {
        return banenrDao.getBanners(banner);
    }

    @Autowired
    private BannerDao banenrDao;
}
