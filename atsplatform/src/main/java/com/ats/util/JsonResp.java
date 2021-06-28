package com.ats.util;

import java.util.List;

public class JsonResp {
    // 成功
    public final static String STATE_OK = "ok";

    public final static Integer CODE_OK = 0;

    // 系统错误
    public final static String STATE_ERR = "error";

    public final static Integer CODE_ERR = 1;

    // 业务错误
    public final static String STATE_WARN = "warn";

    /**
     * @Fields state: 请求结果状态
     */
    private String state;

    /**
     * @Fields errMsg: 错误信息
     */
    private String msg;

    /**
     * @Fields data: 结果集合
     */
    private List data;
    /**
     * 结果集条数
     */
    private Integer count;
    /**
     * 单个对象
     */
    private Object obj;

    /**
     * 内部定义标识：0成功，其他错误
     */
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public JsonResp() {
    }

    public JsonResp(String state) {
        if (!state.equals(JsonResp.STATE_OK) && !state.equals(JsonResp.STATE_ERR)
                && !state.equals(JsonResp.STATE_WARN)) {
//            throw new ParamException(0, "创建JsonResp参数非法");
        }
        this.state = state;
    }

    public JsonResp(String state, Integer code) {
        if (!state.equals(JsonResp.STATE_OK) && !state.equals(JsonResp.STATE_ERR) && !state.equals(JsonResp.STATE_WARN)
                && code.equals(JsonResp.CODE_OK) && code.equals(JsonResp.CODE_ERR)) {
//            throw new ParamException(0, "创建JsonResp参数非法");
        }
        this.state = state;
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
