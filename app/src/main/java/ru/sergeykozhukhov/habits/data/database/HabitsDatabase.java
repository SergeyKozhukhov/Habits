package ru.sergeykozhukhov.habits.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.model.data.ProgressData;


/**
 * Singleton класс, подготавливающий и настраивающий работу с базой данных
 */
@Database(entities = {HabitData.class, ProgressData.class}, version = 1)
public abstract class HabitsDatabase extends RoomDatabase {

    private static HabitsDatabase habitsDatabase;

    public abstract HabitDao getHabitDao();

    /**
     * Название базы данных
     */
    private static final String DATABASE_NAME = "database.db";

    public static HabitsDatabase getInstance(Context context) {
        if (null == habitsDatabase) {
            habitsDatabase = buildInstance(context);
        }
        return habitsDatabase;
    }

    private static HabitsDatabase buildInstance(Context context) {
        return Room.databaseBuilder(context,
                HabitsDatabase.class,
                DATABASE_NAME
        ).build();
    }
}
