package com.tnc.studentlife.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.tnc.studentlife.Interfaces.ItemTouchHelperAdapter;
import com.tnc.studentlife.Interfaces.NotesChangedInterface;
import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.R;
import com.tnc.studentlife.StaticClass.SData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NoteRecycleAdapter extends RecyclerView.Adapter<NoteRecycleAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    Context context;
    int resourses;
    ArrayList<NoteInformation> notes;
    ArrayList<NoteInformation> toRemove;
    Boolean isShow;
    Boolean addDefaultNote;
    ArrayList<NoteInformation> mainArray=SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes();



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
        View myView = LayoutInflater.from(parent.getContext()).inflate(resourses, parent, false);
        return new NoteRecycleAdapter.MyViewHolder(myView);

    }

    @Override
    public void onBindViewHolder(@NonNull final NoteRecycleAdapter.MyViewHolder holder, final int position) {
        if (position < notes.size() && notes.get(position).isShow()) {
            final NoteInformation note = notes.get(position);
            if (SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getChild(note).size() > 0) {
                holder.showHide.setVisibility(View.VISIBLE);
                if (getChildValue(note)) {
                    holder.showHide.setImageResource(R.drawable.ic_hide_note_24dp);
                } else {
                    holder.showHide.setImageResource(R.drawable.ic_show_24dp);
                }
            } else {
                holder.showHide.setVisibility(View.INVISIBLE);
            }
            holder.noteDataET.setText(note.getNotesData());
            holder.currentLayout.setPadding(30 * note.getHorizontalPosition(), 0, 0, 0);
            holder.deleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkToDeleteChild(note);
                    deleteRemove();
                    SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes().remove(note);
                    sortNotes();
                    NoteRecycleAdapter.this.notifyDataSetChanged();
                    notifyInterface();
                }
            });
            holder.showHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setChildValue(note, getChildValue(note));

                    NoteRecycleAdapter.this.notifyDataSetChanged();
                    notifyInterface();

                }
            });
            holder.noteDataET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!note.getNotesData().equals(holder.noteDataET.getText().toString())) {
                        for (NoteInformation currentNote : SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()) {
                            if (note == currentNote) {
                                currentNote.setNotesData(holder.noteDataET.getText().toString());
                            }
                            break;
                        }
                        note.setNotesData(holder.noteDataET.getText().toString());
                        SData.SaveToFile();
                    }
                }
            });
