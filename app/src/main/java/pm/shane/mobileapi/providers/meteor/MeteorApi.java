package pm.shane.mobileapi.providers.meteor;

/**
 * Created by Shane on 19/05/2017.
 */
/*
public class MeteorApi implements Api {

    @Override
    public void login(String login, String password, ApiCallback<LoginResponse> callback) {
        executorService.execute(() -> {
            SessionManager.saveUserDetails(login, password);
            RequestBody loginRequestBody = new FormBody.Builder()
                    .add("emailAddress", login)
                    .add("password", password)
                    .build();
            Request loginRequest = new Request.Builder()
                    .url(getBaseUrl() + "/rest/brand/3/portalUser/authenticate")
                    .post(loginRequestBody)
                    .build();
            Call loginCall = client.newCall(loginRequest);
            Response webLoginResponse;
            MeteorResponse<MeteorLoginResponseData> meteorLoginResponse;
            try {
                webLoginResponse = loginCall.execute();
                meteorLoginResponse = gson.fromJson(webLoginResponse.body().string(), new TypeToken<VodaResponse<meteorLoginResponseData>>(){}.getType());
            } catch (IOException e) {
                callback.onException(loginCall, e);
                return;
            }
            LoginResponse generalResponse;
            if (meteorLoginResponse.getError() != null) {
                generalResponse = new LoginResponse(meteorLoginResponse.getError());
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
            int customerType = User.CustomerType.POST_PAY;
            boolean personal = meteorLoginResponse.getData().getCustTypeCode().startsWith("PER");
            if (meteorLoginResponse.getData().getCustTypeCode().startsWith("RTG")) {
                customerType = User.CustomerType.PRE_PAY;
            }
            SessionManager.setLoggedInUser(new User(customerType, meteorLoginResponse.getData().getCustName(), personal));
            generalResponse = new LoginResponse(meteorLoginResponse.getData().getCustName(), meteorLoginResponse.getData().getCustTypeCode());
            callback.onSuccess(generalResponse);

        });
    }

    @Override
    public void sendWebText(Sms sms, ApiCallback<WebTextResponse> callback) {

    }

    @Override
    public void getRemainingWebTexts(ApiCallback<WebTextResponse> callback) {

    }

    @Override
    public void getBalance(ApiCallback<BalanceResponse> callback) {

    }

    @Override
    public String getBaseUrl() {
        return "https://my.meteor.ie";
    }

}
*/