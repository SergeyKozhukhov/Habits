package ru.sergeykozhukhov.habits.notes.backup;

import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.notes.backup.ISaveJwtInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class SaveJwtInteractor implements ISaveJwtInteractor {

    private IHabitsPreferencesRepository habitsPreferencesRepository;

    public SaveJwtInteractor(IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    @Override
    public void saveJwt(Jwt jwt) {
        habitsPreferencesRepository.saveJwt(jwt);
    }
}
