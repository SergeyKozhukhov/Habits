package ru.sergeykozhukhov.habits.base.domain.usecase.builder;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.builder.IGetJwtValue;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;
import ru.sergeykozhukhov.habits.base.model.exception.GetJwtException;

public class GetJwtValue implements IGetJwtValue {

    private final IHabitsWebRepository habitsWebRepository;
    private final IHabitsPreferencesRepository habitsPreferencesRepository;

    public GetJwtValue(@NonNull IHabitsWebRepository habitsWebRepository,
                       @NonNull IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    @Override
    public String getValue() throws GetJwtException {
        try{
            Jwt token = habitsWebRepository.getJwt();
            return token.getJwt();
        } catch (Exception e) {
            Jwt token = habitsPreferencesRepository.loadJwt();
            if (token.getJwt().equals(""))
                throw new GetJwtException(R.string.get_jwt_exception);
            habitsWebRepository.setJwt(token);
            return token.getJwt();
        }
    }
}
