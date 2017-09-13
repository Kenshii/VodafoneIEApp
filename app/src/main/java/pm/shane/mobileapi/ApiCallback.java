package pm.shane.mobileapi;

import okhttp3.Call;
import pm.shane.mobileapi.providers.general.response.Response;

/**
 * Created by Shane on 12/05/2017.
 */

public interface ApiCallback<T extends Response> {

    void onSuccess(T response);
    void onFailure(T response);
    void onException(Call call, Exception e);

}
