package com.pnf.elar.app.Bo;

import java.util.List;

/**
 * Created by VKrishnasamy on 26-09-2016.
 */

public class CurriculamEduPost {
    /**
     * id : 65
     * picturediary_knowledge_area_count : 25
     * title : Mål: Förskolan ska sträva efter att varje barn utvecklar
     * subchildren : [{"id":"66","picturediary_knowledge_area_count":"15","title":"Öppenhet, respekt, solidaritet och ansvar.","rght":"25","updated":"2015-09-21 13:15:16","created":"2015-09-21 13:15:16","status":"1","root_parent_id":"64","description":"Förskolan ska aktivt och medvetet påverka och stimulera barnen att utveckla förståelse för vårt samhälles gemensamma demokratiska värderingar och efterhand omfatta dem.","lft":"24","customer_id":"120","parent_id":"65"},{"id":"67","picturediary_knowledge_area_count":"15","title":"Förmåga att ta hänsyn till och leva sig in i andra människors situation samt vilja att hjälpa andra.","rght":"27","updated":"2015-09-21 13:15:16","created":"2015-09-21 13:15:16","status":"1","root_parent_id":"64","description":"Förskolan ska aktivt och medvetet påverka och stimulera barnen att utveckla förståelse för vårt samhälles gemensamma demokratiska värderingar och efterhand omfatta dem.","lft":"26","customer_id":"120","parent_id":"65"},{"id":"68","picturediary_knowledge_area_count":"11","title":"Sin förmåga att upptäcka, reflektera över och ta ställning till olika etiska dilemman och livsfrågor i vardagen.","rght":"29","updated":"2015-09-21 13:15:16","created":"2015-09-21 13:15:16","status":"1","root_parent_id":"64","description":"Förskolan ska aktivt och medvetet påverka och stimulera barnen att utveckla förståelse för vårt samhälles gemensamma demokratiska värderingar och efterhand omfatta dem.","lft":"28","customer_id":"120","parent_id":"65"},{"id":"69","picturediary_knowledge_area_count":"8","title":"Förståelse för att alla människor har lika värde oberoende av social bakgrund och oavsett kön, etnisk tillhörighet, religion eller annan trosuppfattning, sexuell läggning eller funktionsnedsättning.","rght":"31","updated":"2015-09-21 13:15:16","created":"2015-09-21 13:15:16","status":"1","root_parent_id":"64","description":"Förskolan ska aktivt och medvetet påverka och stimulera barnen att utveckla förståelse för vårt samhälles gemensamma demokratiska värderingar och efterhand omfatta dem.","lft":"30","customer_id":"120","parent_id":"65"},{"id":"70","picturediary_knowledge_area_count":"0","title":"Respekt för allt levande och omsorg om sin närmiljö.","rght":"33","updated":"2015-09-21 13:15:16","created":"2015-09-21 13:15:16","status":"1","root_parent_id":"64","description":"Förskolan ska aktivt och medvetet påverka och stimulera barnen att utveckla förståelse för vårt samhälles gemensamma demokratiska värderingar och efterhand omfatta dem.","lft":"32","customer_id":"120","parent_id":"65"}]
     * rght : 34
     * updated : 2015-09-21 13:15:16
     * created : 2015-09-21 13:15:16
     * status : 1
     * description : Förskolan ska aktivt och medvetet påverka och stimulera barnen att utveckla förståelse för vårt samhälles gemensamma demokratiska värderingar och efterhand omfatta dem.
     * lft : 23
     * customer_id : 120
     * parent_id : 64
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
    private String customer_id;
    private String parent_id;
    /**
     * id : 66
     * picturediary_knowledge_area_count : 15
     * title : Öppenhet, respekt, solidaritet och ansvar.
     * rght : 25
     * updated : 2015-09-21 13:15:16
     * created : 2015-09-21 13:15:16
     * status : 1
     * root_parent_id : 64
     * description : Förskolan ska aktivt och medvetet påverka och stimulera barnen att utveckla förståelse för vårt samhälles gemensamma demokratiska värderingar och efterhand omfatta dem.
     * lft : 24
     * customer_id : 120
     * parent_id : 65
     */

    public List<SubchildrenEntity> subchildren;

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

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<SubchildrenEntity> getSubchildren() {
        return subchildren;
    }

    public void setSubchildren(List<SubchildrenEntity> subchildren) {
        this.subchildren = subchildren;
    }

    public static class SubchildrenEntity {
        private String id;
        private String picturediary_knowledge_area_count;
        private String title;
        private String rght;
        private String updated;
        private String created;
        private String status;
        private String root_parent_id;
        private String description;
        private String lft;
        private String customer_id;
        private String parent_id;
private boolean selected;
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

        public String getRoot_parent_id() {
            return root_parent_id;
        }

        public void setRoot_parent_id(String root_parent_id) {
            this.root_parent_id = root_parent_id;
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

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
