package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public class OutOfServiceApiError extends ApiError {

    public OutOfServiceApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.OUT_OF_SERVICE;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.out_of_service);
    }

    @Override
    public boolean shouldRetry() {
        return true;
    }

}
