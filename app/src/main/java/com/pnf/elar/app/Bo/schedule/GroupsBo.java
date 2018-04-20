package com.pnf.elar.app.Bo.schedule;

/**
 * Created by VKrishnasamy on 19-01-2017.
 */

public class GroupsBo {
    /**
     * id : 40
     * CLA_CUS_Rid : 1
     * group_type : class
     * parent_id : 0
     * course_id : 0
     * name : A4
     * start : 1430949600
     * end : 1497564000
     * news_count : 0
     * status : 1
     * picturediary_group_count : 31
     * created : 2015-05-08 10:12:06
     * modified : 2015-11-10 15:59:53
     */

    private String id;
    private String CLA_CUS_Rid;
    private String group_type;
    private String parent_id;
    private String course_id;
    private String name;
    private String start;
    private String end;
    private String news_count;
    private String status;
    private String picturediary_group_count;
    private String created;
    private String modified;

    private boolean selected;

    public GroupsBo(String id, String name, boolean selected) {
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

    public String getCLA_CUS_Rid() {
        return CLA_CUS_Rid;
    }

    public void setCLA_CUS_Rid(String CLA_CUS_Rid) {
        this.CLA_CUS_Rid = CLA_CUS_Rid;
    }

    public String getGroup_type() {
        return group_type;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getNews_count() {
        return news_count;
    }

    public void setNews_count(String news_count) {
        this.news_count = news_count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicturediary_group_count() {
        return picturediary_group_count;
    }

    public void setPicturediary_group_count(String picturediary_group_count) {
        this.picturediary_group_count = picturediary_group_count;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
