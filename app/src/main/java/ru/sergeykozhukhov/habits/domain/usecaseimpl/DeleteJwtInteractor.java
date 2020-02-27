package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.usecase.IDeleteJwtInteractor;

/**
 * Реализация интерактора выхода пользователя из аккаунта
 */
public class DeleteJwtInteractor implements IDeleteJwtInteractor {

    /**
     * Репозиторий (сервер)
     */
    private final IHabitsWebRepository habitsWebRepository;

    /**
     * Репозиторий (preferences)
     */
    private final IHabitsPreferencesRepository habitsPreferencesRepository;

    public DeleteJwtInteractor(IHabitsWebRepository habitsWebRepository, IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    /**
     * Обнуление/удаление сохраненного token (jwt)
     */
    @Override
    public void deleteJwt() {
        habitsPreferencesRepository.deleteJwt();
        habitsWebRepository.deleteJwt();
    }
}
