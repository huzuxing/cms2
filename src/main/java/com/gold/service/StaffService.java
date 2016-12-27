package com.gold.service;

import com.gold.common.Pager;
import com.gold.dao.scenework.ToolsDao;
import com.gold.dao.staff.StaffDao;
import com.gold.entity.Staff;
import com.gold.entity.Tools;
import com.gold.util.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by huzuxing on 2016/10/8.
 */
@Component("staffService")
public class StaffService {
    @Autowired
    private StaffDao staffDao;

    public void save(Staff bean) {
        bean.setCreateTime(new Date());
        staffDao.save(bean);
    }

    public Pager<Staff> getStaffList(String name, Integer id, Integer pageNo, Integer pageSize) {
        Staff bean = new Staff();
        bean.setName(name);
        bean.setId(id);
        Pager pager = new Pager(QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize));
        pager.setTotalCount(staffDao.getTotalCount(bean));
        pager.setList(staffDao.getStaffList(bean, null, null));
        return pager;
    }

    public void delete(Integer id) {
        Staff bean = staffDao.getById(id);
        if (null != bean) {
            staffDao.delete(bean);
        }
    }

    public Staff getStaffById(Integer id) {
        return staffDao.getById(id);
    }

    public void update(Staff bean) {
        staffDao.update(bean);
    }
}
