package pm.shane.mobileapi.client;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import pm.shane.mobileapi.providers.general.response.Response;
import pm.shane.mobileapi.providers.vodafone.response.VodaResponse;
import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;

/**
 * Created by Shane on 29/04/2017.
 */

public class WebClientManager {

    private static OkHttpClient webClient;
    private static ClearableCookieJar cookieJar;
    private static final int MAX_ATTEMPTS = 3;

    private static okhttp3.Response interceptUserAgent(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest.newBuilder()
            .header("User-Agent", MainApp.getContext().getString(R.string.app_name))
            .build();
        return chain.proceed(requestWithUserAgent);
    }

    private static okhttp3.Response interceptRetry(Interceptor.Chain chain) throws IOException {
        Gson gson = new Gson();
        int tryCount = 0;
        while (tryCount++ < MAX_ATTEMPTS) {
            Request request = chain.request();
            okhttp3.Response response;
            try {
                response = chain.proceed(request);
            } catch (IOException e) {
                if (tryCount == MAX_ATTEMPTS) {
                    throw e;
                } else {
                    continue;
                }
            }
            if (!request.url().host().equals("mcare.vodafone.ie")) {
                return response;
            }
            MediaType contentType = response.body().contentType();
            String bodyString = response.body().string();
            Response responseObj = gson.fromJson(bodyString, new TypeToken<VodaResponse>() {}.getType());
            if (tryCount == MAX_ATTEMPTS || responseObj.getError() == null || !responseObj.getError().shouldRetry()) {
                ResponseBody bodyCopy = ResponseBody.create(contentType, bodyString);
                return response.newBuilder().body(bodyCopy).build();
            }
        }
        return null;
    }

    public static ClearableCookieJar getCookieJar() {
        if (WebClientManager.cookieJar == null) {
            cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MainApp.getContext()));
        }
        return WebClientManager.cookieJar;
    }

    public static synchronized OkHttpClient getWebClient() {
        if (WebClientManager.webClient == null) {
            X509TrustManager trustManager = getPinningTrustManager();
            WebClientManager.webClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(7, TimeUnit.SECONDS)
                .writeTimeout(7, TimeUnit.SECONDS)
                .addInterceptor(WebClientManager::interceptUserAgent)
                .addInterceptor(WebClientManager::interceptRetry)
                .sslSocketFactory(getSSLContext(trustManager).getSocketFactory(), trustManager)
                .cookieJar(getCookieJar())
                .build();
        }
        return WebClientManager.webClient;
    }

    private static X509TrustManager getPinningTrustManager() {
        boolean certPinningEnabled = MainApp.getContext().getResources().getBoolean(R.bool.cert_pinning_enabled);
        return new SslPinningTrustManager(MainApp.getContext(), R.raw.pinning_cert, certPinningEnabled);
    }

    private static SSLContext getSSLContext(X509TrustManager trustManager) {
        SSLContext sslContext;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream caInput = MainApp.getContext().getResources().openRawResource(R.raw.server_cert);
            keyStore.load(caInput, MainApp.getContext().getString(R.string.server_cert_key).toCharArray());
            caInput.close();
            TrustManager[] trustThatCert = new TrustManager[]{trustManager};
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(keyStore, MainApp.getContext().getString(R.string.pinning_cert_key).toCharArray());
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), trustThatCert, null);
            return sslContext;
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
