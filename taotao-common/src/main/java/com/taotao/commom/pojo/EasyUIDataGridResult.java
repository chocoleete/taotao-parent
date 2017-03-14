package com.taotao.commom.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lee on 2017/3/10.
 */
@SuppressWarnings(value = "all")
public class EasyUIDataGridResult implements Serializable {
    //成员变量
    private Integer total;
    private List rows;

    public EasyUIDataGridResult() {
    }

    public EasyUIDataGridResult(List rows, Long total) {
        this.rows = rows;
        this.total = total.intValue();
    }

    public EasyUIDataGridResult(List rows, Integer total) {
        this.rows = rows;
        this.total = total;
    }
    //get和set方法

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
