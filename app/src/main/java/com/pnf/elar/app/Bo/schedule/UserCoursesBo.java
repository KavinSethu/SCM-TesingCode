package com.pnf.elar.app.Bo.schedule;

/**
 * Created by VKrishnasamy on 19-01-2017.
 */

public class UserCoursesBo {


    /**
     * id : 237
     * COU_Name : Angular.js
     * course_code : xazKMR
     * free_course : 1
     */

    private CouCourseEntity CouCourse;

    public UserCoursesBo(CouCourseEntity couCourse) {
        CouCourse = couCourse;
    }

    public CouCourseEntity getCouCourse() {
        return CouCourse;
    }

    public void setCouCourse(CouCourseEntity CouCourse) {
        this.CouCourse = CouCourse;
    }

    public static class CouCourseEntity {
        private String id;
        private String COU_Name;
        private String course_code;
        private String free_course;

        public CouCourseEntity(String id, String COU_Name) {
            this.id = id;
            this.COU_Name = COU_Name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCOU_Name() {
            return COU_Name;
        }

        public void setCOU_Name(String COU_Name) {
            this.COU_Name = COU_Name;
        }

        public String getCourse_code() {
            return course_code;
        }

        public void setCourse_code(String course_code) {
            this.course_code = course_code;
        }

        public String getFree_course() {
            return free_course;
        }

        public void setFree_course(String free_course) {
            this.free_course = free_course;
        }
    }
}
