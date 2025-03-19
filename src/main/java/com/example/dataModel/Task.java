package com.example.dataModel;

import java.io.Serializable;

sealed public abstract class Task implements Serializable permits SimpleTask, ComplexTask{
    private static int idCounter = 1;
    protected int idTask;
    private String name;
    private String status;

    public Task(String name) {
        this.idTask = idCounter++;
        this.name = name;
        this.status = "Uncompleted";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public abstract double estimateDuration();

    public int getIdTask() {
        return idTask;
    }
}
