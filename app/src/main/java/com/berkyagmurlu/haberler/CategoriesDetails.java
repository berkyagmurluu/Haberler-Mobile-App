package com.berkyagmurlu.haberler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesDetails extends AppCompatActivity {

    Toolbar mToolbar;
    RequestQueue rQueue;


    RecyclerView recyclerView;
    RecyclerView.Adapter mRecAdapter;
    //RecyclerView.LayoutManager layoutManager;

    List<NewsList> newsLists;

    //String url_goster = "http://192.168.1.41:8080/portal/categoriesJson.php";
    String url_goster = "http://10.0.2.2:8080/portal/categoriesDetailsJson.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_details);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewC_Container);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsLists = new ArrayList<>();
        mRecAdapter = new CategoriesAdapter(CategoriesDetails.this, newsLists);
        recyclerView.setAdapter(mRecAdapter);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);

        rQueue = Volley.newRequestQueue(getApplicationContext());

        categoriesDetailsFunc();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(bundle.getString("details"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }





    }

    public void categoriesDetailsFunc() // Veritabanındaki Kayıtları Getirir.
    {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest (Request.Method.POST, url_goster, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray haberler = response.getJSONArray("data"); // JSON Dönüştürücü

                    for(int i = 0; i < haberler.length(); i++)
                    {
                        NewsList nList = new NewsList();
                        JSONObject haberlerData = haberler.getJSONObject(i);

                        // Veritabanındaki Alanlar
                        String haberlerTitle = haberlerData.getString("title");
                        String haberlerImage = haberlerData.getString("image");
                        String haberlerDate = haberlerData.getString("date");
                        String haberlerIcerık = haberlerData.getString("content");

                        nList.setNewsTitle(haberlerTitle);
                        nList.setNewsImage(haberlerImage);
                        nList.setNewsDate(haberlerDate);
                        nList.setNewsContent(haberlerIcerık);
                        newsLists.add(nList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mRecAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("Hata ",error.getLocalizedMessage());
            }
        });

        rQueue.add(jsObjRequest);
    }
}
