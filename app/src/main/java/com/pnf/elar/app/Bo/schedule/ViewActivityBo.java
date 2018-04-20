package com.pnf.elar.app.Bo.schedule;

import java.util.List;

/**
 * Created by VKrishnasamy on 07-02-2017.
 */

public class ViewActivityBo {
    /**
     * Schema : {"id":"856","class_id":"0","user_id":"44","customer_id":"1","cou_course_id":"258","parent_schema_id":"0","tutoring_period_id":"0","building_id":null,"room_id":null,"examination_id":"0","aspect_ids":"329","assigned_teacher_ids":"","deadline":"1","schedule_type":"assignment","type":"unique","title":"testing ","description":"test description","start":"2017-02-09 15:00:00","end":"","tutoring_student_or_studupartner":"","with_parent":"no","tutoring_time":"0","book_tutoring_student_id":"0","added_by_teacher":"1","created":"2017-02-06 11:18:31","modified":"2017-02-06 11:18:31","classname":null,"iamsick":"1","fromDate":"02/09/2017 15:00","toDate":"01/01/1970 01:00"}
     * ClaClass : {"name":null}
     * User : {"id":"44","USR_CUS_Rid":"1","group_id":"2","username":"AllVar050","USR_FirstName":"Eva","USR_LastName":"Perthus","USR_Birthday":"1990-01-01 00:00:00","USR_Email":"jagan4610@gmail.com","USR_StreetAddress":"10 Market Road","start":"2013-11-01 00:00:00","end":"2017-11-30 00:00:00"}
     * CouCourse : {"id":"258","COU_Name":"CNN"}
     * TutoringPeriod : {"id":null,"teacher_id":null,"class_id":null,"group_type":null,"title":null,"start":null,"end":null,"tutoring_student_or_studupartner":null,"with_parent":null,"tutoring_time":null,"created":null,"modified":null}
     * Examination : {"id":null,"customer_id":null,"teacher_id":null,"course_id":null,"group_id":null,"group_type":null,"exam_type":null,"name":null,"exam_date":null,"exam_date_end":null,"total_points":null,"exam_image_count":null,"exam_task_count":null,"lock_status":null,"created":null,"modified":null}
     * Building : {"id":null,"customer_id":null,"name":null,"image":null,"status":null,"created":null,"modified":null}
     * Room : {"id":null,"customer_id":null,"building_id":null,"name":null,"people_amount":null,"type":null,"image":null,"status":null,"created":null,"modified":null}
     * Attendance : {"id":null,"schema_id":null,"student_id":null,"teacher_id":null,"present":null,"latetime":null,"sick_description":null,"written_by_parent":null,"status":null,"created":null,"modified":null}
     * SchemasStudypartner : []
     * SchemaGroup : [{"id":"93","teacher_id":"44","group_id":"11","schema_id":"856","created":"2017-02-06 11:18:31","modified":"2017-02-06 11:18:31","status":"1"}]
     * SchemaTeacher : [{"id":"259","schema_id":"856","teacher_id":"44"}]
     * Studentdoc : []
     * Sick : []
     */

