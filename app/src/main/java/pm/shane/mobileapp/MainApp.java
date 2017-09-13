package pm.shane.mobileapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Shane on 28/04/2017.
 */

public class MainApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MainApp.context = this;
        SessionManager.instantiatePreferences();
    }

    public static Context getContext() {
        return MainApp.context;
    }

}
