package ru.sergeykozhukhov.habits.domain;

import androidx.annotation.NonNull;

/**
 * Интерфейс конвертера (конвертация между data и domain слоями)
 *
 * @param <From> класс data слоя
 * @param <To>   соответствующий класс domain слоя
 */
public interface IConverter<From, To> {

    /**
     * Конвертация в соответствующий экземпляр класса domain слоя
     *
     * @param from экземпляр класса data слоя
     * @return экземпляр соответствующего класса domain слоя
     */
    @NonNull
    To convertTo(@NonNull From from);

    /**
     * Конвертация в соответствующий экземпляр класса data слоя
     *
     * @param to экземпляр класса domain слоя
     * @return экземпляр соответствующего класса data слоя
     */
    @NonNull
    From convertFrom(@NonNull To to);
}
