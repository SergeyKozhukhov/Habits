package ru.sergeykozhukhov.habits.base.domain.usecase;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IInsertHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class InsertWebHabitsInteractor implements IInsertHabitsInteractor {

    private final IHabitsWebRepository habitsWebRepository;

    public InsertWebHabitsInteractor(IHabitsWebRepository habitsWebRepository) {
        this.habitsWebRepository = habitsWebRepository;
    }


    @Override
    public Single<Long> insertHabits(List<Habit> habitList, String jwt) {
        return habitsWebRepository.insertHabits(habitList, jwt);
    }
}
