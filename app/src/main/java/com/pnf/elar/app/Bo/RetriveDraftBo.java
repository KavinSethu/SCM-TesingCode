package com.pnf.elar.app.Bo;

import java.util.List;

/**
 * Created by VKrishnasamy on 04-10-2016.
 */

public class RetriveDraftBo {
    /**
     * status : true
     * drafts : [{"PictureDiary":{"id":"627","user_id":"231","user_cus_id":"1","category":"student","student_id":"1,104","class_id":"0","filename":"","imagename":"","filesize":"","description":"","filetype":"","status":"1","picturediary_knowledge_area_count":"0","picturediary_comment_count":"0","picture_diary_like_count":"0","saved_status":"0","notify_email":"0","created":"2016-10-04 09:14:18","modified":"2016-10-04 09:14:18","comment_date":"2016-10-04 09:14:18"},"PicturediaryTeacher":{"id":"231","USR_FirstName":"Katrina","USR_LastName":"Lindsj&ouml;"},"PicturediaryContent":[{"id":"519","picture_diary_id":"627","type":"other","imagename":"1475565262-3513246541.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"535371","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"},{"id":"520","picture_diary_id":"627","type":"other","imagename":"1475565263-8086745315.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"2456","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"},{"id":"521","picture_diary_id":"627","type":"other","imagename":"1475565263-6606068136.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"4118","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"}],"PicturediaryGroup":[{"ClaClass":{"id":"40","CLA_CUS_Rid":"1","group_type":"class","parent_id":"0","course_id":"0","name":"Class A","start":"1430949600","end":"1497564000","news_count":"0","status":"1","picturediary_group_count":"27","created":"2015-05-08 10:12:06","modified":"2015-11-10 15:59:53"}}],"PicturediaryStudent":[{"id":"559","picture_diary_id":"627","student_id":"1","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23","Student":{"id":"1","USR_FirstName":"Linnea","USR_LastName":"Larsson"}},{"id":"560","picture_diary_id":"627","student_id":"104","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23","Student":{"id":"104","USR_FirstName":"Sahil","USR_LastName":"Kapoor"}}],"PicturediaryKnowledgeArea":[{"KnowledgeArea":{"id":"66"}},{"KnowledgeArea":{"id":"67"}},{"KnowledgeArea":{"id":"68"}}],"images":[{"id":"519","picture_diary_id":"627","type":"other","imagename":"1475565262-3513246541.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"535371","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"},{"id":"520","picture_diary_id":"627","type":"other","imagename":"1475565263-8086745315.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"2456","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"},{"id":"521","picture_diary_id":"627","type":"other","imagename":"1475565263-6606068136.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"4118","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"}],"videos":[],"random_files":[]}]
     */

    private String status;
    /**
     * PictureDiary : {"id":"627","user_id":"231","user_cus_id":"1","category":"student","student_id":"1,104","class_id":"0","filename":"","imagename":"","filesize":"","description":"","filetype":"","status":"1","picturediary_knowledge_area_count":"0","picturediary_comment_count":"0","picture_diary_like_count":"0","saved_status":"0","notify_email":"0","created":"2016-10-04 09:14:18","modified":"2016-10-04 09:14:18","comment_date":"2016-10-04 09:14:18"}
     * PicturediaryTeacher : {"id":"231","USR_FirstName":"Katrina","USR_LastName":"Lindsj&ouml;"}
     * PicturediaryContent : [{"id":"519","picture_diary_id":"627","type":"other","imagename":"1475565262-3513246541.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"535371","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"},{"id":"520","picture_diary_id":"627","type":"other","imagename":"1475565263-8086745315.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"2456","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"},{"id":"521","picture_diary_id":"627","type":"other","imagename":"1475565263-6606068136.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"4118","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"}]
     * PicturediaryGroup : [{"ClaClass":{"id":"40","CLA_CUS_Rid":"1","group_type":"class","parent_id":"0","course_id":"0","name":"Class A","start":"1430949600","end":"1497564000","news_count":"0","status":"1","picturediary_group_count":"27","created":"2015-05-08 10:12:06","modified":"2015-11-10 15:59:53"}}]
     * PicturediaryStudent : [{"id":"559","picture_diary_id":"627","student_id":"1","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23","Student":{"id":"1","USR_FirstName":"Linnea","USR_LastName":"Larsson"}},{"id":"560","picture_diary_id":"627","student_id":"104","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23","Student":{"id":"104","USR_FirstName":"Sahil","USR_LastName":"Kapoor"}}]
     * PicturediaryKnowledgeArea : [{"KnowledgeArea":{"id":"66"}},{"KnowledgeArea":{"id":"67"}},{"KnowledgeArea":{"id":"68"}}]
     * images : [{"id":"519","picture_diary_id":"627","type":"other","imagename":"1475565262-3513246541.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"535371","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"},{"id":"520","picture_diary_id":"627","type":"other","imagename":"1475565263-8086745315.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"2456","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"},{"id":"521","picture_diary_id":"627","type":"other","imagename":"1475565263-6606068136.jpg","videoname":"","videoname_mp4":"","videoname_ogg":"","videoname_webm":"","random_file_name":"","filesize":"4118","created":"2016-10-04 09:14:23","modified":"2016-10-04 09:14:23"}]
     * videos : []
     * random_files : []
     */

