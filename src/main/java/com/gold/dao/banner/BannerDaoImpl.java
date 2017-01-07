package com.gold.dao.banner;

import com.gold.common.Finder;
import com.gold.dao.HibernateTempDao;
import com.gold.entity.Banner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huzuxing on 2016/12/27.
 */
@Repository("bannerDao")
public class BannerDaoImpl extends HibernateTempDao<Banner> implements BannerDao {
    @Override
    public Class<Banner> getClazz() {
        return Banner.class;
    }

    @Override
    public void saveAll(List<Banner> list) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            if (null == list || list.size() <= 0) {
                throw new Exception("null Banner to save ...");
            }
            Finder finder = new Finder("delete from Banner where cate=:cate", session);
            finder.setParameter("cate", list.get(0).getCate());
            finder.executeUpdate();
            for(Banner banner : list) {
                session.save(banner);
            }
            tx.commit();
        } catch (Exception e) {
            if (null != tx)
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    @Override
    public List<Banner> getBanners(Banner bean) {
        Finder finder = new Finder("select o from Banner o where 1=1");
        if (null != bean) {
            if (null != bean.getCate())
            finder.append(" and o.cate=:cate");
            finder.setParameter("cate", bean.getCate());
        }
        return queryList(finder);
    }
}
