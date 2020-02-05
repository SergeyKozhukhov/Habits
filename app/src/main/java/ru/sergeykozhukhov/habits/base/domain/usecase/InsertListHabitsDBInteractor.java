package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IInsertListHabitsDBInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class InsertListHabitsDBInteractor implements IInsertListHabitsDBInteractor {

    private final IHabitsDatabaseRepository habitsRepository;


    public InsertListHabitsDBInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Single<Long> insertListHabitsDb(List<Habit> habitList) {
        return habitsRepository.insertListHabits(habitList);
    }
}
