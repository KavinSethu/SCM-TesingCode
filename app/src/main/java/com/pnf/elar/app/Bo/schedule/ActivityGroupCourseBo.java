package com.pnf.elar.app.Bo.schedule;

import java.util.List;

/**
 * Created by VKrishnasamy on 19-01-2017.
 */

public class ActivityGroupCourseBo {

    private String status;
    private String message;
    private List<UserCoursesBo> usercourses;

    private List<CourseBean> groups;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




    public List<UserCoursesBo> getUsercourses() {
        return usercourses;
    }

    public void setUsercourses(List<UserCoursesBo> usercourses) {
        this.usercourses = usercourses;
    }

    public List<CourseBean> getGroups() {
        return groups;
    }

    public void setGroups(List<CourseBean> groups) {
        this.groups = groups;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
