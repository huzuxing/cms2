package com.gold.service;

import com.gold.common.Pager;
import com.gold.dao.staff.StaffDao;
import com.gold.dao.teamgroup.TeamGroupDao;
import com.gold.entity.Staff;
import com.gold.entity.TeamGroup;
import com.gold.util.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by huzuxing on 2016/10/8.
 */
@Component("teamGroupService")
public class TeamGroupService {
    @Autowired
    private TeamGroupDao teamGroupDao;

    public void save(TeamGroup bean) {
        bean.setCreateTime(new Date());
        teamGroupDao.save(bean);
    }

    public Pager<TeamGroup> getTeamGroupList(String name, Integer id, Integer pageNo, Integer pageSize) {
        TeamGroup bean = new TeamGroup();
        bean.setName(name);
        bean.setId(id);
        Pager pager = new Pager(QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize));
        pager.setTotalCount(teamGroupDao.getTotalCount(bean));
        pager.setList(teamGroupDao.getTeamGroupList(bean, QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize)));
        return pager;
    }

    public void delete(Integer id) {
        TeamGroup bean = teamGroupDao.getById(id);
        if (null != bean) {
            teamGroupDao.delete(bean);
        }
    }

    public TeamGroup getTeamGroupById(Integer id) {
        return teamGroupDao.getById(id);
    }

    public void update(TeamGroup bean) {
        teamGroupDao.update(bean);
    }

    public List<TeamGroup> getTeamGroups() {
        return teamGroupDao.getTeamGroupList(null, null, null);
    }
}
