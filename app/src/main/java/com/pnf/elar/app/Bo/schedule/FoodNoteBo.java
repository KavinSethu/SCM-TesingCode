package com.pnf.elar.app.Bo.schedule;

import java.util.List;

/**
 * Created by VKrishnasamy on 29-12-2016.
 */

public class FoodNoteBo {


    /**
     * status : true
     * final_students : [{"allergy_name":"hlooooo I am here","free_text":"hello","user_name":"LinneaLarsson","image":"/img/portraits/profile45.png"},{"allergy_name":"","free_text":"","user_name":"SahilKapoor","image":"/img/portraits/profile61.png"},{"allergy_name":"fev","free_text":"eng","user_name":"AdamAdamsson","image":"/img/portraits/profile7.png"},{"allergy_name":"ctctc","free_text":"jsvsbbchfghb","user_name":"AdamAdamsson","image":"/img/portraits/profile61.png"},{"allergy_name":"","free_text":"hdhx","user_name":"AdamAdamsson","image":"/img/portraits/profile7.png"}]
     * student_count_without_absent_dates : 56
     * present_student_count : 0
     */

    public String status;
    public long student_count_without_absent_dates;
    public long present_student_count;

    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * allergy_name : hlooooo I am here
     * free_text : hello
     * user_name : LinneaLarsson
     * image : /img/portraits/profile45.png
     */

    public List<FinalStudentsEntity> final_students;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getStudent_count_without_absent_dates() {
        return student_count_without_absent_dates;
    }

    public void setStudent_count_without_absent_dates(long student_count_without_absent_dates) {
        this.student_count_without_absent_dates = student_count_without_absent_dates;
    }

    public long getPresent_student_count() {
        return present_student_count;
    }

    public void setPresent_student_count(long present_student_count) {
        this.present_student_count = present_student_count;
    }

    public List<FinalStudentsEntity> getFinal_students() {
        return final_students;
    }

    public void setFinal_students(List<FinalStudentsEntity> final_students) {
        this.final_students = final_students;
    }

    public static class FinalStudentsEntity {
        private String allergy_name;
        private String free_text;
        private String user_name;
        private String image;
private String first_name;
        private String last_name;
        public String getAllergy_name() {
            return allergy_name;
        }

        public void setAllergy_name(String allergy_name) {
            this.allergy_name = allergy_name;
        }

        public String getFree_text() {
            return free_text;
        }

        public void setFree_text(String free_text) {
            this.free_text = free_text;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }
    }
}
