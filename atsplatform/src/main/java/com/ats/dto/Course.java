package com.ats.dto;

/**
 * @author Wyj
 * date 2021-06-20
 */
public class Course {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程学分
     */
    private Double courseCredit;

    /**
     * 课程分数
     */
    private String courseScore;

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseCredit(Double courseCredit) {
        this.courseCredit = courseCredit;
    }

    public void setCourseScore(String courseScore) {
        this.courseScore = courseScore;
    }

    public String getCourseName() {
        return courseName;
    }

    public Double getCourseCredit() {
        return courseCredit;
    }

    public String getCourseScore() {
        return courseScore;
    }
}
