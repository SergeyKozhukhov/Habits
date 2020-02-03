package ru.sergeykozhukhov.habits.base.domain.usecase;

import android.util.Log;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IAuthenticateClient;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;

public class AuthenticateClientInteractor implements IAuthenticateClient {

    private final IHabitsWebRepository habitsWebRepository;

    public AuthenticateClientInteractor(IHabitsWebRepository habitsWebRepository) {
        this.habitsWebRepository = habitsWebRepository;
    }

    @Override
    public Authentication authenticateClient(Confidentiality confidentiality) {
        try {
            return habitsWebRepository.authClient(confidentiality);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Single<Authentication> authenticateClientRx(Confidentiality confidentiality) {
        Log.d("auth_interact", "requst");
        return habitsWebRepository.authClientRx(confidentiality);
    }
}
