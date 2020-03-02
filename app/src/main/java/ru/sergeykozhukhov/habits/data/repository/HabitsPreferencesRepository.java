package ru.sergeykozhukhov.habits.data.repository;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

/**
 * Реализация репозитория (preferences)
 */
public class HabitsPreferencesRepository implements IHabitsPreferencesRepository {

    /**
     * Класс, подготавливающий и настраивающий работу с preferences
     */
    private HabitsPreferences habitsPreferences;

    /**
     * Конвертер Jwt модели между data и domain слоями
     */
    private JwtConverter jwtConverter;

    public HabitsPreferencesRepository(
            @NonNull HabitsPreferences habitsPreferences,
            @NonNull JwtConverter jwtConverter) {
        this.habitsPreferences = habitsPreferences;
        this.jwtConverter = jwtConverter;
    }

    /**
     * Сохранение token (jwt) в preferences
     *
     * @param jwt token (jwt)
     */
    @Override
    public void saveJwt(@NonNull Jwt jwt) {
        SharedPreferences.Editor editor = habitsPreferences.getSharedPreferences().edit();
        editor.putString(HabitsPreferences.JWT_PREFERENCES, jwt.getJwt());
        editor.apply();
    }

    /**
     * Получение сохраненного token (jwt)
     *
     * @return сохраненный token (jwt)
     */
    @Nullable
    @Override
    public Jwt loadJwt() {
        JwtData jwtData = new JwtData(habitsPreferences.getSharedPreferences()
                .getString(HabitsPreferences.JWT_PREFERENCES, ""));
        if (jwtData.getJwt().equals(""))
            return null;
        return jwtConverter.convertTo(jwtData);
    }

    /**
     * Удаление token (jwt)
     */
    @Override
    public void deleteJwt() {
        SharedPreferences.Editor editor = habitsPreferences.getSharedPreferences().edit();
        editor.remove(HabitsPreferences.JWT_PREFERENCES);
        editor.apply();
    }


}
