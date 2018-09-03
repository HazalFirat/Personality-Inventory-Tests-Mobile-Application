package com.example.personalityinventory.model;

import java.sql.Date;

public class Test {
    int TestID;
    String TestName;
    int Duration;
    String Description;
    String DateAdded;

    public Test() {
    }

    public Test(String name, int dur,String desc,String date) {
        this.TestName = name;
        this.Duration = dur;
        this.Description = desc;
        this.DateAdded = date;
    }

    public int getTestID() {
        return TestID;
    }

    public void setTestID(int testID) {
        TestID = testID;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }
}
