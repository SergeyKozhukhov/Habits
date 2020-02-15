package ru.sergeykozhukhov.habits.base.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Relation;

public class StatisticData {

    @ColumnInfo(name = "id_habit")
    private long idHabit;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "duration")
    private int duration;

    @ColumnInfo(name = "current_quantity")
    private int currentQuantity;

    @Ignore
    public StatisticData(String title, int duration, int currentQuantity) {
        this.title = title;
        this.duration = duration;
        this.currentQuantity = currentQuantity;
    }

    public StatisticData(long idHabit, String title, int duration, int currentQuantity) {
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
}
