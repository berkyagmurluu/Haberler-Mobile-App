package com.berkyagmurlu.haberler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActionBarDrawerToggle mToogle;
    private DrawerLayout mDrawerLayout;
    private static ImageLoader mImageLoader;
    private NavigationView mNavigation;

    RecyclerView recyclerView;
    RecyclerView.Adapter mRecAdapter;
    //RecyclerView.LayoutManager layoutManager;

    List<NewsList> newsLists;

    RequestQueue rQueue;

    String url_goster = "http://10.0.2.2:8080/portal/mainJson.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToogle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.Open, R.string.Close);

        mNavigation = (NavigationView)findViewById(R.id.navigation_view);
        mNavigation.setNavigationItemSelectedListener(this);

        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();


        rQueue = Volley.newRequestQueue(getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.recycleViewC_Container);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsLists = new ArrayList<>();
        mRecAdapter = new NewsAdapter(MainActivity.this, newsLists);
        recyclerView.setAdapter(mRecAdapter);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Ana Sayfa");
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mNavigation.setCheckedItem(R.id.nav_home);

        haberlerListele();
    }

    public void haberlerListele() // Veritabanındaki Kayıtları Getirir.
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

    /*public static ImageLoader getImageLoader(Context context)
    {
        if(mImageLoader == null)
        {
            mImageLoader = new ImageLoader(Volley.newRequestQueue(context), new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
                @Override
                public Bitmap getBitmap(String url) {
                    return mCache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    mCache.put(url,bitmap);
                }
            });
        }

        return mImageLoader;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToogle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.nav_home)
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            getSupportActionBar().setTitle("Ana Sayfa");
            toolbar.setTitleTextColor(Color.WHITE);

            Intent myHome = new Intent(this, MainActivity.class);
            startActivity(myHome);
            finish();

        }
        else if(id == R.id.nav_category)
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            getSupportActionBar().setTitle("Kategoriler");
            toolbar.setTitleTextColor(Color.WHITE);

            Intent myCategory = new Intent(this, Categories.class);
            startActivity(myCategory);
            finish();
        }
        else if(id == R.id.nav_news)
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            getSupportActionBar().setTitle("Haberler");
            toolbar.setTitleTextColor(Color.WHITE);

            Intent myNews = new Intent(this, News.class);
            startActivity(myNews);
            finish();
        }
        else if(id == R.id.nav_lastMınute)
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            getSupportActionBar().setTitle("Son Dakika");
            toolbar.setTitleTextColor(Color.WHITE);

            Intent myLastMınute = new Intent(this, LastMinute.class);
            startActivity(myLastMınute);
            finish();
        }
        else if(id == R.id.nav_contact)
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            getSupportActionBar().setTitle("Bize Ulaşın");
            toolbar.setTitleTextColor(Color.WHITE);

            Intent myContact = new Intent(this, ContactUs.class);
            startActivity(myContact);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
