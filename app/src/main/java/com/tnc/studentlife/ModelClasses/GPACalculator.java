package com.tnc.studentlife.ModelClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class GPACalculator  implements Serializable {
    private ArrayList<CourseInformation> courses;
    private Float currentCGpa;
    private Float currentSGpa;

    public ArrayList<CourseInformation> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<CourseInformation> courses) {
        this.courses = courses;
    }

    public Float getCurrentCGpa() {
        return currentCGpa;
    }

    public void setCurrentCGpa(Float currentCGpa) {
        this.currentCGpa = currentCGpa;
    }

    public Float getCurrentSGpa() {
        return currentSGpa;
    }

    public void setCurrentSGpa(Float currentSGpa) {
        this.currentSGpa = currentSGpa;
    }
}
