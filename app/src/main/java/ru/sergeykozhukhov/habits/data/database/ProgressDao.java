package ru.sergeykozhukhov.habits.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.data.ProgressData;

@Dao
public interface ProgressDao {

    /**
     * Получение записей всех дат выполнения опредленной привычки
     * @param idHabit
     * @return
     */
    @Query("SELECT * FROM progresses WHERE id_habit = :idHabit")
    Single<List<ProgressData>> getProgressByHabit(long idHabit);

    /**
     * Добавление списка записей о датах выполнения
     * @param progressDataList
     * @return
     */
    @Insert
    Completable insertProgressList(List<ProgressData> progressDataList);

    /**
     * Удаление списка записей о датах выполнения
     * @param progressDataList
     */
    @Delete
    void deleteProgressList(List<ProgressData> progressDataList);

}
