package com.taotao.pojo;

/**
 * Created by lee on 2017/3/22.
 */
@SuppressWarnings(value = "all")
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private String address;

    public Student() {
    }

    public Student(Integer id, String name, Integer age, String address) {
        this.address = address;
        this.age = age;
        this.id = id;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

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
}
