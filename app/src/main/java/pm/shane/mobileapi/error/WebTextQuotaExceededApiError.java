package pm.shane.mobileapi.error;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;

/**
 * Created by Shane on 01/05/2017.
 */

public class WebTextQuotaExceededApiError extends ApiError {

    public WebTextQuotaExceededApiError(VodaResponse response) {
        super(response);
    }

    @Override
    public ErrorCodes.Code getCode() {
        return ErrorCodes.Code.WEB_TEXT_QUOTA_EXCEEDED;
    }

    @Override
    public String getMessage() {
        return MainApp.getContext().getString(R.string.web_text_quota_exceeded);
    }

}
