package ru.sergeykozhukhov.habits.notes.database.habit;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface HabitDao {

    @Query("SELECT * FROM Habit")
    List<Habit> getAll();

    @Query("SELECT * FROM Habit")
    Flowable<List<Habit>> getHabits();

    /*@Query("SELECT * FROM HabitData WHERE id_user = :id")
    List<HabitData> getById(long id);*/

    @Insert
    void insert(Habit habit);

    @Insert
    void insert(List<Habit> employees);

    @Update
    void update(Habit habit);

    @Delete
    void delete(Habit habit);

}