package ru.sergeykozhukhov.habits.base.data.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.data.HabitData;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.data.StatisticData;

@Dao
public interface HabitDao {


    @Query("SELECT * FROM habits")
    Flowable<List<HabitData>> getHabitList();

    @Transaction
    @Query("SELECT * from habits")
    Single<List<HabitWithProgressesData>> getHabitWithProgressesList();

    @Query("SELECT habits.id_habit, title, duration, COUNT (progresses.id_habit) as current_quantity " +
            "FROM habits INNER JOIN progresses ON habits.id_habit == progresses.id_habit " +
            "GROUP BY progresses.id_habit")
    Single<List<StatisticData>> getStatisticList();

    @Insert
    long insertHabit(HabitData habitData);

    @Insert
    Completable insertHabitList(List<HabitData> habitData);

    @Update
    void updateHabit(HabitData habitData);

    @Delete
    void deleteHabit(HabitData habitData);

    @Query("DELETE from habits")
    void deleteAll();







}
