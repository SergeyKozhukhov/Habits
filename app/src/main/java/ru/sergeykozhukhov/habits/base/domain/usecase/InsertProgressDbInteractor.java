package ru.sergeykozhukhov.habits.base.domain.usecase;

import java.util.HashMap;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IInsertProgressDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public class InsertProgressDbInteractor implements IInsertProgressDbInteractor {

    private final IHabitsDatabaseRepository habitsDatabaseRepository;

    public InsertProgressDbInteractor(IHabitsDatabaseRepository habitsDatabaseRepository) {
        this.habitsDatabaseRepository = habitsDatabaseRepository;
    }

    @Override
    public Completable insertProgress(Progress progress) {
        return habitsDatabaseRepository.insertProgress(progress);
    }
}
