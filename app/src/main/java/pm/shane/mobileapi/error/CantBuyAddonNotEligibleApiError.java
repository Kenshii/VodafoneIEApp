package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public class CantBuyAddonNotEligibleApiError extends ApiError {

    public CantBuyAddonNotEligibleApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.CANT_BUY_ADDON_NOT_ELIGIBLE;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.cant_buy_addon_not_eligible);
    }

}
