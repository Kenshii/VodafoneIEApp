package pm.shane.mobileapi.error;

/**
 * Created by Shane on 29/04/2017.
 */

public class ErrorCodes {

    public enum Code {
        UNKNOWN,
        // SUCCESS,
        USER_NOT_REGISTERED,
        USER_SESSION_EXPIRED,
        USER_WRONG_PASSWORD,
        USER_LOCKED_TEMPORARY,
        USER_LOCKED_PERMANENT,
        USER_BLOCKED,
        LDAP_ERROR,
        USER_NOT_LOGGED_IN,
        OUT_OF_SERVICE,
        ICCS_NOT_AVAILABLE,
        NEEDS_SELF_SERVICE_PIN,
        WEB_TEXT_QUOTA_EXCEEDED,
        CANT_BUY_ADDON_NO_CREDIT,
        CANT_BUY_ADDON_CLOSED,
        CANT_BUY_ADDON_EXPIRED,
        CANT_BUY_ADDON_NOT_ELIGIBLE,
        USAGE_DETAILS_CONNECTION_FAILED,
        MISSING_LOGIN_DETAILS,
        FAILED_TO_GET_AUTO_LOGIN_TOKEN,
        NON_KEY_ACCOUNT_BALANCE_API_ERROR,
        FAILED_TO_LOG_IN,
        FAILED_TO_LOG_OUT
    }
    
}