//            holder.noteDataET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
////                @Override
////                public void onFocusChange(View view, boolean b) {
////                    if (!b) {
////                        holder.deleteNote.setVisibility(View.INVISIBLE);
////                    } else {
//////                        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//////                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//////                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
////                        view.requestFocus();
////                        holder.deleteNote.setVisibility(View.VISIBLE);
////
////                    }
////                }
////            });


            holder.noteDataET.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (i == KeyEvent.KEYCODE_ENTER) {
                        NoteInformation noteInformation = new NoteInformation();
                        noteInformation.setVerticalPosition(note.getVerticalPosition() + 1);
                        noteInformation.setHorizontalPosition(note.getHorizontalPosition());
                        noteInformation.setNotesData("");
                        SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().addNote(noteInformation);
                        sortNotes();
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        NoteRecycleAdapter.this.notifyDataSetChanged();
                        notifyInterface();
                    }
                    return true;
                }
            });
        } else {
            holder.currentLayout.setVisibility(View.GONE);
            holder.addNote.setVisibility(View.VISIBLE);
            holder.addNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteInformation note = new NoteInformation();
                    note.setNotesData("");
                    if (mainArray.size() == 0) {
                        note.setHorizontalPosition(0);
                        note.setVerticalPosition(0);
                    } else {
                        note.setHorizontalPosition(notes.get(notes.size() - 1).getHorizontalPosition());
                        note.setVerticalPosition(notes.get(notes.size() - 1).getVerticalPosition());
                    }
                    SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().addNote(note);
                    NoteRecycleAdapter.this.notifyDataSetChanged();
                    notifyInterface();
                }
            });
        }
    }

    private boolean getChildValue(NoteInformation note) {
        boolean initialValue = true;
        for (NoteInformation childNote : SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getChild(note)) {
            initialValue = childNote.isShow();
            break;
        }
        return !initialValue;
    }

    private void setChildValue(NoteInformation note, boolean value) {
        for (NoteInformation childNote : SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getChild(note)) {
            setChildValue(childNote, value);
            SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes().get(childNote.getVerticalPosition()).setShow(value);
            childNote.setShow(value);
        }
    }

    private void sortNotes() {
        int currentIndex = 0;
        for (NoteInformation noteInformation : SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()) {
            noteInformation.setVerticalPosition(currentIndex);
            currentIndex++;
        }

    }

    private void notifyInterface() {

        SData.SaveToFile();
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
        toRemove.addAll(SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getAllChild(note));
    }

    @Override
    public int getItemCount() {
        if (!addDefaultNote) {
            return notes.size();
        }
        return notes.size() + 1;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(notes, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(notes, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void itemDrag(int fromIndex, int toIndex) {
        //check k jis index pe ja rhy uski kia position hai
        if(fromIndex>=notes.size()&&notes.size()<=toIndex)return;
        if (!SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getAllChild(notes.get(fromIndex)).contains(notes.get(toIndex))) {
            //For movement from bottom to top
            //1.Get currentNode that needed to move and all its child
            //2.Delete the node and child from main notes list
            //3.Sort the notes but from index where to put the node,add count(node+child) to all entries under that index
            //4.get the horizontal position to start for the next note and put its value there
            //5.Set the horizontal position of the note+child array
            //5.Add these notes to respective place
            //step4 start
            int positionToHorizontal=notes.get(toIndex).getHorizontalPosition();
            //step4 end
            // Step1 start
            ArrayList<NoteInformation> notesFamilyToChange = new ArrayList<>();
            notesFamilyToChange.add(notes.get(fromIndex));
            notesFamilyToChange.addAll(SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getAllChild(notes.get(fromIndex)));
            //Step1 End
            //Step2 start
            checkToDeleteChild(notes.get(fromIndex));
            deleteRemove();
            SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes().remove(notesFamilyToChange.get(0));
            //Step2 end
            //Step3 start
            sortNotes();
            //Step3 end
            //step5 start
            int startHorizontal=notesFamilyToChange.get(0).getHorizontalPosition();
            int startVertical=notesFamilyToChange.get(0).getVerticalPosition();
            int differenceToHorizontal=startHorizontal-positionToHorizontal;
            int differenceToVertical=startVertical-toIndex;
            for(NoteInformation noteInformation:notesFamilyToChange){
                noteInformation.setHorizontalPosition(noteInformation.getHorizontalPosition()-differenceToHorizontal);
                noteInformation.setVerticalPosition(noteInformation.getVerticalPosition()-differenceToVertical);
            }
            Collections.sort(notesFamilyToChange, new Comparator<NoteInformation>() {
                @Override
                public int compare(NoteInformation noteInformation, NoteInformation t1) {
                    if(noteInformation.getVerticalPosition()>t1.getVerticalPosition())return 1;
                    else if(noteInformation.getVerticalPosition()==t1.getVerticalPosition())
                        return 0;
                    return -1;
                }
            });
            for(NoteInformation noteInformation:notesFamilyToChange){
                SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().addNote(noteInformation);
            }
        }
        NoteRecycleAdapter.this.notifyDataSetChanged();
        notifyInterface();
    }

    @Override
    public void onItemSwiped(int position, int direction) {
        if (position != 0) {
            if (direction == ItemTouchHelper.END) {
                if (notes.get(position - 1).getHorizontalPosition() >= notes.get(position).getHorizontalPosition()) {
                    increaseChildNotesNewHorizontalPlace(notes.get(position));
                    mainArray.get(position).setHorizontalPosition(mainArray.get(position).getHorizontalPosition()+1);
                }
            } else if (direction == ItemTouchHelper.START) {
                if (notes.get(position).getHorizontalPosition() != 0) {
                    decreaseChildNotesNewHorizontalPlace(notes.get(position));
                    mainArray.get(position).setHorizontalPosition(mainArray.get(position).getHorizontalPosition()-1);
                }
            }
        }
        NoteRecycleAdapter.this.notifyDataSetChanged();
        notifyInterface();
    }


    private void increaseChildNotesNewHorizontalPlace(NoteInformation parentNote) {
        for (NoteInformation childNote : SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getAllChild(parentNote)) {
            childNote.setHorizontalPosition(childNote.getHorizontalPosition() + 1);
        }
    }

    private void decreaseChildNotesNewHorizontalPlace(NoteInformation parentNote) {
        for (NoteInformation childNote : SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getAllChild(parentNote)) {
            childNote.setHorizontalPosition(childNote.getHorizontalPosition() - 1);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText noteDataET;
        ImageView deleteNote, showHide;
        TextView addNote;
        LinearLayout currentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noteDataET = itemView.findViewById(R.id.showNoteDataET);
            deleteNote = itemView.findViewById(R.id.removeNote);
            showHide = itemView.findViewById(R.id.showHideChild);
            currentLayout = itemView.findViewById(R.id.currentNotesLayout);
            addNote = itemView.findViewById(R.id.addNote);
        }
    }
}
