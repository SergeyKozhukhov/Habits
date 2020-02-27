package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.usecase.ILoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

/**
 * Реализация интерактора получения списка минимальной информации о привычках с соответствующим количеством выполненных дней
 */
public class LoadStatisticListInteractor implements ILoadStatisticListInteractor {

    /**
     * Репозиторий (база данных)
     */
    private final IHabitsDatabaseRepository habitsDatabaseRepository;

    public LoadStatisticListInteractor(IHabitsDatabaseRepository habitsDatabaseRepository) {
        this.habitsDatabaseRepository = habitsDatabaseRepository;
    }

    /**
     * Получение списка минимальной информации о привычках  и соответствующим количетсвом выполненных дней
     *
     * @return single со списком минимальной информации о привычках и соответствующим количестве выполненных дней
     */
    @NonNull
    @Override
    public Single<List<Statistic>> loadStatisticsList() {
        return habitsDatabaseRepository.loadStatisticList()
                .onErrorResumeNext(throwable ->
                        Single.error(new LoadDbException(R.string.load_db_exception, throwable)));
    }
}
