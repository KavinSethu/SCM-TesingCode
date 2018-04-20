package com.pnf.elar.app.Bo.schedule;

import java.util.List;

/**
 * Created by VKrishnasamy on 27-01-2017.
 */

public class StudentsStudyPartnerBo {
    public String status;
    public String message;
    public List<StudentsUserEntity> students;
    public List<StudentsUserEntity> partners;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StudentsUserEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsUserEntity> students) {
        this.students = students;
    }

    public List<StudentsUserEntity> getPartners() {
        return partners;
    }

    public void setPartners(List<StudentsUserEntity> partners) {
        this.partners = partners;
    }
}
