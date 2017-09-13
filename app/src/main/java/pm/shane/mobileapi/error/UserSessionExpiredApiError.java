package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public class UserSessionExpiredApiError extends ApiError {

    public UserSessionExpiredApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.USER_SESSION_EXPIRED;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.user_session_expired);
    }

}
