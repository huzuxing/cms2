package com.gold.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huzuxing on 2016/12/24.
 */
@Entity
@Table(name = "team_group")
public class TeamGroup implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "ideal")
    private String ideal;

    @Column(name = "catchword")
    private String catchWord;

    @Column(name = "logo")
    private String logo;

    @Column(name = "time_axis")
    private String timeAxis;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIdeal() {
        return ideal;
    }

    public void setIdeal(String ideal) {
        this.ideal = ideal;
    }

    public String getCatchWord() {
        return catchWord;
    }

    public void setCatchWord(String catchWord) {
        this.catchWord = catchWord;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTimeAxis() {
        return timeAxis;
    }

    public void setTimeAxis(String timeAxis) {
        this.timeAxis = timeAxis;
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
}
