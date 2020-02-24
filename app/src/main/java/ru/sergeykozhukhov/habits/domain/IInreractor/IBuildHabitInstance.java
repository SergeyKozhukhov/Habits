package ru.sergeykozhukhov.habits.domain.IInreractor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

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
