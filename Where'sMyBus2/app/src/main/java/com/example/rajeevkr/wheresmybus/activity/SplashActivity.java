package com.example.rajeevkr.wheresmybus.activity;


import android.content.Intent;
import android.provider.SyncStateContract;

import com.daimajia.androidanimations.library.Techniques;
import com.example.rajeevkr.wheresmybus.R;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Splash animation app start up screen
 * Created by rajeevkr on 5/28/16.
 */

public class SplashActivity extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {

        final int durationInms = 2 * 1000;
        final int textAnimationDurationInms = 3 * 1000;

        configSplash.setBackgroundColor(R.color.colorPrimary);
        configSplash.setAnimCircularRevealDuration(durationInms);

//        //Customize Logo
        configSplash.setLogoSplash(R.drawable.vc_splash_bus); //or any other drawable
        configSplash.setAnimLogoSplashDuration(durationInms); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeInUp);

        //Customize Title
        configSplash.setTitleFont("fonts/pacifico.ttf");
        configSplash.setTitleSplash("Where's My Bus ?");
        configSplash.setTitleTextColor(android.R.color.white);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(textAnimationDurationInms);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
    }

    @Override
    public void animationsFinished() {
        MainActivity.start(this);
        finish();
    }
}
