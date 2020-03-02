package ru.sergeykozhukhov.habits.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Singleton класс, подготавливающий и настраивающий работу с preferences
 */
public class HabitsPreferences {

    private static HabitsPreferences habitsPreferences;
    private SharedPreferences sharedPreferences;

    /**
     * Наименование preferences файла
     */
    private static final String PREFERENCES_NAME = "settings";

    /**
     * Строковый идентификатор для получения token (jwt)
     */
    public static final String JWT_PREFERENCES = "JWT_PREFERENCES";


    private HabitsPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static HabitsPreferences getInstance(Context context) {
        if (habitsPreferences == null) {
            habitsPreferences = buildInstance(context);
        }
        return habitsPreferences;
    }

    private static HabitsPreferences buildInstance(Context context) {

        habitsPreferences = new HabitsPreferences(context);
        return habitsPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

}