    private DataEntity data;
    /**
     * data : {"Schema":{"id":"856","class_id":"0","user_id":"44","customer_id":"1","cou_course_id":"258","parent_schema_id":"0","tutoring_period_id":"0","building_id":null,"room_id":null,"examination_id":"0","aspect_ids":"329","assigned_teacher_ids":"","deadline":"1","schedule_type":"assignment","type":"unique","title":"testing ","description":"test description","start":"2017-02-09 15:00:00","end":"","tutoring_student_or_studupartner":"","with_parent":"no","tutoring_time":"0","book_tutoring_student_id":"0","added_by_teacher":"1","created":"2017-02-06 11:18:31","modified":"2017-02-06 11:18:31","classname":null,"iamsick":"1","fromDate":"02/09/2017 15:00","toDate":"01/01/1970 01:00"},"ClaClass":{"name":null},"User":{"id":"44","USR_CUS_Rid":"1","group_id":"2","username":"AllVar050","USR_FirstName":"Eva","USR_LastName":"Perthus","USR_Birthday":"1990-01-01 00:00:00","USR_Email":"jagan4610@gmail.com","USR_StreetAddress":"10 Market Road","start":"2013-11-01 00:00:00","end":"2017-11-30 00:00:00"},"CouCourse":{"id":"258","COU_Name":"CNN"},"TutoringPeriod":{"id":null,"teacher_id":null,"class_id":null,"group_type":null,"title":null,"start":null,"end":null,"tutoring_student_or_studupartner":null,"with_parent":null,"tutoring_time":null,"created":null,"modified":null},"Examination":{"id":null,"customer_id":null,"teacher_id":null,"course_id":null,"group_id":null,"group_type":null,"exam_type":null,"name":null,"exam_date":null,"exam_date_end":null,"total_points":null,"exam_image_count":null,"exam_task_count":null,"lock_status":null,"created":null,"modified":null},"Building":{"id":null,"customer_id":null,"name":null,"image":null,"status":null,"created":null,"modified":null},"Room":{"id":null,"customer_id":null,"building_id":null,"name":null,"people_amount":null,"type":null,"image":null,"status":null,"created":null,"modified":null},"Attendance":{"id":null,"schema_id":null,"student_id":null,"teacher_id":null,"present":null,"latetime":null,"sick_description":null,"written_by_parent":null,"status":null,"created":null,"modified":null},"SchemasStudypartner":[],"SchemaGroup":[{"id":"93","teacher_id":"44","group_id":"11","schema_id":"856","created":"2017-02-06 11:18:31","modified":"2017-02-06 11:18:31","status":"1"}],"SchemaTeacher":[{"id":"259","schema_id":"856","teacher_id":"44"}],"Studentdoc":[],"Sick":[]}
     * selectedStudents : [{"id":"343","user_id":"140"},{"id":"344","user_id":"649"},{"id":"345","user_id":"634"}]
     * selectedStudypartners : []
     * status : true
     */

    private String status;
    /**
     * id : 343
     * user_id : 140
     */

    private List<SelectedStudentsEntity> selectedStudents;
    private List<?> selectedStudypartners;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectedStudentsEntity> getSelectedStudents() {
        return selectedStudents;
    }

    public void setSelectedStudents(List<SelectedStudentsEntity> selectedStudents) {
        this.selectedStudents = selectedStudents;
    }

    public List<?> getSelectedStudypartners() {
        return selectedStudypartners;
    }

    public void setSelectedStudypartners(List<?> selectedStudypartners) {
        this.selectedStudypartners = selectedStudypartners;
    }

    public static class DataEntity {
        /**
         * id : 856
         * class_id : 0
         * user_id : 44
         * customer_id : 1
         * cou_course_id : 258
         * parent_schema_id : 0
         * tutoring_period_id : 0
         * building_id : null
         * room_id : null
         * examination_id : 0
         * aspect_ids : 329
         * assigned_teacher_ids :
         * deadline : 1
         * schedule_type : assignment
         * type : unique
         * title : testing
         * description : test description
         * start : 2017-02-09 15:00:00
         * end :
         * tutoring_student_or_studupartner :
         * with_parent : no
         * tutoring_time : 0
         * book_tutoring_student_id : 0
         * added_by_teacher : 1
         * created : 2017-02-06 11:18:31
         * modified : 2017-02-06 11:18:31
         * classname : null
         * iamsick : 1
         * fromDate : 02/09/2017 15:00
         * toDate : 01/01/1970 01:00
         */

        private SchemaEntity Schema;
        /**
         * name : null
         */

        private ClaClassEntity ClaClass;
        /**
         * id : 44
         * USR_CUS_Rid : 1
         * group_id : 2
         * username : AllVar050
         * USR_FirstName : Eva
         * USR_LastName : Perthus
         * USR_Birthday : 1990-01-01 00:00:00
         * USR_Email : jagan4610@gmail.com
         * USR_StreetAddress : 10 Market Road
         * start : 2013-11-01 00:00:00
         * end : 2017-11-30 00:00:00
         */

        private UserEntity User;
        /**
         * id : 258
         * COU_Name : CNN
         */

        private CouCourseEntity CouCourse;
        /**
         * id : null
         * teacher_id : null
         * class_id : null
         * group_type : null
         * title : null
         * start : null
         * end : null
         * tutoring_student_or_studupartner : null
         * with_parent : null
         * tutoring_time : null
         * created : null
         * modified : null
         */

        private TutoringPeriodEntity TutoringPeriod;
        /**
         * id : null
         * customer_id : null
         * teacher_id : null
         * course_id : null
         * group_id : null
         * group_type : null
         * exam_type : null
         * name : null
         * exam_date : null
         * exam_date_end : null
         * total_points : null
         * exam_image_count : null
         * exam_task_count : null
         * lock_status : null
         * created : null
         * modified : null
         */

