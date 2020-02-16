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

    /**
     * Получение записей всех привычек
     * @return
     */
    @Query("SELECT * FROM habits")
    Flowable<List<HabitData>> getHabitList();

    /**
     * Получение записей всех привычек со списком соответстующей информации о датах выполнения
     * @return
     */
    @Transaction
    @Query("SELECT * from habits")
    Single<List<HabitWithProgressesData>> getHabitWithProgressesList();

    /**
     * Получение записей всех привычек с указанием колличества дней выполнения
     * @return
     */
    @Query("SELECT habits.id_habit, title, duration, COUNT (progresses.id_habit) as current_quantity " +
            "FROM habits INNER JOIN progresses ON habits.id_habit == progresses.id_habit " +
            "GROUP BY progresses.id_habit")
    Single<List<StatisticData>> getStatisticList();

    /**
     * Добавление записи о привычки
     * @param habitData
     * @return
     */
    @Insert
    Single<Long> insertHabit(HabitData habitData);

    /**
     * Добавление списка записей о привычках
     * @param habitData
     * @return
     */
    @Insert
    Completable insertHabitList(List<HabitData> habitData);

    /**
     * Обновление записи о привычке
     * @param habitData
     * @return
     */
    @Update
    Completable updateHabit(HabitData habitData);

    /**
     * Удаление записи о привычке
     * @param habitData
     * @return
     */
    @Delete
    Completable deleteHabit(HabitData habitData);

    /**
     * Удаление всех записей о привычках
     */
    @Query("DELETE from habits")
    void deleteAll();







}
