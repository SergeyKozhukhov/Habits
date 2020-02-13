package ru.sergeykozhukhov.habits.base.domain.usecase.util;

import java.util.List;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.util.IInsertProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public class InsertProgressListDbInteractor implements IInsertProgressListDbInteractor {

    private final IHabitsDatabaseRepository habitsDatabaseRepository;

    public InsertProgressListDbInteractor(IHabitsDatabaseRepository habitsDatabaseRepository) {
        this.habitsDatabaseRepository = habitsDatabaseRepository;
    }

    @Override
    public Completable insertProgressList(List<Progress> progressList) {
        return habitsDatabaseRepository.insertProgressList(progressList);
    }
}
