package ru.sergeykozhukhov.habits.base.domain.IInreractor.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

/**
 * Интерфейс получения нового экземпляра класса habit
 */
public interface IBuildHabitInstance {
    @NonNull
    Habit build(@Nullable String title,
                @Nullable String description,
                @Nullable String startDate,
                @Nullable String duration) throws BuildException;
}
