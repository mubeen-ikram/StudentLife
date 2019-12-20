package com.tnc.studentlife.ModelClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class TimeTableInformation implements Serializable {
    private ArrayList<CourseInformation> currentCourses;
    private ArrayList<EventInformation> myEvents;


    public ArrayList<CourseInformation> getCurrentCourses() {
        return currentCourses;
    }

    public void setCurrentCourses(ArrayList<CourseInformation> currentCourses) {
        this.currentCourses = currentCourses;
    }

    public ArrayList<EventInformation> getMyEvents() {
        return myEvents;
    }

    public void setMyEvents(ArrayList<EventInformation> myEvents) {
        this.myEvents = myEvents;
    }
}
