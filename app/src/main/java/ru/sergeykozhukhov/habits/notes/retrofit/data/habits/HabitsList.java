package ru.sergeykozhukhov.habits.notes.retrofit.data.habits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ru.sergeykozhukhov.habits.notes.database.habit.Habit;

public class HabitsList {
    @SerializedName("data")
    @Expose
    private ArrayList<Habit> habits = null;

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public void setHabits(ArrayList<Habit> habits) {
        this.habits = habits;
    }
}
