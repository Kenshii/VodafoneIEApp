package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public class IccsNotAvailableApiError extends ApiError {

    public IccsNotAvailableApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.ICCS_NOT_AVAILABLE;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.iccs_not_available);
    }

    @Override
    public boolean shouldRetry() {
        return true;
    }

}
