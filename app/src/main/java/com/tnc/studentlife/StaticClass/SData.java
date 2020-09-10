package com.tnc.studentlife.StaticClass;

import android.content.Context;
import android.content.SharedPreferences;

import com.tnc.studentlife.ModelClasses.CourseInformation;
import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.ModelClasses.PersonInformation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SData {
    private static PersonInformation userInformation = new PersonInformation();
    private static int currentCourse = -1;
    static SharedPreferences myPreference;


    public static void setMyPreference(Context app) {
        SData.myPreference = app.getSharedPreferences("StudentLife", Context.MODE_PRIVATE);
    }

    public static void SaveToFile() {
        StringBuilder json = new StringBuilder("{ personName:\"" + userInformation.getUserName() + "\"" + ",courses:[");
        for (CourseInformation course : userInformation.getCurrentCourses()) {
            json.append("{").append("courseName=\"").append(course.getCoarseName()).append("\",instructorName=\"").append(course.getInstructorName()).append("\",courseHours=\"").append(course.getClassHours()).append("\",courseNotes=[");
            for (NoteInformation note : course.getNotes().getCurrentNotes()) {
                json.append("{").append("noteData=\"").append(note.getNotesData()).append("\",noteVerticalPosition=\"").append(note.getVerticalPosition()).append("\",noteHorizontalPosition=\"").append(note.getHorizontalPosition()).append("\"}");
                if (note != course.getNotes().getCurrentNotes().get(course.getNotes().getCurrentNotes().size() - 1)) {
                    json.append(',');
                }
            }
            json.append("]}");
            if (course != SData.userInformation.getCurrentCourses().get(SData.userInformation.getCurrentCourses().size() - 1)) {
                json.append(',');
            }
        }
        json.append("]}");
        SharedPreferences.Editor edit = myPreference.edit();
        edit.putString("PersonalInformation", json.toString());
        edit.apply();
    }

    public static void getFromFile() {
        String json = myPreference.getString("PersonalInformation", "");
        clearInfo();
        try {
            assert json != null;
            if (!json.equalsIgnoreCase("")) {
                JSONObject fullResult = new JSONObject(json);
                userInformation.setUserName(fullResult.getString("personName"));
                JSONArray jsonArrayArtical = fullResult.getJSONArray("courses");
                for (int x = 0; x < jsonArrayArtical.length(); x++) {
                    JSONObject course = jsonArrayArtical.getJSONObject(x);
                    CourseInformation crs = new CourseInformation();
                    crs.setCreditHours(Float.parseFloat(course.getString("courseHours")));
                    crs.setInstructorName(course.getString("instructorName"));
                    crs.setCoarseName(course.getString("courseName"));
                    JSONArray notes = course.getJSONArray("courseNotes");
                    for (int y = 0; y < notes.length(); y++) {
                        JSONObject noteJson = notes.getJSONObject(y);
                        NoteInformation note = new NoteInformation();
                        note.setNotesData(noteJson.getString("noteData"));
                        note.setHorizontalPosition(Integer.parseInt(noteJson.getString("noteHorizontalPosition")));
                        note.setVerticalPosition(Integer.parseInt(noteJson.getString("noteVerticalPosition")));
                        crs.getNotes().getCurrentNotes().add(note);
                    }
                    userInformation.getCurrentCourses().add(crs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void clearInfo() {
        userInformation.setCurrentCourses(new ArrayList<CourseInformation>());
        userInformation.setUserAge(null);
        userInformation.setUserName("");
        userInformation.setUserBatch("");
        userInformation.setUserUniversity("");
    }


    public static int getCurrentCourse() {
        return currentCourse;
    }

    public static void setCurrentCourse(int currentCourse) {
        SData.currentCourse = currentCourse;
    }

    public static PersonInformation getUserInformation() {
        return userInformation;
    }

    public static void setUserInformation(PersonInformation userInformation) {
        SData.userInformation = userInformation;
    }
}
