package ru.sergeykozhukhov.habits.presentation.view.account;

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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.viewmodel.RegistrationViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;

    private EditText firsnameRegEditText;
    private EditText lastnameRegEditText;
    private EditText loginRegEditText;
    private EditText passwordRegEditText;
    private EditText passwordConfirmationEditText;

    private Button requestRegButton;

    public static Fragment newInstance() {
        return new RegistrationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        firsnameRegEditText = view.findViewById(R.id.firstname_registration_edit_text);
        lastnameRegEditText = view.findViewById(R.id.lastname_registration_edit_text);
        loginRegEditText = view.findViewById(R.id.login__registration_edit_text);
        passwordRegEditText = view.findViewById(R.id.password_registration_edit_text);
        passwordConfirmationEditText = view.findViewById(R.id.password_confirmation_edit_text);

        requestRegButton =  view.findViewById(R.id.request_registration_button);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        setupMvvm();
        initViewListeners();
    }

    private void initData() {

    }

    private void initViewListeners() {

        requestRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationViewModel.registerClient(
                        firsnameRegEditText.getText().toString(),
                        lastnameRegEditText.getText().toString(),
                        loginRegEditText.getText().toString(),
                        passwordRegEditText.getText().toString(),
                        passwordConfirmationEditText.getText().toString()
                );
            }
        });
    }

    private void setupMvvm(){
        registrationViewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
                .get(RegistrationViewModel.class);

        registrationViewModel.getSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
                FragmentActivity activity = getActivity();
                if (activity instanceof RegistrationFragment.OnRegistrationSuccess) {
                    ((RegistrationFragment.OnRegistrationSuccess) activity).openAuthentication();
                }
            }
        });

        registrationViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnRegistrationSuccess {
        void openAuthentication();
    }


}
