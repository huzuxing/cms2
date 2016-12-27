package com.gold.dao.material;

import com.gold.common.Finder;
import com.gold.dao.HibernateTempDao;
import com.gold.entity.Material;
import com.gold.entity.MaterialLog;
import com.gold.entity.MaterialPicture;
import com.gold.util.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huzuxing on 2016/9/30.
 */
@Repository("materialDao")
public class MaterialDaoImpl extends HibernateTempDao<Material> implements MaterialDao {
    @Override
    public Class<Material> getClazz() {
        return Material.class;
    }

    public Material getById(Integer id) {
        if (null == id)
            return null;
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from Material o where o.id=:id", session);
            finder.setParameter("id", id);
            Material bean = (Material) finder.uniqueResult();
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public List<Material> getMaterialList(Material bean, Integer pageNo, Integer pageSize) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from Material o where 1=1", session);
            addParams(bean, finder);
            finder.append(" order by o.createTime desc");
            finder.setPageNo(pageNo);
            finder.setPageSize(pageSize);
            List<Material> list = finder.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public Integer getTotalCount(Material bean) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select count(o) from Material o where 1=1", session);
            addParams(bean, finder);
            finder.append(" order by o.createTime desc");
            Long count = (Long) finder.uniqueResult();
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    private void addMaterialLogParams(MaterialLog bean,Finder finder) {
        if (null != bean) {
            if (!StringUtils.isNullOrEmpty(bean.getMaterialName())) {
                finder.append(" and o.materialName like :name");
                finder.setParameter("name", "%" + bean.getMaterialName() + "%");
            }
            if (null != bean.getCate() && bean.getCate() >= 0) {
                finder.append(" and o.cate=:cate");
                finder.setParameter("cate", bean.getCate());
            }
        }
    }

    public Integer getMaterialLogTotalCount(MaterialLog bean) {
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select count(o) from MaterialLog o where 1=1", session);
            addMaterialLogParams(bean, finder);
            finder.append(" order by o.createTime desc");
            Long count = (Long) finder.uniqueResult();
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    @Override
    public void saveMaterialLog(MaterialLog bean, Material entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            session.save(bean);
            session.update(entity);
            tx.commit();
        } catch (Exception e) {
            if (null != tx)
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    @Override
    public List<MaterialPicture> getMaterialPictures(Integer materialId) {
        if (null == materialId) {
            return null;
        }
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from MaterialPicture o where o.materialId=:materialId", session);
            finder.setParameter("materialId", materialId);
            List<MaterialPicture> list = finder.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    @Override
    public List<MaterialLog> getMaterialLogList(MaterialLog bean, Integer pageNo, Integer pageSize) {

        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from MaterialLog o where 1=1", session);
            addMaterialLogParams(bean, finder);
            finder.append(" order by o.createTime desc");
            finder.setPageNo(pageNo);
            finder.setPageSize(pageSize);
            return finder.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
        return null;
    }

    private void addParams(Material bean, Finder finder) {
        if (null != bean) {
            if (null != bean.getId()) {
                finder.append(" and o.id=:id");
                finder.setParameter("id", bean.getId());
            }
            if (!StringUtils.isNullOrEmpty(bean.getName())) {
                finder.append(" and o.name like :name");
                finder.setParameter("name", "%"+ bean.getName() +"%");
            }
            if (!StringUtils.isNullOrEmpty(bean.getTechParam())) {
                finder.append(" and o.techParam like :techParam");
                finder.setParameter("techParam", "%"+ bean.getTechParam() +"%");
            }
        }
    }

    public void update(Material bean, String[] pictures) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            session.update(bean);
            Finder finder = new Finder("delete from material_picture where material_id=:materialId", session);
            finder.setParameter("materialId", bean.getId());
            finder.setHql(false);
            finder.executeUpdate();
            if (null != pictures) {
                savePictures(bean.getId(), pictures, session);
            }
            tx.commit();
        } catch (Exception e) {
            if (null != tx)
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    private void savePictures(int id, String[] pictures, Session session) throws Exception {
        for (String pic : pictures) {
            MaterialPicture picture = new MaterialPicture();
            picture.setMaterialId(id);
            picture.setUrl(pic);
            session.save(picture);
        }
    }

    public void save(Material bean, String[] pictures) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            session.save(bean);
            if (null != pictures) {
                savePictures(bean.getId(), pictures, session);
            }
            tx.commit();
        } catch (Exception e) {
            if (null != tx)
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }

    public MaterialLog getMaterialLogById(Integer id) {
        if (null == id)
            return null;
        Session session = null;
        try {
            session = getSession();
            Finder finder = new Finder("select o from MaterialLog o where o.id=:id", session);
            finder.setParameter("id", id);
            MaterialLog bean = (MaterialLog) finder.uniqueResult();
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != session && session.isOpen())
                session.close();
        }
    }
}
