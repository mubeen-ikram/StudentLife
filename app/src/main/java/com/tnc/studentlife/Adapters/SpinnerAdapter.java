package com.tnc.studentlife.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tnc.studentlife.ModelClasses.NoteInformation;
import com.tnc.studentlife.R;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<NoteInformation> {
    Context context;
    ArrayList<NoteInformation> notesInformation;
    LayoutInflater inflter;
    int resources;

    public SpinnerAdapter(@NonNull Context context, int resource, ArrayList<NoteInformation> notesToShow) {
        super(context, resource);
        this.notesInformation=notesToShow;
        this.context=context;
        this.resources=resource;
    }


    @Override
    public int getCount() {
        return notesInformation.size();
    }

    @Override
    public NoteInformation getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(resources, null);
        TextView names = (TextView) view.findViewById(R.id.input_text);
        names.setText(this.notesInformation.get(i).getNotesData());
        return view;
    }
}
