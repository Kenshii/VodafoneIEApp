package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public class UserNotRegisteredApiError extends ApiError {

    public UserNotRegisteredApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.USER_NOT_REGISTERED;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.user_not_registered);
    }

}
