package ru.sergeykozhukhov.habits.notes.backup;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class LoadJwtInteractor implements ILoadJwtInteractor {

    private IHabitsPreferencesRepository habitsPreferencesRepository;
    private IHabitsWebRepository habitsWebRepository;

    public LoadJwtInteractor(@NonNull IHabitsPreferencesRepository habitsPreferencesRepository,
                             @NonNull IHabitsWebRepository habitsWebRepository) {
        this.habitsPreferencesRepository = habitsPreferencesRepository;
        this.habitsWebRepository = habitsWebRepository;
    }

    @Override
    public void loadJwt() {
        Jwt jwt = habitsPreferencesRepository.loadJwt();
        habitsWebRepository.setJwt(jwt);
    }
}
