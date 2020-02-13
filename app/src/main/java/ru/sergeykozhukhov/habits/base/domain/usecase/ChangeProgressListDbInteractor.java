package ru.sergeykozhukhov.habits.base.domain.usecase;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IChangeProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public class ChangeProgressListDbInteractor implements IChangeProgressListDbInteractor {

    private static final String TAG = "ChangeProgressIntrctr";
    private final IHabitsDatabaseRepository habitsDatabaseRepository;
    private long idHabit;
    private List<Progress> progressLoadedList;
    private List<Date> progressAddedList;
    private List<Date> progressDeletedList;


    public ChangeProgressListDbInteractor(IHabitsDatabaseRepository habitsDatabaseRepository) {
        this.habitsDatabaseRepository = habitsDatabaseRepository;

    }


    @Override
    public Single<List<Date>> getProgressList(long idHabit) {
        this.idHabit = idHabit;
        return habitsDatabaseRepository.loadProgressListByIdHabit(idHabit)
                .map(new Function<List<Progress>, List<Date>>() {
                    @Override
                    public List<Date> apply(List<Progress> progressList) throws Exception {
                        progressLoadedList = progressList;
                        List<Date> dateList  = new ArrayList<>(progressList.size());
                        progressAddedList = new LinkedList<>();
                        progressDeletedList = new LinkedList<>();
                        for (Progress progress: progressList){
                            dateList.add(progress.getDate());
                        }
                        return dateList;
                    }
                });
    }

    @Override
    public void addNewDate(Date date) {
        if (!progressDeletedList.remove(date)) {
            progressAddedList.add(date);
        }
    }

    @Override
    public void deleteDate(Date date) {
        if (!progressAddedList.remove(date)) {
            progressDeletedList.add(date);
        }
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

            deleteCompletable = habitsDatabaseRepository.deleteProgressList(deleteProgresses);
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
            insertCompletable = habitsDatabaseRepository.insertProgressList(addProgresses);
        }

        if (deleteCompletable != null && insertCompletable ==null){
            return deleteCompletable;
        }
        else if (deleteCompletable == null && insertCompletable !=null)
        {
            return insertCompletable;
        }
        else if (deleteCompletable != null){
            return deleteCompletable.andThen(insertCompletable);
        }
        else {
            return null;
        }
    }







}
