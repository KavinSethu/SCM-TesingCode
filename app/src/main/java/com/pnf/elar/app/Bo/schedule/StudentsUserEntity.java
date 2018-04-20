package com.pnf.elar.app.Bo.schedule;

/**
 * Created by VKrishnasamy on 27-01-2017.
 */

public class StudentsUserEntity {

    /*public User user;*/


/*    public class User
    {*/
        private String id;
        /*private String USR_CUS_Rid;*/
      /*  private String group_id;*/
        private String username;
      /*  private String USR_FirstName;
        private String USR_LastName;
        private String USR_Birthday;*/
        private String USR_Email;
      /*  private String USR_StreetAddress;
        private String start;
        private String end;*/

        private String name;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

       /* public String getUSR_CUS_Rid() {
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
*/
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

       /* public String getUSR_FirstName() {
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
        }*/

        public String getUSR_Email() {
            return USR_Email;
        }

        public void setUSR_Email(String USR_Email) {
            this.USR_Email = USR_Email;
        }

      /*  public String getUSR_StreetAddress() {
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
        }*/

    /*}*/
}
