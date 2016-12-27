package com.gold.dao.teamgroup;

import com.gold.common.Finder;
import com.gold.dao.HibernateTempDao;
import com.gold.entity.Staff;
import com.gold.entity.TeamGroup;
import com.gold.util.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huzuxing on 2016/10/8.
 */
@Repository("teamGroupDao")
public class TeamGroupDaoImpl extends HibernateTempDao<TeamGroup> implements TeamGroupDao {
    @Override
    public Class<TeamGroup> getClazz() {
        return TeamGroup.class;
    }

    public TeamGroup getById(Integer id) {
        if (null == id)
            return null;
        Finder finder = new Finder("select o from TeamGroup o where o.id=:id");
        finder.setParameter("id", id);
        TeamGroup bean = (TeamGroup) queryObject(finder);
        return bean;
    }

    public List<TeamGroup> getTeamGroupList(TeamGroup bean, Integer pageNo, Integer pageSize) {
        Finder finder = new Finder("select o from TeamGroup o where 1=1");
        addParams(bean, finder);
        finder.append(" order by o.createTime desc");
        finder.setPageNo(pageNo);
        finder.setPageSize(pageSize);
        List<TeamGroup> list = queryList(finder);
        return list;
    }

    public Integer getTotalCount(TeamGroup bean) {
        Finder finder = new Finder("select count(o) from TeamGroup o where 1=1");
        addParams(bean, finder);
        finder.append(" order by o.createTime desc");
        Long count = (Long) queryObject(finder);
        return count.intValue();
    }

    private void addParams(TeamGroup bean, Finder finder) {
        if (null != bean) {
            if (null != bean.getId()) {
                finder.append(" and o.id=:id");
                finder.setParameter("id", bean.getId());
            }
            if (!StringUtils.isNullOrEmpty(bean.getName())) {
                finder.append(" and o.name like :name");
                finder.setParameter("name", "%" + bean.getName() + "%");
            }
        }
    }
}
