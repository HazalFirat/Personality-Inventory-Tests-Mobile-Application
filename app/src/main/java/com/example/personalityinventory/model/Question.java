package com.example.personalityinventory.model;

public class Question {
    int QuestionID;
    String QuestionContent;
    int TestID;
    int SubjectID;

    public Question() {

    }

    public Question(String questionContent, int testID, int subjectID) {
        QuestionContent = questionContent;
        TestID = testID;
        SubjectID = subjectID;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public String getQuestionContent() {
        return QuestionContent;
    }

    public void setQuestionContent(String questionContent) {
        QuestionContent = questionContent;
    }

    public int getTestID() {
        return TestID;
    }

    public void setTestID(int testID) {
        TestID = testID;
    }

    public int getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(int subjectID) {
        SubjectID = subjectID;
    }
}