    private List<DraftsEntity> drafts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DraftsEntity> getDrafts() {
        return drafts;
    }

    public void setDrafts(List<DraftsEntity> drafts) {
        this.drafts = drafts;
    }

    public static class DraftsEntity {
        /**
         * id : 627
         * user_id : 231
         * user_cus_id : 1
         * category : student
         * student_id : 1,104
         * class_id : 0
         * filename :
         * imagename :
         * filesize :
         * description :
         * filetype :
         * status : 1
         * picturediary_knowledge_area_count : 0
         * picturediary_comment_count : 0
         * picture_diary_like_count : 0
         * saved_status : 0
         * notify_email : 0
         * created : 2016-10-04 09:14:18
         * modified : 2016-10-04 09:14:18
         * comment_date : 2016-10-04 09:14:18
         */

        private PictureDiaryEntity PictureDiary;
        /**
         * id : 231
         * USR_FirstName : Katrina
         * USR_LastName : Lindsj&ouml;
         */

        private PicturediaryTeacherEntity PicturediaryTeacher;
        /**
         * id : 519
         * picture_diary_id : 627
         * type : other
         * imagename : 1475565262-3513246541.jpg
         * videoname :
         * videoname_mp4 :
         * videoname_ogg :
         * videoname_webm :
         * random_file_name :
         * filesize : 535371
         * created : 2016-10-04 09:14:23
         * modified : 2016-10-04 09:14:23
         */

        private List<PicturediaryContentEntity> PicturediaryContent;
        /**
         * ClaClass : {"id":"40","CLA_CUS_Rid":"1","group_type":"class","parent_id":"0","course_id":"0","name":"Class A","start":"1430949600","end":"1497564000","news_count":"0","status":"1","picturediary_group_count":"27","created":"2015-05-08 10:12:06","modified":"2015-11-10 15:59:53"}
         */

        private List<PicturediaryGroupEntity> PicturediaryGroup;
        /**
         * id : 559
         * picture_diary_id : 627
         * student_id : 1
         * created : 2016-10-04 09:14:23
         * modified : 2016-10-04 09:14:23
         * Student : {"id":"1","USR_FirstName":"Linnea","USR_LastName":"Larsson"}
         */

        private List<PicturediaryStudentEntity> PicturediaryStudent;
        /**
         * KnowledgeArea : {"id":"66"}
         */

        private List<PicturediaryKnowledgeAreaEntity> PicturediaryKnowledgeArea;
        /**
         * id : 519
         * picture_diary_id : 627
         * type : other
         * imagename : 1475565262-3513246541.jpg
         * videoname :
         * videoname_mp4 :
         * videoname_ogg :
         * videoname_webm :
         * random_file_name :
         * filesize : 535371
         * created : 2016-10-04 09:14:23
         * modified : 2016-10-04 09:14:23
         */

        private List<ImagesEntity> images;
        private List<VideosEntity> videos;
        private List<?> random_files;

        public PictureDiaryEntity getPictureDiary() {
            return PictureDiary;
        }

        public void setPictureDiary(PictureDiaryEntity PictureDiary) {
            this.PictureDiary = PictureDiary;
        }

        public PicturediaryTeacherEntity getPicturediaryTeacher() {
            return PicturediaryTeacher;
        }

        public void setPicturediaryTeacher(PicturediaryTeacherEntity PicturediaryTeacher) {
            this.PicturediaryTeacher = PicturediaryTeacher;
        }

