package com.example.personalityinventory.model;

public class Subject {
    int SubjectID;
    String SubjectName;

    public Subject(){

    }

    public Subject(String subjectName) {
        SubjectName = subjectName;
    }

    public int getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(int subjectID) {
        SubjectID = subjectID;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }
}
