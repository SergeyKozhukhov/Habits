package ru.sergeykozhukhov.habits.notes.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.notes.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.notes.database.habit.Habit;
import ru.sergeykozhukhov.habits.notes.database.habit.HabitDao;
import ru.sergeykozhukhov.habits.notes.database.user.Jwt;
import ru.sergeykozhukhov.habits.notes.database.user.UserDao;
import ru.sergeykozhukhov.habits.notes.fragments.adapter.HabitsAdapter;
import ru.sergeykozhukhov.habits.notes.retrofit.data.authentication.AuthUser;
import ru.sergeykozhukhov.habits.notes.retrofit.data.authentication.JwtData;
import ru.sergeykozhukhov.habits.notes.retrofit.data.habits.HabitsList;
import ru.sergeykozhukhov.habits.notes.retrofit.helper.ApiService;
import ru.sergeykozhukhov.habits.notes.retrofit.helper.RetrofitClient;

public class HabitsFragment extends Fragment {

    private ApiService apiService;

    private HabitsDatabase habitsDB;

    private UserDao userDao;
    private HabitDao habitDao;

    private Executor executor;
    private ExecutorService executorService;

    private OnItemClickListener onItemClickListener; // обработчик нажатия на ячейку списка

    private HabitsAdapter habitsAdapter;
    private RecyclerView habitsRecyclerView;

    private Button synchronizeButton;
    private Button synchronizeWebButton;

    private JwtData jwtData;

    private List<Habit> listHabit;
    Disposable disposableListHabits;

