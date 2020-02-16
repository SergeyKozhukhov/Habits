package ru.sergeykozhukhov.habits.base.domain.usecase.util;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.util.IInsertProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;
import ru.sergeykozhukhov.habits.base.model.exception.InsertDbException;

public class InsertProgressListDbInteractor implements IInsertProgressListDbInteractor {

    private final IHabitsDatabaseRepository habitsDatabaseRepository;

    public InsertProgressListDbInteractor(IHabitsDatabaseRepository habitsDatabaseRepository) {
        this.habitsDatabaseRepository = habitsDatabaseRepository;
    }

    @Override
    public Completable insertProgressList(List<Progress> progressList) {
        return habitsDatabaseRepository.insertProgressList(progressList)
                .onErrorResumeNext(throwable ->
                        Completable.error(new InsertDbException(R.string.insert_db_exception, throwable)));
    }
}
