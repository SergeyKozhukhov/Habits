package ru.sergeykozhukhov.habits.notes.backup;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IBackupWebInteractor;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class BackupWebHabitListWebInteractor implements IBackupWebInteractor {

    private IHabitsWebRepository habitsWebRepository;
    private IHabitsDatabaseRepository habitsDatabaseRepository;
    private IHabitsPreferencesRepository habitsPreferencesRepository;

    public BackupWebHabitListWebInteractor(IHabitsWebRepository habitsWebRepository, IHabitsDatabaseRepository habitsDatabaseRepository, IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsDatabaseRepository = habitsDatabaseRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    @Override
    public Completable insertHabitWithProgressesList() {
        return null;
    }

   /* @Override
    public Completable insertHabit() {


        String jwt;
        try{
            jwt = habitsWebRepository.getJwt().getJwt();
        } catch (Exception e) {
            jwt = habitsPreferencesRepository.loadJwt().getJwt();
            habitsWebRepository.setJwt(new Jwt(jwt));
            if (jwt == null)
                return null;
            e.printStackTrace();
        }

        List<Habit> habits = habitsDatabaseRepository.loadHabitList().firstOrError().blockingGet();

        return habitsWebRepository.insertHabit(habits, jwt);
    }

    @Override
    public Completable insertProgressList() {
        String jwt;
        try{
            jwt = habitsWebRepository.getJwt().getJwt();
        } catch (Exception e) {
            jwt = habitsPreferencesRepository.loadJwt().getJwt();
            habitsWebRepository.setJwt(new Jwt(jwt));
            if (jwt == null)
                return null;
            e.printStackTrace();
        }

        List<Progress> progressList = habitsDatabaseRepository.loadProgressList()
                .blockingGet();
        return habitsWebRepository.insertProgressList(progressList, jwt);
    }*/

    /*@Override
    public Completable insertHabitWithProgressesList() {
        String jwt;
        try{
            jwt = habitsWebRepository.getJwt().getJwt();
        } catch (Exception e) {
            jwt = habitsPreferencesRepository.loadJwt().getJwt();
            habitsWebRepository.setJwt(new Jwt(jwt));
            if (jwt == null)
                return null;
            e.printStackTrace();
        }

        List<HabitWithProgressesData> habitWithProgressesDataList = habitsDatabaseRepository.loadHabitWithProgressesList().subscribeOn(Schedulers.newThread())
                .blockingGet();

        return habitsWebRepository.insertHabitWithProgressesList(habitWithProgressesDataList, jwt);

    }*/

}
