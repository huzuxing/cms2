package com.gold.service;

import com.gold.common.Pager;
import com.gold.dao.scenework.ToolsDao;
import com.gold.entity.Tools;
import com.gold.entity.ToolsLog;
import com.gold.util.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by huzuxing on 2016/9/24.
 */
@Component("toolsService")
public class ToolsService {
    @Autowired
    private ToolsDao toolsDao;

    public void save(Tools bean) {
        bean.setCreateTime(new Date());
        bean.setStatus(0);
        toolsDao.save(bean);
    }

    public Pager<Tools> getToolsList(String name,Integer status, Integer id, Integer pageNo, Integer pageSize) {
        Tools bean = new Tools();
        bean.setName(name);
        bean.setId(id);
        bean.setStatus(status);
        Pager pager = new Pager(QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize));
        pager.setTotalCount(toolsDao.getTotalCount(bean));
        pager.setList(toolsDao.getToolsList(bean, QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize)));
        return pager;
    }

    public void delete(Integer id) {
        Tools bean = toolsDao.getById(id);
        if (null != bean) {
            toolsDao.delete(bean);
        }
    }

    public Tools getToolsById(Integer id) {
        return toolsDao.getById(id);
    }

    public void update(Tools bean) {
        toolsDao.update(bean);
    }

    public void updateToolsAndAddToolsLog(Tools bean, Integer cate) {
        ToolsLog logBean = new ToolsLog();
        logBean.setStatus(cate);
        logBean.setToolsId(bean.getId());
        logBean.setToolsName(bean.getName());
        logBean.setTime(bean.getTime());
        logBean.setReason(bean.getReason());
        logBean.setOperator(bean.getOperator());
        logBean.setAuditor(bean.getAuditor());
        logBean.setPhone(bean.getPhone());
        toolsDao.updateToolsAndAddToolsLog(bean, logBean);
    }

    public Pager<ToolsLog> getToolsLogPager(String name, Integer status, Integer id, Integer pageNo, Integer pageSize) {
        ToolsLog bean = new ToolsLog();
        bean.setToolsName(name);
        bean.setId(id);
        if (null != status)
            bean.setStatus(status);
        Pager pager = new Pager(QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize));
        pager.setTotalCount(toolsDao.getToolsLogTotalCount(bean));
        pager.setList(toolsDao.getToolsLogList(bean, QueryUtils.pageNoCheck(pageNo), QueryUtils.pageSizeCheck(pageSize)));
        return pager;
    }

    public ToolsLog getToolsLogById(Integer id) {
        return toolsDao.getToolsLogById(id);
    }

    public Tools getToolsByName(String name) {
        return toolsDao.getToolsByName(name);
    }

    public void toolsOutOrInBatch(List<Tools> toolses, List<ToolsLog> toolsLogs) throws Exception {
        toolsDao.toolsOutOrInBatch(toolses,toolsLogs);
    }
}
