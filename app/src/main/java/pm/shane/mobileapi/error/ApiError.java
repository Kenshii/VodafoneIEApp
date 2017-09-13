package pm.shane.mobileapi.error;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public abstract class ApiError {

    VodaResponse response;

    ApiError(VodaResponse response) {
        this.response = response;
    }

    public VodaResponse getResponse() {
        return this.response;
    }

    public abstract ErrorCodes.Code getCode();

    public abstract String getMessage();

    public void handle(final Activity callerActivity) {
        callerActivity.runOnUiThread(() -> new AlertDialog.Builder(callerActivity)
            .setTitle("Failed")
            .setMessage(getMessage())
            .setCancelable(false)
            .setPositiveButton(MainApp.getContext().getString(android.R.string.ok), (dialog, which) -> {})
            .show());
    }

    public boolean shouldRetry() {
        return false;
    }

}
