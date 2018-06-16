package com.tanaseandrei.quizcourse;

/**
 * Created by asadf on 6/9/2018.
 */

public class SHowQuestionsModel {

    String id;
    String question;

    public SHowQuestionsModel() {

    }

    public SHowQuestionsModel(String id, String question) {
        this.id = id;
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
