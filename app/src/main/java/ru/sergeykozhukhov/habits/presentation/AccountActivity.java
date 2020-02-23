package ru.sergeykozhukhov.habits.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.IntentFilter;
import android.os.Bundle;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AccountManagerViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;
import ru.sergeykozhukhov.habits.presentation.view.account.AccountFragment;
import ru.sergeykozhukhov.habits.presentation.view.account.AuthenticationFragment;
import ru.sergeykozhukhov.habits.presentation.view.account.RegistrationFragment;
import ru.sergeykozhukhov.habits.presentation.view.account.EnterAccountFragment;

public class AccountActivity extends AppCompatActivity implements EnterAccountFragment.OnClientClickListener,
        AccountFragment.OnLogoutClickListener,
        AuthenticationFragment.OnLoginSuccess,
        RegistrationFragment.OnRegistrationSuccess {

    private AccountManagerViewModel accountManagerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);



        setMvvm();
        /*if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.account_host, EnterAccountFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setMvvm(){
        accountManagerViewModel = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(AccountManagerViewModel.class);

        accountManagerViewModel.getSuccessSingleLiveEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.account_host, AccountFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        accountManagerViewModel.getErrorSingleLiveEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.account_host, EnterAccountFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
        accountManagerViewModel.isLogInClient();

    }

    @Override
    public void onRegistrationClick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.account_host, RegistrationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAuthenticationClick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.account_host, AuthenticationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick() {
        this.finish();
    }

    @Override
    public void openAccount() {
        accountManagerViewModel.isLogInClient();
    }

    @Override
    public void openAuthentication() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.account_host, AuthenticationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}