        public List<PicturediaryContentEntity> getPicturediaryContent() {
            return PicturediaryContent;
        }

        public void setPicturediaryContent(List<PicturediaryContentEntity> PicturediaryContent) {
            this.PicturediaryContent = PicturediaryContent;
        }

        public List<PicturediaryGroupEntity> getPicturediaryGroup() {
            return PicturediaryGroup;
        }

        public void setPicturediaryGroup(List<PicturediaryGroupEntity> PicturediaryGroup) {
            this.PicturediaryGroup = PicturediaryGroup;
        }

        public List<PicturediaryStudentEntity> getPicturediaryStudent() {
            return PicturediaryStudent;
        }

        public void setPicturediaryStudent(List<PicturediaryStudentEntity> PicturediaryStudent) {
            this.PicturediaryStudent = PicturediaryStudent;
        }

        public List<PicturediaryKnowledgeAreaEntity> getPicturediaryKnowledgeArea() {
            return PicturediaryKnowledgeArea;
        }

        public void setPicturediaryKnowledgeArea(List<PicturediaryKnowledgeAreaEntity> PicturediaryKnowledgeArea) {
            this.PicturediaryKnowledgeArea = PicturediaryKnowledgeArea;
        }

        public List<ImagesEntity> getImages() {
            return images;
        }

        public void setImages(List<ImagesEntity> images) {
            this.images = images;
        }

        public List<VideosEntity> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosEntity> videos) {
            this.videos = videos;
        }

        public List<?> getRandom_files() {
            return random_files;
        }

        public void setRandom_files(List<?> random_files) {
            this.random_files = random_files;
        }

        public static class PictureDiaryEntity {
            private String id;
            private String user_id;
            private String user_cus_id;
            private String category;
            private String student_id;
            private String class_id;
            private String filename;
            private String imagename;
            private String filesize;
            private String description;
            private String filetype;
            private String status;
            private String picturediary_knowledge_area_count;
            private String picturediary_comment_count;
            private String picture_diary_like_count;
            private String saved_status;
            private String notify_email;
            private String created;
            private String modified;
            private String comment_date;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_cus_id() {
                return user_cus_id;
            }

