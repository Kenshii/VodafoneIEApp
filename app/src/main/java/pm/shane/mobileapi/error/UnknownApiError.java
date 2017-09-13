package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public class UnknownApiError extends ApiError {

    public UnknownApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.UNKNOWN;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.unknown_error);
    }

}
