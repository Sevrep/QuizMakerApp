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
    private String subjectid;
    private String subjectteacher;

    public Questions(int questionid, String questiontext, String questionchoicea, String questionchoiceb, String questionchoicec, String questionchoiced, String questionanswer, String questiontype, String subjectid, String subjectteacher) {
        this.questionid = questionid;
        this.questiontext = questiontext;
        this.questionchoicea = questionchoicea;
        this.questionchoiceb = questionchoiceb;
        this.questionchoicec = questionchoicec;
        this.questionchoiced = questionchoiced;
        this.questionanswer = questionanswer;
        this.questiontype = questiontype;
        this.subjectid = subjectid;
        this.subjectteacher = subjectteacher;
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

    public String getSubjectid() {
        return subjectid;
    }

    public String getSubjectteacher() {
        return subjectteacher;
    }
}
