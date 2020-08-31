package com.sungkyul.sensor;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.sungkyul.sensor.StretchinFragment.SFragment1;
import com.sungkyul.sensor.StretchinFragment.SFragment2;
import com.sungkyul.sensor.StretchinFragment.SFragment3;
import com.sungkyul.sensor.StretchinFragment.SFragment4;
import com.sungkyul.sensor.StretchinFragment.SFragment5;

public class StretchingActivity extends AppIntro {

    Fragment Sfragment1 = new SFragment1();
    Fragment Sfragment2 = new SFragment2();
    Fragment Sfragment3 = new SFragment3();
    Fragment Sfragment4 = new SFragment4();
    Fragment Sfragment5 = new SFragment5();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        addSlide(Sfragment1);
        addSlide(Sfragment2);
        addSlide(Sfragment3);
        addSlide(Sfragment4);
        addSlide(Sfragment5);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startMainActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startMainActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void startMainActivity(){
        finish();
    }
}
