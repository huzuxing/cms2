package com.gold.dao.scenework;

import com.gold.entity.Tools;
import com.gold.entity.ToolsLog;

import java.util.List;

/**
 * Created by huzuxing on 2016/9/30.
 */
public interface ToolsDao {
    public void save(Tools bean);
    public void delete(Tools bean);
    public void update(Tools bean);
    public Tools getById(Integer id);
    public List<Tools> getToolsList(Tools bean, Integer pageNo, Integer pageSize);
    public Integer getTotalCount(Tools bean);
    public void updateToolsAndAddToolsLog(Tools bean, ToolsLog logBean);
    public List<ToolsLog> getToolsLogList(ToolsLog bean, Integer pageNo, Integer pageSize);
    public Integer getToolsLogTotalCount(ToolsLog bean);
    public ToolsLog getToolsLogById(Integer id);
}
