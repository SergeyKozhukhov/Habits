package ru.sergeykozhukhov.habits.notes.fragments;

import ru.sergeykozhukhov.habits.notes.database.habit.Habit;

public interface OnItemClickListener {
    void onItemClick(Habit habit);
}
