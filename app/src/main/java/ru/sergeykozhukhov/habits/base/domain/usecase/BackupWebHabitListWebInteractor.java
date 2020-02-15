package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IBackupWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IGetJwtValue;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.exception.GetJwtException;

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

    @Override
    public Completable insertHabitWithProgressesList() {
        String jwt = null;
        try {
            jwt = getJwtValue.getValue();
        } catch (GetJwtException e) {
            return Completable.error(e);
        }

        List<HabitWithProgresses> habitWithProgressesList = habitsDatabaseRepository.loadHabitWithProgressesList()
                .subscribeOn(Schedulers.newThread())
                .blockingGet();

        return habitsWebRepository.insertHabitWithProgressesList(habitWithProgressesList, jwt);

    }

}
