package ru.sergeykozhukhov.habits.presentation.element;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

// https://codeunplug.com/check-internet-connection-status-using-broadcast-receiver-in-android/
public class NetworkChangeReceiver extends BroadcastReceiver {

    private boolean isConnection = false  ;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(context, "Wifi enabled", Toast.LENGTH_LONG).show();
                isConnection = true;

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(context, "Mobile data enabled", Toast.LENGTH_LONG).show();
                isConnection = true;
            }
        } else {
            Toast.makeText(context, "No internet is available", Toast.LENGTH_LONG).show();
            isConnection = false;
        }
    }

    public boolean getIsConnection(){
        return isConnection;
    }
}
