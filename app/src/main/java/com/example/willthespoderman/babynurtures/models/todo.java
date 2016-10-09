package com.example.willthespoderman.babynurtures.models;

/**
 * Created by WILL THE SPODERMAN on 9/30/2015.
 */
public class todo {

    private String startTime, endTime, tasktodo;
    private int Id;

    public todo(){}

    public todo(String startTime, String endTime, String tasktodo, int id){

        this.startTime = startTime;
        this.endTime = endTime;
        this.tasktodo = tasktodo;
        Id = id;


    }

    //setters

    public void setId(int id){}

    public void setStartTime(String startTime){this.startTime = startTime;}

    public void setEndTime(String endTime){this.endTime = endTime;}

    public void setTask(String task){this.tasktodo = task;}


    //getters

    public String getStartTime(){return startTime;}

    public String getEndTime(){return endTime;}

    public String getTask(){return tasktodo;}

}
