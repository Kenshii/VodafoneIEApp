package pm.shane.mobileapi.providers.vodafone.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pm.shane.mobileapi.error.ApiError;
import pm.shane.mobileapi.error.CantBuyAddonClosedApiError;
import pm.shane.mobileapi.error.CantBuyAddonExpiredApiError;
import pm.shane.mobileapi.error.CantBuyAddonNoCreditApiError;
import pm.shane.mobileapi.error.CantBuyAddonNotEligibleApiError;
import pm.shane.mobileapi.error.FailedToGetAutoLoginTokenApiError;
import pm.shane.mobileapi.error.IccsNotAvailableApiError;
import pm.shane.mobileapi.error.LdapApiError;
import pm.shane.mobileapi.error.LoginFailedApiError;
import pm.shane.mobileapi.error.LogoutFailedApiError;
import pm.shane.mobileapi.error.MissingLoginDetailsApiError;
import pm.shane.mobileapi.error.NeedsSelfServicePinApiError;
import pm.shane.mobileapi.error.NonKeyAccountBalanceApiError;
import pm.shane.mobileapi.error.OutOfServiceApiError;
import pm.shane.mobileapi.error.UnknownApiError;
import pm.shane.mobileapi.error.UsageDetailsConnectionFailedApiError;
import pm.shane.mobileapi.error.UserBlockedApiError;
import pm.shane.mobileapi.error.UserLockedPermanentApiError;
import pm.shane.mobileapi.error.UserLockedTemporaryApiError;
import pm.shane.mobileapi.error.UserNotRegisteredApiError;
import pm.shane.mobileapi.error.UserSessionExpiredApiError;
import pm.shane.mobileapi.error.UserWrongPasswordApiError;
import pm.shane.mobileapi.error.WebTextQuotaExceededApiError;
import pm.shane.mobileapi.providers.general.response.Response;

/**
 * Created by Shane on 29/04/2017.
 */

public class VodaResponse<T> extends Response {

    private static final int MAX_PASSWORD_ATTEMPTS = 5;
    @SerializedName("errorcode")
    @Expose
    private String errorCode = "";
    @SerializedName("errordesc")
    @Expose
    private String errorDesc = "";
    @SerializedName("myvregistered")
    @Expose
    private boolean registered = true;
    @SerializedName("locked")
    @Expose
    private boolean locked = false;
    @SerializedName("sessionexpired")
    @Expose
    private boolean sessionExpired = false;
    @SerializedName("incorrectpassword")
    @Expose
    private boolean incorrectPassword = false;
    @SerializedName("incorrectpasswordattempts")
    @Expose
    private int incorrectPasswordAttempts;
    @SerializedName("barred")
    @Expose
    private boolean barred = false;
    @SerializedName("subscriberactive")
    @Expose
    private boolean subscriberActive = true;
    @SerializedName("blocked")
    @Expose
    private boolean blocked = false;
    @SerializedName("ldaperror")
    @Expose
    private boolean ldapError = false;
    @SerializedName("loggedin")
    @Expose
    private boolean loggedIn = true;
    @SerializedName("loggedout")
    @Expose
    private boolean loggedOut = true;
    @SerializedName("outofservice")
    @Expose
    private boolean outOfService = false;
    @SerializedName("nonkeyaccount")
    @Expose
    private boolean nonKeyAccount = false;
    @SerializedName("iccsunavailable")
    @Expose
    private boolean iccsUnavailable = false;
    @SerializedName("level2verified")
    @Expose
    private boolean level2Verified = true;
    @SerializedName("status")
    @Expose
    private String status;
    private ApiError apiError;
    @SerializedName("data")
    @Expose
    private T[] data;

    private enum VodaResponseCodes {
        FAILED_TO_GET_AUTO_LOGIN_TOKEN ("FAILURE.services.WebUserLogin.Manuallogin"),
        WEB_TEXT_QUOTA_EXCEEDED ("FAILURE.messaging.webtext.quotaLimit"),
        NO_NBA_ERROR ("FAILURE.mcare.action.BaseAction.nbaexecute.nooffer"),
        CANT_BUY_ADDON_NO_CREDIT ("INSUFFICENT.CREDIT"),
        CANT_BUY_ADDON_CLOSED ("ADDON.CLOSED"),
        CANT_BUY_ADDON_EXPIRED ("ADDON.EXPIRED"),
        CANT_BUY_ADDON_NOT_ELIGIBLE ("ADDON.NOT.ELIGIBLE"),
        USAGE_DETAILS_CONNECTION_FAILED ("FAILURE.mcare.getPrepayUsage"),
        MISSING_LOGIN_DETAILS ("WebUserLogin.bothrequired");

        private final String text;

