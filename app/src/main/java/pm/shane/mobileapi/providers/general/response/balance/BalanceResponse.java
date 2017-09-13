package pm.shane.mobileapi.providers.general.response.balance;

import pm.shane.mobileapi.error.ApiError;
import pm.shane.mobileapi.providers.general.response.Response;

/**
 * Created by Shane on 12/05/2017.
 */

public class BalanceResponse extends Response {

    private double balance;

    public BalanceResponse(ApiError error) {
        super(error);
    }

    public BalanceResponse(double balance) {
        this(null);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

}
