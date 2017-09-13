package pm.shane.mobileapi.providers.vodafone.response.plandetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Shane on 10/06/2017.
 */

public class PrePayPlanDetailsResponseData {

    @SerializedName("topupoffer")
    @Expose
    private String topUpOffer;
    @SerializedName("topupoffercode")
    @Expose
    private String topUpOfferCode;
    @SerializedName("topupofferexpirydate")
    @Expose
    private Long topUpOfferExpiryDate;
    @SerializedName("offerstatus")
    @Expose
    private String offerStatus;

    public String getTopUpOfferName() {
        return topUpOffer;
    }

    public String getTopUpOfferCode() {
        return topUpOfferCode;
    }

    public Date getTopUpOfferExpirationDate() {
        return new Date(topUpOfferExpiryDate);
    }

    public Boolean isOfferActive() {
        return offerStatus.equalsIgnoreCase("active");
    }

}