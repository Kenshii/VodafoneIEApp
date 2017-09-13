package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 03/05/2017.
 */

public class FailedToGetAutoLoginTokenApiError extends ApiError {

    public FailedToGetAutoLoginTokenApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.FAILED_TO_GET_AUTO_LOGIN_TOKEN;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.failed_to_get_auto_login_token);
    }

    @Override
    public boolean shouldRetry() {
        return true;
    }

}