        private ExaminationEntity Examination;
        /**
         * id : null
         * customer_id : null
         * name : null
         * image : null
         * status : null
         * created : null
         * modified : null
         */

        private BuildingEntity Building;
        /**
         * id : null
         * customer_id : null
         * building_id : null
         * name : null
         * people_amount : null
         * type : null
         * image : null
         * status : null
         * created : null
         * modified : null
         */

        private RoomEntity Room;
        /**
         * id : null
         * schema_id : null
         * student_id : null
         * teacher_id : null
         * present : null
         * latetime : null
         * sick_description : null
         * written_by_parent : null
         * status : null
         * created : null
         * modified : null
         */

        private AttendanceEntity Attendance;
        private List<?> SchemasStudypartner;
        /**
         * id : 93
         * teacher_id : 44
         * group_id : 11
         * schema_id : 856
         * created : 2017-02-06 11:18:31
         * modified : 2017-02-06 11:18:31
         * status : 1
         */

        private List<SchemaGroupEntity> SchemaGroup;
        /**
         * id : 259
         * schema_id : 856
         * teacher_id : 44
         */

        private List<SchemaTeacherEntity> SchemaTeacher;
        private List<?> Studentdoc;
        private List<?> Sick;

        public SchemaEntity getSchema() {
            return Schema;
        }

        public void setSchema(SchemaEntity Schema) {
            this.Schema = Schema;
        }

        public ClaClassEntity getClaClass() {
            return ClaClass;
        }

        public void setClaClass(ClaClassEntity ClaClass) {
            this.ClaClass = ClaClass;
        }

        public UserEntity getUser() {
            return User;
        }

        public void setUser(UserEntity User) {
            this.User = User;
        }

        public CouCourseEntity getCouCourse() {
            return CouCourse;
        }

        public void setCouCourse(CouCourseEntity CouCourse) {
            this.CouCourse = CouCourse;
        }

        public TutoringPeriodEntity getTutoringPeriod() {
            return TutoringPeriod;
        }

        public void setTutoringPeriod(TutoringPeriodEntity TutoringPeriod) {
            this.TutoringPeriod = TutoringPeriod;
        }

        public ExaminationEntity getExamination() {
            return Examination;
        }

        public void setExamination(ExaminationEntity Examination) {
            this.Examination = Examination;
        }

        public BuildingEntity getBuilding() {
            return Building;
        }

        public void setBuilding(BuildingEntity Building) {
            this.Building = Building;
        }

        public RoomEntity getRoom() {
            return Room;
        }

        public void setRoom(RoomEntity Room) {
            this.Room = Room;
        }

        public AttendanceEntity getAttendance() {
            return Attendance;
        }

        public void setAttendance(AttendanceEntity Attendance) {
            this.Attendance = Attendance;
        }

        public List<?> getSchemasStudypartner() {
            return SchemasStudypartner;
        }

        public void setSchemasStudypartner(List<?> SchemasStudypartner) {
            this.SchemasStudypartner = SchemasStudypartner;
        }

        public List<SchemaGroupEntity> getSchemaGroup() {
            return SchemaGroup;
        }

        public void setSchemaGroup(List<SchemaGroupEntity> SchemaGroup) {
            this.SchemaGroup = SchemaGroup;
        }

        public List<SchemaTeacherEntity> getSchemaTeacher() {
            return SchemaTeacher;
        }

        public void setSchemaTeacher(List<SchemaTeacherEntity> SchemaTeacher) {
            this.SchemaTeacher = SchemaTeacher;
        }

        public List<?> getStudentdoc() {
            return Studentdoc;
        }

        public void setStudentdoc(List<?> Studentdoc) {
            this.Studentdoc = Studentdoc;
        }

        public List<?> getSick() {
            return Sick;
        }

        public void setSick(List<?> Sick) {
            this.Sick = Sick;
        }

