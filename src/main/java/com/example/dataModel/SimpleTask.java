package com.example.dataModel;

public non-sealed class SimpleTask extends Task {
    private double startHour;
    private double endHour;

    public SimpleTask(String name, double startHour, double endHour) {
        super(name);
        this.startHour = startHour;
        this.endHour = endHour;
    }


    @Override
    public double estimateDuration() {
        return endHour - startHour;
    }

    public double getStartHour() {
        return startHour;
    }

    public void setStartHour(double startHour) {
        this.startHour = startHour;
    }

    public double getEndHour() {
        return endHour;
    }

    public void setEndHour(double endHour) {
        this.endHour = endHour;
    }

}
