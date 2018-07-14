package com.berkyagmurlu.haberler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


public class Categories extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActionBarDrawerToggle mToogle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigation;
    ListView liste;
    ArrayAdapter<String> adapter;
    ArrayList<String> items;
    RequestQueue rQueue;


    RecyclerView recyclerView;
    RecyclerView.Adapter mRecAdapter;
    //RecyclerView.LayoutManager layoutManager;

    List<NewsList> newsLists;

    //String url_goster = "http://192.168.1.41:8080/portal/categoriesJson.php";
    String url_goster = "http://10.0.2.2:8080/portal/categoriesJson.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        liste = (ListView)findViewById(R.id.cat_list);
        items=new ArrayList<String>();
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1 ,items);
        liste.setAdapter(adapter);

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myDetail = new Intent(Categories.this, CategoriesDetails.class);
                myDetail.putExtra("details", liste.getItemAtPosition(position).toString());
                startActivity(myDetail);
            }
        });


        rQueue = Volley.newRequestQueue(getApplicationContext());



        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToogle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.Open, R.string.Close);

        mNavigation = (NavigationView)findViewById(R.id.navigation_view);
        mNavigation.setNavigationItemSelectedListener(this);

        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Kategoriler");
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mNavigation.setCheckedItem(R.id.nav_category);

        ogrencilerListe();

    }

    public void ogrencilerListe() // Veritabanındaki Kayıtları Getirir.
    {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest (Request.Method.POST, url_goster, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray categoryList = response.getJSONArray("data"); // JSON Dönüştürücü

                    for(int i = 0; i < categoryList.length(); i++)
                    {
                        JSONObject category = categoryList.getJSONObject(i);

                        // Veritabanındaki Alanlar
                        String categoryName = category.getString("name");
                        //String categoryDate = category.getString("date");

                        items.add(categoryName);

                        //txtJson.append(categoryName + " " + categoryDate + " \n ");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("Hata ",error.getLocalizedMessage());
            }
        });

        rQueue.add(jsObjRequest);
    }

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
