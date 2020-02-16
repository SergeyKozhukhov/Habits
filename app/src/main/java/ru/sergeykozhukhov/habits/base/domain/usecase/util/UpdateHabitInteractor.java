package ru.sergeykozhukhov.habits.base.domain.usecase.util;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.util.IUpdateHabitInreractor;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class UpdateHabitInteractor implements IUpdateHabitInreractor {

    private final IHabitsDatabaseRepository habitsDatabaseRepository;

    public UpdateHabitInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsDatabaseRepository = habitsRepository;
    }

    @NonNull
    @Override
    public Completable updateHabit(final Habit habit) {
        return habitsDatabaseRepository.updateHabit(habit);
    }
}