        public static class SchemaEntity {
            private String id;
            private String class_id;
            private String user_id;
            private String customer_id;
            private String cou_course_id;
            private String parent_schema_id;
            private String tutoring_period_id;
            private Object building_id;
            private Object room_id;
            private String examination_id;
            private String aspect_ids;
            private String assigned_teacher_ids;
            private String deadline;
            private String schedule_type;
            private String type;
            private String title;
            private String description;
            private String start;
            private String end;
            private String tutoring_student_or_studupartner;
            private String with_parent;
            private String tutoring_time;
            private String book_tutoring_student_id;
            private String added_by_teacher;
            private String created;
            private String modified;
            private Object classname;
            private String iamsick;
            private String fromDate;
            private String toDate;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getClass_id() {
                return class_id;
            }

            public void setClass_id(String class_id) {
                this.class_id = class_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getCustomer_id() {
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

            public String getTutoring_student_or_studupartner() {
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

            public Object getClassname() {
                return classname;
            }

            public void setClassname(Object classname) {
                this.classname = classname;
            }

            public String getIamsick() {
                return iamsick;
            }

            public void setIamsick(String iamsick) {
                this.iamsick = iamsick;
            }

            public String getFromDate() {
                return fromDate;
            }

            public void setFromDate(String fromDate) {
                this.fromDate = fromDate;
            }

            public String getToDate() {
                return toDate;
            }

            public void setToDate(String toDate) {
                this.toDate = toDate;
            }
        }

        public static class ClaClassEntity {
            private Object name;

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }
        }

        public static class UserEntity {
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

        public static class CouCourseEntity {
            private String id;
            private String COU_Name;

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
        }

        public static class TutoringPeriodEntity {
            private Object id;
            private Object teacher_id;
            private Object class_id;
            private Object group_type;
            private Object title;
            private Object start;
            private Object end;
            private Object tutoring_student_or_studupartner;
            private Object with_parent;
            private Object tutoring_time;
            private Object created;
            private Object modified;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getTeacher_id() {
                return teacher_id;
            }

            public void setTeacher_id(Object teacher_id) {
                this.teacher_id = teacher_id;
            }

            public Object getClass_id() {
                return class_id;
            }

            public void setClass_id(Object class_id) {
                this.class_id = class_id;
            }

            public Object getGroup_type() {
                return group_type;
            }

            public void setGroup_type(Object group_type) {
                this.group_type = group_type;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public Object getStart() {
                return start;
            }

            public void setStart(Object start) {
                this.start = start;
            }

            public Object getEnd() {
                return end;
            }

            public void setEnd(Object end) {
                this.end = end;
            }

            public Object getTutoring_student_or_studupartner() {
                return tutoring_student_or_studupartner;
            }

            public void setTutoring_student_or_studupartner(Object tutoring_student_or_studupartner) {
                this.tutoring_student_or_studupartner = tutoring_student_or_studupartner;
            }

            public Object getWith_parent() {
                return with_parent;
            }

            public void setWith_parent(Object with_parent) {
                this.with_parent = with_parent;
            }

            public Object getTutoring_time() {
                return tutoring_time;
            }

            public void setTutoring_time(Object tutoring_time) {
                this.tutoring_time = tutoring_time;
            }

            public Object getCreated() {
                return created;
            }

            public void setCreated(Object created) {
                this.created = created;
            }

            public Object getModified() {
                return modified;
            }

            public void setModified(Object modified) {
                this.modified = modified;
            }
        }

        public static class ExaminationEntity {
            private Object id;
            private Object customer_id;
            private Object teacher_id;
            private Object course_id;
            private Object group_id;
            private Object group_type;
            private Object exam_type;
            private Object name;
            private Object exam_date;
            private Object exam_date_end;
            private Object total_points;
            private Object exam_image_count;
            private Object exam_task_count;
            private Object lock_status;
            private Object created;
            private Object modified;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(Object customer_id) {
                this.customer_id = customer_id;
            }

            public Object getTeacher_id() {
                return teacher_id;
            }

            public void setTeacher_id(Object teacher_id) {
                this.teacher_id = teacher_id;
            }

            public Object getCourse_id() {
                return course_id;
            }

            public void setCourse_id(Object course_id) {
                this.course_id = course_id;
            }

            public Object getGroup_id() {
                return group_id;
            }

            public void setGroup_id(Object group_id) {
                this.group_id = group_id;
            }

            public Object getGroup_type() {
                return group_type;
            }

            public void setGroup_type(Object group_type) {
                this.group_type = group_type;
            }

            public Object getExam_type() {
                return exam_type;
            }

            public void setExam_type(Object exam_type) {
                this.exam_type = exam_type;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getExam_date() {
                return exam_date;
            }

            public void setExam_date(Object exam_date) {
                this.exam_date = exam_date;
            }

            public Object getExam_date_end() {
                return exam_date_end;
            }

            public void setExam_date_end(Object exam_date_end) {
                this.exam_date_end = exam_date_end;
            }

            public Object getTotal_points() {
                return total_points;
            }

            public void setTotal_points(Object total_points) {
                this.total_points = total_points;
            }

            public Object getExam_image_count() {
                return exam_image_count;
            }

            public void setExam_image_count(Object exam_image_count) {
                this.exam_image_count = exam_image_count;
            }

            public Object getExam_task_count() {
                return exam_task_count;
            }

            public void setExam_task_count(Object exam_task_count) {
                this.exam_task_count = exam_task_count;
            }

            public Object getLock_status() {
                return lock_status;
            }

            public void setLock_status(Object lock_status) {
                this.lock_status = lock_status;
            }

            public Object getCreated() {
                return created;
            }

            public void setCreated(Object created) {
                this.created = created;
            }

            public Object getModified() {
                return modified;
            }

            public void setModified(Object modified) {
                this.modified = modified;
            }
        }

        public static class BuildingEntity {
            private Object id;
            private Object customer_id;
            private Object name;
            private Object image;
            private Object status;
            private Object created;
            private Object modified;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(Object customer_id) {
                this.customer_id = customer_id;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getImage() {
                return image;
            }

            public void setImage(Object image) {
                this.image = image;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getCreated() {
                return created;
            }

            public void setCreated(Object created) {
                this.created = created;
            }

            public Object getModified() {
                return modified;
            }

            public void setModified(Object modified) {
                this.modified = modified;
            }
        }

        public static class RoomEntity {
            private Object id;
            private Object customer_id;
            private Object building_id;
            private Object name;
            private Object people_amount;
            private Object type;
            private Object image;
            private Object status;
            private Object created;
            private Object modified;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(Object customer_id) {
                this.customer_id = customer_id;
            }

            public Object getBuilding_id() {
                return building_id;
            }

            public void setBuilding_id(Object building_id) {
                this.building_id = building_id;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getPeople_amount() {
                return people_amount;
            }

            public void setPeople_amount(Object people_amount) {
                this.people_amount = people_amount;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public Object getImage() {
                return image;
            }

            public void setImage(Object image) {
                this.image = image;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getCreated() {
                return created;
            }

            public void setCreated(Object created) {
                this.created = created;
            }

            public Object getModified() {
                return modified;
            }

            public void setModified(Object modified) {
                this.modified = modified;
            }
        }

        public static class AttendanceEntity {
            private String id;
            private String schema_id;
            private String student_id;
            private String teacher_id;
            private String present;
            private String latetime;
            private String sick_description;
            private String written_by_parent;
            private String status;
            private String created;
            private String modified;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSchema_id() {
                return schema_id;
            }

            public void setSchema_id(String schema_id) {
                this.schema_id = schema_id;
            }

            public String getStudent_id() {
                return student_id;
            }

            public void setStudent_id(String student_id) {
                this.student_id = student_id;
            }

            public String getTeacher_id() {
                return teacher_id;
            }

            public void setTeacher_id(String teacher_id) {
                this.teacher_id = teacher_id;
            }

            public String getPresent() {
                return present;
            }

            public void setPresent(String present) {
                this.present = present;
            }

            public String getLatetime() {
                return latetime;
            }

            public void setLatetime(String latetime) {
                this.latetime = latetime;
            }

            public String getSick_description() {
                return sick_description;
            }

            public void setSick_description(String sick_description) {
                this.sick_description = sick_description;
            }

            public String getWritten_by_parent() {
                return written_by_parent;
            }

            public void setWritten_by_parent(String written_by_parent) {
                this.written_by_parent = written_by_parent;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getTeacher_id() {
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class SchemaTeacherEntity {
            private String id;
            private String schema_id;
            private String teacher_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSchema_id() {
                return schema_id;
            }

            public void setSchema_id(String schema_id) {
                this.schema_id = schema_id;
            }

            public String getTeacher_id() {
                return teacher_id;
            }

            public void setTeacher_id(String teacher_id) {
                this.teacher_id = teacher_id;
            }
        }
    }

    public static class SelectedStudentsEntity {
        private String id;
        private String user_id;

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
    }
}
