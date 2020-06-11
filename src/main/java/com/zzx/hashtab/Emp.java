package com.zzx.hashtab;

/**
 * 雇员类
 *
 * @Author zzx
 * @Date 2020-06-11 21:27
 */
public class Emp {
    /**
     * 为了方便不写get set，此处将成员变量设为public
     * 雇员的id
     */
    public int id;
    /**
     * 雇员的姓名
     */
    public String name;
    /**
     * 下一个雇员
     */
    public Emp next;

    //id和name的带参构造
    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
