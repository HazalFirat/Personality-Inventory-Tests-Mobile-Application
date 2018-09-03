package com.example.personalityinventory.model;

public class Result {
    int ResultID;
    int UserID;
    int TestID;
    int SubjectID;
    float Percent;
    int QuestionID;

    public Result() {

    }

    public Result(int userID, int testID, int subjectID, float percent, int questionID) {
        UserID = userID;
        TestID = testID;
        SubjectID = subjectID;
        Percent = percent;
        QuestionID = questionID;
    }

    public int getResultID() {
        return ResultID;
    }

    public void setResultID(int resultID) {
        ResultID = resultID;
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

    public float getPercent() {
        return Percent;
    }

    public void setPercent(float percent) {
        Percent = percent;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}

