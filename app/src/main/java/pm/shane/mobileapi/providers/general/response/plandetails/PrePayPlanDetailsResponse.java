package pm.shane.mobileapi.providers.general.response.plandetails;

import java.util.Date;

import pm.shane.mobileapi.error.ApiError;
import pm.shane.mobileapi.providers.general.response.Response;

/**
 * Created by Shane on 09/06/2017.
 */

public class PrePayPlanDetailsResponse extends Response {

    private String name;
    private String code;
    private Date expirationDate;
    private boolean active;

    public PrePayPlanDetailsResponse(ApiError error) {
        super(error);
    }

    public PrePayPlanDetailsResponse(String name, String code, Date expirationDate, boolean active) {
        this(null);
        this.name = name;
        this.code = code;
        this.expirationDate = expirationDate;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public boolean isActive() {
        return active;
    }

}
