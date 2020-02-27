package ru.sergeykozhukhov.habits.data.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.sergeykozhukhov.habits.model.data.JwtData;

/**
 * Singleton класс, подготавливающий и настраивающий работу с сервером
 */
public class HabitsRetrofitClient {

    private static HabitsRetrofitClient habitsRetrofitClient;

    /**
     * Базовый адрес сервера
     */
    private static final String ROOT_URL = "https://testx0123test.000webhostapp.com/";

    /**
     * Объект retrofit для совершения http операций с сервером
     */
    private Retrofit retrofit;
    /**
     * web-api для работы с сервером
     */
    private IHabitsService habitsService;

    /**
     * token (jwt) клиента
     */
    private JwtData jwtData;


    private HabitsRetrofitClient() {
        retrofit = buildRetrofit();
        habitsService = retrofit.create(IHabitsService.class);
    }

    public static HabitsRetrofitClient getInstance() {

        if (habitsRetrofitClient == null) {
            habitsRetrofitClient = new HabitsRetrofitClient();
        }
        return habitsRetrofitClient;
    }

    /**
     * Настройка и создание объектра retrofit (настройка конвертера json, возвращение rx источников, логирование запросов и ответов сервера)
     *
     * @return объект retrofit
     */
    private Retrofit buildRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();


        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setDateFormat("yyyy-MM-dd");
        Gson gson = builder.create();

        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public IHabitsService getApiService() {
        return habitsService;
    }

    public void setJwtData(JwtData jwt) {
        jwtData = jwt;
    }

    public void clearJwtData() {
        jwtData = null;
    }

    public JwtData getJwtData() {
        return jwtData;
    }


}
