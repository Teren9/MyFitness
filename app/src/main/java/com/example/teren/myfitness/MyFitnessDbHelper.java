package com.example.teren.myfitness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.AccessControlContext;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by teren on 17/07/16.
 */
public class MyFitnessDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final String TABLE_EXERCISES = "exercises";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BODY_PART = "body_part";
    private static final String KEY_NUMBER_OF_SETS = "number_of_sets";
    private static final String KEY_NUMBER_OF_REPS = "number_of_reps";
    private static final String KEY_WEIGHTS = "weights";
    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_BODY_PART,KEY_NUMBER_OF_SETS,KEY_NUMBER_OF_REPS,KEY_WEIGHTS};


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+TABLE_EXERCISES + "( " +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_NAME +" TEXT, " +
                    KEY_BODY_PART +" TEXT, " +
                    KEY_NUMBER_OF_SETS + " INTEGER, " +
                    KEY_NUMBER_OF_REPS + " INTEGER, " +
                    KEY_WEIGHTS + " REAL" +
                    ")";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS "+TABLE_EXERCISES;

    public MyFitnessDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addExercise(Exercise exercise){
        Log.d("add Exercise", exercise.getName());
        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();
        values.put(KEY_NAME, exercise.getName());
        values.put(KEY_BODY_PART, exercise.getBodyPart());
        values.put(KEY_NUMBER_OF_SETS, exercise.getNumberOfSets());
        values.put(KEY_NUMBER_OF_REPS, exercise.getNumberOfReps());
        values.put(KEY_WEIGHTS, exercise.getWeights());

        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }
    public Exercise getExercise(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_EXERCISES,
                        COLUMNS,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor!=null){
            cursor.moveToFirst();
        }

        Exercise exercise = new Exercise();
        exercise.setId(Integer.parseInt(cursor.getString(0)));
        exercise.setName(cursor.getString(1));
        exercise.setBodyPart(cursor.getString(2));
        exercise.setNumberOfSets(Integer.parseInt(cursor.getString(3)));
        exercise.setNumberOfReps(Integer.parseInt(cursor.getString(4)));
        exercise.setWeights(Double.parseDouble(cursor.getString(5)));

        Log.d("getExercise("+id+")", exercise.getName());
        db.close();
        return exercise;
    }
    public List<Exercise> getAllExercises(){
        List<Exercise> exercises = new LinkedList<Exercise>();
        String query = "SELECT * FROM " + TABLE_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Exercise exercise;
        if(cursor.moveToFirst()){
            do{
                exercise = new Exercise();
                exercise.setId(Integer.parseInt(cursor.getString(0)));
                exercise.setName(cursor.getString(1));
                exercise.setBodyPart(cursor.getString(2));
                exercise.setNumberOfSets(Integer.parseInt(cursor.getString(3)));
                exercise.setNumberOfReps(Integer.parseInt(cursor.getString(4)));
                exercise.setWeights(Double.parseDouble(cursor.getString(5)));

                exercises.add(exercise);
            }while(cursor.moveToNext());
        }
        db.close();
        return exercises;
    }
    public int updateExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, exercise.getName());
        values.put(KEY_BODY_PART, exercise.getBodyPart());
        values.put(KEY_NUMBER_OF_SETS, exercise.getNumberOfSets());
        values.put(KEY_NUMBER_OF_REPS, exercise.getNumberOfReps());
        values.put(KEY_WEIGHTS, exercise.getWeights());
        int i = db.update(TABLE_EXERCISES,values,KEY_ID +" = ?", new String[]{String.valueOf(exercise.getId())});
        Log.d("updated Exercise", exercise.getName());
        db.close();
        return i;
    }
    public void deleteExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXERCISES,KEY_ID + " = ?", new String[]{String.valueOf(exercise.getId())});
        db.close();
        Log.d("deleted Exercise", exercise.getName());
    }

}
