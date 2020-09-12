package com.tnc.studentlife.Activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tnc.studentlife.AdapterHelper.SimpleItemTouchHelperCallback;
import com.tnc.studentlife.Adapters.NoteRecycleAdapter;
import com.tnc.studentlife.Adapters.SpinnerAdapter;
import com.tnc.studentlife.Interfaces.NotesChangedInterface;
import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.ModelClasses.PersonInformation;
import com.tnc.studentlife.R;
import com.tnc.studentlife.StaticClass.SData;

import java.util.ArrayList;
import java.util.Objects;

//import static com.tnc.studentlife.StaticClass.SData.userInformation;

public class CourseDetailActivity extends AppCompatActivity implements NotesChangedInterface {
    EditText courseName;
    ImageView courseOptions;
//    Spinner noteParentSpinner;
    RecyclerView notesDetails;
//    Button createNote;
    Boolean addDefaultNote;
    ArrayList<NoteInformation> notesToShow;
    PersonInformation userInformation;



//    //For Movement
//    private float x1,x2;
//    static final int MIN_DISTANCE = 150;

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
        courseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userInformation.getCurrentCourses().get(SData.getCurrentCourse()).setCoarseName(editable.toString());
                courseName.setText(editable.toString());
                SData.SaveToFile();
            }
        });

    }


//    private void AddNotes() {
//        showAddDialogue();
//    }

//    private void showAddDialogue() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailActivity.this);
//        final View customLayout = getLayoutInflater().inflate(R.layout.add_note_dialogue, null);
//        noteDataET = customLayout.findViewById(R.id.noteDataET);
//        noteParentSpinner = customLayout.findViewById(R.id.parentNoteSpinner);
//        fillSpinner();
//        createNote = customLayout.findViewById(R.id.createNoteBtn);
//        builder.setView(customLayout);
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//        createNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (noteDataET.getText().toString().equals("")) {
//                    noteDataET.setError("Please Enter Note Data");
//                    return;
//                }
//                NoteInformation nextNote = new NoteInformation();
//                nextNote.setNotesData(noteDataET.getText().toString());
//                nextNote.setHorizontalPosition(1);
//                nextNote.setVerticalPosition(1);
//                if (noteParentSpinner.getVisibility() != View.GONE) {
//                    if (noteParentSpinner.getSelectedItem().toString().equals(userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getCoarseName())) {
////                        nextNote.setParentId(0);
//                    }
//                }
//                int currentId = 1;
//                for (NoteInformation note : userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()) {
//                    if (note.getNotesData().equals(noteParentSpinner.getSelectedItem().toString())) {
////                        nextNote.setParentId(note.getVerticalPosition());
//                        nextNote.setHorizontalPosition(note.getHorizontalPosition()+1);
//                    }
//                    if (note.getVerticalPosition() >= currentId) {
//                        currentId = note.getVerticalPosition() + 1;
//                    }
//                }
//                nextNote.setVerticalPosition(currentId);
//                sortNotes(nextNote);
//                SData.SaveToFile();
//                updateView();
//                dialog.dismiss();
//            }
//        });
//
//    }
//
//    private void sortNotes(NoteInformation nextNote) {
//        ArrayList<NoteInformation> notes=SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes();
//        int x=0;
//        for(;x<notes.size();x++){
//
//        }
//        notes.add(x,nextNote);
//    }




    private void updateView() {
        notesToShow=new ArrayList<>();
        if (SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes().size()<1){
            addDefaultNote=true;
        }
        else{
            addDefaultNote=false;
        }
        for(NoteInformation note:SData.getUserInformation().getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()){
            if (note.isShow())
                notesToShow.add(note);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CourseDetailActivity.this);
        notesDetails.setHasFixedSize(true);
        notesDetails.setLayoutManager(layoutManager);
        NoteRecycleAdapter mycourseAdapter = new NoteRecycleAdapter(this, R.layout.note_recycle_view,
                notesToShow,addDefaultNote);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mycourseAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(notesDetails);
        notesDetails.setAdapter(mycourseAdapter);

    }


//    private void fillSpinner() {
//        ArrayList<String> toShow = new ArrayList<>();
//        for (NoteInformation note : userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getNotes().getCurrentNotes()) {
//            toShow.add(note.getNotesData());
//        }
//        ArrayAdapter<NoteInformation> childNameAdpater = new SpinnerAdapter(this,R.layout.spinner_single_list,notesToShow);
//        if (toShow.size() > 0) {
//            toShow.add(userInformation.getCurrentCourses().get(SData.getCurrentCourse()).getCoarseName());
//            noteParentSpinner.setAdapter(childNameAdpater);
//        } else
//            noteParentSpinner.setVisibility(View.GONE);
//    }
    private void setVariables() {
        courseName = findViewById(R.id.courseDetailName);
        courseOptions = findViewById(R.id.moreCourseOptions);
        notesDetails = findViewById(R.id.detailNotesRecycleView);
        userInformation=SData.getUserInformation();
    }

    @Override
    public void dataChanged() {
        updateView();
    }
}
