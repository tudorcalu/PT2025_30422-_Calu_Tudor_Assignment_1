package com.example.dataModel;

import java.util.ArrayList;
import java.util.List;

public final class ComplexTask extends Task{
    private List<Task> subTasks;

    public ComplexTask(String name) {
        super(name);
        subTasks = new ArrayList<>();
    }

    public void addSubTask(Task task) {
        subTasks.add(task);
    }

    public void removeSubTask(Task task) {
        subTasks.remove(task);
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }


    @Override
    public double estimateDuration() {
        double totalDuration = 0;
        for (Task task : subTasks) {
            totalDuration += task.estimateDuration();
        }
        return totalDuration;
    }

    public void updateStatusBasedOnSubtasks() {
        if (subTasks.isEmpty()) {

            return;
        }
        boolean allCompleted = true;
        for (Task subTask : subTasks) {
            if (!"Completed".equals(subTask.getStatus())) {
                allCompleted = false;
                break;
            }
        }
        if (allCompleted) {
            setStatus("Completed");
        } else {
            setStatus("Uncompleted");
        }
    }


}
