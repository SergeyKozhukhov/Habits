package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;

public interface ILoadStatisticListInteractor {

    @NonNull
    Single<List<Statistic>> loadStatisticsList();


}
