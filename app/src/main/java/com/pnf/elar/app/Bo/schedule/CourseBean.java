package com.pnf.elar.app.Bo.schedule;

/**
 * Created by VKrishnasamy on 04-11-2016.
 */

public class CourseBean {


    public String id;
    public String name;

    public boolean selected;

    public CourseBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CourseBean(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
