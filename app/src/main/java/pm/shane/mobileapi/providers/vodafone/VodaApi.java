package pm.shane.mobileapi.providers.vodafone;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ListIterator;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pm.shane.mobileapi.Api;
import pm.shane.mobileapi.ApiCallback;
import pm.shane.mobileapi.client.WebClientManager;
import pm.shane.mobileapi.model.Sms;
import pm.shane.mobileapi.model.User;
import pm.shane.mobileapi.providers.general.response.balance.BalanceResponse;
import pm.shane.mobileapi.providers.general.response.balance.LoyaltyBalanceResponse;
import pm.shane.mobileapi.providers.general.response.login.LoginResponse;
import pm.shane.mobileapi.providers.general.response.plandetails.PostPayPlanDetailsResponse;
import pm.shane.mobileapi.providers.general.response.plandetails.PrePayPlanDetailsResponse;
import pm.shane.mobileapi.providers.general.response.webtext.WebTextResponse;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;
import pm.shane.mobileapi.providers.vodafone.response.balance.BalanceResponseData;
import pm.shane.mobileapi.providers.vodafone.response.balance.LoyaltyBalanceResponseData;
import pm.shane.mobileapi.providers.vodafone.response.login.LoginResponseData;
import pm.shane.mobileapi.providers.vodafone.response.plandetails.PostPayPlanDetailsResponseData;
import pm.shane.mobileapi.providers.vodafone.response.plandetails.PrePayPlanDetailsResponseData;
import pm.shane.mobileapi.providers.vodafone.response.webtext.WebTextResponseData;
import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;
import pm.shane.mobileapp.SessionManager;

/**
 * Created by Shane on 11/05/2017.
 */

public class VodaApi implements Api {

    @Override
    public void login(final String login, final String password, final ApiCallback<LoginResponse> callback) {
        executorService.execute(() -> {
            Call loginCall = null;
            try {
                String loginEndPoint = "/mcare/login.shtml";
                WebClientManager.getCookieJar().clear(); // If invalid cookie exists, it won't fix on login.
                SessionManager.saveUserDetails(login, password);
                RequestBody loginRequestBody = SessionManager.getRequestBodyForEndpoint(loginEndPoint)
                        .build();
                Request loginRequest = new Request.Builder()
                        .url(getBaseUrl() + loginEndPoint)
                        .post(loginRequestBody)
                        .build();
                loginCall = WebClientManager.getWebClient().newCall(loginRequest);
                Response webLoginResponse;
                VodaResponse<LoginResponseData> vodaLoginResponse;
                webLoginResponse = loginCall.execute();
                vodaLoginResponse = gson.fromJson(webLoginResponse.body().string(), new TypeToken<VodaResponse<LoginResponseData>>() {}.getType());
                LoginResponse generalResponse;
                if (vodaLoginResponse.getError() != null) {
                    generalResponse = new LoginResponse(vodaLoginResponse.getError());
                    callback.onFailure(generalResponse);
                    return;
                }
                List<Cookie> cookies = Cookie.parseAll(webLoginResponse.request().url(), webLoginResponse.headers());
                for (Cookie cookie : cookies) {
                    if (!cookie.name().equals("JSESSIONID")) {
                        continue;
                    }
                    SessionManager.setCookie(cookie.value());
                }
                User.CustomerType customerType = User.CustomerType.POST_PAY;
                if (vodaLoginResponse.getData().getCustTypeCode().startsWith("RTG")) {
                    customerType = User.CustomerType.PRE_PAY;
                }
                // SessionManager.saveUserDetails("353857235267", "");
                // Findings: msisdn must match cookie. password doesn't matter.
                SessionManager.setLoggedInUser(new User(customerType, vodaLoginResponse.getData().getCustName()));
                generalResponse = new LoginResponse(vodaLoginResponse.getData());
                callback.onSuccess(generalResponse);
            } catch (Exception e) {
                callback.onException(loginCall, e);
            }
        });
    }

