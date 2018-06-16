package com.tanaseandrei.quizcourse;

/**
 * Created by Asad Fiaz on 2/22/2018.
 */

//Model Class for question
public class Question {

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String Option4;
    private int answerNr;

    public Question() {
    }

    public Question(String question, String option1, String option2, String option3, String option4, int answerNr) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        Option4 = option4;
        this.answerNr = answerNr;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return Option4;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        Option4 = option4;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }
}
