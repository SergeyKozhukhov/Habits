package ru.sergeykozhukhov.habits.base.domain.usecase;

import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ISaveJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public class SaveJwtInteractor implements ISaveJwtInteractor {

    private IHabitsPreferencesRepository habitsPreferencesRepository;

    public SaveJwtInteractor(IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    @Override
    public void saveJwt(Jwt jwt) {
        habitsPreferencesRepository.setJwt(jwt);
    }
}
