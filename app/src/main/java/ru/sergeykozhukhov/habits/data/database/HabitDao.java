package ru.sergeykozhukhov.habits.data.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.data.StatisticData;

/**
 * Интерфейс определяющий возможные операции с базой данных
 */
@Dao
public interface HabitDao {

    /**
     * Добавление записи о привычки
     *
     * @param habitData - привычка (data слой)
     * @return single с id добавленной привычки
     */
    @Insert
    Single<Long> insertHabit(HabitData habitData);

    /**
     * Добавление списка записей датах выполнения
     *
     * @param progressDataList список записей дат выполнения (data слой)
     */
    @Insert
    Completable insertProgressList(List<ProgressData> progressDataList);

    /**
     * Получение записей всех привычек
     *
     * @return
     */
    @Query("SELECT * FROM habits")
    Flowable<List<HabitData>> getHabitList();

    /**
     * Получение записей всех дат выполнения опредленной привычки
     *
     * @param idHabit id привычки
     * @return single со списком дат выполнения (data слой)
     */
    @Query("SELECT * FROM progresses WHERE id_habit = :idHabit")
    Single<List<ProgressData>> getProgressByHabit(long idHabit);

    /**
     * Получение записей всех привычек со списком соответстующей информации о датах выполнения
     *
     * @return single со списком записей всех привычек и соответствующей информации о датах выполнения (data слой)
     */
    @Transaction
    @Query("SELECT * from habits")
    Single<List<HabitWithProgressesData>> getHabitWithProgressesList();

    /**
     * Получение списка данных по привычкам с указанием количества выполненных дней
     *
     * @return single со списком данных по привычкам и указанием количества выполненных дней (data слой)
     */
    @Query("SELECT habits.id_habit, title, duration, COUNT (progresses.id_habit) as current_quantity " +
            "FROM habits INNER JOIN progresses ON habits.id_habit == progresses.id_habit " +
            "GROUP BY progresses.id_habit")
    Single<List<StatisticData>> getStatisticList();

    /**
     * Удаление всех записей о привычках
     */
    @Query("DELETE from habits")
    Completable deleteAll();

    /**
     * Удаление списка записей о датах выполнения (data слой)
     */
    @Delete
    Completable deleteProgressList(List<ProgressData> progressDataList);

}
