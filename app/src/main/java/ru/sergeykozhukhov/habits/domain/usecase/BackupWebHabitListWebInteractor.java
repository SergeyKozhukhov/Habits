package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IBackupWebInteractor;
import ru.sergeykozhukhov.habits.domain.IInreractor.provider.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.exception.InsertWebException;

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