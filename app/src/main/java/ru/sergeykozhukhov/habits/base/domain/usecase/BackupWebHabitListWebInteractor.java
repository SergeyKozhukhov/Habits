package ru.sergeykozhukhov.habits.base.domain.usecase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IBackupWebInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
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
    public Completable insertHabits() {


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

        List<Habit> habits = habitsDatabaseRepository.loadHabits().firstOrError().blockingGet();

        return habitsWebRepository.insertHabits(habits, jwt);
    }
}
