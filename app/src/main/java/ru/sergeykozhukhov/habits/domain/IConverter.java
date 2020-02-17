package ru.sergeykozhukhov.habits.domain;

import androidx.annotation.NonNull;

/**
 * Интерфейс конвертера (конвертация между data и domain слоями)
 * @param <From>
 * @param <To>
 */
public interface IConverter<From, To> {
    @NonNull
    To convertTo(@NonNull From from);

    @NonNull
    From convertFrom(@NonNull To to);
}
