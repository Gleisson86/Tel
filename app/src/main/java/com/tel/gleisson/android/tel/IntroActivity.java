package com.tel.gleisson.android.tel;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by Gleisson e Rosy on 07/10/2016.
 */

public class IntroActivity extends AppIntro {


    private PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        addSlide(SampleSlide.newInstance(R.layout.intro1));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));
        setFlowAnimation();
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
        finish();
    }


        @Override

        public void onSkipPressed(Fragment currentFragment ){
            super.onSkipPressed(currentFragment);
            Intent intent1 = new Intent(this, LoginActivity.class);
            this.startActivity(intent1);
            finish();
        }

        @Override
        public void onDonePressed(Fragment currentFragment){
            super.onDonePressed(currentFragment);
            Intent intent1 = new Intent(this, LoginActivity.class);
            this.startActivity(intent1);
           finish();
        }

        @Override
        public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment){
            super.onSlideChanged(oldFragment, newFragment);
        }

    }

