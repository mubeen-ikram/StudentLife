package com.tnc.studentlife.ModelClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class CompleteNotes implements Serializable {
    private ArrayList<NoteInformation> currentNotes=new ArrayList<>();
    //Different method on these notes

    public boolean addNote(NoteInformation noteInformation){
        if (noteInformation.getVerticalPosition()==-1){
            noteInformation.setHorizontalPosition(0);
            noteInformation.setVerticalPosition(currentNotes.size());
            currentNotes.add(noteInformation);
        }
        else {
            updateChildIndex(noteInformation);
            //Check if position is -1
            //place it at the index just below the parentId set of knots where the position is > prentId.position
            //findTheIndexToAdd
//            NoteInformation parentNote=new NoteInformation();
//            int nodeIndex=0;
//            //Index to parent
//            for (NoteInformation parenNote:currentNotes){
//                if (parenNote.getVerticalPosition()==noteInformation.getVerticalPosition()){
//                    parentNote=parenNote;
//                    break;
//                }
//                nodeIndex++;
//            }
//            //Index where to put the note
//            for( int x=nodeIndex+1;x<currentNotes.size();x++){
//                nodeIndex++;
//                if(currentNotes.get(x).getHorizontalPosition()<=parentNote.getHorizontalPosition()){
//                    break;
//                }
//            }
//            //Now NodeIndex hold the index where to put the input text stored in nodeIndex
//            noteInformation.setHorizontalPosition(parentNote.getHorizontalPosition()+1);
//            currentNotes.add(nodeIndex,noteInformation);
        }
        return true;
    }


    public ArrayList<NoteInformation> getChild(NoteInformation parentNode){
        ArrayList<NoteInformation> childNotes=new ArrayList<>();
        for(int x=parentNode.getVerticalPosition()+1;x<currentNotes.size();x++){
            if(parentNode.getHorizontalPosition()==currentNotes.get(x).getHorizontalPosition())
                break;
            if (parentNode.getHorizontalPosition()+1==currentNotes.get(x).getHorizontalPosition()){
                childNotes.add(currentNotes.get(x));
            }
        }
        return childNotes;
    }





    private void updateChildIndex(NoteInformation note){
        for(int x=note.getVerticalPosition();x<currentNotes.size();x++){
            currentNotes.get(x).setVerticalPosition(currentNotes.get(x).getVerticalPosition()+1);
        }
        currentNotes.add(note.getVerticalPosition(),note);
    }

    public void setCurrentNotes(ArrayList<NoteInformation> currentNotes) {
        this.currentNotes = currentNotes;
    }


    public ArrayList<NoteInformation> getCurrentNotes() {
        return currentNotes;
    }
}
