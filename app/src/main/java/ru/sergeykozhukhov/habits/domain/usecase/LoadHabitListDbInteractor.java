package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.ILoadHabitListDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

public class LoadHabitListDbInteractor implements ILoadHabitListDbInteractor {

    private final IHabitsDatabaseRepository habitsRepository;

    public LoadHabitListDbInteractor(@NonNull IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @NonNull
    @Override
    public Flowable<List<Habit>> loadHabitList() {
        return habitsRepository.loadHabitList()
                .onErrorResumeNext(throwable -> {
                    return Flowable.error(new LoadDbException(R.string.load_db_exception, throwable));
                });
    }

}
