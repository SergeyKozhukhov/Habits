package ru.sergeykozhukhov.habits.base.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import ru.sergeykozhukhov.habits.base.data.model.JwtData;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public class HabitsPreferences {

    private HabitsPreferences habitsPreferences;
    private static SharedPreferences sharedPreferences;

    private static JwtData jwtData;

    public static final String PREFERENCES_NAME = "settings";
    public static final String JWT_PREFERENCES = "JWT_PREFERENCES";


    public static SharedPreferences getInstance(Context context) {
        if (sharedPreferences == null ){
            sharedPreferences = buildInstance(context);
        }
        return sharedPreferences;
    }

    private static SharedPreferences buildInstance(Context context){
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    public static void setJwtData(JwtData jwt){
        jwtData = jwt;
    }
    public static JwtData getInstanceJwtData(){
        return jwtData;
    }

    public void cleanUp(){
        sharedPreferences = null;
        jwtData = null;
    }





}
