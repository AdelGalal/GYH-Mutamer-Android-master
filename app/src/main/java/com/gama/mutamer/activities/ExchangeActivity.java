package com.gama.mutamer.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.data.repositories.NationalitiesRepository;
import com.gama.mutamer.fragments.dialogs.CountryFilterFragment;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.KeyboardHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.helpers.webService.ServicePost;
import com.gama.mutamer.helpers.webService.ServiceResult;
import com.gama.mutamer.interfaces.ICountryChangeListener;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.utils.NameViewModel;
import com.gama.mutamer.viewModels.webServices.ExchangeViewModel;
import com.gama.mutamer.webServices.responses.ExchangeResponse;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeActivity extends AppCompatActivity implements ICountryChangeListener {


    /***
     * Vars
     */
    private int mFromIndex = 35, mToIndex = 34;
    private ArrayList<NameViewModel> mCountries = new ArrayList<>();
    private ExchangeResponse mResponse;
    private String mLang = LanguageHelper.LANGUAGE_ENGLISH;

    /***
     * Views
     */
    @BindView(R.id.ivFromCountry) ImageView ivFromCountry;
    @BindView(R.id.tvFromCurrencyName) TextView tvFromCurrencyName;
    @BindView(R.id.ivToCountry) ImageView ivToCountry;
    @BindView(R.id.tvToCurrencyName) TextView tvToCurrencyName;
    @BindView(R.id.etAmount) EditText etAmount;
    @BindView(R.id.tvResult) TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.exchange_currencies));
        }
        mLang = new LanguageHelper().getCurrentLanguage(this);
        new GetCountriesAsync(this).execute();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.cvToCurrency)
    void toCurrencyButtonPressed(View v) {
        CountryFilterFragment bottomSheetDialogFragment = CountryFilterFragment.newInstance(mCountries, mToIndex, Constants.TO_CURRENCY_FLAG, mLang);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.cvFromCurrency)
    void fromCurrencyButtonPressed(View v) {
        CountryFilterFragment bottomSheetDialogFragment = CountryFilterFragment.newInstance(mCountries, mFromIndex, Constants.FROM_CURRENCY_FLAG, mLang);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnConvert)
    void convertButtonPressed(View v) {
        new KeyboardHelper().Hide(this);
        if (etAmount.getText().length() == 0) {
            DialogsHelper.showToast(this, R.string.please_enter_amount);
            return;
        }
        if (mCountries.size() >= mFromIndex && mCountries.size() >= mToIndex) {
            NameViewModel fromCountry = mCountries.get(mFromIndex);
            NameViewModel toCountry = mCountries.get(mToIndex);
            new ExchangeMoneyAsync(this).execute(new ExchangeViewModel(fromCountry.getCurrencyCode(), toCountry.getCurrencyCode()));
        } else {
            DialogsHelper.showToast(this, R.string.unexcepted_error);
        }
    }


    private void bindUi() {
        if (mCountries.size() > 0) {
            NameViewModel fromCountry = mCountries.get(mFromIndex);
            NameViewModel toCountry = mCountries.get(mToIndex);
            tvFromCurrencyName.setText(String.format("%s - %s", fromCountry.getName(mLang), fromCountry.getCurrencyCode()));
            tvToCurrencyName.setText(String.format("%s - %s", toCountry.getName(mLang), toCountry.getCurrencyCode()));
            ivFromCountry.setImageBitmap(null);
            ivToCountry.setImageBitmap(null);
            try {
                ivFromCountry.setImageDrawable(ContextCompat.getDrawable(this, getResources().getIdentifier("a" + fromCountry.getId(), "drawable", this.getPackageName())));
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
            try {
                ivToCountry.setImageDrawable(ContextCompat.getDrawable(this, getResources().getIdentifier("a" + toCountry.getId(), "drawable", this.getPackageName())));
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
        }
    }

    private void bindResult() {
        try {
            if (mResponse != null && mResponse.getResult() != null) {
                float amount = Float.parseFloat(etAmount.getText().toString());
                float rate = 0;
                NameViewModel toCountry = mCountries.get(mToIndex);
                for (Field field : mResponse.getResult().getClass().getDeclaredFields()) {
                    if (field.getName().equalsIgnoreCase(toCountry.getCurrencyCode())) {
                        try {
                            rate = field.getFloat(mResponse.getResult());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                tvResult.setText(String.valueOf(String.valueOf(amount * rate)));
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
            DialogsHelper.showToast(this, R.string.unexcepted_error);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void CountryChanged(int countryIndex, int code) {
        if (code == Constants.FROM_CURRENCY_FLAG)
            mFromIndex = countryIndex;
        else
            mToIndex = countryIndex;
        bindUi();
    }

    private static class GetCountriesAsync extends AsyncTask<Void, Void, Void> {

        private WeakReference<ExchangeActivity> activityReference;

        GetCountriesAsync(ExchangeActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ExchangeActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            activity.bindUi();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ExchangeActivity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) return null;

                activity.mCountries = new NationalitiesRepository()
                        .getExchangeList(activity.mLang);
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
            return null;
        }
    }

    private static class ExchangeMoneyAsync extends AsyncTask<ExchangeViewModel, Void, ExchangeResponse> {

        ProgressDialog mDialog;

        private WeakReference<ExchangeActivity> activityReference;

        ExchangeMoneyAsync(ExchangeActivity context) {
            activityReference = new WeakReference<>(context);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                ExchangeActivity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) return;

                mDialog = DialogsHelper.getProgressDialog(activity, R.string.please_wait);
                mDialog.show();
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
        }

        @Override
        protected void onPostExecute(ExchangeResponse exchangeResponse) {
            super.onPostExecute(exchangeResponse);
            try {
                ExchangeActivity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) return;

                mDialog.cancel();
                if (exchangeResponse != null && exchangeResponse.isSuccess()) {
                    activity.mResponse = exchangeResponse;
                    activity.bindResult();
                } else {
                    DialogsHelper.showToast(activity, R.string.unexcepted_error);
                }
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
        }

        @Override
        protected ExchangeResponse doInBackground(ExchangeViewModel... models) {
            try {
                ExchangeActivity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) return null;


                ServiceResult result = new ServicePost().DoPost(String.format(activity.getString(R.string.url_exhange), models[0].getBaseCurrancy(), models[0].getToCurrancy()));
                return result.isSuccess() ? new Gson().fromJson(result.getResult(), ExchangeResponse.class) : new ExchangeResponse();
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
                return new ExchangeResponse();
            }
        }
    }
}
