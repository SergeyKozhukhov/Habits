package ru.sergeykozhukhov.habits.base.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.sergeykozhukhov.habits.base.model.data.HabitData;
import ru.sergeykozhukhov.habits.base.model.data.ProgressData;

@Database(entities = {HabitData.class, ProgressData.class}, version = 1)
public abstract class HabitsDatabase extends RoomDatabase {

    public abstract HabitDao getHabitDao();
    public abstract ProgressDao getProgressDao();

    private static HabitsDatabase habitsDatabase;
    private static final String DATABASE_NAME = "database.db";

    public static HabitsDatabase getInstance(Context context){
        if (null == habitsDatabase){
            habitsDatabase = buildInstance(context);
        }
        return habitsDatabase;
    }

    private static HabitsDatabase buildInstance(Context context){
        return Room.databaseBuilder(context,
                HabitsDatabase.class,
                DATABASE_NAME
        ).build();
    }

    public void cleanUp(){
        habitsDatabase = null;
    }

}
