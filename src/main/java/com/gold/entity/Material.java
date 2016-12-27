package com.gold.entity;

import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huzuxing on 2016/9/29.
 */
@Entity
@Table(name = "material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tech_param")
    private String techParam;

    @Column(name = "storeroom1")
    private Integer storeroom1;

    @Column(name = "storeroom2")
    private Integer storeroom2;

    @Column(name = "system")
    private Integer system;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "createtime")
    private Date createTime;

    @Column(name = "updatetime")
    private Date updateTime;

    @Column(name = "name")
    private String name;

    @Column(name = "room1_rest")
    private Integer room1Rest;

    @Column(name = "room2_rest")
    private Integer room2Rest;

    @Column(name = "system_rest")
    private Integer systemRest;

    @Column(name = "totalCount")
    private Integer totalCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTechParam() {
        return techParam;
    }

    public void setTechParam(String techParam) {
        this.techParam = techParam;
    }

    public Integer getStoreroom1() {
        return storeroom1;
    }

    public void setStoreroom1(Integer storeroom1) {
        this.storeroom1 = storeroom1;
    }

    public Integer getStoreroom2() {
        return storeroom2;
    }

    public void setStoreroom2(Integer storeroom2) {
        this.storeroom2 = storeroom2;
    }

    public Integer getSystem() {
        return system;
    }

    public void setSystem(Integer system) {
        this.system = system;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoom1Rest() {
        return room1Rest;
    }

    public void setRoom1Rest(Integer room1Rest) {
        this.room1Rest = room1Rest;
    }

    public Integer getRoom2Rest() {
        return room2Rest;
    }

    public void setRoom2Rest(Integer room2Rest) {
        this.room2Rest = room2Rest;
    }

    public Integer getSystemRest() {
        return systemRest;
    }

    public void setSystemRest(Integer systemRest) {
        this.systemRest = systemRest;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
