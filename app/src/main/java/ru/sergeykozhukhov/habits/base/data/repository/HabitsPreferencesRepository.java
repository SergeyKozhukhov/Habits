package ru.sergeykozhukhov.habits.base.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public class HabitsPreferencesRepository implements IHabitsPreferencesRepository {

    // Утечка контекста?
    private Context context;
    private SharedPreferences sharedPreferences;

    public HabitsPreferencesRepository(
            @NonNull Context context,
            @NonNull HabitsPreferences habitsPreferences) {
        this.context = context;
        sharedPreferences = HabitsPreferences.getInstance(context);
    }

    @Override
    public void setJwt(Jwt jwt) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(HabitsPreferences.JWT_PREFERENCES, jwt.getJwt());
        ed.apply();
    }

    @Override
    public Jwt getJwt() {
        String jwt = sharedPreferences.getString(HabitsPreferences.JWT_PREFERENCES, "");
        return new Jwt(jwt);
    }
}