    @Override
    public void logout(ApiCallback<pm.shane.mobileapi.providers.general.response.Response> callback) {
        executorService.execute(() -> {
            Call logoutCall = null;
            try {
                String logoutEndPoint = "/mcare/logout.shtml";
                RequestBody logoutRequestBody = SessionManager.getRequestBodyForEndpoint(logoutEndPoint)
                        .build();
                Request logoutRequest = new Request.Builder()
                        .url(getBaseUrl() + logoutEndPoint)
                        .post(logoutRequestBody)
                        .build();
                logoutCall = WebClientManager.getWebClient().newCall(logoutRequest);
                Response webLogoutResponse;
                VodaResponse vodaLogoutResponse;
                webLogoutResponse = logoutCall.execute();
                vodaLogoutResponse = gson.fromJson(webLogoutResponse.body().string(), new TypeToken<VodaResponse>() {}.getType());
                pm.shane.mobileapi.providers.general.response.Response generalResponse;
                if (vodaLogoutResponse.getError() != null) {
                    generalResponse = new pm.shane.mobileapi.providers.general.response.Response(vodaLogoutResponse.getError());
                    callback.onFailure(generalResponse);
                    return;
                }
                // Findings: msisdn must match cookie. password doesn't matter.
                SessionManager.setLoggedInUser(null);
                WebClientManager.getCookieJar().clear(); // If invalid cookie exists, it won't fix on login.
                SessionManager.saveUserDetails(null, null);
                generalResponse = new pm.shane.mobileapi.providers.general.response.Response();
                callback.onSuccess(generalResponse);
            } catch (Exception e) {
                callback.onException(logoutCall, e);
            }
        });
    }

    @Override
    public void sendWebText(final Sms sms, final ApiCallback<WebTextResponse> callback) {
        executorService.execute(() -> {
            String sendSmsEndPoint = "/mcare/webtext.shtml";
            FormBody.Builder sendSmsBodyBuilder = SessionManager.getRequestBodyForEndpoint(sendSmsEndPoint);
            ListIterator<String> recipients = sms.getRecipients().listIterator();
            while (recipients.hasNext()) {
                sendSmsBodyBuilder.add("recipient_" + recipients.nextIndex(), recipients.next());
            }
            sendSmsBodyBuilder.add("message", sms.getMessage());
            Request sendSmsRequest = new Request.Builder()
                .url(getBaseUrl() + sendSmsEndPoint)
                .post(sendSmsBodyBuilder.build())
                .build();
            Call sendSmsRequestCall = WebClientManager.getWebClient().newCall(sendSmsRequest);
            Response webTextResponse;
            VodaResponse<WebTextResponseData> vodaWebTextResponse;
            try {
                webTextResponse = sendSmsRequestCall.execute();
                vodaWebTextResponse = gson.fromJson(webTextResponse.body().string(), new TypeToken<VodaResponse<WebTextResponseData>>(){}.getType());
            } catch (IOException e) {
                callback.onException(sendSmsRequestCall, e);
                return;
            }
            WebTextResponse generalResponse;
            if (vodaWebTextResponse.getError() != null) {
                generalResponse = new WebTextResponse(vodaWebTextResponse.getError());
                callback.onFailure(generalResponse);
                return;
            }
            int remainingWebTexts = vodaWebTextResponse.getData().getTotal() - vodaWebTextResponse.getData().getUsed();
            generalResponse = new WebTextResponse(remainingWebTexts, remainingWebTexts, vodaWebTextResponse.getData().getRenewalDate());
            callback.onSuccess(generalResponse);
        });
    }

    @Override
    public void getRemainingWebTexts(final ApiCallback<WebTextResponse> callback) {
        executorService.execute(() -> {
            Call sendSmsRequestCall = null;
            try {
                String webTextSetupEndPoint = "/mcare/webtextsetup.shtml";
                RequestBody getRemainingWebTextsBody = SessionManager.getRequestBodyForEndpoint(webTextSetupEndPoint)
                        .build();
                Request getRemainingWebTextsRequest = new Request.Builder()
                        .url(getBaseUrl() + webTextSetupEndPoint)
                        .post(getRemainingWebTextsBody)
                        .build();
                sendSmsRequestCall = WebClientManager.getWebClient().newCall(getRemainingWebTextsRequest);
                Response webTextResponse;
                VodaResponse<WebTextResponseData> vodaWebTextResponse;
                webTextResponse = sendSmsRequestCall.execute();
                vodaWebTextResponse = gson.fromJson(webTextResponse.body().string(), new TypeToken<VodaResponse<WebTextResponseData>>() {}.getType());
                WebTextResponse generalResponse;
                if (vodaWebTextResponse.getError() != null) {
                    generalResponse = new WebTextResponse(vodaWebTextResponse.getError());
                    callback.onFailure(generalResponse);
                    return;
                }
                int remainingWebTexts = vodaWebTextResponse.getData().getTotal() - vodaWebTextResponse.getData().getUsed();
                generalResponse = new WebTextResponse(remainingWebTexts, remainingWebTexts, vodaWebTextResponse.getData().getRenewalDate());
                callback.onSuccess(generalResponse);
            } catch (Exception e) {
                callback.onException(sendSmsRequestCall, e);
            }
        });
    }