    public static Fragment newInstance() {
        return new HabitsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.habits_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        habitsRecyclerView = view.findViewById(R.id.habits_recycler_view);
        habitsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        synchronizeButton = view.findViewById(R.id.replication_button);
        synchronizeWebButton = view.findViewById(R.id.backup_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initListeners();
        initViews();
        //updateHabitsAdapter();
        //updateHabitsAdapterRxJava();


    }

    private void initViews(){
        habitsAdapter = new HabitsAdapter(onItemClickListener);
        // habitsRecyclerView.setAdapter(habitsAdapter);
    }

    private void initListeners(){

        synchronizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jwtString = getClientToken();
                // Log.d("insertWeb", jwtString);
                JwtData jwt = new JwtData(jwtString);
                String rrr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hbnktc2l0ZS5vcmciLCJhdWQiOiJodHRwOlwvXC9hbnktc2l0ZS5jb20iLCJpYXQiOjEzNTY5OTk1MjQsIm5iZiI6MTM1NzAwMDAwMCwiZGF0YSI6eyJpZCI6IjciLCJmaXJzdG5hbWUiOiIxMjMiLCJsYXN0bmFtZSI6IjEyMyIsImVtYWlsIjoiMTIzIn19.pUc8VsdD5eXuW0xFLeP9lbPFlYBdY3ssPWaJ3MH_qD0";
                Toast.makeText(requireContext(), jwtString + " uuu", Toast.LENGTH_SHORT).show();
                jwtData = new JwtData(rrr);
                insertHabitWeb(jwtData);
                //updateHabitsAdapterFromWeb(jwt);
                //updateHabitsAdapterFromWeb(jwt);
            }
        });

        synchronizeWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateHabitsAdapterFromWeb(jwtData);


            }
        });

        onItemClickListener = new OnItemClickListener(){
            @Override
            public void onItemClick(Habit habit) {
                FragmentActivity activity = getActivity();
                if (activity instanceof DetailsHabitHolder){
                    ((DetailsHabitHolder)activity).showDetailsHabit(habit);
                }
            }
        };

        // habitsRecyclerView.setAdapter(new HabitsAdapter(Arrays.asList(HabitData.values()), onItemClickListener));
    }

    private void insertHabitWeb(JwtData jwtData){
        AuthUser authUser = new AuthUser(
                "email", "password"
        );

        //Log.d("insertWeb", jwtData.getJwt());
        apiService.insertHabits(authUser, jwtData.getJwt()).enqueue(new Callback<HabitsList>() {
            @Override
            public void onResponse(Call<HabitsList> call, Response<HabitsList> response) {
                Log.d("insertWeb", "333");
            }

            @Override
            public void onFailure(Call<HabitsList> call, Throwable t) {
                Log.d("insertWeb", "000");
            }
        });
    }

    private void initData(){

        apiService = RetrofitClient.getApiService();

        habitsDB = HabitsDatabase.getInstance(requireContext());

        userDao = habitsDB.getUserDao();
        habitDao = habitsDB.getHabitDao();

        executor = Executors.newSingleThreadExecutor();
        //executor = Executors.newFixedThreadPool(3);
        executorService = Executors.newSingleThreadExecutor();

        listHabit = null;


    }

    private void updateHabitsAdapter(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Habit> habits = habitDao.getAll();
                habitsAdapter.setHabits(habits);
            }
        });
    }

    /**
     * Disposable -
     *
     * Тип Disposable позволяет вызывать метод dispose,
     * означающий «Я закончил работать с этим ресурсом, мне больше не нужны данные»
     *
     * void dispose() - освобождает ресурс. Операция должна быть идемпотентной.
     * Идемпотентная операция может повторяться произвольным числом раз,
     * и результат будет таким же, как если бы это было сделано только один раз.
     *
     * bollean isDisposed() - true, если ресурс удален
     *
     * observeOn(AndroidSchedulers.mainThread()) - возвращение результата в ui поток
     *
     * Schedulers дают возможность указать, где и вероятно, когда выполнять задачи, связанные с работой цепочки Observable.
     */
    private void updateHabitsAdapterRxJava()
    {

        disposableListHabits = habitDao.getHabits()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Habit>>() {
                    @Override
                    public void accept(List<Habit> habits) throws Exception {
                        habitsAdapter.setHabits(habits);
                        Toast.makeText(requireContext(), "rx", Toast.LENGTH_SHORT).show();
                        habitsRecyclerView.setAdapter(habitsAdapter);

                    }


                });


    }

    private String getClientToken() {

        Future<Jwt> jwtFuture = executorService.submit(new Callable<Jwt>() {
            @Override
            public Jwt call() throws Exception {
                Jwt jwt = userDao.getToken();
                return jwt;
            }
        });

        String jwtString;

        try {
            jwtString = jwtFuture.get().getJwt();
        } catch (Exception ie) {
            jwtString = null;
            ie.printStackTrace(System.err);
        }

        executorService.shutdown();

        return jwtString;
    }

    private void updateHabitsAdapterFromWeb(JwtData jwt){

        /*apiService.getHabitList(jwt).enqueue(new Callback<HabitsList>() {
            @Override
            public void onResponse(Call<HabitsList> call, Response<HabitsList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Toast.makeText(requireContext(), "Success synchronize", Toast.LENGTH_SHORT).show();
                        insertHabits(response.body().getHabitList());
                    }
                }
            }

            @Override
            public void onFailure(Call<HabitsList> call, Throwable t) {

                    Toast.makeText(requireContext(), "Fail synchronize", Toast.LENGTH_SHORT).show();
            }
        });*/

        apiService.getHabitsRx(jwt)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<HabitsList>() {
                    @Override
                    public void onSuccess(HabitsList habitsList) {
                        Log.d("HabitsFragment", "onSuccess");
                        //Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HabitsFragment", "onError");

                        //Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

        /*apiService.getHabitsRx(jwt)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<HabitsList>() {
                    @Override
                    public void onSuccess(HabitsList habitsList) {
                        insertHabits(habitsList.getHabitList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });*/


    }

    private void insertHabits(final List<Habit> habits){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                habitDao.insert(habits);
            }
        });

    }


    /*
     * Интерфейс для взаимодействия с другим фрагментом
     * */
    public interface DetailsHabitHolder{
        void showDetailsHabit(@NonNull Habit habit);
    }


}
