package com.tnc.studentlife.Activities;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.tnc.studentlife.ModelClasses.CourseInformation;
import com.tnc.studentlife.R;
import com.tnc.studentlife.StaticClass.SData;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    EditText courseNameET,courseInstructorEt,courseHoursEt;
    Button makeCourse;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer_activty);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                checkFragment();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        SData.setMyPreference(getApplicationContext());
        SData.getFromFile();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkFragment() {
        if(navController.getCurrentDestination().getId()==R.id.nav_home){
            showAddCourseDialogue();
        }
        else{
            Toast.makeText(MainDrawerActivity.this,"Your are not at home fragment",Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showAddCourseDialogue() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainDrawerActivity.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.add_course_dialoguebox, null);
        courseNameET=customLayout.findViewById(R.id.courseNameET);
        courseInstructorEt=customLayout.findViewById(R.id.courseInstructorET);
        courseHoursEt=customLayout.findViewById(R.id.courseHoursET);
        makeCourse=customLayout.findViewById(R.id.createCourseBtn);
        builder.setView(customLayout);
        final AlertDialog dialog = builder.create();
        dialog.show();
        makeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(courseNameET.getText().toString().equals("")){
                    courseNameET.setError("Please Enter course  Name");
                    return;
                }
                if(courseInstructorEt.getText().toString().equals("")){
                    courseInstructorEt.setError("Please Enter Instructor  Name");
                    return;
                }
                if(courseHoursEt.getText().toString().equals("")){
                    courseHoursEt.setError("Please Enter Course Hours");
                    return;
                }
                CourseInformation extraCourse=new CourseInformation();
                extraCourse.setCoarseName(courseNameET.getText().toString());
                extraCourse.setInstructorName(courseInstructorEt.getText().toString());
                extraCourse.setClassHours(Float.valueOf(courseHoursEt.getText().toString()));
                extraCourse.setCreditHours(3);
                SData.getUserInformation().getCurrentCourses().add(extraCourse);
                SData.SaveToFile();
                navController.navigate(R.id.nav_home);
                dialog.dismiss();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer_activty, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
