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

public class HabitsRetrofitClient {

    private static HabitsRetrofitClient habitsRetrofitClient;

    private static final String ROOT_URL = "https://testx0123test.000webhostapp.com/";

    private Retrofit retrofit;
    private IHabitsService habitsService;

    private JwtData jwtData;


    private HabitsRetrofitClient() {
        retrofit = buildRetrofit();
        habitsService = retrofit.create(IHabitsService.class);
    }

    public static HabitsRetrofitClient getInstance() {

        if (habitsRetrofitClient == null){
            habitsRetrofitClient = new HabitsRetrofitClient();
        }
        return habitsRetrofitClient;
    }

    private Retrofit buildRetrofit(){

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

    public void setJwtData(JwtData jwt){
        jwtData = jwt;
    }

    public void clearJwtData(){
        jwtData = null;
    }

    public JwtData getJwtData() {
        return jwtData;
    }

    public void cleanUp(){
        retrofit = null;
    }


}
