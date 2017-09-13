package pm.shane.mobileapi.error;

import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;
import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;

/**
 * Created by Shane on 13/06/2017.
 */

public class LogoutFailedApiError extends ApiError {

    public LogoutFailedApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.FAILED_TO_LOG_OUT;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.failed_to_log_out);
    }

}
