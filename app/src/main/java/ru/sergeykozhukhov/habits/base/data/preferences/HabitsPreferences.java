package ru.sergeykozhukhov.habits.base.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class HabitsPreferences {

    private HabitsPreferences habitsPreferences;
    private static SharedPreferences sharedPreferences;

    public static final String PREFERENCES_NAME = "settings";
    public static final String JWT_PREFERENCES = "JWT_PREFERENCES";


    public static SharedPreferences getInstance(Context context) {
        if (sharedPreferences == null){
            sharedPreferences = buildInstance(context);
        }
        return sharedPreferences;
    }

    private static SharedPreferences buildInstance(Context context){
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void cleanUp(){
        sharedPreferences = null;
    }





}
