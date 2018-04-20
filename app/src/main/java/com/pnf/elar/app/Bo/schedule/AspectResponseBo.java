package com.pnf.elar.app.Bo.schedule;

import java.util.List;

/**
 * Created by VKrishnasamy on 24-01-2017.
 */

public class AspectResponseBo {
    /**
     * status : true
     * aspects : [{"Aspect":{"id":"291","customer_id":"1","course_id":"237","name":"Flipped classroom Manager","description":"Flipped classroom Manager","quiz_question_count":"0","created":"2016-11-23 09:11:13","modified":"2016-11-23 09:11:13"}}]
     */

    private String status;
    /**
     * Aspect : {"id":"291","customer_id":"1","course_id":"237","name":"Flipped classroom Manager","description":"Flipped classroom Manager","quiz_question_count":"0","created":"2016-11-23 09:11:13","modified":"2016-11-23 09:11:13"}
     */


    public String message;

    private List<AspectsEntity> aspects;

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

    public List<AspectsEntity> getAspects() {
        return aspects;
    }

    public void setAspects(List<AspectsEntity> aspects) {
        this.aspects = aspects;
    }

    public static class AspectsEntity {
        /**
         * id : 291
         * customer_id : 1
         * course_id : 237
         * name : Flipped classroom Manager
         * description : Flipped classroom Manager
         * quiz_question_count : 0
         * created : 2016-11-23 09:11:13
         * modified : 2016-11-23 09:11:13
         */

        private AspectEntity Aspect;

        public AspectEntity getAspect() {
            return Aspect;
        }

        public void setAspect(AspectEntity Aspect) {
            this.Aspect = Aspect;
        }

        public static class AspectEntity {
            private String id;
            private String course_id;
            private String name;
            private String description;
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

            public String getCourse_id() {
                return course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
