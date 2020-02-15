package ru.sergeykozhukhov.habits.base.model.domain;

public class Statistic {

    private long idHabit;
    private String title;
    private int duration;
    private int currentQuantity;

    public Statistic(String title, int duration, int currentQuantity) {
        this.title = title;
        this.duration = duration;
        this.currentQuantity = currentQuantity;
    }

    public Statistic(long idHabit, String title, int duration, int currentQuantity) {
        this.idHabit = idHabit;
        this.title = title;
        this.duration = duration;
        this.currentQuantity = currentQuantity;
    }

    public long getIdHabit() {
        return idHabit;
    }

    public void setIdHabit(long idHabit) {
        this.idHabit = idHabit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "idHabit=" + idHabit +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", currentQuantity=" + currentQuantity +
                '}';
    }
}