    @Override
    public void getBalance(final ApiCallback<BalanceResponse> callback) {
        executorService.execute(() -> {
            Call balanceRequestCall = null;
            try {
                String balanceEndPoint = "/mcare/prepaybalance.shtml";
                VodaResponse<BalanceResponseData> vodaBalanceDataResponse;
                Type balanceResponseType = new TypeToken<VodaResponse<BalanceResponseData>>() {}.getType();
                if (SessionManager.getLoggedInUser().getType() == User.CustomerType.POST_PAY) {
                    balanceEndPoint = "/mcare/accountbalance.shtml";
                }
                RequestBody balanceBody = SessionManager.getRequestBodyForEndpoint(balanceEndPoint)
                        .build();
                Request balanceRequest = new Request.Builder()
                        .url(getBaseUrl() + balanceEndPoint)
                        .post(balanceBody)
                        .build();
                balanceRequestCall = WebClientManager.getWebClient().newCall(balanceRequest);
                Response webBalanceResponse;
                webBalanceResponse = balanceRequestCall.execute();
                vodaBalanceDataResponse = gson.fromJson(webBalanceResponse.body().string(), balanceResponseType);
                BalanceResponse generalResponse;
                if (vodaBalanceDataResponse.getError() != null) {
                    generalResponse = new BalanceResponse(vodaBalanceDataResponse.getError());
                    callback.onFailure(generalResponse);
                    return;
                }
                generalResponse = new BalanceResponse(vodaBalanceDataResponse.getData().getBalance());
                callback.onSuccess(generalResponse);
            } catch (Exception e) {
                callback.onException(balanceRequestCall, e);
            }
        });
    }

    @Override
    public void getPrePayPlanDetails(ApiCallback<PrePayPlanDetailsResponse> callback) {
        executorService.execute(() -> {
            Call prePayPlanDetailsCall = null;
            try {
                if (SessionManager.getLoggedInUser().getType() != User.CustomerType.PRE_PAY) {
                    throw new UnsupportedOperationException("Only pre-pay users can use this.");
                }
                String prePayPlanDetailsEndPoint = "/mcare/topupoffer.shtml";
                Type prePayPlanDetailsResponseType = new TypeToken<VodaResponse<PrePayPlanDetailsResponseData>>() {}.getType();
                RequestBody prePayPlanDetailsBody = SessionManager.getRequestBodyForEndpoint(prePayPlanDetailsEndPoint)
                        .build();
                Request prePayPlanDetailsRequest = new Request.Builder()
                        .url(getBaseUrl() + prePayPlanDetailsEndPoint)
                        .post(prePayPlanDetailsBody)
                        .build();
                prePayPlanDetailsCall = WebClientManager.getWebClient().newCall(prePayPlanDetailsRequest);
                Response webPrePayPlanDetailsResponse;
                VodaResponse<PrePayPlanDetailsResponseData> vodaPrePayPlanDetailsDataResponse;
                webPrePayPlanDetailsResponse = prePayPlanDetailsCall.execute();
                vodaPrePayPlanDetailsDataResponse = gson.fromJson(webPrePayPlanDetailsResponse.body().string(), prePayPlanDetailsResponseType);
                PrePayPlanDetailsResponse generalResponse;
                if (vodaPrePayPlanDetailsDataResponse.getError() != null) {
                    generalResponse = new PrePayPlanDetailsResponse(vodaPrePayPlanDetailsDataResponse.getError());
                    callback.onFailure(generalResponse);
                    return;
                }
                generalResponse = new PrePayPlanDetailsResponse(vodaPrePayPlanDetailsDataResponse.getData().getTopUpOfferName(), vodaPrePayPlanDetailsDataResponse.getData().getTopUpOfferCode(), vodaPrePayPlanDetailsDataResponse.getData().getTopUpOfferExpirationDate(), vodaPrePayPlanDetailsDataResponse.getData().isOfferActive());
                callback.onSuccess(generalResponse);
            } catch (Exception e) {
                callback.onException(prePayPlanDetailsCall, e);
            }
        });
    }

