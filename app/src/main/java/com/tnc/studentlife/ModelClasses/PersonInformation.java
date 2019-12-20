package com.tnc.studentlife.ModelClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonInformation  implements Serializable {
    private String userName;
    private String userEmail;
    private String userUniversity;
    private Float userAge;
    private String userBatch;
    private ArrayList<CourseInformation> currentCourses;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUniversity() {
        return userUniversity;
    }

    public void setUserUniversity(String userUniversity) {
        this.userUniversity = userUniversity;
    }

    public Float getUserAge() {
        return userAge;
    }

    public void setUserAge(Float userAge) {
        this.userAge = userAge;
    }

    public String getUserBatch() {
        return userBatch;
    }

    public void setUserBatch(String userBatch) {
        this.userBatch = userBatch;
    }

    public ArrayList<CourseInformation> getCurrentCourses() {
        return currentCourses;
    }

    public void setCurrentCourses(ArrayList<CourseInformation> currentCourses) {
        this.currentCourses = currentCourses;
    }
}
