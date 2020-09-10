package com.tnc.studentlife.Activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import com.tnc.studentlife.Adapters.SpinnerAdapter;
import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.ModelClasses.PersonInformation;
import com.tnc.studentlife.R;
import com.tnc.studentlife.StaticClass.SData;

import java.util.ArrayList;
import java.util.Objects;

//import static com.tnc.studentlife.StaticClass.SData.userInformation;

public class CourseDetailActivity extends AppCompatActivity {
    EditText courseName, noteDataET,addNewNote;
    ImageView courseOptions;
    Spinner noteParentSpinner;
    RecyclerView notesDetails;
    Button createNote;
    ArrayList<NoteInformation> notesToShow;
    PersonInformation userInformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Notes");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setVariables();
        setValues();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setValues() {
        courseName.setText(userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getCoarseName());

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
                nextNote.setHorizontalPosition(1);
                nextNote.setVerticalPosition(1);
                if (noteParentSpinner.getVisibility() != View.GONE) {
                    if (noteParentSpinner.getSelectedItem().toString().equals(userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getCoarseName())) {
//                        nextNote.setParentId(0);
                    }
                }
                int currentId = 1;
                for (NoteInformation note : userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()) {
                    if (note.getNotesData().equals(noteParentSpinner.getSelectedItem().toString())) {
//                        nextNote.setParentId(note.getVerticalPosition());
                        nextNote.setHorizontalPosition(note.getHorizontalPosition()+1);
                    }
                    if (note.getVerticalPosition() >= currentId) {
                        currentId = note.getVerticalPosition() + 1;
                    }
                }
                nextNote.setVerticalPosition(currentId);
                sortNotes(nextNote);

//                userInformation.getCurrentCourses().get(SData.currentCourse).getNotes().add(nextNote);
                SData.SaveToFile();
                updateView();
                dialog.dismiss();
            }
        });

    }

    private void sortNotes(NoteInformation nextNote) {
        ArrayList<NoteInformation> notes=SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes();
        int x=0;
        for(;x<notes.size();x++){

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
        for(NoteInformation current:userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()){
//            if(current.getParentId()==0)
            if(current.getHorizontalPosition()==0)
                notesToShow.add(current);
        }
    }

    private void fillSpinner() {
        ArrayList<String> toShow = new ArrayList<>();
        for (NoteInformation note : userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()) {
            toShow.add(note.getNotesData());
        }
        ArrayAdapter<NoteInformation> childNameAdpater = new SpinnerAdapter(this,R.layout.spinner_single_list,notesToShow);
        if (toShow.size() > 0) {
            toShow.add(userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getCoarseName());
            noteParentSpinner.setAdapter(childNameAdpater);
        } else
            noteParentSpinner.setVisibility(View.GONE);
    }
    private void setVariables() {
        courseName = findViewById(R.id.courseDetailName);
        courseOptions = findViewById(R.id.moreCourseOptions);
        notesDetails = findViewById(R.id.detailNotesRecycleView);
        userInformation=SData.getUserInformation();
        addNewNote=findViewById(R.id.NewList);
    }

}
