package com.gold.service;

import com.gold.common.Pager;
import com.gold.dao.news.NewsDao;
import com.gold.entity.News;
import com.gold.util.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by huzuxing on 2016/9/24.
 */
@Component("newsService")
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public void save(News bean) {
        bean.setCreateTime(new Date());
        newsDao.save(bean);
    }

    public Pager<News> getNewsList(String title, Integer id, Integer pageNo, Integer pageSize) {
        News bean = new News();
        bean.setTitle(title);
        bean.setId(id);
        Pager pager = new Pager(QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize));
        pager.setTotalCount(newsDao.getTotalCount(bean));
        pager.setList(newsDao.getNewsList(bean, QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize)));
        return pager;
    }

    public void delete(Integer id) {
        News bean = newsDao.getById(id);
        if (null != bean) {
            newsDao.delete(bean);
        }
    }

    public News getNewsById(Integer id) {
        return newsDao.getById(id);
    }
}
