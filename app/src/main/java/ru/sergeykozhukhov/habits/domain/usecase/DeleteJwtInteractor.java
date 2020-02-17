package ru.sergeykozhukhov.habits.domain.usecase;

import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IDeleteJwtInteractor;

public class DeleteJwtInteractor implements IDeleteJwtInteractor {

    private final IHabitsWebRepository habitsWebRepository;
    private final IHabitsPreferencesRepository habitsPreferencesRepository;

    public DeleteJwtInteractor(IHabitsWebRepository habitsWebRepository, IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    @Override
    public void deleteJwt() {
        habitsPreferencesRepository.deleteJwt();
        habitsWebRepository.deleteJwt();
    }
}
