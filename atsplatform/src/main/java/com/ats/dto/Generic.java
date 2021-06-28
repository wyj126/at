package com.ats.dto;

/**
 * 泛型类 用于接收不同类型的数据
 * @author Wyj
 * date 2021-06-17
 */
public class Generic<T> {

    //key这个成员变量的类型为T,T的类型由外部指定
    private T object;

    public Generic(T object){this.object = object;}

    public T getObject(){return object;}

}
