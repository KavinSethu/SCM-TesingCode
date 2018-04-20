package com.elar.attandance.list.model;

/**
 * Created by VKrishnasamy on 23-11-2016.
 */

public class RetrievalHistory {
    /**
     * type : Hämtad
     * data_type : retrieved
     * id : 3235
     * mark_time : 10:35
     * name : Adam Adamsson
     */

    private String type;
    private String data_type;
    private String id;
    private String mark_time;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMark_time() {
        return mark_time;
    }

    public void setMark_time(String mark_time) {
        this.mark_time = mark_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
