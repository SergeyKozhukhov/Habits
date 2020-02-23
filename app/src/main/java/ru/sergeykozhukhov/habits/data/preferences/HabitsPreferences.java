package ru.sergeykozhukhov.habits.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class HabitsPreferences {

    private static HabitsPreferences habitsPreferences;
    private SharedPreferences sharedPreferences;

    public static final String PREFERENCES_NAME = "settings";
    public static final String JWT_PREFERENCES = "JWT_PREFERENCES";


    private HabitsPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static HabitsPreferences getInstance(Context context) {
        if (habitsPreferences == null ){
            habitsPreferences = buildInstance(context);
        }
        return habitsPreferences;
    }

    private static HabitsPreferences buildInstance(Context context){

        habitsPreferences = new HabitsPreferences(context);
        return habitsPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    public void cleanUp(){
        sharedPreferences = null;
    }





}
