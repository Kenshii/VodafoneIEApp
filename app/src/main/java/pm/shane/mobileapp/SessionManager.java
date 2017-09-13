package pm.shane.mobileapp;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.FormBody;
import pm.shane.mobileapi.model.User;
import pm.shane.crypto.AESEncrypted;
import pm.shane.crypto.BaseEncryption;

/**
 * Created by Shane on 29/04/2017.
 */

public class SessionManager {

    private static SharedPreferences preferences;
    private static User loggedInUser;

    // Called once on application start
    public static synchronized void instantiatePreferences() {
        if (SessionManager.preferences == null) {
            SessionManager.preferences = MainApp.getContext().getSharedPreferences("SessionPref", Context.MODE_PRIVATE);
        }
    }

    public static void saveUserDetails(String msisdn, String password) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("msisdn", msisdn);
        editor.putString("password", password);
        editor.apply();
    }

    public static void setCookie(String cookie) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cookie", cookie);
        editor.apply();
    }

    public static FormBody.Builder getRequestBodyForEndpoint(String endpoint) {
        String msisdn = preferences.getString("msisdn", "");
        String password = preferences.getString("password", "");
        String cookie = preferences.getString("cookie", "");
        AESEncrypted aesEncrypted = BaseEncryption.aesEncryptString(String.format("%s|upwd|%s|%s|%s|appVersion", msisdn, password, cookie, endpoint));
        return new FormBody.Builder()
                .add("token", aesEncrypted.getEncryptedToken())
                .add("tokenkey", aesEncrypted.getEncryptedTokenKey())
                .add("tokeniv", aesEncrypted.getTokenIv());
    }

    public static void setLoggedInUser(User loggedInUser) {
        SessionManager.loggedInUser = loggedInUser;
    }
    public static User getLoggedInUser() {
        return SessionManager.loggedInUser;
    }

}
