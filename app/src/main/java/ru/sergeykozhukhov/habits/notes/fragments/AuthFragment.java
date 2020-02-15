package ru.sergeykozhukhov.habits.notes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.notes.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.notes.database.habit.HabitDao;
import ru.sergeykozhukhov.habits.notes.database.user.User;
import ru.sergeykozhukhov.habits.notes.database.user.UserDao;
import ru.sergeykozhukhov.habits.notes.retrofit.data.authentication.AuthUser;
import ru.sergeykozhukhov.habits.notes.retrofit.data.authentication.AuthResult;
import ru.sergeykozhukhov.habits.notes.retrofit.helper.ApiService;
import ru.sergeykozhukhov.habits.notes.retrofit.helper.RetrofitClient;

public class AuthFragment extends Fragment {

    private ApiService apiService;

    private HabitsDatabase habitsDB;

    private UserDao userDao;
    private HabitDao habitDao;

    private Executor executor;

    private EditText loginAuthEditText;
    private EditText passwordAuthEditText;

    private Button requestAuthButton;

    public static Fragment newInstance() {
        return new AuthFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.authentication_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginAuthEditText = view.findViewById(R.id.login_auth_edit_text);
        passwordAuthEditText = view.findViewById(R.id.password_auth_edit_text);

        requestAuthButton = view.findViewById(R.id.request_auth_button);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initViewListeners();
    }

    private void initData() {

        apiService = RetrofitClient.getApiService();

        habitsDB = HabitsDatabase.getInstance(requireContext());

        userDao = habitsDB.getUserDao();
        habitDao = habitsDB.getHabitDao();

        executor = Executors.newSingleThreadExecutor();
    }

    private void initViewListeners() {

        requestAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = loginAuthEditText.getText().toString();
                String password = passwordAuthEditText.getText().toString();

                AuthUser authUser = new AuthUser(login, password);

                sendRequestAuthRx(authUser);

            }
        });
    }


    private void sendRequestAuth(AuthUser authUser) {


        apiService.authClient(authUser).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // tv_response_register.setText(response.body().getMessage() + "\n" + response.body().getToken());

                        Toast.makeText(requireContext(), "Auth success", Toast.LENGTH_SHORT).show();
                        User user = new User(
                                "login",
                                "null",
                                "Ivan",
                                response.body().getJwt().toString());

                        insertUser(user);

                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {

            }
        });
    }

    private void sendRequestAuthRx(AuthUser authUser){
        Disposable disposable = apiService.authClientRx(authUser)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AuthResult>() {
                    @Override
                    public void accept(AuthResult authResult) throws Exception {
                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void insertUser(final User user){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(user);
            }
        });

    }







}
