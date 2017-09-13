package pm.shane.mobileapi.providers.vodafone.response.webtext;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Shane on 12/05/2017.
 */

public class WebTextResponseData {

    @SerializedName("webtextsent")
    @Expose
    private boolean webTextSent;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("applimit")
    @Expose
    private Integer appLimit;
    @SerializedName("used")
    @Expose
    private Integer used;
    @SerializedName("renewaldate")
    @Expose
    private long renewalDate;

    public boolean getWebTextSent() {
        return webTextSent;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getAppLimit() {
        return appLimit;
    }

    public Integer getUsed() {
        return used;
    }

    public Date getRenewalDate() {
        return new Date(renewalDate);
    }

}
