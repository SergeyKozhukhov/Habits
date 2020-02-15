package ru.sergeykozhukhov.habits.base.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.base.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.base.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.base.model.data.JwtData;
import ru.sergeykozhukhov.habits.base.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class HabitsPreferencesRepository implements IHabitsPreferencesRepository {

    private HabitsPreferences habitsPreferences;
    private JwtConverter jwtConverter;

    public HabitsPreferencesRepository(
            @NonNull HabitsPreferences habitsPreferences,
            @NonNull JwtConverter jwtConverter) {
        this.habitsPreferences = habitsPreferences;
        this.jwtConverter = jwtConverter;
    }

    @Override
    public void saveJwt(@NonNull Jwt jwt) {
        SharedPreferences.Editor editor = habitsPreferences.getSharedPreferences().edit();
        editor.putString(HabitsPreferences.JWT_PREFERENCES, jwt.getJwt());
        editor.apply();
    }

    @Nullable
    @Override
    public Jwt loadJwt() {
        JwtData jwtData = new JwtData(habitsPreferences.getSharedPreferences()
                .getString(HabitsPreferences.JWT_PREFERENCES, ""));
        if (jwtData.getJwt().equals(""))
            return null;
        return jwtConverter.convertTo(jwtData);
    }

    @Override
    public void deleteJwt() {
        SharedPreferences.Editor editor = habitsPreferences.getSharedPreferences().edit();
        editor.remove(HabitsPreferences.JWT_PREFERENCES);
        editor.apply();
    }


}
