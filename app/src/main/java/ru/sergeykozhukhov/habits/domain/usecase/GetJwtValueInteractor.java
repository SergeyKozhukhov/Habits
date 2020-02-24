package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;

public class GetJwtValueInteractor implements IGetJwtValueInteractor {

    private final IHabitsWebRepository habitsWebRepository;
    private final IHabitsPreferencesRepository habitsPreferencesRepository;

    public GetJwtValueInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                 @NonNull IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }


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
