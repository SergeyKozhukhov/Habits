package ru.sergeykozhukhov.habits.base.data.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.data.model.HabitData;

@Dao
public interface HabitDao {

    @Query("SELECT * FROM habits")
    List<HabitData> getAll();

    @Query("SELECT * FROM habits")
    Flowable<List<HabitData>> getHabits();

    @Insert
    void insert(HabitData habitData);

    @Insert
    long insertHabit(HabitData habitData);

    @Insert
    void insert(List<HabitData> habitData);

    @Update
    void update(HabitData habitData);

    @Delete
    void delete(HabitData habitData);

    @Query("DELETE from habits")
    void deleteAllHabits();

}
