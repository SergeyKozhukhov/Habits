package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;

public interface IConverter<From, To> {
    @NonNull
    To convertTo(@NonNull From from);

    @NonNull
    From convertFrom(@NonNull To to);
}
