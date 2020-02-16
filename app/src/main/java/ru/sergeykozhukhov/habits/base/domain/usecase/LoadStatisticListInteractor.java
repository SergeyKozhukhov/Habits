package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ILoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;
import ru.sergeykozhukhov.habits.base.model.exception.LoadDbException;

public class LoadStatisticListInteractor implements ILoadStatisticListInteractor {

    private final IHabitsDatabaseRepository habitsRepository;

    public LoadStatisticListInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @NonNull
    @Override
    public Single<List<Statistic>> loadStatisticsList() {
        return habitsRepository.loadStatisticList()
                .onErrorResumeNext(throwable ->
                        Single.error(new LoadDbException(R.string.load_db_exception, throwable)));
    }
}
