package ru.sergeykozhukhov.habits.notes.database;

import android.content.Context;

import androidx.room.Room;

public class Database {
    public static Database instance;

    private HabitsDatabase database;

    public void newInstance(Context context) {
        instance = this;
        database = Room.databaseBuilder(context, HabitsDatabase.class, "database")
                .build();
    }

    public static Database getInstance() {
        return instance;
    }

    public HabitsDatabase getDatabase() {
        return database;
    }
}
