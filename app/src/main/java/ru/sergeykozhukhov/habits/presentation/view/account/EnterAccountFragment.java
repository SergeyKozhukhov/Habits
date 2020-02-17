package ru.sergeykozhukhov.habits.presentation.view.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import ru.sergeykozhukhov.habitData.R;

public class EnterAccountFragment extends Fragment{


    private Button openRegistrationButton;
    private Button openAuthenticationButton;

    public static Fragment newInstance() {
        return new EnterAccountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enter_account_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        openRegistrationButton = view.findViewById(R.id.registration_account_button);
        openAuthenticationButton = view.findViewById(R.id.authentication_account_button);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initListeners();
       }


    private void initListeners(){
        openRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity instanceof EnterAccountFragment.OnClientClickListener) {
                    ((EnterAccountFragment.OnClientClickListener)activity).onRegistrationClick();

                }
            }
        });

        openAuthenticationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity instanceof EnterAccountFragment.OnClientClickListener) {
                    ((EnterAccountFragment.OnClientClickListener)activity).onAuthenticationClick();

                }
            }
        });
    }

    public interface OnClientClickListener{
        void onRegistrationClick();
        void onAuthenticationClick();
    }


}
