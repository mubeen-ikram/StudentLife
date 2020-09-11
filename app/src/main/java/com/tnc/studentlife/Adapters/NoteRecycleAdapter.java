package com.tnc.studentlife.Adapters;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tnc.studentlife.Interfaces.NotesChangedInterface;
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
    //Only allow when comming from courseDetailActivity otherwise the application will crash
    Boolean addDefaultNote;

    public NoteRecycleAdapter(Context courseDetailActivity, int notes_approve_card, ArrayList<NoteInformation> notes, Boolean addDefaultNote) {
        context = courseDetailActivity;
        resourses = notes_approve_card;
        this.notes = notes;
        toRemove = new ArrayList<>();
        isShow = true;
        this.addDefaultNote = addDefaultNote;

    }

    @NonNull
    @Override
    public NoteRecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(parent.getContext()).inflate(resourses, parent, false);
        return new NoteRecycleAdapter.MyViewHolder(myview);

    }

    @Override
    public void onBindViewHolder(@NonNull final NoteRecycleAdapter.MyViewHolder holder, final int position) {
        if (position < notes.size()) {
            final NoteInformation note = notes.get(position);
            holder.noteDataET.setText(note.getNotesData());
            holder.currentLayout.setPadding(30 * note.getHorizontalPosition() , 10, 0, 0);
            ArrayList<NoteInformation> childNotes = new ArrayList<>();
            for (NoteInformation child : SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()) {
                if (child.getHorizontalPosition() >= note.getHorizontalPosition()) break;
                if (child.getHorizontalPosition() + 1 == note.getHorizontalPosition()) {
                    childNotes.add(child);
                }
            }
            if (childNotes.size() > 0) {
                holder.showHide.setVisibility(View.VISIBLE);
            }
            NoteRecycleAdapter childAdapter = new NoteRecycleAdapter(context, resourses, childNotes, false);
            holder.showHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isShow) {
                        isShow = false;
                        holder.showHide.setImageResource(R.drawable.ic_hide_note);

                    } else {
                        isShow = true;
                        holder.showHide.setImageResource(R.drawable.ic_show_24dp);
//                        holder.childNotes.setVisibility(View.VISIBLE);
//                        holder.childNotes.setPadding(30 * note.getHorizontalPosition() + 1, 10, 0, 0);
                    }
                }
            });
            holder.makeChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    note.setHorizontalPosition(note.getHorizontalPosition()+1);
                    SData.SaveToFile();
                    NoteRecycleAdapter.this.notifyDataSetChanged();

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
                    notifyInterface();
                }
            });
//        holder.noteDataET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (check[0]) {
//                    check[0] = false;
//                    if (!note.getNotesData().equals(holder.noteDataET.getText().toString())) {
//                        note.setNotesData(holder.noteDataET.getText().toString());
//                        SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes().set(position, note);
//                        SData.SaveToFile();
//                    }
//                    holder.deleteNote.setVisibility(View.INVISIBLE);
//                    Toast.makeText(context, "Focus change " + position, Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    holder.deleteNote.setVisibility(View.VISIBLE);
//                    check[0] = true;
//                }
//            }
//        });

            holder.noteDataET.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (!note.getNotesData().equals(holder.noteDataET.getText().toString())) {
                        for (NoteInformation currentNote : SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()) {
                            if (note == currentNote) {
                                currentNote.setNotesData(holder.noteDataET.getText().toString());
                            }
                        }
                        SData.SaveToFile();
                    }
                    holder.noteDataET.clearFocus();
                    if (i == KeyEvent.KEYCODE_ENTER) {
                        NoteInformation noteInformation = new NoteInformation();
                        noteInformation.setVerticalPosition(note.getVerticalPosition() + 1);
                        noteInformation.setHorizontalPosition(note.getHorizontalPosition());
                        noteInformation.setNotesData(" ");
                        SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().addNote(noteInformation);
                        SData.SaveToFile();
                        NoteRecycleAdapter.this.notifyDataSetChanged();
                        notifyInterface();
                    }
                    return true;
//                if (i == KeyEvent.KEYCODE_ENTER) {
//                    ;
//                }
//                return false;
                }
            });
        } else {
            holder.currentLayout.setVisibility(View.GONE);
            holder.addNote.setVisibility(View.VISIBLE);
            holder.addNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteInformation note = new NoteInformation();
                    note.setNotesData(" ");
                    if (notes.size() == 0) {
                        note.setHorizontalPosition(0);
                        note.setVerticalPosition(0);
                    } else {
                        note.setHorizontalPosition(notes.get(notes.size() - 1).getHorizontalPosition());
                        note.setVerticalPosition(notes.get(notes.size() - 1).getVerticalPosition());
                    }
                    SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().addNote(note);
                    SData.SaveToFile();
                    NoteRecycleAdapter.this.notifyDataSetChanged();
                    notifyInterface();
                }
            });
        }
    }

    private void notifyInterface(){
        if (context instanceof NotesChangedInterface) {
            ((NotesChangedInterface) context).dataChanged();
        }
    }


    private void deleteRemove() {
        for (NoteInformation currentNote : toRemove) {
            SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes().remove(currentNote);
        }
    }

    private void checkToDeleteChild(NoteInformation note) {
        for (NoteInformation currentNote : SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()) {
            if (note.getHorizontalPosition() >= currentNote.getHorizontalPosition()) break;
            if (currentNote.getHorizontalPosition() > note.getHorizontalPosition()) {
                toRemove.add(currentNote);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (!addDefaultNote)
            return notes.size();
        return notes.size() + 1;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText noteDataET;
        ImageView deleteNote, showHide,makeChild;
        TextView addNote;
        LinearLayout currentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noteDataET = itemView.findViewById(R.id.showNoteDataET);
            deleteNote = itemView.findViewById(R.id.removeNote);
            makeChild=itemView.findViewById(R.id.makeCurrentChild);
            currentLayout=itemView.findViewById(R.id.currentNotesLayout);
            showHide = itemView.findViewById(R.id.showHideChild);
            addNote=itemView.findViewById(R.id.addNote);
        }
    }
}
