package pm.shane.mobileapi.error;

import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;
import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;

/**
 * Created by Shane on 09/06/2017.
 */

public class NonKeyAccountBalanceApiError extends ApiError {

    public NonKeyAccountBalanceApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.NON_KEY_ACCOUNT_BALANCE_API_ERROR;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.not_allowed_view_balance);
    }

}
