package ru.sergeykozhukhov.habits.notes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.sergeykozhukhov.habits.notes.database.habit.Habit;
import ru.sergeykozhukhov.habits.notes.database.habit.HabitDao;
import ru.sergeykozhukhov.habits.notes.database.progress.Progress;
import ru.sergeykozhukhov.habits.notes.database.progress.ProgressDao;
import ru.sergeykozhukhov.habits.notes.database.user.User;
import ru.sergeykozhukhov.habits.notes.database.user.UserDao;

@Database(entities = {User.class, Habit.class, Progress.class}, version = 1)
public abstract class HabitsDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
    public abstract HabitDao getHabitDao();
    public abstract ProgressDao getProgressDao();

    private static HabitsDatabase habitsDB;
    private static final String HABITS_DB_NAME = "database.db";

    public static HabitsDatabase getInstance(Context context){
        if (null == habitsDB){
            habitsDB = buildDatabaseInstance(context);
        }
        return habitsDB;
    }

    private static HabitsDatabase buildDatabaseInstance(Context context){
        return Room.databaseBuilder(context,
                HabitsDatabase.class,
                HABITS_DB_NAME
                ).build();
    }

    public void cleanUp(){
        habitsDB = null;
    }

}




