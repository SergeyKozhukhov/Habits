package ru.sergeykozhukhov.habits.base.domain.usecase;

import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IGetJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public class GetJwtInteractor implements IGetJwtInteractor {

    private IHabitsPreferencesRepository habitsPreferencesRepository;

    public GetJwtInteractor(IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }


    @Override
    public Jwt getJwt() {
        return habitsPreferencesRepository.getJwt();
    }
}
