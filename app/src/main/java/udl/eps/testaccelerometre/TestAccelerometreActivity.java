package udl.eps.testaccelerometre;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TestAccelerometreActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean color = false;
    private TextView view, view2;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private long lastUpdate;
    private float currentLux = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Sensor accelerometer, light;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view = findViewById(R.id.textView);
        view2 = findViewById(R.id.textView2);
        scrollView = findViewById(R.id.scrollview);
        linearLayout = findViewById(R.id.scrollviewlayout);

        view.setBackgroundColor(Color.GREEN);
        scrollView.setBackgroundColor(Color.YELLOW);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {
            if ((accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) != null) {
                // Ho movem al onResume()
                /*sensorManager.registerListener(this,
                        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_NORMAL);*/
                // register this class as a listener for the accelerometer sensor

                view2.append("\nResolution: " + accelerometer.getResolution() + "\n");
                view2.append("Maximum Range: " + accelerometer.getMaximumRange() + "\n");
                view2.append("Power: " + accelerometer.getPower() + "\n");
                view2.append("Min Delay: " + accelerometer.getMinDelay() + "\n");
                view2.append("Max Delay: " + accelerometer.getMaxDelay());

            } else {
                view2.setText(R.string.nosensor);
            }
            TextView textview = new TextView(this);
            if ((light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)) != null){
                textview.setText(R.string.light);
                textview.append("\nMax Rang: " + light.getMaximumRange());
            } else {
                textview.setText(R.string.nolight);
            }
            linearLayout.addView(textview);
        }
        lastUpdate = System.currentTimeMillis();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            getAccelerometer(event);
        else getLightSensor(event);
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 2)
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;

            Toast.makeText(this, R.string.shuffed, Toast.LENGTH_SHORT).show();
            if (color) {
                view.setBackgroundColor(Color.GREEN);

            } else {
                view.setBackgroundColor(Color.RED);
            }
            color = !color;
        }
    }

    private void getLightSensor(SensorEvent event){
        int maxRang = ((int) sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT).getMaximumRange());
        int rang1 = maxRang - (maxRang * 2) / 3;
        int rang2 = maxRang - maxRang / 3;

        float actuallux = event.values[0];

        long actualTime = System.currentTimeMillis();
        if(currentLux != actuallux) {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;

            TextView textview = new TextView(this);
            if (event.values[0] < rang1) {
                textview.setText("New value light sensor = " + event.values[0]);
                textview.append("\nLOW Intensity");
            } else if (rang1 < event.values[0] && event.values[0] < rang2) {
                textview.setText("New value light sensor = " + event.values[0]);
                textview.append("\nMEDIUM Intensity");
            } else if (rang2 < event.values[0]) {
                textview.setText("New value light sensor = " + event.values[0]);
                textview.append("\nHIGH Intensity");
            }
            linearLayout.addView(textview);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
        currentLux = actuallux;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}