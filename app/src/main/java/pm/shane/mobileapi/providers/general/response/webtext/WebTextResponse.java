package pm.shane.mobileapi.providers.general.response.webtext;

import java.util.Date;

import pm.shane.mobileapi.error.ApiError;
import pm.shane.mobileapi.providers.general.response.Response;

/**
 * Created by Shane on 12/05/2017.
 */

public class WebTextResponse extends Response {

    private int nationalRemaining;
    private int internationalRemaining;
    private Date renewalDate;

    public WebTextResponse(ApiError error) {
        super(error);
    }

    public WebTextResponse(int nationalRemaining, int internationalRemaining, Date renewalDate) {
        this(null);
        this.nationalRemaining = nationalRemaining;
        this.internationalRemaining = internationalRemaining;
        this.renewalDate = renewalDate;
    }

    public int getNationalRemaining() {
        return nationalRemaining;
    }

    public int getInternationalRemaining() {
        return internationalRemaining;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

}
