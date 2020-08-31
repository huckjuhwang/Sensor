package com.sungkyul.sensor;

/**
 * Copyright © 2018 Arumizz Artworks. All Rights Reserved.
 */

/**
 * Device Sensor Handler Callback Class
 */
public interface SensorHandlerCallback {
    /**
     * 기기 센서에 의해 상태가 변화되었을 때 호출된다.
     *
     * @param axisX X축 좌표
     * @param axisY Y축 좌표
     * @param axisZ Z축 좌표
     * @param angleXY X-Y축 기준 각도(=실제 화면 기준 회전 각도)
     * @param angleXZ X-Z축 기준 각도
     * @param angleYZ Y-Z축 기준 각도
     * @param absAngleXY X-Y축 기준 절대값 각도(0~360도 기준 각도)
     * @param orientationMode 회전 모드
     *                                               - "portrait" : 정방향 세로 모드
     *                                               - "portrait_reverse" : 역방향 세로 모드
     *                                               - "landscape" : 정방향 가로 모드
     *                                               - "landscape_reverse" : 역방향 가로 모드
     */
    void onChanged(float axisX, float axisY, float axisZ, float angleXY, float angleXZ, float angleYZ,
                   float absAngleXY, String orientationMode);
}