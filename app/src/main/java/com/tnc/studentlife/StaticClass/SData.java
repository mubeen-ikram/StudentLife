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
    public static PersonInformation userInformation = new PersonInformation();
    public static int currentCourse = -1;
    static SharedPreferences myPreference;


    public static void setMyPreference(Context app) {
        SData.myPreference = app.getSharedPreferences("StudentLife", app.MODE_PRIVATE);
    }

    public static void SaveToFile() {
        String json = "{ personName:\"" + userInformation.getUserName() + "\"" + ",courses:[";
        for (CourseInformation course : userInformation.getCurrentCourses()) {
            json = json + "{" + "courseName=\"" + course.getCoarseName()
                    + "\",instructorName=\"" + course.getInstructorName()
                    + "\",courseHours=\"" + course.getClassHours()
                    + "\",courseNotes=[";
            for (NoteInformation note : course.getNotes()) {
                json = json + "{" + "noteData=\"" + note.getNotesData()
                        + "\",noteId=\"" + note.getCurrentNoteId()
                        + "\",notePosition=\"" + note.getPosition()
                        + "\",noteParentId=\"" + note.getParentId() + "\"}";
                if (note != course.getNotes().get(course.getNotes().size() - 1)) {
                    json = json + ',';
                }
            }
            json = json + "]}";
            if (course != SData.userInformation.getCurrentCourses().get(SData.userInformation.getCurrentCourses().size() - 1)) {
                json = json + ',';
            }
        }
        json = json + "]}";
        SharedPreferences.Editor edit = myPreference.edit();
        edit.putString("PersonalInformation", json);
        edit.commit();
    }

    public static void getFromFile() {

        String json = myPreference.getString("PersonalInformation", "");
        clearInfo();

        try {
            if (!json.equalsIgnoreCase("")) {
                JSONObject fullResult = new JSONObject(json);
                userInformation.setUserName(fullResult.getString("personName"));
                JSONArray jsonArrayArtical = fullResult.getJSONArray("courses");
                for (int x = 0; x < jsonArrayArtical.length(); x++) {
                    JSONObject course = jsonArrayArtical.getJSONObject(x);
                    CourseInformation crs=new CourseInformation();
                    crs.setCreditHours(Float.valueOf(course.getString("courseHours")));
                    crs.setInstructorName(course.getString("instructorName"));
                    crs.setCoarseName(course.getString("courseName"));
                    JSONArray notes=course.getJSONArray("courseNotes");
                    for(int y=0;y<notes.length();y++){
                        JSONObject noteJson = notes.getJSONObject(y);
                        NoteInformation note=new NoteInformation();
                        note.setNotesData(noteJson.getString("noteData"));
                        note.setPosition(Integer.valueOf(noteJson.getString("notePosition")));
                        note.setParentId(Integer.valueOf(noteJson.getString("noteParentId")));
                        note.setCurrentNoteId(Integer.valueOf(noteJson.getString("noteId")));
                        crs.getNotes().add(note);
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
}
