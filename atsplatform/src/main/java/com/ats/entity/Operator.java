package com.ats.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Operator)实体类
 *
 * @author makejava
 * @since 2021-06-16 23:53:33
 */
public class Operator implements Serializable {
    private static final long serialVersionUID = -40308935975323067L;

    private Integer id;
    /**
     * 操作模块
     */
    private String operatorModule;
    /**
     * 请求路径
     */
    private String requestUri;
    /**
     * 操作时间
     */
    private Date operatorTime;
    /**
     * 操作ip
     */
    private String operatorIp;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperatorModule() {
        return operatorModule;
    }

    public void setOperatorModule(String operatorModule) {
        this.operatorModule = operatorModule;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

}

