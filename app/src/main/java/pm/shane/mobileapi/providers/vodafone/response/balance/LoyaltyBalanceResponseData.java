package pm.shane.mobileapi.providers.vodafone.response.balance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shane on 14/06/2017.
 */

public class LoyaltyBalanceResponseData {

    @SerializedName("cherrypointsbalance")
    @Expose
    private Integer loyaltyBalance;
    @SerializedName("optedin")
    @Expose
    private Boolean optedIn;

    public Integer getLoyaltyBalance() {
        return loyaltyBalance;
    }

    public Boolean isOptedIn() {
        return optedIn;
    }

}
