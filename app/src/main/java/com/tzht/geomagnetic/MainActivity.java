package com.tzht.geomagnetic;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener, TextToSpeech.OnInitListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private TextView tvXAxis, tvYAxis, tvZAxis;
    private TextView tvState;

    private Detector detector;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvXAxis = findViewById(R.id.tv_x_axis);
        tvYAxis = findViewById(R.id.tv_y_axis);
        tvZAxis = findViewById(R.id.tv_z_axis);
        tvState = findViewById(R.id.tv_state);

        detector = Detector.prepare(null);

        tts = new TextToSpeech(this, this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.CHINESE);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakText(String message) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, "STREAM_NOTIFICATION");//设置播放类型（音频流类型）
        tts.speak(message, TextToSpeech.QUEUE_ADD, params);//将这个发音任务添加当前任务之后
        tts.playSilence(100, TextToSpeech.QUEUE_ADD, params);//间隔多长时间
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {     //只检查磁场的变化
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            tvXAxis.setText("x方向的磁场分量为：" + x);
            tvYAxis.setText("y方向的磁场分量为：" + y);
            tvZAxis.setText("z方向的磁场分量为：" + z);

            detector.handle(x, y, z);
            if (detector.isVehicleExists()) {
                tvState.setText("有车");
            } else {
                tvState.setText("无车");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
