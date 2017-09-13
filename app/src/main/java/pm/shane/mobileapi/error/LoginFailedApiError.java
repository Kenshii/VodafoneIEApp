package pm.shane.mobileapi.error;

import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;
import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;

/**
 * Created by Shane on 13/06/2017.
 */

public class LoginFailedApiError extends ApiError {

    public LoginFailedApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.FAILED_TO_LOG_IN;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.failed_to_log_in);
    }

}
