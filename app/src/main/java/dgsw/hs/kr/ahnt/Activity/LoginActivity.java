package dgsw.hs.kr.ahnt.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dgsw.hs.kr.ahnt.Helper.CalendarHelper;
import dgsw.hs.kr.ahnt.Interface.IPassValue;
import dgsw.hs.kr.ahnt.Model.TokenInfo;
import dgsw.hs.kr.ahnt.Network.NetworkManager;
import dgsw.hs.kr.ahnt.Network.Response.LoginData;
import dgsw.hs.kr.ahnt.Network.Response.ResponseFormat;
import dgsw.hs.kr.ahnt.R;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements IPassValue<ResponseFormat<LoginData>>{

    @BindView(R.id.email) EditText mEmailView;
    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.login_progress) View mProgressView;
    @BindView(R.id.login_form) View mLoginFormView;

    RealmConfiguration realmConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Realm.init(this);
        /*this.realmConfiguration = new RealmConfiguration.Builder().schemaVersion(1).migration(new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                Log.d(this.getClass().getSimpleName(), "Old version: [" + oldVersion + "], new version: [" + newVersion + "]");
                RealmSchema realmSchema = realm.getSchema();
                for(long version = oldVersion; version < newVersion; version++) {
                    if(version == 0) { // to 1
                        RealmObjectSchema stationSchema = realmSchema.get("TokenInfo");
                        stationSchema.addField("createdAt", Date.class);
                    }
                }
            }
        }).build();
        Realm.setDefaultConfiguration(this.realmConfiguration);*/

        AndroidNetworking.initialize(this);
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return false;

            attemptLogin();
            return true;
        });
    }

    @OnClick(R.id.register_button)
    public void registerButtonClicked() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.email_sign_in_button)
    public void signInButtonClicked() {
        attemptLogin();
    }

    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //Hide keyboard
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            NetworkManager.login(this, email, password);
//            NetworkManager.loginAsyncTask(this, email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.matches("\\w+@dgsw\\.hs\\.kr$");
    }

    private boolean isPasswordValid(String password) {
        return password.matches("^(?=[A-z])(?=.*\\d)(?=.*[!@#$%^&*()_+~`\\-=\\[\\]{},./?])[A-z0-9!@#$%^&*()_+~`\\-=\\[\\]{},./?]{8,}$");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void passValue(ResponseFormat<LoginData> value) {
        showProgress(false);
        Resources r = getResources();

        if (value != null) {
            if (value.getStatus() == r.getInteger(R.integer.status_success)) {
                Realm realm = Realm.getDefaultInstance();

                realm.beginTransaction();
                TokenInfo tokenInfo = realm.createObject(TokenInfo.class);
                tokenInfo.setToken(value.getData().getToken());
                tokenInfo.setCreatedAt(new Date());
                realm.commitTransaction();

                // 화면전환
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (value.getStatus() == r.getInteger(R.integer.status_server_error) || value.getStatus() == r.getInteger(R.integer.status_bad_request)) {
                    Toast.makeText(this, R.string.error_server, Toast.LENGTH_SHORT).show();
                } else if (value.getStatus() == r.getInteger(R.integer.status_login_fail)) {
                    Toast.makeText(this, R.string.error_login, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.error_server, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, R.string.error_server, Toast.LENGTH_SHORT).show();
        }
    }
}

