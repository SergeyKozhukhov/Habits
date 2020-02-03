package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;

public interface IAuthenticateClient {

    Authentication authenticateClient(Confidentiality confidentiality);

    Single<Authentication> authenticateClientRx(Confidentiality confidentiality);

}
