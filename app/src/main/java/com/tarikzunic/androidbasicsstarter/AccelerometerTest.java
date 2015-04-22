package com.tarikzunic.androidbasicsstarter;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AccelerometerTest extends Activity implements SensorEventListener {
    StringBuilder builder = new StringBuilder();
    StringBuilder builderCompass = new StringBuilder();
    TextView textView, textCompass;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Sensor g;
    float[] rMatrix = new float[9];
    float[] iMatrix = new float[9];
    float[] gravity = new float[3];
    float[] geoMagnetic = new float[3];
    float[] orientation = new float[3];

 /*   public AccelerometerTest() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_test);
        textView = (TextView) findViewById(R.id.textAccelTest);
        textCompass = (TextView) findViewById(R.id.textCompass);
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        g = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, g, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accelerometer_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values.clone();
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geoMagnetic = event.values.clone();
        }

        builder.setLength(0);
        builder.append("x: ");
        builder.append(gravity[0]);
        builder.append("\ny: ");
        builder.append(gravity[1]);
        builder.append("\nz: ");
        builder.append(gravity[2]);
        textView.setText(builder.toString());

        if (SensorManager.getRotationMatrix(rMatrix, iMatrix, gravity, geoMagnetic)) {
            SensorManager.getOrientation(rMatrix, orientation);
        }
        builderCompass.setLength(0);
        builderCompass.append("azimut: ");
        builderCompass.append(orientation[0] * 57.2957795);
        builderCompass.append("\npitch: ");
        builderCompass.append(orientation[1] * 57.2957795);
        builderCompass.append("\nroll: ");
        builderCompass.append(orientation[2] * 57.2957795);
        textCompass.setText(builderCompass.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
