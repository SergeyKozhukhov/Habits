package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.usecase.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;

/**
 * Реализация интерактора получения токена, сохраненного в памяти/preferences
 */
public class GetJwtValueInteractor implements IGetJwtValueInteractor {

    /**
     * Репозиторий (сервер)
     */
    private final IHabitsWebRepository habitsWebRepository;

    /**
     * Репозиторий (preferences)
     */
    private final IHabitsPreferencesRepository habitsPreferencesRepository;

    public GetJwtValueInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                 @NonNull IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    /**
     * Получение строкового представления token (jwt), сохраненного ранее
     *
     * @return строковое представление token (jwt)
     * @throws GetJwtException исключение при ошибке получения токена (jwt) или его отсутсвия
     */
    @NonNull
    @Override
    public String getValue() throws GetJwtException {
        try {
            Jwt token = habitsWebRepository.getJwt();
            return token.getJwt();
        } catch (Exception e) {
            Jwt token = habitsPreferencesRepository.loadJwt();
            if (token == null) {
                throw new GetJwtException(R.string.get_jwt_exception);
            }
            habitsWebRepository.setJwt(token);
            return token.getJwt();
        }
    }
}
