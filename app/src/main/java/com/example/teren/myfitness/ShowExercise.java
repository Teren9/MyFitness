package com.example.teren.myfitness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by teren on 17/07/16.
 */
public class ShowExercise extends Activity {
    private int id;
    private Exercise exercise;
    private MyFitnessDbHelper db;
    private TextView name_text, sets_text, reps_text, weights_text,body_part_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_exercise);
        this.db = new MyFitnessDbHelper(this);
        Intent intent = getIntent();
        this.id = intent.getIntExtra(MainActivity.MESSAGE_KEY, 0);

        this.name_text = (TextView)findViewById(R.id.exercise_name);
        this.sets_text = (TextView)findViewById(R.id.sets_value);
        this.reps_text = (TextView)findViewById(R.id.reps_value);
        this.weights_text = (TextView)findViewById(R.id.weights_value);
        this.body_part_text = (TextView)findViewById(R.id.body_part);

        this.exercise = new Exercise();

        if ( id!= 0 ) {
            this.exercise = this.db.getExercise(this.id);
            this.sets_text.setText(String.valueOf(this.exercise.getNumberOfSets()));
            this.name_text.setText(this.exercise.getName());
            this.reps_text.setText(String.valueOf(this.exercise.getNumberOfReps()));
            this.weights_text.setText(String.valueOf(this.exercise.getWeights()));
            this.body_part_text.setText(this.exercise.getBodyPart());
        }


    }

    public void onClickSave(View view) {
        exercise.setName(String.valueOf(this.name_text.getText()));
        exercise.setBodyPart(String.valueOf(this.body_part_text.getText()));
        if (!String.valueOf(this.reps_text.getText()).isEmpty())
            exercise.setNumberOfReps(Integer.parseInt(String.valueOf(this.reps_text.getText())));
        if (!String.valueOf(this.sets_text.getText()).isEmpty())
            exercise.setNumberOfSets(Integer.parseInt(String.valueOf(this.sets_text.getText())));
        if (!String.valueOf(this.weights_text.getText()).isEmpty())
            exercise.setWeights(Double.parseDouble(String.valueOf(this.weights_text.getText())));

        if(id==0)
            db.addExercise(exercise);
        else
            db.updateExercise(exercise);
        this.finish();
    }
    public void onClickDelete(View view) {
        if(id!=0)
            db.deleteExercise(exercise);
        this.finish();
    }
}
