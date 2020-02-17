package ru.sergeykozhukhov.habits.domain.IInreractor;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

/**
 * Интерфейс интерактора получения списка информации о привычке с количеством выполненных дней
 */
public interface ILoadStatisticListInteractor {

    @NonNull
    Single<List<Statistic>> loadStatisticsList();


}
