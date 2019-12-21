package com.tnc.studentlife.Activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.tnc.studentlife.Adapters.NoteRecycleAdapter;
import com.tnc.studentlife.ModelClasses.CourseInformation;
import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.R;
import com.tnc.studentlife.StaticClass.SData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import static com.tnc.studentlife.StaticClass.SData.userInformation;
import static java.security.AccessController.getContext;

public class CourseDetailActivity extends AppCompatActivity {
    EditText courseName, noteDataET;
    ImageView courseOptions;
    Spinner noteParentSpinner;
    RecyclerView notesDetails;
    Button createNote;
    int indexToMove;
    ArrayList<NoteInformation> notesToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Notes");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setVariables();
        setValues();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNotes();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setValues() {
        courseName.setText(userInformation.getCurrentCourses().get(SData.currentCourse).getCoarseName());
        updateView();
    }


    private void AddNotes() {
        showAddDialogue();
    }

    private void showAddDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailActivity.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.add_note_dialogue, null);
//        customLayout.setBackground(getDrawable(R.color.colorCardBackground));
        noteDataET = customLayout.findViewById(R.id.noteDataET);
        noteParentSpinner = customLayout.findViewById(R.id.parentNoteSpinner);
        fillSpinner();
        createNote = customLayout.findViewById(R.id.createNoteBtn);
        builder.setView(customLayout);
        final AlertDialog dialog = builder.create();
        dialog.show();
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noteDataET.getText().toString().equals("")) {
                    noteDataET.setError("Please Enter Note Data");
                    return;
                }
                NoteInformation nextNote = new NoteInformation();
                nextNote.setNotesData(noteDataET.getText().toString());
                nextNote.setParentId(0);
                nextNote.setPosition(1);
                if (noteParentSpinner.getVisibility() != View.GONE) {
                    if (noteParentSpinner.getSelectedItem().toString().equals(userInformation.getCurrentCourses().get(SData.currentCourse).getCoarseName())) {
                        nextNote.setParentId(0);
                    }
                }
                int currentId = 1;
                for (NoteInformation note : userInformation.getCurrentCourses().get(SData.currentCourse).getNotes()) {
                    if (note.getNotesData().equals(noteParentSpinner.getSelectedItem().toString())) {
                        nextNote.setParentId(note.getCurrentNoteId());
                        nextNote.setPosition(note.getPosition()+1);
                    }
                    if (note.getCurrentNoteId() >= currentId) {
                        currentId = note.getCurrentNoteId() + 1;
                    }
                }
                nextNote.setCurrentNoteId(currentId);
                sortNotes(nextNote);

//                userInformation.getCurrentCourses().get(SData.currentCourse).getNotes().add(nextNote);
                SData.SaveToFile();
                updateView();
                dialog.dismiss();
            }
        });

    }

    private void sortNotes(NoteInformation nextNote) {
        ArrayList<NoteInformation> notes=SData.userInformation.getCurrentCourses().get(SData.currentCourse).getNotes();
        int x=0;
        for(;x<notes.size();x++){
            if(nextNote.getParentId()==notes.get(x).getCurrentNoteId()){
                x++;
                break;
            }
        }
        notes.add(x,nextNote);
    }




    private void updateView() {
        setInitialNotes();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CourseDetailActivity.this);
        notesDetails.setHasFixedSize(true);
        notesDetails.setLayoutManager(layoutManager);
        NoteRecycleAdapter mycourseAdapter = new NoteRecycleAdapter(this, R.layout.note_recycle_view,
                notesToShow);
        notesDetails.setAdapter(mycourseAdapter);
    }

    private void setInitialNotes() {
        notesToShow=new ArrayList<>();
        for(NoteInformation current:userInformation.getCurrentCourses().get(SData.currentCourse).getNotes()){
            if(current.getParentId()==0)
                notesToShow.add(current);
        }
    }

    private void fillSpinner() {
        ArrayList<String> toShow = new ArrayList<>();
        for (NoteInformation note : userInformation.getCurrentCourses().get(SData.currentCourse).getNotes()) {
            toShow.add(note.getNotesData());
        }
        ArrayAdapter<String> childNameAdpater = new ArrayAdapter<String>(this,
                R.layout.parent_note_spinner, toShow);
        if (toShow.size() > 0) {
            toShow.add(userInformation.getCurrentCourses().get(SData.currentCourse).getCoarseName());
            noteParentSpinner.setAdapter(childNameAdpater);
        } else
            noteParentSpinner.setVisibility(View.GONE);
    }
    private void setVariables() {
        courseName = findViewById(R.id.courseDetailName);
        courseOptions = findViewById(R.id.moreCourseOptions);
        notesDetails = findViewById(R.id.detailNotesRecycleView);
    }

}
