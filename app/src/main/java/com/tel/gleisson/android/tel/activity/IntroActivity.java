package com.tel.gleisson.android.tel.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.tel.gleisson.android.tel.util.PrefManager;
import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.util.SampleSlide;

/**
 * Created by Gleisson e Rosy on 07/10/2016.
 */

public class IntroActivity extends AppIntro {

  private PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstance(R.layout.intro1));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));

        setFlowAnimation();

       prefManager = new PrefManager(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        prefManager.setFirstTimeLaunch(false);

    }

    @Override

        public void onSkipPressed(Fragment currentFragment ){
            super.onSkipPressed(currentFragment);
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            this.startActivity(intent);
            finish();
        }

        @Override
        public void onDonePressed(Fragment currentFragment){
            super.onDonePressed(currentFragment);
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            this.startActivity(intent);
           finish();
        }

        @Override
        public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment){
            super.onSlideChanged(oldFragment, newFragment);
        }

    }

