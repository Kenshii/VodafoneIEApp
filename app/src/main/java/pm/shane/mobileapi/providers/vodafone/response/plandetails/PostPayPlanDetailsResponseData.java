package pm.shane.mobileapi.providers.vodafone.response.plandetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shane on 10/06/2017.
 */

public class PostPayPlanDetailsResponseData {

    @SerializedName("plan")
    @Expose
    private String planName;
    @SerializedName("plancode")
    @Expose
    private String planCode;
    @SerializedName("plandesc")
    @Expose
    private String planDescription;
    @SerializedName("planprice")
    @Expose
    private Double planPrice;
    @SerializedName("minsinternational")
    @Expose
    private Integer internationalMins;
    @SerializedName("plantextinternational")
    @Expose
    private Integer internationalTexts;
    @SerializedName("plandata")
    @Expose
    private String dataAmount;
    @SerializedName("plandataunits")
    @Expose
    private String dataUnits;
    @SerializedName("keycontact")
    @Expose
    private Boolean keyContact;
    @SerializedName("outsideplancosts")
    @Expose
    private OutsidePlanCost[] outsidePlanCosts;

    public String getPlanName() {
        return planName;
    }

    public String getPlanCode() {
        return planCode;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public Double getPlanPrice() {
        return planPrice;
    }

    public Integer getInternationalMins() {
        return internationalMins;
    }

    public Integer getInternationalTexts() {
        return internationalTexts;
    }

    public Double getDataAmount() {
        return Double.valueOf(dataAmount);
    }

    public String getDataUnits() {
        return dataUnits;
    }

    public Boolean isKeyContact() {
        return keyContact;
    }

    public OutsidePlanCost[] getOutsidePlanCosts() {
        return outsidePlanCosts;
    }

}
