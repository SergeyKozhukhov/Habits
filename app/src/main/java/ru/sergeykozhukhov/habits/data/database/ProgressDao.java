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
     * Получение всех записей дат выполения по всем привычкам
     * @return
     */
    @Query("SELECT * FROM progresses")
    Single<List<ProgressData>> getProgressList();

    /**
     * Добавление записи о дате выполнения
     * @param progressData
     * @return
     */
    @Insert
    Completable insertProgress(ProgressData progressData);

    /**
     * Добавление списка записей о датах выполнения
     * @param progressDataList
     * @return
     */
    @Insert
    Completable insertProgressList(List<ProgressData> progressDataList);

    /**
     * Обновление записи о дате выполнения
     * @param progressData
     * @return
     */
    @Update
    Completable updateProgress(ProgressData progressData);

    /**
     * Удаление записи о дате выполнения
     * @param progressData
     */
    @Delete
    void deleteProgress(ProgressData progressData);

    /**
     * Удаление списка записей о датах выполнения
     * @param progressDataList
     */
    @Delete
    void deleteProgressList(List<ProgressData> progressDataList);

}
