package ru.sergeykozhukhov.habits.base.domain.usecase;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ILoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;

public class LoadStatisticListInteractor implements ILoadStatisticListInteractor {

    private final IHabitsDatabaseRepository habitsRepository;

    public LoadStatisticListInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }


    @Override
    public Single<List<Statistic>> loadStatisticsList() {
        return habitsRepository.loadStatisticList();
    }
}
