package com.ats.dto;

import java.util.List;

/**
 * 记录学生录入信息
 * @author Wyj
 * date 2021-06-18
 */
public class Student {

    /**
     * 姓名
     */
    private String name;

    /**
     * 学号
     */
    private Integer stuNum;

    /**
     * 学院
     */
    private String collage;

    /**
     * 专业
     */
    private String major;

    /**
     * 入学日期
     */
    private String admissionDate;

    /**
     * 制表日期
     */
    private String tabulationDate;

    /**
     * 指导老师
     */
    private String instructor;

    /**
     * 课程信息
     */
    private List<Course> course;

    /**
     * 学位论文题目
     */
    private String thesisTopic;

    /**
     * 论文分数
     */
    private String thesisScore;

    /**
     * 类型 0-本科生 1-研究生
     */
    private Integer type;

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public String getThesisTopic() {
        return thesisTopic;
    }

    public String getThesisScore() {
        return thesisScore;
    }

    public void setThesisTopic(String thesisTopic) {
        this.thesisTopic = thesisTopic;
    }

    public void setThesisScore(String thesisScore) {
        this.thesisScore = thesisScore;
    }

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStuNum(Integer stuNum) {
        this.stuNum = stuNum;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public void setTabulationDate(String tabulationDate) {
        this.tabulationDate = tabulationDate;
    }

    public String getName() {
        return name;
    }

    public Integer getStuNum() {
        return stuNum;
    }

    public String getCollage() {
        return collage;
    }

    public String getMajor() {
        return major;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public String getTabulationDate() {
        return tabulationDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", stuNum=" + stuNum +
                ", collage='" + collage + '\'' +
                ", major='" + major + '\'' +
                ", admissionDate=" + admissionDate +
                ", tabulationDate=" + tabulationDate +
                '}';
    }
}
