package com.pnf.elar.app.Bo;

import java.util.List;

/**
 * Created by pmohan on 06-09-2017.
 */

public class TodaysNoteBO {
    /**
     * status : true
     * note_list : [{"TodayNote":{"id":"20","student_id":"1","writtenby_id":"44","note_date":"2017-01-27","description":"Fell and hit knee","created":"2017-01-27 11:53:32","modified":"2017-01-27 11:53:32"}},{"TodayNote":{"id":"9","student_id":"1","writtenby_id":"44","note_date":"2017-01-10","description":"dfgdfgdfg dfgdf gdfg","created":"2017-01-10 07:53:09","modified":"2017-01-10 07:53:09"}},{"TodayNote":{"id":"3","student_id":"1","writtenby_id":"44","note_date":"2017-01-04","description":"Where does it come from?\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of &quot;de Finibus Bonorum et Malorum&quot; (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, &quot;Lorem ipsum dolor sit amet..&quot;, comes from a line in section 1.10.32.\n\nThe standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from &quot;de Finibus Bonorum et Malorum&quot; by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.","created":"2017-01-04 05:50:58","modified":"2017-01-04 11:55:17"}}]
     */

    private String status;
    private List<NoteListBean> note_list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NoteListBean> getNote_list() {
        return note_list;
    }

    public void setNote_list(List<NoteListBean> note_list) {
        this.note_list = note_list;
    }

    public static class NoteListBean {
        /**
         * TodayNote : {"id":"20","student_id":"1","writtenby_id":"44","note_date":"2017-01-27","description":"Fell and hit knee","created":"2017-01-27 11:53:32","modified":"2017-01-27 11:53:32"}
         */

        private TodayNoteBean TodayNote;

        public TodayNoteBean getTodayNote() {
            return TodayNote;
        }

        public void setTodayNote(TodayNoteBean TodayNote) {
            this.TodayNote = TodayNote;
        }

        public static class TodayNoteBean {
            /**
             * id : 20
             * student_id : 1
             * writtenby_id : 44
             * note_date : 2017-01-27
             * description : Fell and hit knee
             * created : 2017-01-27 11:53:32
             * modified : 2017-01-27 11:53:32
             */

            private String id;
            private String student_id;
            private String writtenby_id;
            private String note_date;
            private String description;
            private String created;
            private String modified;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStudent_id() {
                return student_id;
            }

            public void setStudent_id(String student_id) {
                this.student_id = student_id;
            }

            public String getWrittenby_id() {
                return writtenby_id;
            }

            public void setWrittenby_id(String writtenby_id) {
                this.writtenby_id = writtenby_id;
            }

            public String getNote_date() {
                return note_date;
            }

            public void setNote_date(String note_date) {
                this.note_date = note_date;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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
}
