package com.pnf.elar.app.Bo.schedule;

import java.util.List;

/**
 * Created by VKrishnasamy on 07-01-2017.
 */

public class GetAbsentNoteBo {
    /**
     * status : true
     * data : {"student_name":"  Hema    Lathaa","image":"/img/portraits/profile26.png","description":"test","written_by":"Katrina Lindsjö","wholedaysick_id":"2495","written_by_parent":"0","sickdate":"2017-01-07","leave_type":"leave","approved":"0","created":"2017-01-07","parents":[]}
     */

    private String status;
    /**
     * student_name :   Hema    Lathaa
     * image : /img/portraits/profile26.png
     * description : test
     * written_by : Katrina Lindsjö
     * wholedaysick_id : 2495
     * written_by_parent : 0
     * sickdate : 2017-01-07
     * leave_type : leave
     * approved : 0
     * created : 2017-01-07
     * parents : []
     */

    private DataEntity data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String student_name;
        private String image;
        private String description;
        private String written_by;
        private String wholedaysick_id;
        private String written_by_parent;
        private String sickdate;
        private String leave_type;
        private String approved;
        private String created;
        private List<?> parents;

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWritten_by() {
            return written_by;
        }

        public void setWritten_by(String written_by) {
            this.written_by = written_by;
        }

        public String getWholedaysick_id() {
            return wholedaysick_id;
        }

        public void setWholedaysick_id(String wholedaysick_id) {
            this.wholedaysick_id = wholedaysick_id;
        }

        public String getWritten_by_parent() {
            return written_by_parent;
        }

        public void setWritten_by_parent(String written_by_parent) {
            this.written_by_parent = written_by_parent;
        }

        public String getSickdate() {
            return sickdate;
        }

        public void setSickdate(String sickdate) {
            this.sickdate = sickdate;
        }

        public String getLeave_type() {
            return leave_type;
        }

        public void setLeave_type(String leave_type) {
            this.leave_type = leave_type;
        }

        public String getApproved() {
            return approved;
        }

        public void setApproved(String approved) {
            this.approved = approved;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public List<?> getParents() {
            return parents;
        }

        public void setParents(List<?> parents) {
            this.parents = parents;
        }
    }
}
