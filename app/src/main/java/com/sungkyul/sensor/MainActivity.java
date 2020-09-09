package com.sungkyul.sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    /**
     * 센서 핸들러 ( 김도윤 수정 테스트 )
     */
    protected SensorHandler mSensorHandler = null;
    TextView txtmode, txtdoo, txtX, txtY, txtZ, txtAlarm, txtTutleNectAlram;
    Button btnStartService, btnStopService, btnStretching;
    int angular = 0;

    ImageView image;

    String Turtle_neck = "X";
    boolean start;
    boolean isToast = false;
    boolean booltime = false;
    long starttime, end, time;

    Toast toast;
    Vibrator vibrator;

    Intent serviceIntent;


    /**
     *
     * 김도윤 FaceTracker 코드 삽입
     * 2020.09.09
     */
    ConstraintLayout background;
    CameraSource cameraSource;

    boolean TF = false;


    private void init(){
        background = findViewById(R.id.background);
        initCameraSource();
    }

    //카메라 얼굴 감지 소스
    private void initCameraSource() {
        FaceDetector detector = new FaceDetector.Builder(this)
                .setTrackingEnabled(true)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setMode(FaceDetector.FAST_MODE)
                .build();
        detector.setProcessor(new MultiProcessor.Builder(new FaceTrackerDaemon(MainActivity.this)).build());

        cameraSource = new CameraSource.Builder(this, detector)
                .setRequestedPreviewSize(1024, 768)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraSource.start();
        }
        catch (IOException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TurtleNeck");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            Toast.makeText(this, "Permission not granted!\n Grant permission and restart app", Toast.LENGTH_SHORT).show();
        }else{
            init();
        }




        start=false;
        txtdoo = findViewById(R.id.txtdoo);
        txtmode = findViewById(R.id.txtmode);
        txtAlarm = findViewById(R.id.txtAlarm);
        btnStartService = findViewById(R.id.buttonStartService);
        /*        btnStopService = findViewById(R.id.buttonStopService);*/

        txtTutleNectAlram = findViewById(R.id.txtTutleNectAlram);
        btnStretching = findViewById(R.id.btnStretching);

        image = findViewById(R.id.turtle1);

        //진동사용하기
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        //ex 3초 진동하기
//        vibrator.vibrate(3000);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!start){
                    start = true;
                    btnStartService.setBackgroundResource(R.drawable.button_failbackground);
                    btnStartService.setText("서비스 종료하기");
                    startService();
                }else if(start){
                    start = false;
                    btnStartService.setBackgroundResource(R.drawable.button_background);
                    btnStartService.setText("서비스 시작하기");
                    txtTutleNectAlram.setVisibility(View.INVISIBLE);
                    stopService();

                }

            }
        });

        mSensorHandler = new SensorHandler(this, new SensorHandlerCallback() {
            @Override
            public void onChanged(float axisX, float axisY, float axisZ, float angleXY, float angleXZ, float angleYZ,
                                  float absAngleXY, String orientationMode) {
                // angleXY가 센서 각도이고, absAngleXY가 절대 각도이다.
                // 또한 각 각도는 부동소수점 값이므로 정수형으로 변환하여 사용하면 된다.
                // orientationMode 값은 현재 유지해주어야 하는 회전 모드 문자열 이름이다.
                // 여기에 원하는 처리 로직을 작성하면 된다.


                //TF가 True면 시작했을때 false는 Stop상태.
                if(start) {
                    txtmode.setText(orientationMode);

                    if (orientationMode.equals("정방향 세로모드")
                            || orientationMode.equals("역방향 세로모드")) {


                        //정방향 세로모드와 역방향 세로모드를 했을땐 axisY의 값으로만 보면된다.
                        //axisy<0 이면 각도를 O으로 고정시켜주고 터틀넥이 "X"
                        if (axisY < 0) {
                            angular = 0;
                            Turtle_neck = "X";
                            booltime = false;
                        } else {
                            angular = (int)(axisY * 9);
                            // 각도가 50이상이면서 Z각도가 양수일때만 거북목이라고 친다.
                            if (angular < 50 && (axisZ > 0))
                            {
                                if(booltime == false && Turtle_neck.equals("O"))    //거북목이면서(O) 시간측정 시작안할때 ==> false
                                {
                                    booltime = true;
                                    starttime = System.currentTimeMillis();
                                }
                                else if(booltime == true && Turtle_neck.equals("O"))
                                {
                                    end = System.currentTimeMillis();
                                    time = 5 - (end-starttime)/1000 ;
                                    if(time <= 5 && time >= 0){
                                        txtTutleNectAlram.setVisibility(View.VISIBLE);
                                        txtTutleNectAlram.setText("목을 세우세요. " + time + "초뒤 알람 발생");
                                        //토스트 메세지


                                        if(time == 3){
                                            isToast = false;
                                        }

                                        if(time == 0 && !isToast){

                                            toast = Toast.makeText(MainActivity.this, "고개를 들라!!!", Toast.LENGTH_SHORT);
                                            toast.show();
                                            isToast = true;
                                            //0.5초를 진동하겠다.
                                            vibrator.vibrate(500);
                                        }
                                    }else{
                                        txtTutleNectAlram.setVisibility(View.INVISIBLE);
                                        booltime = false;
                                    }
                                }
                                Turtle_neck = "O";
                            } else {
                                Turtle_neck = "X";
                                booltime = false;
                                txtTutleNectAlram.setVisibility(View.INVISIBLE);
                            }
                            testRotation(angular +1);
                        }

                    } else if (orientationMode.equals("정방향 가로모드")
                            || orientationMode.equals("역방향 가로모드")) {

                        if(toast == null) {
                            isToast = false;
                        }


                        if (axisX < 0) {
                            angular = 0;
                            Turtle_neck = "X";
                            booltime = false;
                        } else {
                            angular = (int)(axisX * 9);

                            if ((angular < 50) && (axisZ > 0))
                            {
                                if(booltime == false && Turtle_neck.equals("O"))
                                {
                                    booltime = true;
                                    starttime = System.currentTimeMillis();
                                }
                                else if(booltime == true && Turtle_neck.equals("O"))
                                {
                                    end = System.currentTimeMillis();
                                    time = 5 - (end-starttime)/1000 ;
                                    if(time <= 5 && time >= 0){
                                        txtTutleNectAlram.setVisibility(View.VISIBLE);
                                        txtTutleNectAlram.setText("목을 세우세요. " + time + "초뒤 알람 발생");

                                        if(time == 3){
                                            isToast = false;
                                        }

                                        //토스트 메세지
                                        if(time == 0 && !isToast)
                                        {
                                            toast = Toast.makeText(MainActivity.this, "고개를 들라!!!", Toast.LENGTH_SHORT);
                                            toast.show();
                                            isToast = true;
                                            //0.5초를 진동하겠다.
                                            vibrator.vibrate(500);
                                        }
                                    }else{
                                        txtTutleNectAlram.setVisibility(View.INVISIBLE);
                                        booltime = false;
                                    }
                                }
                                Turtle_neck = "O";
                            } else {
                                Turtle_neck = "X";
                                booltime = false;
//                                txtAlarm.setText(Turtle_neck);
                                txtTutleNectAlram.setVisibility(View.INVISIBLE);
                            }


                        }
                    }

                    txtdoo.setText(angular + "도");
                    txtAlarm.setText(Turtle_neck);

//                    startService(); 각도 잴때마다 진동울린 ㄴ 거라서 주석해놈
                }


            }
        });

        btnStretching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(this.getClass().getName(), "btnStretching 클릭");
                Intent intent = new Intent(MainActivity.this, StretchingActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
/*           백그라운드 재생 나중에 수정해야댐
if (mSensorHandler != null) {
            mSensorHandler.onPause();
        }*/

        if (cameraSource!=null) {
            cameraSource.stop();
        }
        execution();


    }

    @Override
    protected void onResume() {
        super.onResume();
/*       백그라운드 재생 나중에 수정해야댐
if (mSensorHandler != null) {
            mSensorHandler.onResume();
        }*/

        if (cameraSource != null) {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                cameraSource.start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (cameraSource!=null) {
            cameraSource.release();
        }


        stopService();
        mSensorHandler.onDestroy();
    }

    public void testRotation(int i) {
        RotateAnimation ra = new RotateAnimation(
                angular,
                i,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        ra.setDuration(250);
        ra.setFillAfter(true);
        image.startAnimation(ra);
        angular = i;
    }

    public void startService() {
        Intent serviceIntent = new Intent(getApplicationContext(), ForegroundService.class);
        Log.e("StartService() :" , "in .. start");
        serviceIntent.putExtra("inputExtra", "현재 각도 : " + angular + "°" + "\n 거북목 : " + Turtle_neck );
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        Log.e("stopService() :" , "in .. stop");
        stopService(serviceIntent);
    }


    //화면 업데이트 => 김도윤코드
    public void updateMainView(Condition condition){
        switch (condition){
            // 얼굴인식
            case FACE_FOUND:
                facefound();
                break;
            // 얼굴인식 X
            case FACE_NOT_FOUND:
                facenofound();
                break;
            default:
                execution();
        }
    }

    // 처음 화면 실행할때 회색화면
    private void execution() {
        if (background != null)
            background.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
    }

    // 얼굴 인식 했을때
    private void facefound() {
        if(background != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!TF) {
                        Log.i("얼굴 ", "인식됌");
                        Toast.makeText(MainActivity.this, "인식되었습니다.", Toast.LENGTH_SHORT).show();
                        TF=true;
                    }
                }
            });
        }
    }

    // 얼굴 인식 못했을때
    private void facenofound() {
        if(background != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(TF) {
                        Log.i("얼굴 ", "인식안됌");
                        Toast.makeText(MainActivity.this, "인식 안되었습니다.", Toast.LENGTH_SHORT).show();
                        TF=false;
                    }

                }
            });
        }
    }



}