package pm.shane.mobileapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Shane on 30/04/2017.
 */

public class PhoneStatus {

    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager)MainApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isConnected() {
        NetworkInfo networkInfo = getActiveNetworkInfo();
        return networkInfo != null && getActiveNetworkInfo().isConnected();
    }

}