    @Override
    public void getPostPayPlanDetails(ApiCallback<PostPayPlanDetailsResponse> callback) {
        executorService.execute(() -> {
            Call postPayPlanDetailsCall = null;
            try {
                if (SessionManager.getLoggedInUser().getType() != User.CustomerType.POST_PAY) {
                    throw new UnsupportedOperationException("Only post-pay users can use this.");
                }
                String postPayPlanDetailsEndPoint = "/mcare/paymonthlyplan.shtml";
                Type postPayPlanDetailsResponseType = new TypeToken<VodaResponse<PostPayPlanDetailsResponseData>>() {}.getType();
                RequestBody postPayPlanDetailsBody = SessionManager.getRequestBodyForEndpoint(postPayPlanDetailsEndPoint)
                        .build();
                Request postPayPlanDetailsRequest = new Request.Builder()
                        .url(getBaseUrl() + postPayPlanDetailsEndPoint)
                        .post(postPayPlanDetailsBody)
                        .build();
                postPayPlanDetailsCall = WebClientManager.getWebClient().newCall(postPayPlanDetailsRequest);
                Response webPostPayPlanDetailsResponse;
                VodaResponse<PostPayPlanDetailsResponseData> vodaPostPayPlanDetailsResponse;
                webPostPayPlanDetailsResponse = postPayPlanDetailsCall.execute();
                vodaPostPayPlanDetailsResponse = gson.fromJson(webPostPayPlanDetailsResponse.body().string(), postPayPlanDetailsResponseType);
                PostPayPlanDetailsResponse generalResponse;
                if (vodaPostPayPlanDetailsResponse.getError() != null) {
                    generalResponse = new PostPayPlanDetailsResponse(vodaPostPayPlanDetailsResponse.getError());
                    callback.onFailure(generalResponse);
                    return;
                }
                generalResponse = new PostPayPlanDetailsResponse(vodaPostPayPlanDetailsResponse.getData());
                callback.onSuccess(generalResponse);
            } catch (Exception e) {
                callback.onException(postPayPlanDetailsCall, e);
            }
        });
    }

    public void getLoyaltyBalance(ApiCallback<LoyaltyBalanceResponse> callback) {
        executorService.execute(() -> {
            Call loyaltyBalanceCall = null;
            try {
                if (SessionManager.getLoggedInUser().getType() != User.CustomerType.PRE_PAY) {
                    throw new UnsupportedOperationException("Only pre-pay users can use this.");
                }
                String loyaltyBalanceEndPoint = "/mcare/loyaltybalance.shtml";
                Type loyaltyBalanceResponseType = new TypeToken<VodaResponse<PostPayPlanDetailsResponseData>>() {}.getType();
                RequestBody loyaltyBalanceBody = SessionManager.getRequestBodyForEndpoint(loyaltyBalanceEndPoint)
                        .build();
                Request loyaltyBalanceRequest = new Request.Builder()
                        .url(getBaseUrl() + loyaltyBalanceEndPoint)
                        .post(loyaltyBalanceBody)
                        .build();
                loyaltyBalanceCall = WebClientManager.getWebClient().newCall(loyaltyBalanceRequest);
                Response webLoyaltyBalanceResponse;
                VodaResponse<LoyaltyBalanceResponseData> vodaLoyaltyBalanceResponse;
                webLoyaltyBalanceResponse = loyaltyBalanceCall.execute();
                vodaLoyaltyBalanceResponse = gson.fromJson(webLoyaltyBalanceResponse.body().string(), loyaltyBalanceResponseType);
                LoyaltyBalanceResponse generalResponse;
                if (vodaLoyaltyBalanceResponse.getError() != null) {
                    generalResponse = new LoyaltyBalanceResponse(vodaLoyaltyBalanceResponse.getError());
                    callback.onFailure(generalResponse);
                    return;
                }
                generalResponse = new LoyaltyBalanceResponse(vodaLoyaltyBalanceResponse.getData().getLoyaltyBalance());
                callback.onSuccess(generalResponse);
            } catch (Exception e) {
                callback.onException(loyaltyBalanceCall, e);
            }
        });
    }

    @Override
    public String getBaseUrl() {
        return MainApp.getContext().getString(R.string.mcare_base_url);
    }

}
