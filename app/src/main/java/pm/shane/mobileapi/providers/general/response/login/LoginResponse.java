package pm.shane.mobileapi.providers.general.response.login;

import pm.shane.mobileapi.error.ApiError;
import pm.shane.mobileapi.providers.general.response.Response;
import pm.shane.mobileapi.providers.vodafone.response.login.LoginResponseData;

/**
 * Created by Shane on 12/05/2017.
 */

public class LoginResponse extends Response {

    private String customerName;
    private String customerType;
    private String promoWinner;

    public LoginResponse(ApiError error) {
        super(error);
    }

    public LoginResponse(LoginResponseData responseData) {
        this((ApiError)null);
        this.customerName = responseData.getCustName();
        this.customerType = responseData.getCustTypeCode();
        this.promoWinner = responseData.getPromoWinner();
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getPromoWinner() {
        return promoWinner;
    }
}
