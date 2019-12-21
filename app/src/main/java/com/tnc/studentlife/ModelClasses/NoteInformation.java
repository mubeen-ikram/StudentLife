package com.tnc.studentlife.ModelClasses;

import java.io.Serializable;

public class NoteInformation implements Serializable {
    private String notesData;
    private int currentNoteId;
    private int parentId;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCurrentNoteId() {
        return currentNoteId;
    }

    public void setCurrentNoteId(int currentNoteId) {
        this.currentNoteId = currentNoteId;
    }

    public String getNotesData() {
        return notesData;
    }

    public void setNotesData(String notesData) {
        this.notesData = notesData;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
