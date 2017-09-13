package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public class UserLockedPermanentApiError extends ApiError {

    public UserLockedPermanentApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.USER_LOCKED_PERMANENT;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.user_permanently_locked);
    }

}
