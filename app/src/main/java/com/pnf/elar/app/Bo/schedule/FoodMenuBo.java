package com.pnf.elar.app.Bo.schedule;

import java.util.List;

/**
 * Created by VKrishnasamy on 30-12-2016.
 */

public class FoodMenuBo {
    /**
     * status : true
     * foodmenu : [{"id":"16","start_date":"12/25/2016","end_date":"01/01/2017","file_name":"pic40619tn.pdf"},{"id":"17","start_date":"12/25/2016","end_date":"01/01/2017","file_name":"pic56005tn.pdf"},{"id":"18","start_date":"12/25/2016","end_date":"01/01/2017","file_name":"pic29438tn.pdf"},{"id":"19","start_date":"12/25/2016","end_date":"01/01/2017","file_name":"pic96006tn.pdf"}]
     */

    public String status;
    public String message;
    /**
     * id : 16
     * start_date : 12/25/2016
     * end_date : 01/01/2017
     * file_name : pic40619tn.pdf
     */

    public List<FoodmenuEntity> foodmenu;

    public List<FoodmenuEntity> files;

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

    public List<FoodmenuEntity> getFoodmenu() {
        return foodmenu;
    }

    public void setFoodmenu(List<FoodmenuEntity> foodmenu) {
        this.foodmenu = foodmenu;
    }

    public List<FoodmenuEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FoodmenuEntity> files) {
        this.files = files;
    }

    public static class FoodmenuEntity {
        private String id;
        private String start_date;
        private String end_date;
        private String file_name;
        private String path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
