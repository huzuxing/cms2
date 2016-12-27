package com.gold.dao.staff;

import com.gold.entity.Staff;

import java.util.List;

/**
 * Created by huzuxing on 2016/10/8.
 */
public interface StaffDao {
    public void save(Staff bean);
    public void delete(Staff bean);
    public void update(Staff bean);
    public Staff getById(Integer id);
    public List<Staff> getStaffList(Staff bean, Integer pageNo, Integer pageSize);
    public Integer getTotalCount(Staff bean);
}
