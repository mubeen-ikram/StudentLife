package com.tnc.studentlife.ModelClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CourseInformation  implements Serializable {
    private ArrayList<NoteInformation> notes=new ArrayList<>();
    private String coarseName;
    private float creditHours;
    private String instructorName;
    private float classHours;
    private ArrayList<Date> classDates=new ArrayList<>();

    public CourseInformation() {
    }

    public CourseInformation(CourseInformation courseInformation) {
        this.notes= (ArrayList<NoteInformation>) courseInformation.getNotes().clone();
        this.coarseName=courseInformation.getCoarseName();
        this.creditHours=courseInformation.getCreditHours();
        this.classHours=courseInformation.getClassHours();
        this.instructorName=courseInformation.instructorName;
        this.classDates= (ArrayList<Date>) courseInformation.getClassDates().clone();
    }


    public ArrayList<NoteInformation> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<NoteInformation> notes) {
        this.notes = notes;
    }

    public String getCoarseName() {
        return coarseName;
    }

    public void setCoarseName(String coarseName) {
        this.coarseName = coarseName;
    }

    public float getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(float creditHours) {
        this.creditHours = creditHours;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public float getClassHours() {
        return classHours;
    }

    public void setClassHours(float classHours) {
        this.classHours = classHours;
    }

    public ArrayList<Date> getClassDates() {
        return classDates;
    }

    public void setClassDates(ArrayList<Date> classDates) {
        this.classDates = classDates;
    }
}
