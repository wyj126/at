package com.ats.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (PostgraduateCurriculum)实体类
 *
 * @author makejava
 * @since 2021-06-18 16:20:15
 */
public class PostgraduateCurriculum implements Serializable {
    private static final long serialVersionUID = -10911762695961527L;

    private Integer id;

    private String chineseName;

    private String englishName;
    /**
     * 创建时间
     */
    private Date startTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 学分
     */
    private Double credit;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

}

