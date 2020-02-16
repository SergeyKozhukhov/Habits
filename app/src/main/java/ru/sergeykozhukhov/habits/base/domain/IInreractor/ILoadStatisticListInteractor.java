package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;

/**
 * Интерфейс интерактора получения списка информации о привычке с количеством выполненных дней
 */
public interface ILoadStatisticListInteractor {

    @NonNull
    Single<List<Statistic>> loadStatisticsList();


}
