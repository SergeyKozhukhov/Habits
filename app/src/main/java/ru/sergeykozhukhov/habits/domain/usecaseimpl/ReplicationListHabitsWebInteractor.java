package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.usecase.IReplicationWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.ReplicationException;

/**
 * Реализация интерактора восстановления данных о привычках с сервера
 */
public class ReplicationListHabitsWebInteractor implements IReplicationWebInteractor {

    /**
     * Репозиторий (сервкр)
     */
    private final IHabitsWebRepository habitsWebRepository;

    /**
     * Репозиторий (база данных)
     */
    private final IHabitsDatabaseRepository habitsDatabaseRepository;

    /**
     * Интерактор получения токена, сохраненного в памяти/preferences
     */
    private final IGetJwtValueInteractor getJwtValue;

    public ReplicationListHabitsWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                              @NonNull IHabitsDatabaseRepository habitsDatabaseRepository,
                                              @NonNull IGetJwtValueInteractor getJwtValue) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsDatabaseRepository = habitsDatabaseRepository;
        this.getJwtValue = getJwtValue;
    }

    /**
     * Сохранения в базу данных всех записей с сервера
     */
    @NonNull
    @Override
    public Completable loadHabitWithProgressesList() {

        String jwt;
        try {
            jwt = getJwtValue.getValue();
        } catch (GetJwtException e) {
            return Completable.error(e);
        }
        return habitsWebRepository.loadHabitWithProgressesList(jwt)
                .onErrorResumeNext(throwable -> Single.error(new ReplicationException(R.string.load_web_exception, throwable)))
                .flatMapCompletable(
                        habitWithProgresses -> habitsDatabaseRepository.deleteAllHabits()
                                .onErrorResumeNext(throwable ->
                                        Completable.error(new DeleteFromDbException(R.string.cleanup_db_exception, throwable)))
                                .andThen(habitsDatabaseRepository.insertHabitWithProgressesList(habitWithProgresses)
                                        .onErrorResumeNext(throwable ->
                                                Completable.error(new InsertDbException(R.string.insert_db_exception, throwable)))));
    }
}
