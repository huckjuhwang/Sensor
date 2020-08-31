package com.sungkyul.sensor;

/**
 * Copyright © 2018 Arumizz Artworks. All Rights Reserved.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Device Sensor Handler Class
 */
public class SensorHandler {

    /**
     * 센서 감지 속도
     */
    private static final int DETECT_SPEED = SensorManager.SENSOR_DELAY_NORMAL;

    /**
     * 상위 액티비티
     */
    private Activity mActivity = null;

    /**
     * 센서 관련 객체들
     */
    private Sensor mSensorAccelerator = null;
    private SensorManager mSensorManager = null;
    private SensorEventListener mSensorEventListener = null;
    private SensorHandlerCallback mCallback = null;

    /**
     * 생성자
     *
     * @param activity 상위 액티비티
     * @param callback 센서의 상태가 변화되었을 때 처리할 콜백
     */
    public SensorHandler(Activity activity, SensorHandlerCallback callback) {
        mActivity = activity;
        mCallback = callback;

        // 센서 관리자 인스턴스 생성
        mSensorManager = (SensorManager)mActivity.getSystemService(Context.SENSOR_SERVICE);

        // 가속도계 센서 인스턴스 생성
        mSensorAccelerator = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // SensorEventListener 인터페이스 구현
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}

            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float ax, ay, az, anglexy, anglexz, angleyz;

                ax = x;
                ay = y;
                az = z;

                anglexy = (float)(Math.atan2(ax, ay) / (Math.PI / 180));
                anglexz = (float)(Math.atan2(ax, az) / (Math.PI / 180));
                angleyz = (float)(Math.atan2(ay, az) / (Math.PI / 180));

                if (mCallback != null) {
                    mCallback.onChanged(ax, ay, az, anglexy, anglexz, angleyz, calcAbsoluteAngle(anglexy),
                            determineOrientationModeByAngle(anglexy));
                }
            }
        };

        // 가속도계 리스너 등록
        mSensorManager.registerListener(mSensorEventListener, mSensorAccelerator, DETECT_SPEED);
    }

    /**
     * 상위 액티비티의 onResume() 핸들러에서 호출해야 하는 함수
     */
    public void onResume() {
        if (mSensorManager != null && mSensorAccelerator != null && mSensorEventListener != null) {
            mSensorManager.registerListener(mSensorEventListener, mSensorAccelerator, DETECT_SPEED);
        }
    }

    /**
     * 상위 액티비티의 onPause() 핸들러에서 호출해야 하는 함수
     */
    public void onPause() {
        if (mSensorManager != null && mSensorAccelerator != null && mSensorEventListener != null) {
            mSensorManager.unregisterListener(mSensorEventListener);
        }
    }

    /**
     * 상위 액티비티의 onDestroy() 핸들러에서 호출해야 하는 함수
     */
    public void onDestroy() {
        if (mSensorManager != null && mSensorAccelerator != null && mSensorEventListener != null) {
            mSensorManager.unregisterListener(mSensorEventListener);

            mSensorAccelerator = null;
            mSensorManager = null;
            mSensorEventListener = null;
            mCallback = null;
        }
    }

    /**
     * 지정한 각도를 0 ~ 360도 기준으로 환산하여 계산한다.
     *
     * 각도값이 0을 포함하여 양수일 경우 동일한 각도가 반환되고,
     * 각도값이 음수일 경우 360도를 기준으로 해당 각도값의 절대값을 뺀 각도가 반환된다.
     *
     * @param angle 센서에 의한 원본 각도값
     * @return 0 ~ 360도 기준의 각도값
     */
    private float calcAbsoluteAngle(float angle) {
        if (angle >= 0) {
            return angle;
        }
        else {
            return (360 - Math.abs(angle));
        }
    }

    /**
     * 지정한 센서 각도를 판단하여 적절한 회전 모드를 결정한다.
     *
     * @param angle 센서 각도값 또는 360도 기준의 각도값
     * @return 회전 모드
     *                 정방향 세로모드일 경우 "portrait"
     *                 역방향 세로모드일 경우 "portrait_reverse"
     *                 정방향 가로모드일 경우 "landscape"
     *                 역방향 가로모드일 경우 "landscape_reverse"
     */
    private String determineOrientationModeByAngle(float angle) {
        float absAngle = calcAbsoluteAngle(angle);

        // PORTRAIT
        if ((0 <= absAngle && absAngle < 45) || (315 <= absAngle && absAngle < 360)) {
            return "정방향 세로모드";
        }
        // LANDSCAPE
        else if (45 <= absAngle && absAngle < 135) {
            return "정방향 가로모드";
        }
        // PORTRAIT (REVERSE)
        else if (135 <= absAngle && absAngle < 225) {
            return "역방향 세로모드";
        }
        // LANDSCAPE (REVERSE)
        else { // 225 <= absAngle && absAngle < 315
            return "역방향 가로모드";
        }
    }
}