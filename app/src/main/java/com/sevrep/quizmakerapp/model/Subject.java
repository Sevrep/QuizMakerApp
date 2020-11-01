package com.sevrep.quizmakerapp.model;

public class Subject {

    private int subjectid;
    private String subjectname;
    private String subjectteacher;

    public Subject(int subjectid, String subjectname, String subjectteacher) {
        this.subjectid = subjectid;
        this.subjectname = subjectname;
        this.subjectteacher = subjectteacher;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public String getSubjectteacher() {
        return subjectteacher;
    }
}
