package ru.sergeykozhukhov.habits.base.data.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HabitsRetrofitClient {

    private static final String ROOT_URL = "https://testx0123test.000webhostapp.com/";

    private static Retrofit retrofit;
    private static IHabitsService habitsService;

    private static Retrofit getInstance() {

        if (retrofit == null){
            retrofit = buildInstance();
        }
        return retrofit;
    }

    private static Retrofit buildInstance(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();


        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();

        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static IHabitsService getApiService() {
        if (habitsService == null){
            habitsService = getInstance().create(IHabitsService.class);
        }
        return habitsService;
    }


    public void cleanUp(){
        retrofit = null;
    }


}
