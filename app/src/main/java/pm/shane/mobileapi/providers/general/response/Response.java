package pm.shane.mobileapi.providers.general.response;

import pm.shane.mobileapi.error.ApiError;

/**
 * Created by Shane on 12/05/2017.
 */

public class Response {

    private ApiError error;

    public Response() {
        this(null);
    }

    public Response(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }

}
