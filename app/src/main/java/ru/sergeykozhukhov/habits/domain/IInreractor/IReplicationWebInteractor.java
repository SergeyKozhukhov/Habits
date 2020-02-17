package ru.sergeykozhukhov.habits.domain.IInreractor;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;

/**
 * Интерейс интерактора сохранения в базу данных всех записей с сервера
 */
public interface IReplicationWebInteractor {

    @NonNull
    Single<List<HabitWithProgresses>> loadHabitWithProgressesList();

}
