package com.pnf.elar.app.Bo.schedule;

import java.util.List;

/**
 * Created by VKrishnasamy on 21-11-2016.
 */

public class ScheduleMonthList {

    /**
     * status : false
     * schemaCalendar : [{"day":"01","month":"11","year":"2016","Schema":{"id":"844","class_id":"0","user_id":"44","customer_id":"1","cou_course_id":"188","parent_schema_id":"0","tutoring_period_id":"0","building_id":null,"room_id":null,"examination_id":"0","aspect_ids":"","assigned_teacher_ids":"","deadline":"0","schedule_type":"lesson","type":"individually","title":"test","description":"jjjjjjjjjjjjjjj","start":"2016-11-01 14:00:00","end":"2016-11-01 15:00:00","tutoring_student_or_studupartner":"","with_parent":"no","tutoring_time":"0","book_tutoring_student_id":"0","added_by_teacher":"1","created":"2016-11-10 10:01:31","modified":"2016-11-10 10:01:31","Student":null,"User":{"id":"44","USR_CUS_Rid":"1","group_id":"2","username":"AllVar050","USR_FirstName":"Eva","USR_LastName":"Perthus","USR_Birthday":"1990-01-01 00:00:00","USR_Email":"rajivgakharpnf@gmail.com","USR_StreetAddress":"10 Market Road","start":"2013-11-01 00:00:00","end":"2017-11-30 00:00:00"},"GroupInfo":{"name":null},"CourseInfo":{"id":"188","COU_Name":"New course"},"SchemaGroup":[{"id":"80","teacher_id":"44","group_id":"40","schema_id":"844","created":"2016-11-10 10:01:31","modified":"2016-11-10 10:01:31","status":"1"}]}},{"day":"08","month":"11","year":"2016","Schema":{"id":"843","class_id":"0","user_id":"44","customer_id":"1","cou_course_id":"155","parent_schema_id":"0","tutoring_period_id":"0","building_id":null,"room_id":null,"examination_id":"0","aspect_ids":"","assigned_teacher_ids":"","deadline":"0","schedule_type":"activity","type":"individually","title":"dsfdsfds","description":"jeeee","start":"2016-11-08 14:00:00","end":"2016-11-08 15:00:00","tutoring_student_or_studupartner":"","with_parent":"no","tutoring_time":"0","book_tutoring_student_id":"0","added_by_teacher":"1","created":"2016-11-10 09:40:34","modified":"2016-11-10 09:40:34","Student":null,"User":{"id":"44","USR_CUS_Rid":"1","group_id":"2","username":"AllVar050","USR_FirstName":"Eva","USR_LastName":"Perthus","USR_Birthday":"1990-01-01 00:00:00","USR_Email":"rajivgakharpnf@gmail.com","USR_StreetAddress":"10 Market Road","start":"2013-11-01 00:00:00","end":"2017-11-30 00:00:00"},"GroupInfo":{"name":null},"CourseInfo":{"id":"155","COU_Name":"General Knowledge"},"SchemaGroup":[{"id":"79","teacher_id":"44","group_id":"40","schema_id":"843","created":"2016-11-10 09:40:34","modified":"2016-11-10 09:40:34","status":"1"}]}},{"day":"10","month":"11","year":"2016","Schema":{"id":"840","class_id":"0","user_id":"44","customer_id":"1","cou_course_id":"155","parent_schema_id":"0","tutoring_period_id":"0","building_id":null,"room_id":null,"examination_id":"0","aspect_ids":"","assigned_teacher_ids":"","deadline":"0","schedule_type":"lesson","type":"individually","title":"test","description":"test","start":"2016-11-10 12:00:00","end":"2016-11-10 13:00:00","tutoring_student_or_studupartner":"","with_parent":"no","tutoring_time":"0","book_tutoring_student_id":"0","added_by_teacher":"1","created":"2016-11-10 08:25:47","modified":"2016-11-10 08:25:47","Student":null,"User":{"id":"44","USR_CUS_Rid":"1","group_id":"2","username":"AllVar050","USR_FirstName":"Eva","USR_LastName":"Perthus","USR_Birthday":"1990-01-01 00:00:00","USR_Email":"rajivgakharpnf@gmail.com","USR_StreetAddress":"10 Market Road","start":"2013-11-01 00:00:00","end":"2017-11-30 00:00:00"},"GroupInfo":{"name":null},"CourseInfo":{"id":"155","COU_Name":"General Knowledge"},"SchemaGroup":[{"id":"76","teacher_id":"44","group_id":"40","schema_id":"840","created":"2016-11-10 08:25:48","modified":"2016-11-10 08:25:48","status":"1"}]}}]
     */

