package com.project.progetto_oop.model;

public class Survey {
    int id;
    String countryEUStatus;
    String countryCode;
    String questionCode;
    String subset;
    String answer;
    double answers;
    double subsetAnswers;
    double percentage;
    double sampleAnswers;

    public Survey(int id, String countryEUStatus, String countryCode, String questionCode, String subset, String answer, double answers, double subsetAnswers, double percentage, double sampleAnswers) {
        this.id = id;
        this.countryEUStatus = countryEUStatus;
        this.countryCode = countryCode;
        this.questionCode = questionCode;
        this.subset = subset;
        this.answer = answer;
        this.answers = answers;
        this.subsetAnswers = subsetAnswers;
        this.percentage = percentage;
        this.sampleAnswers = sampleAnswers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryEUStatus() {
        return countryEUStatus;
    }

    public void setCountryEUStatus(String countryEUStatus) {
        this.countryEUStatus = countryEUStatus;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getSubset() {
        return subset;
    }

    public void setSubset(String subset) {
        this.subset = subset;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public double getAnswers() {
        return answers;
    }

    public void setAnswers(double answers) {
        this.answers = answers;
    }

    public double getSubsetAnswers() {
        return subsetAnswers;
    }

    public void setSubsetAnswers(double subsetAnswers) {
        this.subsetAnswers = subsetAnswers;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getSampleAnswers() {
        return sampleAnswers;
    }

    public void setSampleAnswers(double sampleAnswers) {
        this.sampleAnswers = sampleAnswers;
    }
}
