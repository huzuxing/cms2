package com.gold.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huzuxing on 2016/10/7.
 */
@Entity
@Table(name = "scene_work")
public class SceneWork implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "charge_person")
    private String chargePerson;

    @Column(name = "member")
    private String member;

    @Column(name = "context")
    private String context;

    @Column(name = "work_record")
    private String workRecord;

    @Column(name = "persons")
    private String persons;

    @Column(name = "attention")
    private String attention;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "work_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date workTime;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "sort")
    private Integer sort;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getWorkRecord() {
        return workRecord;
    }

    public void setWorkRecord(String workRecord) {
        this.workRecord = workRecord;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
