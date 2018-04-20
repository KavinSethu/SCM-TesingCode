package com.pnf.elar.app.Bo;

import java.util.List;

/**
 * Created by VKrishnasamy on 26-09-2016.
 */

public class CurriculamMainTag {
    /**
     * id : 64
     * picturediary_knowledge_area_count : 25
     * title : NORMER OCH VÄRDEN
     * rght : 35
     * updated : 2015-09-21 13:15:16
     * created : 2015-09-21 13:15:16
     * status : 1
     * description : Förskolan ska aktivt och medvetet påverka och stimulera barnen att utveckla förståelse för vårt samhälles gemensamma demokratiska värderingar och efterhand omfatta dem.
     * lft : 22
     */

    private String id;
    private String picturediary_knowledge_area_count;
    private String title;
    private String rght;
    private String updated;
    private String created;
    private String status;
    private String description;
    private String lft;

    public List<CurriculamEduPost> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicturediary_knowledge_area_count() {
        return picturediary_knowledge_area_count;
    }

    public void setPicturediary_knowledge_area_count(String picturediary_knowledge_area_count) {
        this.picturediary_knowledge_area_count = picturediary_knowledge_area_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRght() {
        return rght;
    }

    public void setRght(String rght) {
        this.rght = rght;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLft() {
        return lft;
    }

    public void setLft(String lft) {
        this.lft = lft;
    }

    public List<CurriculamEduPost> getChildren() {
        return children;
    }

    public void setChildren(List<CurriculamEduPost> children) {
        this.children = children;
    }
}
