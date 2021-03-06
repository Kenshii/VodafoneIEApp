package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public class UsageDetailsConnectionFailedApiError extends ApiError {

    public UsageDetailsConnectionFailedApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.USAGE_DETAILS_CONNECTION_FAILED;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.usage_details_connection_failed);
    }

    @Override
    public boolean shouldRetry() {
        return true;
    }

}
