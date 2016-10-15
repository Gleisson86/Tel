package com.tel.gleisson.android.tel;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;



import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by Gleisson e Rosy on 07/10/2016.
 */

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstance(R.layout.intro1));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));

        setFlowAnimation();

    }
        @Override

        public void onSkipPressed(Fragment currentFragment ){
            super.onSkipPressed(currentFragment);
            finish();
        }

        @Override
        public void onDonePressed(Fragment currentFragment){
            super.onDonePressed(currentFragment);
           finish();
        }

        @Override
        public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment){
            super.onSlideChanged(oldFragment, newFragment);
        }

    }

