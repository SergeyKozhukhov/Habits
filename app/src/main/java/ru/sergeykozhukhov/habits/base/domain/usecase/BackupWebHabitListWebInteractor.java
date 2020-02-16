package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IBackupWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IGetJwtValue;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.exception.GetJwtException;
import ru.sergeykozhukhov.habits.base.model.exception.InsertWebException;

public class BackupWebHabitListWebInteractor implements IBackupWebInteractor {

    private final IHabitsWebRepository habitsWebRepository;
    private final IHabitsDatabaseRepository habitsDatabaseRepository;
    private final IGetJwtValue getJwtValue;

    public BackupWebHabitListWebInteractor(
            @NonNull IHabitsWebRepository habitsWebRepository,
            @NonNull IHabitsDatabaseRepository habitsDatabaseRepository,
            @NonNull IGetJwtValue getJwtValue) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsDatabaseRepository = habitsDatabaseRepository;
        this.getJwtValue = getJwtValue;
    }

    @NonNull
    @Override
    public Completable insertHabitWithProgressesList() {
        String jwt;
        try {
            jwt = getJwtValue.getValue();
        } catch (GetJwtException e) {
            return Completable.error(e);
        }

        List<HabitWithProgresses> habitWithProgressesList;
        try {
            habitWithProgressesList = habitsDatabaseRepository.loadHabitWithProgressesList()
                    .subscribeOn(Schedulers.io())
                    .blockingGet();
        } catch (Exception e) {
            return Completable.error(e);
        }
        return habitsWebRepository.insertHabitWithProgressesList(habitWithProgressesList, jwt)
                .onErrorResumeNext(throwable ->
                        Completable.error(new InsertWebException(R.string.insert_web_exception, throwable)));

    }

}
