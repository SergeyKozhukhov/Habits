package ru.sergeykozhukhov.habits.base.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.data.ProgressData;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

@Dao
public interface ProgressDao {

    @Query("SELECT * FROM progresses WHERE id_habit = :idHabit")
    Single<List<ProgressData>> getProgressByHabit(long idHabit);

    @Query("SELECT * FROM progresses")
    Single<List<ProgressData>> getProgressList();

    @Insert
    long insertProgress(ProgressData progressData);

    @Insert
    void insertProgressList(List<ProgressData> progressDataList);

    @Update
    void updateProgress(ProgressData progressData);

    @Delete
    void deleteProgress(ProgressData progressData);

    @Delete
    void deleteProgressList(List<ProgressData> progressDataList);

}
