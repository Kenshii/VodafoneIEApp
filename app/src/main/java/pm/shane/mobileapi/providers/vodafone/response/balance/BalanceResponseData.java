package pm.shane.mobileapi.providers.vodafone.response.balance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shane on 12/05/2017.
 */

public class BalanceResponseData {

    @SerializedName(value = "creditbalance", alternate = {"accountbalance"})
    @Expose
    private Double balance;

    public Double getBalance() {
        return balance;
    }

}
