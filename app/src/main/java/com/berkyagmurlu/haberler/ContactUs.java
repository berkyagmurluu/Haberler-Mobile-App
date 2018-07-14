package com.berkyagmurlu.haberler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class ContactUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActionBarDrawerToggle mToogle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigation;
    RequestQueue rQueue;

    EditText fullname, mail, message;
    Button buttonGonder;


    //String url_goster = "http://192.168.1.41:8080/portal/categoriesJson.php";
    String url_goster = "http://10.0.2.2:8080/portal/contactUs.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        fullname = (EditText)findViewById(R.id.edtFullName);
        mail = (EditText)findViewById(R.id.edtMail);
        message = (EditText)findViewById(R.id.textAreaMessage);

        buttonGonder = (Button) findViewById(R.id.btnGonder);


        rQueue = Volley.newRequestQueue(getApplicationContext());



        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToogle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.Open, R.string.Close);

        mNavigation = (NavigationView)findViewById(R.id.navigation_view);
        mNavigation.setNavigationItemSelectedListener(this);

        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Bize Ulaşın");
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mNavigation.setCheckedItem(R.id.nav_contact);

        buttonGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, url_goster, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(ContactUs.this, Success.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactUs.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError{
                        Map<String,String> parametres = new HashMap<String, String>();
                        parametres.put("fullname",fullname.getText().toString());
                        parametres.put("mail",mail.getText().toString());
                        parametres.put("message",message.getText().toString());

                        return parametres;
                    }
                };
                rQueue.add(request);
            }
        });

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
