package ru.sergeykozhukhov.habits.base.data.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.data.HabitData;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;

@Dao
public interface HabitDao {

    @Query("SELECT * FROM habits")
    Flowable<List<HabitData>> getHabitListFlowable();

    @Insert
    long insertHabit(HabitData habitData);

    @Insert
    void insertHabitList(List<HabitData> habitData);

    @Update
    void updateHabit(HabitData habitData);

    @Delete
    void deleteHabit(HabitData habitData);

    @Query("DELETE from habits")
    void deleteAll();

    @Transaction
    @Query("SELECT * from habits")
    Single<List<HabitWithProgressesData>> getHabitWithProgressesList();





}
