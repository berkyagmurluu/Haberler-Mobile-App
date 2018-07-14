package com.berkyagmurlu.haberler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView txtHbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtHbr = (TextView) findViewById(R.id.txtHaberler);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        txtHbr.startAnimation(myAnim);
        final Intent intent = new Intent(this, MainActivity.class);
        Thread timer = new Thread(){
            public void run() {
                try
                {
                    sleep(2500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
