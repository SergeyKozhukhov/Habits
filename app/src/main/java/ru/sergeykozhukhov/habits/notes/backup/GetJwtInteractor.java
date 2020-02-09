package ru.sergeykozhukhov.habits.notes.backup;

import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class GetJwtInteractor implements IGetJwtInteractor {

    private IHabitsWebRepository habitsWebRepository;
    private IHabitsPreferencesRepository habitsPreferencesRepository;

    public GetJwtInteractor(IHabitsWebRepository habitsWebRepository,
                            IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }


    @Override
    public Jwt getJwt() {
        Jwt jwt = habitsWebRepository.getJwt();
        if (jwt.getJwt() == null){
            jwt = habitsPreferencesRepository.loadJwt();
        }
        return jwt;
    }
}
