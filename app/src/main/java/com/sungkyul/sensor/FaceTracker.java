package com.sungkyul.sensor;

import android.content.Context;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

public class FaceTracker extends Tracker<Face> {
    private Context context;

    public FaceTracker(Context context) {
        this.context = context;
    }

    @Override
    public void onUpdate(Detector.Detections<Face> detections, Face face) {
        ((MainActivity)context).updateMainView(Condition.FACE_FOUND);
    }

    @Override
    public void onMissing(Detector.Detections<Face> detections) {
        super.onMissing(detections); // 인식이 안될때는 동작을 안함
        ((MainActivity)context).updateMainView(Condition.FACE_NOT_FOUND);
    }

    @Override
    public void onDone() {
        super.onDone();
    }
}
