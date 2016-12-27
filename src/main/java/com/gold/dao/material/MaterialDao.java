package com.gold.dao.material;

import com.gold.entity.Material;
import com.gold.entity.MaterialLog;
import com.gold.entity.MaterialPicture;

import java.util.List;

/**
 * Created by huzuxing on 2016/9/30.
 */
public interface MaterialDao {
    public void save(Material bean, String[] pictures);
    public void delete(Material bean);
    public void update(Material bean, String[] pictures);
    public Material getById(Integer id);
    public List<Material> getMaterialList(Material bean, Integer pageNo, Integer pageSize);
    public Integer getTotalCount(Material bean);
    public List<MaterialLog> getMaterialLogList(MaterialLog bean, Integer pageNo, Integer pageSize);
    public Integer getMaterialLogTotalCount(MaterialLog bean);
    public void saveMaterialLog(MaterialLog bean, Material entity);
    public List<MaterialPicture> getMaterialPictures(Integer materialId);
    public MaterialLog getMaterialLogById(Integer id);
}
