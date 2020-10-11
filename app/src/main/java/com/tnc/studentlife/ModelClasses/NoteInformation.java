package com.tnc.studentlife.ModelClasses;


import java.io.Serializable;

public class NoteInformation implements Serializable,Cloneable {
    private String notesData;
    private int verticalPosition=-1;
    private int horizontalPosition=-1;
    private boolean isShow=true;


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


    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
