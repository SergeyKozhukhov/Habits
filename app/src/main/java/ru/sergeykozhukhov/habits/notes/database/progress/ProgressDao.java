package ru.sergeykozhukhov.habits.notes.database.progress;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
@Dao
public interface ProgressDao {

    @Query("SELECT * FROM Progress")
    List<Progress> getAll();

    @Query("SELECT * FROM Progress")
    Flowable<List<Progress>> getProgresses();

    @Query("SELECT * FROM Progress WHERE id_habit = :id_habit")
    List<Progress> getByHabit(long id_habit);

    @Query("SELECT * FROM Progress WHERE id_habit = :id_habit")
    Flowable<List<Progress>> getByHabitId(long id_habit);
    //Flowable<List<Progress>> getByHabitId(long id_habit);

    @Insert
    void insert(Progress progress);

    @Insert
    void insert(List<Progress> progresses);

    @Update
    void update(Progress progress);

    @Delete
    void delete(Progress progress);
}
