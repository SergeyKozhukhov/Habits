package ru.sergeykozhukhov.habits.notes.backup;

import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.notes.backup.ISetJwtInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class SetJwtInteractor implements ISetJwtInteractor {

    private IHabitsWebRepository habitsWebRepository;

    public SetJwtInteractor(IHabitsWebRepository habitsWebRepository) {
        this.habitsWebRepository = habitsWebRepository;
    }

    @Override
    public void setJwt(Jwt jwt) {
        habitsWebRepository.setJwt(jwt);
    }
}
