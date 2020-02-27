package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.usecase.IChangeProgressListDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.exception.ChangeProgressException;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

/**
 * Реализация интерактора по изменению дат выполнения конкретной привычки
 */
public class ChangeProgressListDbInteractor implements IChangeProgressListDbInteractor {

    private static final String TAG = "ChangeProgressIntrctr";

    /**
     * Репозиторий (база данных)
     */
    private final IHabitsDatabaseRepository habitsDatabaseRepository;
    private long idHabit;
    private List<Progress> progressLoadedList;
    private List<Date> progressAddedList;
    private List<Date> progressDeletedList;

    public ChangeProgressListDbInteractor(@NonNull IHabitsDatabaseRepository habitsDatabaseRepository) {
        this.habitsDatabaseRepository = habitsDatabaseRepository;
    }

    @NonNull
    @Override
    public Single<List<Date>> getProgressList(long idHabit) {
        return habitsDatabaseRepository.loadProgressListByIdHabit(idHabit)
                .doOnSuccess(progressList -> {
                    this.idHabit = idHabit;
                    progressLoadedList = progressList;
                    progressAddedList = new LinkedList<>();
                    progressDeletedList = new LinkedList<>();
                })
                .map(progressList -> {
                    List<Date> dateList = new ArrayList<>(progressList.size());
                    for (Progress progress : progressList) {
                        dateList.add(progress.getDate());
                    }
                    return dateList;
                })
                .onErrorResumeNext(throwable ->
                        Single.error(new LoadDbException(R.string.load_db_exception, throwable)));
    }

    @Override
    public void addNewDate(@Nullable Date date) throws ChangeProgressException {
        if (date == null)
            throw new ChangeProgressException(R.string.change_progress_db_exception);
        if (!progressDeletedList.remove(date))
            progressAddedList.add(date);
    }

    @Override
    public void deleteDate(@Nullable Date date) throws ChangeProgressException {
        if (date == null)
            throw new ChangeProgressException(R.string.change_progress_db_exception);
        if (!progressAddedList.remove(date))
            progressDeletedList.add(date);
    }

    @Nullable
    @Override
    public Completable saveProgressList() {
        Completable deleteCompletable = null;

        if (!progressDeletedList.isEmpty()) {
            List<Progress> deleteProgresses;
            Log.d(TAG, "deletedProgress:" + "\n");
            deleteProgresses = new ArrayList<>(progressDeletedList.size());
            for (Date date : progressDeletedList) {
                Log.d(TAG, date.toString() + "\n");
                for (Progress progress : progressLoadedList) {
                    if (date.equals(progress.getDate())) {
                        deleteProgresses.add(progress);
                    }
                }
            }

            deleteCompletable = habitsDatabaseRepository.deleteProgressList(deleteProgresses)
                    .onErrorResumeNext(throwable ->
                            Completable.error(new DeleteFromDbException(R.string.delete_from_db_exception, throwable)));
        }

        Completable insertCompletable = null;
        if (!progressAddedList.isEmpty()) {
            List<Progress> addProgresses;
            Log.d(TAG, "addedProgress:" + "\n");
            addProgresses = new ArrayList<>(progressAddedList.size());
            for (Date date : progressAddedList) {
                Log.d(TAG, date.toString() + "\n");
                addProgresses.add(new Progress(idHabit, date));
            }
            insertCompletable = habitsDatabaseRepository.insertProgressList(addProgresses)
                    .onErrorResumeNext(throwable ->
                            Completable.error(new InsertDbException(R.string.insert_db_exception, throwable)));
        }

        if (deleteCompletable != null && insertCompletable == null) {
            return deleteCompletable;
        } else if (deleteCompletable == null && insertCompletable != null) {
            return insertCompletable;
        } else if (deleteCompletable != null) {
            return deleteCompletable.andThen(insertCompletable);
        } else {
            return null;
        }
    }


}
