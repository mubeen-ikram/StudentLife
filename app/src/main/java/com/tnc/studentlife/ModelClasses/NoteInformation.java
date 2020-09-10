package com.tnc.studentlife.ModelClasses;

import java.io.Serializable;

public class NoteInformation implements Serializable {
    private String notesData;
    private int verticalPosition=-1;
    private int horizontalPosition=-1;

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public String getNotesData() {
        return notesData;
    }

    public void setNotesData(String notesData) {
        this.notesData = notesData;
    }

}
