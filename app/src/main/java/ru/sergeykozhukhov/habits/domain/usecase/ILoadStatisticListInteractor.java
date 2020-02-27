package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

/**
 * Интерфейс интерактора получения списка минимальной информации о привычках с соответствующим количеством выполненных дней
 */
public interface ILoadStatisticListInteractor {

    /**
     * Получение списка минимальной информации о привычках  и соответствующим количетсвом выполненных дней
     *
     * @return single со списком минимальной информации о привычках и соответствующим количестве выполненных дней
     */
    @NonNull
    Single<List<Statistic>> loadStatisticsList();


}
