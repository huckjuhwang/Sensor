package com.sungkyul.sensor;

import android.content.Context;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

public class FaceTrackerDaemon implements MultiProcessor.Factory<Face> {

    private Context context;

    // Vision Cloud API 의 MultiProcessor.Factory 클래스를 확장
    public FaceTrackerDaemon(Context context) {
        this.context = context;
    }

    @Override
    public Tracker<Face> create(Face face) {
        return new FaceTracker(context);
    }
}