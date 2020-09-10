package com.tnc.studentlife.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnc.studentlife.Activities.CourseDetailActivity;
import com.tnc.studentlife.ModelClasses.CompleteNotes;
import com.tnc.studentlife.ModelClasses.CourseInformation;
import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.R;
import com.tnc.studentlife.StaticClass.SData;

import java.util.ArrayList;

public class NotesRecycleViewAdapter extends RecyclerView.Adapter<NotesRecycleViewAdapter.MyViewHolder> {
    ArrayList<CourseInformation> currentCourses;
    Context context;
    private int resources;

    public NotesRecycleViewAdapter(Context context, int notes_approve_card, ArrayList<CourseInformation> courses) {
        this.context=context;
        this.resources=notes_approve_card;
        this.currentCourses=courses;
    }

    @NonNull
    @Override
    public NotesRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecycleViewAdapter.MyViewHolder holder, final int position) {
        final CourseInformation current=currentCourses.get(position);
        holder.courseName.setText(current.getCoarseName());
        String notesToShow="";
        CompleteNotes completeNotes= new CompleteNotes();
        completeNotes.setCurrentNotes(current.getNotes().getCurrentNotes());
        if(current.getNotes()!=null){
            for(NoteInformation note:completeNotes.getCurrentNotes()){
                if(note.getHorizontalPosition()==0){
                    notesToShow=notesToShow+note.getNotesData()+"\n";
                    notesToShow=checkChild(note.getVerticalPosition(),current,notesToShow,completeNotes,0);
                }
                
            }
            holder.courseNote.setText(notesToShow);
        }
        
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CourseDetailActivity.class);
                intent.putExtra(context.getString(R.string.selectedCourse),current);
                SData.setCurrentCourse(position);
                context.startActivity(intent);
            }
        });
        
        

    }

    private String checkChild(int currentNoteId, CourseInformation current, String notesToShow, CompleteNotes completeNotes, int heirarchy) {
        String appendString="";
        for(int x=0;x<=heirarchy;x++){
            appendString=appendString+"|\t"+"\t";
        }
        for(NoteInformation notes:completeNotes.getChild(completeNotes.getCurrentNotes().get(currentNoteId))){
            {
                notesToShow=notesToShow+appendString+notes.getNotesData()+"\n";
                notesToShow=checkChild(notes.getVerticalPosition(),current,notesToShow, completeNotes, heirarchy+1);
            }
        }
        return notesToShow;
    }

    @Override
    public int getItemCount() {
        return currentCourses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        TextView courseNote;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNote=itemView.findViewById(R.id.notesNotesData);
            linearLayout=itemView.findViewById(R.id.courseLinearLayout);
            courseName=itemView.findViewById(R.id.notesCourseName);
        }
    }
}
