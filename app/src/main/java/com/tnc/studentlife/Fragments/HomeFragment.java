package com.tnc.studentlife.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tnc.studentlife.Activities.ui.home.HomeViewModel;
import com.tnc.studentlife.Adapters.NotesRecycleViewAdapter;
import com.tnc.studentlife.ModelClasses.CourseInformation;
import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView notesRecycleView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        notesRecycleView = root.findViewById(R.id.notesRecycleView);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<CourseInformation> courses=new ArrayList<>();
        CourseInformation courseInformation=new CourseInformation();
        courseInformation.setCoarseName("Test Course");
        NoteInformation note=new NoteInformation();
        note.setCurrentNoteId(1);
        note.setParentId(0);
        note.setNotesData("This is test data");
        NoteInformation note2=new NoteInformation();
        note2.setParentId(1);
        note2.setCurrentNoteId(2);
        note2.setNotesData("This is child a long very long test data test data");
        NoteInformation note3=new NoteInformation();
        note3.setParentId(1);
        note3.setCurrentNoteId(3);
        note3.setNotesData("This is child2 test data");
        NoteInformation note4=new NoteInformation();
        note4.setParentId(2);
        note4.setCurrentNoteId(4);
        note4.setNotesData("This is child child test data");
        courseInformation.getNotes().add(note);
        courseInformation.getNotes().add(note2);
        courseInformation.getNotes().add(note3);
        courseInformation.getNotes().add(note4);
        courses.add(courseInformation);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        notesRecycleView.setHasFixedSize(true);
        notesRecycleView.setLayoutManager(layoutManager);
        NotesRecycleViewAdapter mycourseAdapter= new NotesRecycleViewAdapter(getContext(), R.layout.notes_approve_card,courses);
        notesRecycleView.setAdapter(mycourseAdapter);


    }
}