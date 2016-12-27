package com.gold.dao.news;

import com.gold.entity.News;

import java.util.List;

/**
 * Created by huzuxing on 2016/9/24.
 */
public interface NewsDao {
    public void save(News bean);
    public void delete(News bean);
    public void update(News bean);
    public News getById(Integer id);
    public List<News> getNewsList(News bean, Integer pageNo, Integer pageSize);
    public Integer getTotalCount(News bean);
}
