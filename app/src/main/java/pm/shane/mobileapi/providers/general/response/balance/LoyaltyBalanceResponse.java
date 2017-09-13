package pm.shane.mobileapi.providers.general.response.balance;

import pm.shane.mobileapi.error.ApiError;
import pm.shane.mobileapi.providers.general.response.Response;

/**
 * Created by Shane on 14/06/2017.
 */

public class LoyaltyBalanceResponse extends Response {

    private int loyaltyBalance;

    public LoyaltyBalanceResponse(ApiError error) {
        super(error);
    }

    public LoyaltyBalanceResponse(int loyaltyBalance) {
        this.loyaltyBalance = loyaltyBalance;
    }

    public int getLoyaltyBalance() {
        return loyaltyBalance;
    }

}
