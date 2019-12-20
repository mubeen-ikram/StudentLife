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
import com.tnc.studentlife.ModelClasses.CourseInformation;
import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.R;

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
        View myview = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new NotesRecycleViewAdapter.MyViewHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecycleViewAdapter.MyViewHolder holder, int position) {
        final CourseInformation current=currentCourses.get(position);
        holder.courseName.setText(current.getCoarseName());
        String notesToShow="";
        if(current.getNotes()!=null){
            for(NoteInformation note:current.getNotes()){
                if(note.getParentId()==0){
                    notesToShow=notesToShow+note.getNotesData();
                    notesToShow=checkChild(note.getCurrentNoteId(),current,notesToShow,0);
                }
                
            }
            holder.courseNote.setText(notesToShow);
        }
        
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CourseDetailActivity.class);
                intent.putExtra(context.getString(R.string.selectedCourse),current);
                context.startActivity(intent);
            }
        });
        
        

    }

    private String checkChild(int currentNoteId, CourseInformation current, String notesToShow, int heirarchy) {
        String appendString="";
        for(int x=0;x<=heirarchy;x++){
            appendString=appendString+"|\t"+"\t";
        }
        for(NoteInformation notes:current.getNotes()){
            if(notes.getParentId()==currentNoteId){
                notesToShow=notesToShow+"\n"+appendString+notes.getNotesData();
                notesToShow=checkChild(notes.getCurrentNoteId(),current,notesToShow,heirarchy+1);
            }
        }
        return notesToShow;
    }

    @Override
    public int getItemCount() {
        return currentCourses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
