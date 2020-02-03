package ru.sergeykozhukhov.habits.base.presentation.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModel;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModelFactory;

public class AuthenticationFragment extends Fragment {

    private HabitsViewModel habitsViewModel;

    private EditText loginAuthEditText;
    private EditText passwordAuthEditText;

    private Button requestAuthButton;

    public static Fragment newInstance() {
        return new AuthenticationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auth_fragment, container, false);
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
        setupMvvm();

        String string = habitsViewModel.loadJwt().getJwt();
        Toast.makeText(requireContext(), string , Toast.LENGTH_SHORT).show();


    }

    private void initData() {

    }

    private void initViewListeners() {

        requestAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = loginAuthEditText.getText().toString();
                String password = passwordAuthEditText.getText().toString();

                Confidentiality confidentiality = new Confidentiality(login, password);

                // sendRequestAuth(confidentiality);
                sendRequsetAuthRx(confidentiality);


            }
        });
    }

    private void setupMvvm(){
        habitsViewModel = ViewModelProviders.of(this, new HabitsViewModelFactory(requireContext()))
                .get(HabitsViewModel.class);
    }


    private void sendRequestAuth(Confidentiality confidentiality) {
        habitsViewModel.authenticateClient(confidentiality);
    }

    private void sendRequsetAuthRx(Confidentiality confidentiality){
        Log.d("auth_frag", "requset");
        Disposable disposable = habitsViewModel.authenticateClientRx(confidentiality)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Authentication>() {
                    @Override
                    public void accept(Authentication authentication) throws Exception {
                        Log.d("fragment", "success jwt: "+authentication.getJwt() + " " + authentication.getMessage());
                        Toast.makeText(requireContext(), "success jwt: "+authentication.getJwt() + " " + authentication.getMessage(), Toast.LENGTH_SHORT).show();
                        habitsViewModel.saveJwt(new Jwt(authentication.getJwt()));

                    }
                });
    }

    private void sendRequsetAuthTryRx(Confidentiality confidentiality){

    }




}
