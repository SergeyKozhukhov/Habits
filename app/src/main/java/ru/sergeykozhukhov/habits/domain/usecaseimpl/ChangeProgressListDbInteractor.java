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

    /**
     * id привычки, даты выполнения которой подвергаются изменению
     */
    private long idHabit;

    /**
     * Список дат выполнения привычки для добавления в базу данных
     */
    private List<Date> progressAddedList;

    /**
     * Список дат выполнения привычки для удаления из базы данных
     */
    private List<Date> progressDeletedList;

    public ChangeProgressListDbInteractor(@NonNull IHabitsDatabaseRepository habitsDatabaseRepository) {
        this.habitsDatabaseRepository = habitsDatabaseRepository;
    }

    /**
     * Получение списка дат выполнения привычки
     *
     * @param idHabit id привычки
     * @return single со списком дат выполнения привычки (в виде Date)
     */
    @NonNull
    @Override
    public Single<List<Date>> getProgressList(long idHabit) {
        this.idHabit = idHabit;
        progressAddedList = new LinkedList<>();
        progressDeletedList = new LinkedList<>();
        return habitsDatabaseRepository.loadProgressListByIdHabit(idHabit)
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

    /**
     * Добавление даты в список "добавляемых" либо удаление ее из списка "удаляемых" дат выполнения привычки
     *
     * @param date дата
     * @throws ChangeProgressException - исключение при Date = null
     */
    @Override
    public void addNewDate(@Nullable Date date) throws ChangeProgressException {
        if (date == null)
            throw new ChangeProgressException(R.string.change_progress_db_exception);
        if (!progressDeletedList.remove(date))
            progressAddedList.add(date);
    }

    /**
     * Добавление даты в список "удяляемых" либо удаление ее из списка "добавленных" дат выполнения привычки
     *
     * @param date дата
     * @throws ChangeProgressException - исключение при Date = null
     */
    @Override
    public void deleteDate(@Nullable Date date) throws ChangeProgressException {
        if (date == null)
            throw new ChangeProgressException(R.string.change_progress_db_exception);
        if (!progressAddedList.remove(date))
            progressDeletedList.add(date);
    }

    /**
     * Сохранение изменений по датам выполнения привычек
     */
    @Nullable
    @Override
    public Completable saveProgressList() {
        Completable deleteCompletable = null;

        if (!progressDeletedList.isEmpty()) {
            Log.d(TAG, "deletedProgress:" + "\n");
            List<Progress> deleteProgresses = new ArrayList<>(progressDeletedList.size());
            List<Progress> progressList;
            try {
                progressList = habitsDatabaseRepository.getSavedProgressList();
                for (Date date : progressDeletedList) {
                    Log.d(TAG, date.toString() + "\n");
                    for (Progress progress : progressList) {
                        if (date.equals(progress.getDate())) {
                            deleteProgresses.add(progress);
                        }
                    }
                }
                deleteCompletable = habitsDatabaseRepository.deleteProgressList(deleteProgresses)
                        .onErrorResumeNext(throwable ->
                                Completable.error(new DeleteFromDbException(R.string.delete_from_db_exception, throwable)));

                habitsDatabaseRepository.resetSavedProgressList();
            } catch (NullPointerException e) {
                return Completable.error(new DeleteFromDbException(R.string.delete_from_db_exception, new Exception()));
            }
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
