package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public interface IAuthenticateWebInteractor {

    Single<Jwt> authenticateClientRx(Confidentiality confidentiality);

}