    private String status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * day : 01
     * month : 11
     * year : 2016
     * Schema : {"id":"844","class_id":"0","user_id":"44","customer_id":"1","cou_course_id":"188","parent_schema_id":"0","tutoring_period_id":"0","building_id":null,"room_id":null,"examination_id":"0","aspect_ids":"","assigned_teacher_ids":"","deadline":"0","schedule_type":"lesson","type":"individually","title":"test","description":"jjjjjjjjjjjjjjj","start":"2016-11-01 14:00:00","end":"2016-11-01 15:00:00","tutoring_student_or_studupartner":"","with_parent":"no","tutoring_time":"0","book_tutoring_student_id":"0","added_by_teacher":"1","created":"2016-11-10 10:01:31","modified":"2016-11-10 10:01:31","Student":null,"User":{"id":"44","USR_CUS_Rid":"1","group_id":"2","username":"AllVar050","USR_FirstName":"Eva","USR_LastName":"Perthus","USR_Birthday":"1990-01-01 00:00:00","USR_Email":"rajivgakharpnf@gmail.com","USR_StreetAddress":"10 Market Road","start":"2013-11-01 00:00:00","end":"2017-11-30 00:00:00"},"GroupInfo":{"name":null},"CourseInfo":{"id":"188","COU_Name":"New course"},"SchemaGroup":[{"id":"80","teacher_id":"44","group_id":"40","schema_id":"844","created":"2016-11-10 10:01:31","modified":"2016-11-10 10:01:31","status":"1"}]}
     */
private int absent_note_count;
    private int retriever_note_count;

    private String first_name;
    private String last_name;



    public int getAbsent_note_count() {
        return absent_note_count;
    }

    public void setAbsent_note_count(int absent_note_count) {
        this.absent_note_count = absent_note_count;
    }

    public int getRetriever_note_count() {
        return retriever_note_count;
    }

