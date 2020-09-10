package com.tnc.studentlife.Adapters;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.R;
import com.tnc.studentlife.StaticClass.SData;

import java.util.ArrayList;

public class NoteRecycleAdapter extends RecyclerView.Adapter<NoteRecycleAdapter.MyViewHolder> {

    Context context;
    int resourses;
    ArrayList<NoteInformation> notes;
    ArrayList<NoteInformation> toRemove;
    Boolean isShow;
    public NoteRecycleAdapter(Context courseDetailActivity, int notes_approve_card, ArrayList<NoteInformation> notes) {
        context = courseDetailActivity;
        resourses = notes_approve_card;
        this.notes = notes;
        toRemove=new ArrayList<>();
        isShow=true;

    }

    @NonNull
    @Override
    public NoteRecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(parent.getContext()).inflate(resourses, parent, false);
        return new NoteRecycleAdapter.MyViewHolder(myview);

    }

    @Override
    public void onBindViewHolder(@NonNull final NoteRecycleAdapter.MyViewHolder holder, final int position) {
        final Boolean[] check = {false};
        final NoteInformation note = notes.get(position);
        holder.noteDataET.setText(note.getNotesData());
        holder.childNotes.setPadding(30 * note.getHorizontalPosition()+1, 10, 0, 0);
        ArrayList<NoteInformation> childNotes=new ArrayList<>();
        for(NoteInformation child:SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()){
            if(child.getVerticalPosition()==note.getVerticalPosition()){
                childNotes.add(child);
            }
        }
        if(childNotes.size()>0){
            holder.showHide.setVisibility(View.VISIBLE);
        }
        NoteRecycleAdapter childAdapter=new NoteRecycleAdapter(context,resourses,childNotes);
        holder.childNotes.setAdapter(childAdapter);
        holder.childNotes.setLayoutManager(new LinearLayoutManager(context));
        holder.showHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShow){
                    isShow=false;
                    holder.showHide.setImageResource(R.drawable.ic_hide_note_24dp);
                    holder.childNotes.setVisibility(View.GONE);

                }
                else{
                    isShow=true;
                    holder.showHide.setImageResource(R.drawable.ic_show_24dp);
                    holder.childNotes.setVisibility(View.VISIBLE);
                    holder.childNotes.setPadding(30 * note.getHorizontalPosition()+1, 10, 0, 0);
                }
            }
        });


        holder.deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkToDeleteChild(note);
                deleteRemove();
                notes.remove(note);
                SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes().remove(note);
                SData.SaveToFile();
                NoteRecycleAdapter.this.notifyDataSetChanged();
            }
        });
        holder.noteDataET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (check[0]) {
                    check[0] = false;
                    if (!note.getNotesData().equals(holder.noteDataET.getText().toString())) {
                        note.setNotesData(holder.noteDataET.getText().toString());
                        SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes().set(position, note);
                        SData.SaveToFile();
                    }
                    holder.deleteNote.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "Focus change " + position, Toast.LENGTH_SHORT).show();
                }
                else {
                    holder.deleteNote.setVisibility(View.VISIBLE);
                    check[0] = true;
                }
            }
        });

        holder.noteDataET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(context, "Enter was pressed", Toast.LENGTH_SHORT).show();
                    if (!note.getNotesData().equals(holder.noteDataET.getText().toString())) {
                        for(NoteInformation currentNote:SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()){
                            if(note==currentNote){
                                currentNote.setNotesData(holder.noteDataET.getText().toString());
                            }
                        }
                        SData.SaveToFile();
                    }
                    holder.noteDataET.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }

    private void deleteRemove() {
        for(NoteInformation currentNote:toRemove){
            SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes().remove(currentNote);
        }
    }

    private void checkToDeleteChild(NoteInformation note) {
        for(NoteInformation Note:SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()){
            if(Note.getVerticalPosition()==note.getVerticalPosition())
            {
                checkToDeleteChild(Note);
                toRemove.add(Note);
            }
        }


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText noteDataET;
        ImageView deleteNote,showHide;
        RecyclerView childNotes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noteDataET = itemView.findViewById(R.id.showNoteDataET);
            deleteNote = itemView.findViewById(R.id.removeNote);
            childNotes = itemView.findViewById(R.id.child_notes_recycleview);
            showHide=itemView.findViewById(R.id.showHideChild);
        }
    }
}
