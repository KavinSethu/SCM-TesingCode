package com.Util.Model.Model;

/**
 * Created by user on 02-04-2018.
 */

public class UserTerm {

    private String id,customer_id,created_by,term_title,term_description,teacher,student,parent,admin,website;

    private  String securityKey,loginUserID,platform;

    Boolean isChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getTerm_title() {
        return term_title;
    }

    public void setTerm_title(String term_title) {
        this.term_title = term_title;
    }

    public String getTerm_description() {
        return term_description;
    }

    public void setTerm_description(String term_description) {
        this.term_description = term_description;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getLoginUserID() {
        return loginUserID;
    }

    public void setLoginUserID(String loginUserID) {
        this.loginUserID = loginUserID;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
