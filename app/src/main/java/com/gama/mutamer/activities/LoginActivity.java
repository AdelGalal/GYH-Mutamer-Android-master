package com.gama.mutamer.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.gama.mutamer.R;
import com.gama.mutamer.data.repositories.UsersRepository;
import com.gama.mutamer.helpers.BroadcastHelper;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.KeyboardHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.helpers.gson.GsonHelper;
import com.gama.mutamer.helpers.webService.ServicePost;
import com.gama.mutamer.helpers.webService.ServiceResult;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.webServices.LoginViewParam;
import com.gama.mutamer.webServices.requests.FetchProfileRequest;
import com.gama.mutamer.webServices.requests.LoginRequest;
import com.gama.mutamer.webServices.responses.LoginResponse;
import com.gama.mutamer.webServices.responses.PilgrimProfileResponse;

import java.lang.ref.WeakReference;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    /***
     * Vars
     */
    private LoginViewParam mModel = new LoginViewParam();

    /***
     * Views
     */
    @BindView(R.id.etUserName) TextInputEditText etUserName;
    @BindView(R.id.etPassword) TextInputEditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.login));
        }
        etUserName.findFocus();
    }


    @OnClick(R.id.btnLogin)
    void buttonLoginClicked(View v) {
        if (validatedInputs()) {
            bindInputs();
            new KeyboardHelper().Hide(this);
            new LoginAsync(this, mModel)
                    .execute();
        }
    }

    boolean validatedInputs() {
        new KeyboardHelper().Hide(this);
        if (etUserName.getText() == null || etUserName.getText().length() < 6) {
            DialogsHelper.showToast(this, R.string.please_enter_user_name);
            return false;
        }

        if (etPassword.getText() == null || etPassword.getText().length() < 6) {
            DialogsHelper.showToast(this, R.string.please_enter_your_password);
            return false;
        }

        return true;
    }

    void bindInputs() {
        mModel.setUserName(Objects.requireNonNull(etUserName.getText()).toString());
        mModel.setPassword(Objects.requireNonNull(etPassword.getText()).toString());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    static class LoginAsync extends AsyncTask<Void, Void, LoginResponse> {

        ProgressDialog mDialog;
        private WeakReference<LoginActivity> mActivityWeakReference;
        private LoginViewParam mModel;

        LoginAsync(LoginActivity activity, LoginViewParam model) {
            mActivityWeakReference = new WeakReference<>(activity);
            mModel = model;
        }

        @Override protected void onPreExecute() {
            super.onPreExecute();
            Activity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                mDialog = DialogsHelper.getProgressDialog(activity, R.string.please_wait);
                mDialog.show();
            }
        }

        @Override protected LoginResponse doInBackground(Void... voids) {
            Activity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                try {
                    LoginRequest request = new LoginRequest();
                    request.setModel(mModel);
                    ServiceResult result = new ServicePost().DoPost(request, true, activity);
                    return result.isSuccess() ? new LoginResponse(result.getResult()) : new LoginResponse();
                } catch (Exception e) {
                    FirebaseErrorEventLog.SaveEventLog(e);
                }
            }
            return null;
        }

        @Override protected void onPostExecute(LoginResponse loginResponse) {
            super.onPostExecute(loginResponse);
            LoginActivity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                mDialog.cancel();

                if (loginResponse == null) {
                    DialogsHelper.showToast(activity, R.string.unexcepted_error);
                    return;
                }

                if (!loginResponse.isNetworkSuccess()) {
                    DialogsHelper.showToast(activity, R.string.check_internet_connection);
                    return;
                }

                if (!loginResponse.isSuccess()) {
                    DialogsHelper.showToast(activity, R.string.login_fail);
                    return;
                }

                new UsersRepository()
                        .saveLoginInfo(mModel, loginResponse);
                new GetProfileAsync(activity)
                        .execute(loginResponse.getLoginInfo().getAccessToken());

            }
        }
    }

    static class GetProfileAsync extends AsyncTask<String, Void, PilgrimProfileResponse> {

        ProgressDialog mDialog;
        private WeakReference<LoginActivity> mActivityWeakReference;

        GetProfileAsync(LoginActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override protected PilgrimProfileResponse doInBackground(String... strings) {
            Activity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                try {
                    FetchProfileRequest request = new FetchProfileRequest(strings[0]);
                    ServiceResult result = new ServicePost().DoPost(request, false, activity);
                    return result.isSuccess() ? GsonHelper.getGson().fromJson(result.getResult(), PilgrimProfileResponse.class) : null;
                } catch (Exception e) {
                    FirebaseErrorEventLog.SaveEventLog(e);
                }
            }
            return null;
        }

        @Override protected void onPreExecute() {
            Activity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                mDialog = DialogsHelper.getProgressDialog(activity, R.string.fetching_your_profile);
                mDialog.show();
            }
        }

        @Override protected void onPostExecute(PilgrimProfileResponse pilgrimProfileResponse) {
            super.onPostExecute(pilgrimProfileResponse);
            LoginActivity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                mDialog.cancel();

                if (pilgrimProfileResponse != null && pilgrimProfileResponse.isSuccess()) {

                    new UsersRepository()
                            .savePilgrimProfile(pilgrimProfileResponse);

                    BroadcastHelper.sendInform(activity,Constants.MUTAMER_PROFILE_FETCHED);
                    activity.setResult(Constants.LOGIN_SUCCESS);
                    activity.finish();
                } else {
                    DialogsHelper.getAlert(activity, activity.getString(R.string.error), activity.getString(R.string.error_fetching_your_profile), activity.getString(R.string.ok)).show();
                }
            }
        }
    }
}
