package pm.shane.mobileapi;

import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pm.shane.mobileapi.model.Sms;
import pm.shane.mobileapi.providers.general.response.Response;
import pm.shane.mobileapi.providers.general.response.balance.BalanceResponse;
import pm.shane.mobileapi.providers.general.response.login.LoginResponse;
import pm.shane.mobileapi.providers.general.response.plandetails.PostPayPlanDetailsResponse;
import pm.shane.mobileapi.providers.general.response.plandetails.PrePayPlanDetailsResponse;
import pm.shane.mobileapi.providers.general.response.webtext.WebTextResponse;

/**
 * Created by Shane on 11/05/2017.
 */

public interface Api {

    ExecutorService executorService = Executors.newCachedThreadPool();
    Gson gson = new Gson();

    void login(String login, String password, ApiCallback<LoginResponse> callback);
    void logout(ApiCallback<Response> callback); // Returns nothing special, unless an error.
    void sendWebText(Sms sms, ApiCallback<WebTextResponse> callback);
    void getRemainingWebTexts(ApiCallback<WebTextResponse> callback);
    void getBalance(ApiCallback<BalanceResponse> callback);
    void getPrePayPlanDetails(ApiCallback<PrePayPlanDetailsResponse> callback);
    void getPostPayPlanDetails(ApiCallback<PostPayPlanDetailsResponse> callback);

    String getBaseUrl();

}