            public void setUser_cus_id(String user_cus_id) {
                this.user_cus_id = user_cus_id;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getStudent_id() {
                return student_id;
            }

            public void setStudent_id(String student_id) {
                this.student_id = student_id;
            }

            public String getClass_id() {
                return class_id;
            }

            public void setClass_id(String class_id) {
                this.class_id = class_id;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getImagename() {
                return imagename;
            }

            public void setImagename(String imagename) {
                this.imagename = imagename;
            }

            public String getFilesize() {
                return filesize;
            }

            public void setFilesize(String filesize) {
                this.filesize = filesize;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getFiletype() {
                return filetype;
            }

            public void setFiletype(String filetype) {
                this.filetype = filetype;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPicturediary_knowledge_area_count() {
                return picturediary_knowledge_area_count;
            }

            public void setPicturediary_knowledge_area_count(String picturediary_knowledge_area_count) {
                this.picturediary_knowledge_area_count = picturediary_knowledge_area_count;
            }

            public String getPicturediary_comment_count() {
                return picturediary_comment_count;
            }

            public void setPicturediary_comment_count(String picturediary_comment_count) {
                this.picturediary_comment_count = picturediary_comment_count;
            }

            public String getPicture_diary_like_count() {
                return picture_diary_like_count;
            }

            public void setPicture_diary_like_count(String picture_diary_like_count) {
                this.picture_diary_like_count = picture_diary_like_count;
            }

            public String getSaved_status() {
                return saved_status;
            }

            public void setSaved_status(String saved_status) {
                this.saved_status = saved_status;
            }

            public String getNotify_email() {
                return notify_email;
            }

            public void setNotify_email(String notify_email) {
                this.notify_email = notify_email;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }

            public String getComment_date() {
                return comment_date;
            }

            public void setComment_date(String comment_date) {
                this.comment_date = comment_date;
            }
        }

        public static class PicturediaryTeacherEntity {
            private String id;
            private String USR_FirstName;
            private String USR_LastName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
        }

        public static class PicturediaryContentEntity {
            private String id;
            private String picture_diary_id;
            private String type;
            private String imagename;
            private String videoname;
            private String videoname_mp4;
            private String videoname_ogg;
            private String videoname_webm;
            private String random_file_name;
            private String filesize;
            private String created;
            private String modified;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPicture_diary_id() {
                return picture_diary_id;
            }

            public void setPicture_diary_id(String picture_diary_id) {
                this.picture_diary_id = picture_diary_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImagename() {
                return imagename;
            }

            public void setImagename(String imagename) {
                this.imagename = imagename;
            }

            public String getVideoname() {
                return videoname;
            }

            public void setVideoname(String videoname) {
                this.videoname = videoname;
            }

            public String getVideoname_mp4() {
                return videoname_mp4;
            }

            public void setVideoname_mp4(String videoname_mp4) {
                this.videoname_mp4 = videoname_mp4;
            }

            public String getVideoname_ogg() {
                return videoname_ogg;
            }

            public void setVideoname_ogg(String videoname_ogg) {
                this.videoname_ogg = videoname_ogg;
            }

            public String getVideoname_webm() {
                return videoname_webm;
            }

            public void setVideoname_webm(String videoname_webm) {
                this.videoname_webm = videoname_webm;
            }

            public String getRandom_file_name() {
                return random_file_name;
            }

            public void setRandom_file_name(String random_file_name) {
                this.random_file_name = random_file_name;
            }

            public String getFilesize() {
                return filesize;
            }

            public void setFilesize(String filesize) {
                this.filesize = filesize;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }
        }

        public static class PicturediaryGroupEntity {
            /**
             * id : 40
             * CLA_CUS_Rid : 1
             * group_type : class
             * parent_id : 0
             * course_id : 0
             * name : Class A
             * start : 1430949600
             * end : 1497564000
             * news_count : 0
             * status : 1
             * picturediary_group_count : 27
             * created : 2015-05-08 10:12:06
             * modified : 2015-11-10 15:59:53
             */

            private ClaClassEntity ClaClass;

            public ClaClassEntity getClaClass() {
                return ClaClass;
            }

            public void setClaClass(ClaClassEntity ClaClass) {
                this.ClaClass = ClaClass;
            }

            public static class ClaClassEntity {
                private String id;
                private String CLA_CUS_Rid;
                private String group_type;
                private String parent_id;
                private String course_id;
                private String name;
                private String start;
                private String end;
                private String news_count;
                private String status;
                private String picturediary_group_count;
                private String created;
                private String modified;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCLA_CUS_Rid() {
                    return CLA_CUS_Rid;
                }

                public void setCLA_CUS_Rid(String CLA_CUS_Rid) {
                    this.CLA_CUS_Rid = CLA_CUS_Rid;
                }

                public String getGroup_type() {
                    return group_type;
                }

                public void setGroup_type(String group_type) {
                    this.group_type = group_type;
                }

                public String getParent_id() {
                    return parent_id;
                }

                public void setParent_id(String parent_id) {
                    this.parent_id = parent_id;
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

                public String getNews_count() {
                    return news_count;
                }

                public void setNews_count(String news_count) {
                    this.news_count = news_count;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getPicturediary_group_count() {
                    return picturediary_group_count;
                }

                public void setPicturediary_group_count(String picturediary_group_count) {
                    this.picturediary_group_count = picturediary_group_count;
                }

                public String getCreated() {
                    return created;
                }

                public void setCreated(String created) {
                    this.created = created;
                }

                public String getModified() {
                    return modified;
                }

                public void setModified(String modified) {
                    this.modified = modified;
                }
            }
        }

        public static class PicturediaryStudentEntity {
            private String id;
            private String picture_diary_id;
            private String student_id;
            private String created;
            private String modified;
            /**
             * id : 1
             * USR_FirstName : Linnea
             * USR_LastName : Larsson
             */

            private StudentEntity Student;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPicture_diary_id() {
                return picture_diary_id;
            }

            public void setPicture_diary_id(String picture_diary_id) {
                this.picture_diary_id = picture_diary_id;
            }

            public String getStudent_id() {
                return student_id;
            }

            public void setStudent_id(String student_id) {
                this.student_id = student_id;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }

            public StudentEntity getStudent() {
                return Student;
            }

            public void setStudent(StudentEntity Student) {
                this.Student = Student;
            }

            public static class StudentEntity {
                private String id;
                private String USR_FirstName;
                private String USR_LastName;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
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
            }
        }

        public static class PicturediaryKnowledgeAreaEntity {
            /**
             * id : 66
             */

            private KnowledgeAreaEntity KnowledgeArea;

            public KnowledgeAreaEntity getKnowledgeArea() {
                return KnowledgeArea;
            }

            public void setKnowledgeArea(KnowledgeAreaEntity KnowledgeArea) {
                this.KnowledgeArea = KnowledgeArea;
            }

            public static class KnowledgeAreaEntity {
                private String id;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }

        public static class ImagesEntity {
            private String id;
            private String picture_diary_id;
            private String type;
            private String imagename;
            private String videoname;
            private String videoname_mp4;
            private String videoname_ogg;
            private String videoname_webm;
            private String random_file_name;
            private String filesize;
            private String created;
            private String modified;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPicture_diary_id() {
                return picture_diary_id;
            }

            public void setPicture_diary_id(String picture_diary_id) {
                this.picture_diary_id = picture_diary_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImagename() {
                return imagename;
            }

            public void setImagename(String imagename) {
                this.imagename = imagename;
            }

            public String getVideoname() {
                return videoname;
            }

            public void setVideoname(String videoname) {
                this.videoname = videoname;
            }

            public String getVideoname_mp4() {
                return videoname_mp4;
            }

            public void setVideoname_mp4(String videoname_mp4) {
                this.videoname_mp4 = videoname_mp4;
            }

            public String getVideoname_ogg() {
                return videoname_ogg;
            }

            public void setVideoname_ogg(String videoname_ogg) {
                this.videoname_ogg = videoname_ogg;
            }

            public String getVideoname_webm() {
                return videoname_webm;
            }

            public void setVideoname_webm(String videoname_webm) {
                this.videoname_webm = videoname_webm;
            }

            public String getRandom_file_name() {
                return random_file_name;
            }

            public void setRandom_file_name(String random_file_name) {
                this.random_file_name = random_file_name;
            }

            public String getFilesize() {
                return filesize;
            }

            public void setFilesize(String filesize) {
                this.filesize = filesize;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }
        }


        public static class VideosEntity
        {

            /**
             * videoname_webm : files/picturediary/flv/files/picturediary/flv/files/picturediary/images/14762627492795653841.jpg
             * videoname : files/picturediary/flv/14762627492795653841.flv
             * id : 543
             * filesize : 183015
             * created : 2016-10-12 10:59:09
             * picture_diary_id : 643
             * random_file_name :
             * videoname_ogg : 14762627492795653841.ogv
             * videoname_mp4 : files/picturediary/flv/14762627492795653841.mp4
             * type : video
             * modified : 2016-10-12 10:59:09
             * imagename : files/picturediary/images/14762627492795653841.jpg
             */

            private String videoname_webm;
            private String videoname;
            private String id;
            private String filesize;
            private String created;
            private String picture_diary_id;
            private String random_file_name;
            private String videoname_ogg;
            private String videoname_mp4;
            private String type;
            private String modified;
            private String imagename;

            public String getVideoname_webm() {
                return videoname_webm;
            }

            public void setVideoname_webm(String videoname_webm) {
                this.videoname_webm = videoname_webm;
            }

            public String getVideoname() {
                return videoname;
            }

            public void setVideoname(String videoname) {
                this.videoname = videoname;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFilesize() {
                return filesize;
            }

            public void setFilesize(String filesize) {
                this.filesize = filesize;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getPicture_diary_id() {
                return picture_diary_id;
            }

            public void setPicture_diary_id(String picture_diary_id) {
                this.picture_diary_id = picture_diary_id;
            }

            public String getRandom_file_name() {
                return random_file_name;
            }

            public void setRandom_file_name(String random_file_name) {
                this.random_file_name = random_file_name;
            }

            public String getVideoname_ogg() {
                return videoname_ogg;
            }

            public void setVideoname_ogg(String videoname_ogg) {
                this.videoname_ogg = videoname_ogg;
            }

            public String getVideoname_mp4() {
                return videoname_mp4;
            }

            public void setVideoname_mp4(String videoname_mp4) {
                this.videoname_mp4 = videoname_mp4;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }

            public String getImagename() {
                return imagename;
            }

            public void setImagename(String imagename) {
                this.imagename = imagename;
            }
        }
    }
}
