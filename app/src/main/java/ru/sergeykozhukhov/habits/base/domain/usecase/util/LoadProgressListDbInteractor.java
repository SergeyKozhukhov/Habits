package ru.sergeykozhukhov.habits.base.domain.usecase.util;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.util.ILoadProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;
import ru.sergeykozhukhov.habits.base.model.exception.LoadDbException;

public class LoadProgressListDbInteractor implements ILoadProgressListDbInteractor {

    private final IHabitsDatabaseRepository habitsDatabaseRepository;

    public LoadProgressListDbInteractor(IHabitsDatabaseRepository habitsDatabaseRepository) {
        this.habitsDatabaseRepository = habitsDatabaseRepository;
    }

    @Override
    public Single<List<Progress>> loadProgressListByHabit(long idHabit) {
        return habitsDatabaseRepository.loadProgressListByIdHabit(idHabit)
                .onErrorResumeNext(throwable ->
                        Single.error(new LoadDbException(R.string.load_db_exception, throwable)));
    }
}
