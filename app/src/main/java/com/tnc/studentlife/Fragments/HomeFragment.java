package com.tnc.studentlife.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tnc.studentlife.Adapters.NotesRecycleViewAdapter;
import com.tnc.studentlife.R;
import com.tnc.studentlife.StaticClass.SData;

public class HomeFragment extends Fragment {
    RecyclerView notesRecycleView;
    Boolean check=true;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        notesRecycleView = root.findViewById(R.id.notesRecycleView);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        notesRecycleView.setHasFixedSize(true);
//        notesRecycleView.setLayoutManager(layoutManager);
//        notesRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        notesRecycleView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        NotesRecycleViewAdapter mycourseAdapter= new NotesRecycleViewAdapter(getContext(), R.layout.notes_approve_card, SData.getUserInformation().getCurrentCourses());
        notesRecycleView.setAdapter(mycourseAdapter);

    }

}