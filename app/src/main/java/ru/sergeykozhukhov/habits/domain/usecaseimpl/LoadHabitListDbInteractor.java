package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.usecase.ILoadHabitListDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

/**
 * Реализация интерактора получения списка привычек из базы данных
 */
public class LoadHabitListDbInteractor implements ILoadHabitListDbInteractor {

    /**
     * Репозиторий (база данных)
     */
    private final IHabitsDatabaseRepository habitsRepository;

    public LoadHabitListDbInteractor(@NonNull IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    /**
     * Получение списка привычек
     *
     * @return flowable со списком привычек
     */
    @NonNull
    @Override
    public Flowable<List<Habit>> loadHabitList() {
        return habitsRepository.loadHabitList()
                .onErrorResumeNext(throwable -> {
                    return Flowable.error(new LoadDbException(R.string.load_db_exception, throwable));
                });
    }
}
