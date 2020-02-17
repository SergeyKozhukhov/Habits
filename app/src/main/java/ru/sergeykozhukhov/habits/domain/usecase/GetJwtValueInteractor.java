package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.provider.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.exception.GetJwtException;

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
        try{
            Jwt token = habitsWebRepository.getJwt();
            return token.getJwt();
        } catch (Exception e) {
            Jwt token = habitsPreferencesRepository.loadJwt();
            if (token == null)
                throw new GetJwtException(R.string.get_jwt_exception);
            habitsWebRepository.setJwt(token);
            return token.getJwt();
        }
    }

    @NonNull
    @Override
    public Single<Jwt> getValueSingle() {
        try{
            Jwt token = habitsWebRepository.getJwt();
            return Single.just(token);
        } catch (Exception e) {
            Jwt token = habitsPreferencesRepository.loadJwt();
            if (token == null) {
                return Single.error(new GetJwtException(R.string.get_jwt_exception));
            }
            habitsWebRepository.setJwt(token);
            return Single.just(token);
        }
    }
}
