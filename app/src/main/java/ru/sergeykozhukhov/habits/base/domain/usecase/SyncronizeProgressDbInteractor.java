package ru.sergeykozhukhov.habits.base.domain.usecase;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ILoadHabitsDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ILoadProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public class SyncronizeProgressDbInteractor {

    private final IHabitsDatabaseRepository habitsDatabaseRepository;
    private final LoadProgressListDbInteractor loadProgressListDbInteractor;
    private final InsertProgressListDbInteractor insertProgressListDbInteractor;

    private HashMap<Long, Progress> progressLoadedHashMap;
    private HashMap<Long, Date> progressPushedHashMap;
    private HashMap<Long, Progress> progressDeletedHashMap;


    public SyncronizeProgressDbInteractor(IHabitsDatabaseRepository habitsDatabaseRepository,
                                          LoadProgressListDbInteractor loadProgressListDbInteractor,
                                          InsertProgressListDbInteractor insertProgressListDbInteractor) {
        this.habitsDatabaseRepository = habitsDatabaseRepository;
        this.loadProgressListDbInteractor = loadProgressListDbInteractor;
        this.insertProgressListDbInteractor = insertProgressListDbInteractor;
        progressLoadedHashMap = new HashMap<>();
    }

    public Single<List<Progress>> getProgress(long idHabit){
        return loadProgressListDbInteractor.loadProgressListByHabit(idHabit)
                .doOnSuccess(new Consumer<List<Progress>>() {
                    @Override
                    public void accept(List<Progress> progressList) throws Exception {
                        for (Progress progress : progressList){
                            progressLoadedHashMap.put(progress.getDate().getTime(), progress);
                        }
                    }
                });
    }

    public void putNewDate(Date date){
        if (!progressLoadedHashMap.containsKey(date.getTime())){
            //progressPushedHashMap.
        }
    }


}
