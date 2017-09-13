package pm.shane.mobileapi.providers.vodafone.response.plandetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shane on 11/06/2017.
 */

public class OutsidePlanCost {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("cost")
    @Expose
    private String cost;

    public String getLabel() {
        return label;
    }

    public String getCost() {
        return cost;
    }

}
