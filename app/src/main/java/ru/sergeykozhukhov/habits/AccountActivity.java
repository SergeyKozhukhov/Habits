package ru.sergeykozhukhov.habits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import okhttp3.Authenticator;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.AccountManagerViewModel;
import ru.sergeykozhukhov.habits.presentation.HabitsListViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;
import ru.sergeykozhukhov.habits.presentation.view.account.AccountFragment;
import ru.sergeykozhukhov.habits.presentation.view.account.AuthenticationFragment;
import ru.sergeykozhukhov.habits.presentation.view.account.RegistrationFragment;
import ru.sergeykozhukhov.habits.presentation.view.account.EnterAccountFragment;

public class AccountActivity extends AppCompatActivity implements EnterAccountFragment.OnClientClickListener,
        AccountFragment.OnLogoutClickListener,
        AuthenticationFragment.OnLoginSuccess,
        RegistrationFragment.OnRegistrationSuccess {


    AccountManagerViewModel accountManagerViewModel;

    private Button openRegistrationButton;
    private Button openAuthenticationButton;

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

    private void setMvvm(){
        accountManagerViewModel = ViewModelProviders.of(this, new ViewModelFactory(this))
                .get(AccountManagerViewModel.class);

        accountManagerViewModel.getSuccessSingleLiveEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(AccountActivity.this, getString(idRes), Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.account_host, AccountFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        accountManagerViewModel.getErrorSingleLiveEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(AccountActivity.this, getString(idRes), Toast.LENGTH_SHORT).show();
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
