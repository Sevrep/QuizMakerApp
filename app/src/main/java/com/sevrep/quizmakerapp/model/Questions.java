package com.sevrep.quizmakerapp.model;

public class Questions {

    private int questionid;
    private String questiontext;
    private String questionchoicea;
    private String questionchoiceb;
    private String questionchoicec;
    private String questionchoiced;
    private String questionanswer;
    private String questiontype;
    private int subjectid;
    private String fullname;

    public Questions() {
    }

    public Questions(int questionid, String questiontext, String questionanswer) {
        this.questionid = questionid;
        this.questiontext = questiontext;
        this.questionanswer = questionanswer;
    }

    public Questions(int questionid, String questiontext, String questionchoicea, String questionchoiceb, String questionchoicec, String questionchoiced, String questionanswer, String questiontype, int subjectid, String fullname) {
        this.questionid = questionid;
        this.questiontext = questiontext;
        this.questionchoicea = questionchoicea;
        this.questionchoiceb = questionchoiceb;
        this.questionchoicec = questionchoicec;
        this.questionchoiced = questionchoiced;
        this.questionanswer = questionanswer;
        this.questiontype = questiontype;
        this.subjectid = subjectid;
        this.fullname = fullname;
    }

    public int getQuestionid() {
        return questionid;
    }

    public String getQuestiontext() {
        return questiontext;
    }

    public String getQuestionchoicea() {
        return questionchoicea;
    }

    public String getQuestionchoiceb() {
        return questionchoiceb;
    }

    public String getQuestionchoicec() {
        return questionchoicec;
    }

    public String getQuestionchoiced() {
        return questionchoiced;
    }

    public String getQuestionanswer() {
        return questionanswer;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public String getFullname() {
        return fullname;
    }
}
