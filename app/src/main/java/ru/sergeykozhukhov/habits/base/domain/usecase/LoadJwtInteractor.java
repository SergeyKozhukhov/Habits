package ru.sergeykozhukhov.habits.base.domain.usecase;

import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ILoadJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public class LoadJwtInteractor implements ILoadJwtInteractor {

    private IHabitsPreferencesRepository habitsPreferencesRepository;

    public LoadJwtInteractor(IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    @Override
    public void loadJwt() {
        habitsPreferencesRepository.loadJwt();
    }
}
