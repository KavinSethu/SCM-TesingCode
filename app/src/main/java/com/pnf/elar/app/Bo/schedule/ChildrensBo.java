package com.pnf.elar.app.Bo.schedule;

import java.util.List;

/**
 * Created by Administrator on 2/28/2017.
 */

public class ChildrensBo {
    /**
     * status : true
     * children : [{"id":"1","USR_CUS_Rid":"1","group_id":"3","username":"sarabjit","USR_FirstName":"Linnea","USR_LastName":"Larsson","USR_Birthday":"1990-12-30 00:00:00","USR_Email":"raj.sriselvan@kaaylabs.com","USR_StreetAddress":"vill. Sarana","start":"2013-12-01 00:00:00","end":"2017-11-30 00:00:00","USR_status":"1","authentication_token":"c6c904b1985ef90ff76d859c26435b7ecaa8c9e1","USR_image":"/img/portraits/profile83.png"},{"id":"104","USR_CUS_Rid":"1","group_id":"3","username":"Magnus","USR_FirstName":"Sahil","USR_LastName":"Kapoor","USR_Birthday":"2033-11-11 00:00:00","USR_Email":"raj.sriselvan@kaaylabs.com","USR_StreetAddress":"Adamv&Atilde;&curren;gen 1","start":"2014-09-01 00:00:00","end":"2017-11-30 00:00:00","USR_status":"1","authentication_token":"dfd7194ac5b062366cce2f5b01f9b7f58e1f7824","USR_image":"/img/User/104/pic2325download.jpg"}]
     */

    private String status;
    private List<ChildrenBean> children;
public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 1
         * USR_CUS_Rid : 1
         * group_id : 3
         * username : sarabjit
         * USR_FirstName : Linnea
         * USR_LastName : Larsson
         * USR_Birthday : 1990-12-30 00:00:00
         * USR_Email : raj.sriselvan@kaaylabs.com
         * USR_StreetAddress : vill. Sarana
         * start : 2013-12-01 00:00:00
         * end : 2017-11-30 00:00:00
         * USR_status : 1
         * authentication_token : c6c904b1985ef90ff76d859c26435b7ecaa8c9e1
         * USR_image : /img/portraits/profile83.png
         */

        private String id;
        private String USR_CUS_Rid;
        private String group_id;
        private String username;
        private String USR_FirstName;
        private String USR_LastName;
        private String USR_Birthday;
        private String USR_Email;
        private String USR_StreetAddress;
        private String start;
        private String end;
        private String USR_status;
        private String authentication_token;
        private String USR_image;

        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUSR_CUS_Rid() {
            return USR_CUS_Rid;
        }

        public void setUSR_CUS_Rid(String USR_CUS_Rid) {
            this.USR_CUS_Rid = USR_CUS_Rid;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUSR_FirstName() {
            return USR_FirstName;
        }

        public void setUSR_FirstName(String USR_FirstName) {
            this.USR_FirstName = USR_FirstName;
        }

        public String getUSR_LastName() {
            return USR_LastName;
        }

        public void setUSR_LastName(String USR_LastName) {
            this.USR_LastName = USR_LastName;
        }

        public String getUSR_Birthday() {
            return USR_Birthday;
        }

        public void setUSR_Birthday(String USR_Birthday) {
            this.USR_Birthday = USR_Birthday;
        }

        public String getUSR_Email() {
            return USR_Email;
        }

        public void setUSR_Email(String USR_Email) {
            this.USR_Email = USR_Email;
        }

        public String getUSR_StreetAddress() {
            return USR_StreetAddress;
        }

        public void setUSR_StreetAddress(String USR_StreetAddress) {
            this.USR_StreetAddress = USR_StreetAddress;
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

        public String getUSR_status() {
            return USR_status;
        }

        public void setUSR_status(String USR_status) {
            this.USR_status = USR_status;
        }

        public String getAuthentication_token() {
            return authentication_token;
        }

        public void setAuthentication_token(String authentication_token) {
            this.authentication_token = authentication_token;
        }

        public String getUSR_image() {
            return USR_image;
        }

        public void setUSR_image(String USR_image) {
            this.USR_image = USR_image;
        }
    }
}
