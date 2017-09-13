package pm.shane.mobileapi.providers.general.response.plandetails;

import pm.shane.mobileapi.error.ApiError;
import pm.shane.mobileapi.providers.general.response.Response;
import pm.shane.mobileapi.providers.vodafone.response.plandetails.OutsidePlanCost;
import pm.shane.mobileapi.providers.vodafone.response.plandetails.PostPayPlanDetailsResponseData;

/**
 * Created by Shane on 10/06/2017.
 */

public class PostPayPlanDetailsResponse extends Response {

    private String planName;
    private String planCode;
    private String planDescription;
    private Double planPrice;
    private Integer internationalMins;
    private Integer internationalTexts;
    private Double dataAmount;
    private String dataUnits;
    private Boolean keyContact;
    private OutsidePlanCost[] outsidePlanCosts;

    public PostPayPlanDetailsResponse(PostPayPlanDetailsResponseData responseData) {
        this.planName = responseData.getPlanName();
        this.planCode = responseData.getPlanCode();
        this.planDescription = responseData.getPlanDescription();
        this.planPrice = responseData.getPlanPrice();
        this.internationalMins = responseData.getInternationalMins();
        this.internationalTexts = responseData.getInternationalTexts();
        this.dataAmount = responseData.getDataAmount();
        this.dataUnits = responseData.getDataUnits();
        this.keyContact = responseData.isKeyContact();
        this.outsidePlanCosts = responseData.getOutsidePlanCosts();
    }

    public PostPayPlanDetailsResponse(ApiError error) {
        super(error);
    }

    public String getName() {
        return planName;
    }

    public String getCode() {
        return planCode;
    }

    public String getDescription() {
        return planDescription;
    }

    public Double getPrice() {
        return planPrice;
    }

    public Integer getInternationalMins() {
        return internationalMins;
    }

    public Integer getInternationalTexts() {
        return internationalTexts;
    }

    public Double getDataAmount() {
        return dataAmount;
    }

    public String getDataUnits() {
        return dataUnits;
    }

    public Boolean isKeyContact() {
        return keyContact;
    }

    public OutsidePlanCost[] getOutsideCosts() {
        return outsidePlanCosts;
    }

}
