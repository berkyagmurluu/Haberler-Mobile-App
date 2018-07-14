package com.berkyagmurlu.haberler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class MainDetails extends AppCompatActivity{

    TextView txt, txt2;
    NetworkImageView nImage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_details);

        txt = (TextView) findViewById(R.id.txtContent);
        txt2 = (TextView) findViewById(R.id.txtBaslık);

        nImage = (NetworkImageView) findViewById(R.id.imageView_View_V);

        //holder.pImage.setImageUrl(n.getNewsImage(), News.getImageLoader(context));
        //imageN

        Toolbar toolbar = (Toolbar) findViewById(R.id.bar_img);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("");
            }
        }

        String icerik = bundle.getString("contentN");
        String title = bundle.getString("titleN");

        nImage.setImageUrl(bundle.getString("imageN"), News.getImageLoader(context));

        txt.setText(icerik);
        txt2.setText(title);

    }
}
