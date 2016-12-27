package com.gold.dao.tools;

import com.gold.entity.SceneWork;

import java.util.List;

/**
 * Created by huzuxing on 2016/10/7.
 */
public interface SceneWorkDao {
    public void save(SceneWork bean);
    public void delete(SceneWork bean);
    public void update(SceneWork bean);
    public SceneWork getById(Integer id);
    public List<SceneWork> getSceneWorkList(SceneWork bean, Integer pageNo, Integer pageSize);
    public Integer getTotalCount(SceneWork bean);
}
