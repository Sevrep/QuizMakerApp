package com.sevrep.quizmakerapp.model;

public class Questions {

    private int questionid;
    private String questiontext;
    private String questiontexta;
    private String questiontextb;
    private String questiontextc;
    private String questiontextd;
    private String questionchoicea;
    private String questionchoiceb;
    private String questionchoicec;
    private String questionchoiced;
    private String questionanswer;
    private String questiontype;
    private int subjectid;
    private String fullname;

    public Questions(int questionid, String questiontext, String questiontexta, String questiontextb, String questiontextc, String questiontextd, String questionchoicea, String questionchoiceb, String questionchoicec, String questionchoiced, String questionanswer, String questiontype, int subjectid, String fullname) {
        this.questionid = questionid;
        this.questiontext = questiontext;
        this.questiontexta = questiontexta;
        this.questiontextb = questiontextb;
        this.questiontextc = questiontextc;
        this.questiontextd = questiontextd;
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

    public String getQuestiontexta() {
        return questiontexta;
    }

    public String getQuestiontextb() {
        return questiontextb;
    }

    public String getQuestiontextc() {
        return questiontextc;
    }

    public String getQuestiontextd() {
        return questiontextd;
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
