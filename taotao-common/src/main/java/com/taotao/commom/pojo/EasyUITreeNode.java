package com.taotao.commom.pojo;

import java.io.Serializable;

/**
 * Created by lee on 2017/3/11.
 */
@SuppressWarnings(value = "all")
public class EasyUITreeNode implements Serializable {
    private long id;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
