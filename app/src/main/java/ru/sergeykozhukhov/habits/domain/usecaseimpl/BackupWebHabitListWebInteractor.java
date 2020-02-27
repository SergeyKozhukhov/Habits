package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.usecase.IBackupWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.domain.exception.BackupException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

/**
 * Реализация интерактора создания резервной копии данных о привычках на сервере
 */
public class BackupWebHabitListWebInteractor implements IBackupWebInteractor {

    /**
     * Репозиторий (сервер)
     */
    private final IHabitsWebRepository habitsWebRepository;

    /**
     * Репозиторий (база данных)
     */
    private final IHabitsDatabaseRepository habitsDatabaseRepository;

    /**
     * Интераткор получения токена, сохраненного в памяти/preferences
     */
    private final IGetJwtValueInteractor getJwtValue;

    public BackupWebHabitListWebInteractor(
            @NonNull IHabitsWebRepository habitsWebRepository,
            @NonNull IHabitsDatabaseRepository habitsDatabaseRepository,
            @NonNull IGetJwtValueInteractor getJwtValue) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsDatabaseRepository = habitsDatabaseRepository;
        this.getJwtValue = getJwtValue;
    }

    /**
     * Сохранение данных о привычкках и соответствующих датав выполнения на сервер
     */
    @NonNull
    @Override
    public Completable insertHabitWithProgressesList() {

        String jwt;
        try {
            jwt = getJwtValue.getValue();
        } catch (GetJwtException e) {
            return Completable.error(e);
        }
        return habitsDatabaseRepository.loadHabitWithProgressesList()
                .onErrorResumeNext(throwable ->
                        Single.error(new LoadDbException(R.string.load_db_exception, throwable)))
                .flatMapCompletable(habitWithProgressesList ->
                        habitsWebRepository.insertHabitWithProgressesList(habitWithProgressesList, jwt)
                                .onErrorResumeNext(throwable ->
                                        Completable.error(new BackupException(R.string.insert_web_exception, throwable)))
                );
    }
}
