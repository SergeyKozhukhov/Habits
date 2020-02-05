package ru.sergeykozhukhov.habits.base.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.base.data.model.JwtData;
import ru.sergeykozhukhov.habits.base.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public class HabitsPreferencesRepository implements IHabitsPreferencesRepository {

    // Утечка контекста?
    private Context context;
    private SharedPreferences sharedPreferences;
    private JwtConverter jwtConverter;

    public HabitsPreferencesRepository(
            @NonNull Context context,
            @NonNull HabitsPreferences habitsPreferences,
            @NonNull JwtConverter jwtConverter) {
        this.context = context;
        sharedPreferences = HabitsPreferences.getInstance(context);
        this.jwtConverter = jwtConverter;
    }

    @Override
    public void saveJwt(Jwt jwt) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(HabitsPreferences.JWT_PREFERENCES, jwt.getJwt());
        ed.apply();
    }

    @Override
    public void loadJwt() {
        JwtData jwtData = new JwtData(sharedPreferences.getString(HabitsPreferences.JWT_PREFERENCES, "error load jwt"));
        HabitsPreferences.setJwtData(jwtData);

    }

    @Override
    public void setJwt(@NonNull Jwt jwt) {
        HabitsPreferences.setJwtData(jwtConverter.convertFrom(jwt));
    }


    @Override
    public Jwt getJwt() {
        return jwtConverter.convertTo(HabitsPreferences.getInstanceJwtData());
    }


}
