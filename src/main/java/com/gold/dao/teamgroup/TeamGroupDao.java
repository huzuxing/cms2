package com.gold.dao.teamgroup;

import com.gold.entity.Staff;
import com.gold.entity.TeamGroup;

import java.util.List;

/**
 * Created by huzuxing on 2016/10/8.
 */
public interface TeamGroupDao {
    public void save(TeamGroup bean);
    public void delete(TeamGroup bean);
    public void update(TeamGroup bean);
    public TeamGroup getById(Integer id);
    public List<TeamGroup> getTeamGroupList(TeamGroup bean, Integer pageNo, Integer pageSize);
    public Integer getTotalCount(TeamGroup bean);
}
