package com.berkyagmurlu.haberler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Success extends AppCompatActivity {

    Button btnPrev, btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        btnPrev = (Button) findViewById(R.id.previousBtn);
        btnRetry = (Button) findViewById(R.id.retryForm);

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myPrev = new Intent(v.getContext(), MainActivity.class);
                startActivity(myPrev);
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myRetry = new Intent(v.getContext(), ContactUs.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(myRetry);
            }
        });
    }
}
