package com.example.teren.myfitness;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE_KEY = "The cake is a lie.";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ShowExercise.class);
                intent.putExtra(MainActivity.MESSAGE_KEY, 0);
                startActivity(intent);
            }
        });
        //this.addExercisesTesting();

        this.fillListView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.fillListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void addExercisesTesting(){
        this.deleteDatabase("MyDatabase.db");
        MyFitnessDbHelper db = new MyFitnessDbHelper(this);
        Exercise exercise = new Exercise("Push Ups", "Upper Back", 3, 6, 0);
        Exercise exercise2 = new Exercise("Squats", "Legs", 4, 10, 10);
        Exercise exercise3 = new Exercise("Bench Press", "Upper Chest", 4, 10, 7.5);
        db.addExercise(exercise);
        db.addExercise(exercise2);
        db.addExercise(exercise3);

    }


    protected void fillListView(){
        MyFitnessDbHelper db = new MyFitnessDbHelper(this);
        final List<Exercise> exerciseList = db.getAllExercises();
        final ArrayAdapter adapter = new ArrayAdapter<Exercise>(this,R.layout.activity_listview,exerciseList);


        final ListView lv = (ListView)findViewById(R.id.exercisesList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Exercise ex = (Exercise)adapter.getItem(position);
                int real_id = ex.getId();
                Intent intent = new Intent(getApplicationContext(),ShowExercise.class);
                intent.putExtra(MainActivity.MESSAGE_KEY, real_id);
                startActivity(intent);

            }
        });
    }
}
