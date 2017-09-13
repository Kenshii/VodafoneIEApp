package pm.shane.mobileapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import okhttp3.Call;
import pm.shane.mobileapi.Api;
import pm.shane.mobileapi.ApiCallback;
import pm.shane.mobileapi.providers.general.response.Response;
import pm.shane.mobileapi.providers.general.response.login.LoginResponse;
import pm.shane.mobileapi.providers.vodafone.VodaApi;
import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;

public class LoginActivity extends AppCompatActivity {

    private Api api = new VodaApi();

    private class LoginDetailsWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (getPhoneNumberInput().length() == getResources().getInteger(R.integer.phone_number_length)
            && !getPasswordInput().isEmpty()) {
                getLoginBtn().setEnabled(true);
            } else {
                getLoginBtn().setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_actionbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.login_activity_name);
        LoginDetailsWatcher loginDetailsWatcher = new LoginDetailsWatcher();
        getPhoneNumberEditText().addTextChangedListener(loginDetailsWatcher);
        getPasswordEditText().addTextChangedListener(loginDetailsWatcher);
    }

    public void handleHelp(MenuItem item) {
        Toast.makeText(MainApp.getContext(), "Help", Toast.LENGTH_SHORT).show();
    }

    public EditText getPhoneNumberEditText() {
        return (EditText)findViewById(R.id.phoneNumberInput);
    }

    public EditText getPasswordEditText() {
        return (EditText)findViewById(R.id.passwordInput);
    }

    public Button getLoginBtn() {
        return (Button)findViewById(R.id.loginBtn);
    }

    public Button getRegisterBtn() {
        return (Button)findViewById(R.id.registerBtn);
    }

    public ProgressBar getLoginProgressBar() {
        return (ProgressBar)findViewById(R.id.progressBar);
    }

    public String getPhoneNumberInput() {
        return getPhoneNumberEditText().getText().toString();
    }

    public String getPasswordInput() {
        return getPasswordEditText().getText().toString();
    }

    public void handleLogin(View view) {
        getLoginBtn().setEnabled(false);
        getLoginProgressBar().setVisibility(View.VISIBLE);
        final String msisdn = "353" + getPhoneNumberInput().substring(1);
        final String password = getPasswordInput();
        api.login(msisdn, password, new ApiCallback<LoginResponse>() {
            @Override
            public void onSuccess(final LoginResponse response) {
                runOnUiThread(() -> {
                    Toast.makeText(MainApp.getContext(), "Logged in successfully.", Toast.LENGTH_SHORT).show();
                });
                /*Sms sms = new Sms("0857235267", "Testing 123");
                api.sendWebText(sms, new ApiCallback<WebTextResponse>() {
                    @Override
                    public void onSuccess(final WebTextResponse response) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainApp.getContext(), response.getRenewalDate().toString(), Toast.LENGTH_SHORT).show()
                        });
                    }

                    @Override
                    public void onFailure(final WebTextResponse response) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainApp.getContext(), response.getError().getMessage(), Toast.LENGTH_SHORT).show()
                        });
                    }

                    @Override
                    public void onException(Call call, Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainApp.getContext(), "An exception occurred.", Toast.LENGTH_SHORT).show()
                        });
                    }
                });*/
                /*api.getBalance(new ApiCallback<BalanceResponse>() {
                    @Override
                    public void onSuccess(final BalanceResponse response) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainApp.getContext(), "Balance: " + response.getBalance(), Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onFailure(BalanceResponse response) {
                        runOnUiThread(() -> {
                            response.getError().handle(LoginActivity.this);
                        });
                    }

                    @Override
                    public void onException(Call call, Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainApp.getContext(), "An exception occurred.", Toast.LENGTH_SHORT).show();
                        });
                    }
                });*/
                /*api.getPostPayPlanDetails(new ApiCallback<PostPayPlanDetailsResponse>() {
                    @Override
                    public void onSuccess(PostPayPlanDetailsResponse response) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainApp.getContext(), response.getDescription(), Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onFailure(PostPayPlanDetailsResponse response) {
                        runOnUiThread(() -> {
                            response.getError().handle(LoginActivity.this);
                        });
                    }

                    @Override
                    public void onException(Call call, Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainApp.getContext(), "An exception occurred.", Toast.LENGTH_SHORT).show();
                        });
                    }
                });*/
                api.logout(new ApiCallback<Response>() {
                    @Override
                    public void onSuccess(Response response) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainApp.getContext(), R.string.logout_successful, Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onFailure(Response response) {
                        runOnUiThread(() -> {
                            response.getError().handle(LoginActivity.this);
                        });
                    }

                    @Override
                    public void onException(Call call, Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainApp.getContext(), "An exception occurred.", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }

            @Override
            public void onFailure(final LoginResponse response) {
                runOnUiThread(() -> {
                    response.getError().handle(LoginActivity.this);
                });
            }

            @Override
            public void onException(Call call, Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(MainApp.getContext(), "An exception occurred.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

}
