package ru.sergeykozhukhov.habits.base.domain.usecase;

import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ISetJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public class SetJwtInteractor implements ISetJwtInteractor {

    private IHabitsPreferencesRepository habitsPreferencesRepository;

    public SetJwtInteractor(IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    @Override
    public void setJwt(Jwt jwt) {
        habitsPreferencesRepository.setJwt(jwt);
    }
}
