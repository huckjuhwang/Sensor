package com.sungkyul.sensor.StretchinFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sungkyul.sensor.R;

public class SFragment4 extends Fragment {

    public SFragment4() {
        // Required empty public constructor
    }
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.stretching_fr4,container,false);

        return rootView;
    }
}