    public void setRetriever_note_count(int retriever_note_count) {
        this.retriever_note_count = retriever_note_count;
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

    private List<SchemaCalendarEntity> schemaCalendar;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SchemaCalendarEntity> getSchemaCalendar() {
        return schemaCalendar;
    }

    public void setSchemaCalendar(List<SchemaCalendarEntity> schemaCalendar) {
        this.schemaCalendar = schemaCalendar;
    }

    public static class SchemaCalendarEntity {
        private String day;
        private String month;
        private String year;
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * id : 844
         * class_id : 0
         * user_id : 44
         * customer_id : 1
         * cou_course_id : 188
         * parent_schema_id : 0
         * tutoring_period_id : 0
         * building_id : null
         * room_id : null
         * examination_id : 0
         * aspect_ids :
         * assigned_teacher_ids :
         * deadline : 0
         * schedule_type : lesson
         * type : individually
         * title : test
         * description : jjjjjjjjjjjjjjj
         * start : 2016-11-01 14:00:00
         * end : 2016-11-01 15:00:00
         * tutoring_student_or_studupartner :
         * with_parent : no
         * tutoring_time : 0
         * book_tutoring_student_id : 0
         * added_by_teacher : 1
         * created : 2016-11-10 10:01:31
         * modified : 2016-11-10 10:01:31
         * Student : null
         * User : {"id":"44","USR_CUS_Rid":"1","group_id":"2","username":"AllVar050","USR_FirstName":"Eva","USR_LastName":"Perthus","USR_Birthday":"1990-01-01 00:00:00","USR_Email":"rajivgakharpnf@gmail.com","USR_StreetAddress":"10 Market Road","start":"2013-11-01 00:00:00","end":"2017-11-30 00:00:00"}
         * GroupInfo : {"name":null}
         * CourseInfo : {"id":"188","COU_Name":"New course"}
         * SchemaGroup : [{"id":"80","teacher_id":"44","group_id":"40","schema_id":"844","created":"2016-11-10 10:01:31","modified":"2016-11-10 10:01:31","status":"1"}]
         */

        private SchemaEntity Schema;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public SchemaEntity getSchema() {
            return Schema;
        }

        public void setSchema(SchemaEntity Schema) {
            this.Schema = Schema;
        }

        public static class SchemaEntity {
            private String id;
            /*private String class_id;*/
            private String user_id;
        /*    private String customer_id;
            private String cou_course_id;
            private String parent_schema_id;
            private String tutoring_period_id;
            private Object building_id;
            private Object room_id;
            private String examination_id;
            private String aspect_ids;
            private String assigned_teacher_ids;
            private String deadline;
            private String schedule_type;*/
            private String type;
            private String title;
            private String description;
            private String start;
            private String end;
        /*    private String tutoring_student_or_studupartner;
            private String with_parent;
            private String tutoring_time;
            private String book_tutoring_student_id;
            private String added_by_teacher;*/
            private String created;
            private String modified;
            private Object Student;
            private String lmscolors;

            public String getLmscolors() {
                return lmscolors;
            }

            public void setLmscolors(String lmscolors) {
                this.lmscolors = lmscolors;
            }

            /**
             * id : 44
             * USR_CUS_Rid : 1
             * group_id : 2
             * username : AllVar050
             * USR_FirstName : Eva
             * USR_LastName : Perthus
             * USR_Birthday : 1990-01-01 00:00:00
             * USR_Email : rajivgakharpnf@gmail.com
             * USR_StreetAddress : 10 Market Road
             * start : 2013-11-01 00:00:00
             * end : 2017-11-30 00:00:00
             */

            private UserEntity User;
            /**
             * name : null
             */

            /*private GroupInfoEntity GroupInfo;*/
            /**
             * id : 188
             * COU_Name : New course
             */

            /*private CourseInfoEntity CourseInfo;*/
            /**
             * id : 80
             * teacher_id : 44
             * group_id : 40
             * schema_id : 844
             * created : 2016-11-10 10:01:31
             * modified : 2016-11-10 10:01:31
             * status : 1
             */

            /*private List<SchemaGroupEntity> SchemaGroup;*/

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

         /*   public String getClass_id() {
                return class_id;
            }

            public void setClass_id(String class_id) {
                this.class_id = class_id;
            }*/

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

           /* public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getCou_course_id() {
                return cou_course_id;
            }

            public void setCou_course_id(String cou_course_id) {
                this.cou_course_id = cou_course_id;
            }

            public String getParent_schema_id() {
                return parent_schema_id;
            }

            public void setParent_schema_id(String parent_schema_id) {
                this.parent_schema_id = parent_schema_id;
            }

            public String getTutoring_period_id() {
                return tutoring_period_id;
            }

            public void setTutoring_period_id(String tutoring_period_id) {
                this.tutoring_period_id = tutoring_period_id;
            }

            public Object getBuilding_id() {
                return building_id;
            }

            public void setBuilding_id(Object building_id) {
                this.building_id = building_id;
            }

            public Object getRoom_id() {
                return room_id;
            }

            public void setRoom_id(Object room_id) {
                this.room_id = room_id;
            }

            public String getExamination_id() {
                return examination_id;
            }

            public void setExamination_id(String examination_id) {
                this.examination_id = examination_id;
            }

            public String getAspect_ids() {
                return aspect_ids;
            }

            public void setAspect_ids(String aspect_ids) {
                this.aspect_ids = aspect_ids;
            }

            public String getAssigned_teacher_ids() {
                return assigned_teacher_ids;
            }

            public void setAssigned_teacher_ids(String assigned_teacher_ids) {
                this.assigned_teacher_ids = assigned_teacher_ids;
            }

            public String getDeadline() {
                return deadline;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }

            public String getSchedule_type() {
                return schedule_type;
            }

            public void setSchedule_type(String schedule_type) {
                this.schedule_type = schedule_type;
            }
*/
            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

        /*    public String getTutoring_student_or_studupartner() {
                return tutoring_student_or_studupartner;
            }

            public void setTutoring_student_or_studupartner(String tutoring_student_or_studupartner) {
                this.tutoring_student_or_studupartner = tutoring_student_or_studupartner;
            }

            public String getWith_parent() {
                return with_parent;
            }

            public void setWith_parent(String with_parent) {
                this.with_parent = with_parent;
            }

            public String getTutoring_time() {
                return tutoring_time;
            }

            public void setTutoring_time(String tutoring_time) {
                this.tutoring_time = tutoring_time;
            }

            public String getBook_tutoring_student_id() {
                return book_tutoring_student_id;
            }

            public void setBook_tutoring_student_id(String book_tutoring_student_id) {
                this.book_tutoring_student_id = book_tutoring_student_id;
            }

            public String getAdded_by_teacher() {
                return added_by_teacher;
            }

            public void setAdded_by_teacher(String added_by_teacher) {
                this.added_by_teacher = added_by_teacher;
            }
*/
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

            public Object getStudent() {
                return Student;
            }

            public void setStudent(Object Student) {
                this.Student = Student;
            }

            public UserEntity getUser() {
                return User;
            }

            public void setUser(UserEntity User) {
                this.User = User;
            }

           /* public GroupInfoEntity getGroupInfo() {
                return GroupInfo;
            }

            public void setGroupInfo(GroupInfoEntity GroupInfo) {
                this.GroupInfo = GroupInfo;
            }

            public CourseInfoEntity getCourseInfo() {
                return CourseInfo;
            }

            public void setCourseInfo(CourseInfoEntity CourseInfo) {
                this.CourseInfo = CourseInfo;
            }

            public List<SchemaGroupEntity> getSchemaGroup() {
                return SchemaGroup;
            }

            public void setSchemaGroup(List<SchemaGroupEntity> SchemaGroup) {
                this.SchemaGroup = SchemaGroup;
            }
*/
           class UserEntity {

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




           }

            public static class GroupInfoEntity {
                private Object name;

                public Object getName() {
                    return name;
                }

                public void setName(Object name) {
                    this.name = name;
                }
            }

            public static class CourseInfoEntity {
                private String id;
                private String COU_Name;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

               /* public String getCOU_Name() {
                    return COU_Name;
                }

                public void setCOU_Name(String COU_Name) {
                    this.COU_Name = COU_Name;
                }*/
            }

            public static class SchemaGroupEntity {
                private String id;
                private String teacher_id;
                private String group_id;
                private String schema_id;
                private String created;
                private String modified;
                private String status;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

          /*      public String getTeacher_id() {
                    return teacher_id;
                }

                public void setTeacher_id(String teacher_id) {
                    this.teacher_id = teacher_id;
                }

                public String getGroup_id() {
                    return group_id;
                }

                public void setGroup_id(String group_id) {
                    this.group_id = group_id;
                }

                public String getSchema_id() {
                    return schema_id;
                }

                public void setSchema_id(String schema_id) {
                    this.schema_id = schema_id;
                }*/

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

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }
        }
    }
}
