package com.gold.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huzuxing on 2016/10/23.
 */
@Entity
@Table(name = "tools_log")
public class ToolsLog implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tools_id")
    private Integer toolsId;

    @Column(name = "tools_name")
    private String toolsName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "time")
    private Date time;

    @Column(name = "reason")
    private String reason;

    @Column(name = "operator")
    private String operator;

    @Column(name = "phone")
    private String phone;

    @Column(name = "auditor")
    private String auditor;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getToolsId() {
        return toolsId;
    }

    public void setToolsId(Integer toolsId) {
        this.toolsId = toolsId;
    }

    public String getToolsName() {
        return toolsName;
    }

    public void setToolsName(String toolsName) {
        this.toolsName = toolsName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
