package com.gama.mutamer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.utils.MutamerApp;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * Startup Splash Activity
 */
public class SplashActivity extends AppCompatActivity {

    /***
     * Views
     */
    @BindView(R.id.ivSplash) ImageView ivSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Crashlytics.getInstance().crash();
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        showAnimation();
    }

    /***
     * Show Splash Image Animation
     */
    private void showAnimation() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fader);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {

            }

            @Override public void onAnimationEnd(Animation animation) {
                MutamerApp.UserChooseLanguage = SharedPrefHelper.getSharedInt(SplashActivity.this, Constants.USER_CHOOSE_LANGUAGE);

                startActivity(new Intent(SplashActivity.this, MutamerApp.UserChooseLanguage == 0 ? LanguageSelectionActivity.class : MainActivity.class));
                finish();
            }

            @Override public void onAnimationRepeat(Animation animation) {

            }
        });
        ivSplash.startAnimation(fadeInAnimation);
    }
}
