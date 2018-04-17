package dgsw.hs.kr.ahnt.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import dgsw.hs.kr.ahnt.Interface.IPassValue;
import dgsw.hs.kr.ahnt.Network.NetworkManager;
import dgsw.hs.kr.ahnt.Network.Request.RegisterRequest;
import dgsw.hs.kr.ahnt.Network.Response.ResponseFormat;
import dgsw.hs.kr.ahnt.R;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements IPassValue<ResponseFormat<Void>> {
    // UI references.
    @BindView(R.id.email) EditText mEmailView;
    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.username) EditText mUsernameView;
    @BindView(R.id.rgGender) RadioGroup rgGender;
    @BindView(R.id.rbtnWoman) RadioButton rbtnWoman;
    @BindView(R.id.mobile) EditText mMobileView;
    @BindView(R.id.spinnerGrade) Spinner spGrade;
    @BindView(R.id.spinnerClassRoom) Spinner spClassRoom;
    @BindView(R.id.spinnerClassNumber) Spinner spClassNumber;
    @BindView(R.id.register_button) Button registerButton;
    @BindView(R.id.login_form) View mProgressView;
    @BindView(R.id.login_progress) View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        registerButton.setOnClickListener(view -> attemptRegister());

        InputFilter filterKor = (source, start, end, dest, dstart, dend) -> {
            Pattern ps = Pattern.compile("^[ㄱ-ㅎ가-흐]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        };

        mUsernameView.setFilters(new InputFilter[]{filterKor});
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mUsernameView.setError(null);
        mMobileView.setError(null);
        rbtnWoman.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Store values at the time of the login attempt.
        RegisterRequest form = new RegisterRequest();

        form.setEmail(mEmailView.getText().toString());
        form.setPw(mPasswordView.getText().toString());
        form.setName(mUsernameView.getText().toString());
        form.setMobile(mMobileView.getText().toString());
        form.setClassIdx((spClassRoom.getSelectedItemPosition() + 1) + "");
        form.setClassNumber((spClassNumber.getSelectedItemPosition() + 1) + "");

        int id = rgGender.getCheckedRadioButtonId();

        // Check for a valid mobile number.
        if (TextUtils.isEmpty(form.getMobile())) {
            mMobileView.setError(getString(R.string.error_field_required));
            focusView = mMobileView;
            cancel = true;
        }

        if(id == -1) {
            rbtnWoman.setError(getString(R.string.error_field_required));
            focusView = rbtnWoman;
            cancel = true;
        } else {
            RadioButton rbtn = findViewById(id);
            form.setGender(rbtn.getHint().toString());
        }

        // Check for a valid username.
        if(TextUtils.isEmpty(form.getName())) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(form.getRawPw()) && !isPasswordValid(form.getRawPw())) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if(TextUtils.isEmpty(form.getRawPw())) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(form.getEmail())) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(form.getEmail())) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            NetworkManager.register(this, form);
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
    public void passValue(ResponseFormat<Void> value) {
        showProgress(false);
        Resources r = getResources();

        if (value != null) {
            if (value.getStatus() == r.getInteger(R.integer.status_success)) {
                Toast.makeText(this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
            } else if (value.getStatus() == r.getInteger(R.integer.status_server_error) || value.getStatus() == r.getInteger(R.integer.status_bad_request)) {
                Toast.makeText(this, R.string.error_server, Toast.LENGTH_SHORT).show();
            } else if (value.getStatus() == r.getInteger(R.integer.status_register_fail_duplicate_email)) {
                mEmailView.setError(getString(R.string.error_register_duplicate_email));
                mEmailView.requestFocus();
            } else {
                Toast.makeText(this, R.string.error_server, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.error_server, Toast.LENGTH_SHORT).show();
        }
    }
}

