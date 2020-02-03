package ru.sergeykozhukhov.habits.base.domain;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;

public interface IHabitsWebRepository {

    Authentication authClient(Confidentiality confidentiality);
    Single<Authentication> authClientRx(Confidentiality confidentiality);
}
