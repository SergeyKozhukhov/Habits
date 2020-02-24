package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IBackupWebInteractor;
import ru.sergeykozhukhov.habits.domain.IInreractor.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.domain.exception.BackupException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

public class BackupWebHabitListWebInteractor implements IBackupWebInteractor {

    private final IHabitsWebRepository habitsWebRepository;
    private final IHabitsDatabaseRepository habitsDatabaseRepository;
    private final IGetJwtValueInteractor getJwtValue;

    public BackupWebHabitListWebInteractor(
            @NonNull IHabitsWebRepository habitsWebRepository,
            @NonNull IHabitsDatabaseRepository habitsDatabaseRepository,
            @NonNull IGetJwtValueInteractor getJwtValue) {
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

        /*return habitsDatabaseRepository.loadHabitWithProgressesList()
                .onErrorResumeNext(Single::error)
                .flatMapCompletable(habitWithProgressesList ->
                        habitsWebRepository.insertHabitWithProgressesList(habitWithProgressesList, jwt)).onErrorResumeNext(throwable -> {
                    if (!(throwable instanceof LoadDbException))
                        return Completable.error(new BackupException(R.string.insert_web_exception, throwable));
                    return Completable.error(throwable);
                });*/
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
                        Completable.error(new BackupException(R.string.insert_web_exception, throwable)));

    }

}
