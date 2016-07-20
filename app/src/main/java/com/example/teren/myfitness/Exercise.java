package com.example.teren.myfitness;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static java.security.AccessController.getContext;

/**
 * Created by teren on 16/07/16.
 */
public class Exercise {
    private int id;
    private String name;
    private String bodyPart;
    private int numberOfSets;
    private int numberOfReps;
    private double weights;

    public void setId(int id){
        this.id= id;
    }
    public int getId(){ return this.id; }
    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setBodyPart(String bodyPart){
        this.bodyPart = bodyPart;
    }
    public String getBodyPart(){
        return this.bodyPart;
    }
    public void setNumberOfSets(int numberOfSets){
        this.numberOfSets = numberOfSets;
    }
    public int getNumberOfSets(){
        return this.numberOfSets;
    }
    public void setNumberOfReps(int numberOfReps){
        this.numberOfReps= numberOfReps;
    }
    public int getNumberOfReps() { return this.numberOfReps; }
    public void setWeights(double weights){
        this.weights= weights;
    }
    public double getWeights(){
        return this.weights;
    }

    Exercise(){
        this.name = "";
        this.bodyPart = "";
        this.numberOfSets = 0;
        this.numberOfReps = 0;
        this.weights = 0.0;
    }
    Exercise(String name, String bodyPart, int numberOfSets,int numberOfReps, double weights){
        this.name = name;
        this.bodyPart = bodyPart;
        this.numberOfSets = numberOfSets;
        this.numberOfReps = numberOfReps;
        this.weights = weights;
    }
    @Override
    public String toString(){
        return this.getName() + " - " + this.getBodyPart();
    }


}
