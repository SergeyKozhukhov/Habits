package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;

public interface IConverter<From, To> {
    @NonNull
    To convert(@NonNull From from);
}