        VodaResponseCodes(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    private String getErrorCode() {
        return errorCode;
    }

    private String getErrorDesc() {
        return errorDesc;
    }

    public boolean isRegistered() {
        return registered;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isSessionExpired() {
        return sessionExpired;
    }

    public boolean isIncorrectPassword() {
        return incorrectPassword;
    }

    public int getIncorrectPasswordAttempts() {
        return incorrectPasswordAttempts;
    }

    public int getRemainingPasswordAttempts() {
        return MAX_PASSWORD_ATTEMPTS - getIncorrectPasswordAttempts();
    }

    public boolean isBarred() {
        return barred;
    }

    public boolean isSubscriberActive() {
        return subscriberActive;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public boolean isLdapError() {
        return ldapError;
    }

    public Boolean isLoggedIn() {
        return loggedIn;
    }

    public Boolean isLoggedOut() {
        return loggedOut;
    }

    public boolean isOutOfService() {
        return outOfService;
    }

    public boolean isNonKeyAccount() {
        return nonKeyAccount;
    }

    public boolean isIccsUnavailable() {
        return iccsUnavailable;
    }

    public boolean isLevel2Verified() {
        return level2Verified;
    }

    private String getStatus() {
        return status;
    }

    @Override
    public ApiError getError() { // Non-zero means error occurred.
        if (apiError != null) {
            return apiError;
        }
        apiError = new UnknownApiError(this);
        if (getStatus().equals("ok")) {
            apiError = null; // Always success = 0.
        } else if (!isRegistered()) {
            apiError = new UserNotRegisteredApiError(this);
        } else if (isLocked()) {
            apiError = new UserLockedTemporaryApiError(this);
        } else if (isSessionExpired()) {
            apiError = new UserSessionExpiredApiError(this);
        } else if (isIncorrectPassword()) {
            if (getRemainingPasswordAttempts() > 0) {
                apiError = new UserWrongPasswordApiError(this);
            } else {
                apiError = new UserLockedTemporaryApiError(this);
            }
        } else if (isBarred()) {
            apiError = new UserLockedPermanentApiError(this);
        } else if (!isSubscriberActive()) {
            apiError = new UserLockedPermanentApiError(this);
        } else if (isBlocked()) {
            apiError = new UserBlockedApiError(this);
        } else if (isLdapError()) {
            apiError = new LdapApiError(this);
        } else if (!isLoggedIn()) {
            apiError = new LoginFailedApiError(this);
        } else if (!isLoggedOut()) {
            apiError = new LogoutFailedApiError(this);
        }
        else if (isOutOfService()) {
            apiError = new OutOfServiceApiError(this);
        } else if (isIccsUnavailable()) {
            apiError = new IccsNotAvailableApiError(this);
        } else if (isNonKeyAccount()) {
            apiError = new NonKeyAccountBalanceApiError(this);
        }
        else if (!isLevel2Verified()) {
            apiError = new NeedsSelfServicePinApiError(this);
        } else if (getErrorCode().equals(VodaResponseCodes.WEB_TEXT_QUOTA_EXCEEDED.toString())) {
            apiError = new WebTextQuotaExceededApiError(this);
        } else if (getErrorCode().equals(VodaResponseCodes.CANT_BUY_ADDON_NO_CREDIT.toString())) {
            apiError = new CantBuyAddonNoCreditApiError(this);
        } else if (getErrorCode().equals(VodaResponseCodes.CANT_BUY_ADDON_CLOSED.toString())) {
            apiError = new CantBuyAddonClosedApiError(this);
        } else if (getErrorCode().equals(VodaResponseCodes.CANT_BUY_ADDON_EXPIRED.toString())) {
            apiError = new CantBuyAddonExpiredApiError(this);
        } else if (getErrorCode().equals(VodaResponseCodes.CANT_BUY_ADDON_NOT_ELIGIBLE.toString())) {
            apiError = new CantBuyAddonNotEligibleApiError(this);
        } else if (getErrorCode().equals(VodaResponseCodes.USAGE_DETAILS_CONNECTION_FAILED.toString())) {
            apiError = new UsageDetailsConnectionFailedApiError(this);
        }  else if (getErrorCode().equals(VodaResponseCodes.MISSING_LOGIN_DETAILS.toString())) {
            apiError = new MissingLoginDetailsApiError(this);
        } else if (getErrorDesc().equals(VodaResponseCodes.FAILED_TO_GET_AUTO_LOGIN_TOKEN.toString())) { // desc isn't a typo. api is weird.
            apiError = new FailedToGetAutoLoginTokenApiError(this);
        }
        return apiError;
    }

    public T getData() {
        if (data == null || data.length == 0) {
            return null;
        }
        return data[0];
    }

    public T[] getDataArray() {
        return data;
    }

}
