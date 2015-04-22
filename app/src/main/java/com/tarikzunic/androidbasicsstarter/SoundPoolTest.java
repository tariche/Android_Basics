package com.tarikzunic.androidbasicsstarter;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.view.View.OnTouchListener;


public class SoundPoolTest extends Activity implements OnTouchListener {
//    SoundPool.Builder soundPoolBuilder;
    SoundPool soundPool;
    int explosionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_pool_test);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        TextView textView = (TextView) findViewById(R.id.textViewSound);
        textView.setOnTouchListener(this);

        AssetManager assetManager = getAssets();
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        /*soundPoolBuilder = new SoundPool.Builder();
        soundPoolBuilder.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build());*/

        try {
            AssetFileDescriptor descriptor = assetManager.openFd("sounds/explosion.ogg");
            explosionId = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound_pool_test, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            soundPool.unload(explosionId);
            soundPool.release();
        }
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
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (explosionId != -1) {
                soundPool.play(explosionId, 1, 1, 0, 0, 1);
                Toast.makeText(this, "Explodirao si :)", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
