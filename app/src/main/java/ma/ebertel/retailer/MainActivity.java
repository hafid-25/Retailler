package ma.ebertel.retailer;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.ebertel.retailer.Adapters.ViewPagerAdapter;
import ma.ebertel.retailer.Fragments.About;
import ma.ebertel.retailer.Fragments.Home;
import ma.ebertel.retailer.Fragments.Sealer;
import ma.ebertel.retailer.Fragments.UserGenInfo;
import ma.ebertel.retailer.Fragments.UserPerInfo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    private FrameLayout wrapper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isUserVond = false;
    private boolean isUserVis = false;

    public String codeBarContent ="";
    public Bitmap clientImage = null;

    public String clientId = "";
    public String clientFullName ="";
    public String clientPhoneNumber="";
    public String clientEmail = "";
    public String clientCity="";
    public String clientRegion = "";
    public String clientAddress = "";
    public String clientType="";
    public boolean clientPeriority = true;
    public boolean clientSatisfacion = true;
    public boolean clientInterssCnss = true;
    public String clientLocation = "";

    public List<String> Potence = new ArrayList<>();

    public List<String[]> Dealers = new ArrayList<>();
    public List<String[]> Rechargs = new ArrayList<>();
    public List<String[]> Sims = new ArrayList<>();

    public boolean MobileMonyInteress = true;
    public boolean MobileMonyPropose  = true;
    public String MobileMonnyType = "";

    public boolean Telephony = false;
    public String TelephonyGamme = "Bas Gamme";

    public boolean Accessoire = false;
    public String AccessoireGamme = "Bas Gamme";

    public String serverUrl = "http://hafid.skandev.com/addClient.php";

    public List<String> regionCode = new ArrayList<>();
    public List<String> regionname = new ArrayList<>();

    public List<String> cityName = new ArrayList<>();
    public List<String> cityCode = new ArrayList<>();

    public String selectedRegionCode = "";
    public String selectedCityCode = "";

    // visiteur data
    public JSONObject dealerJSonObject = null;
    public JSONArray dealerJSonArray = null;

    // setting the client product for visitor
    public List<String> clientDetergent = new ArrayList<>();
    public List<String> clientThe = new ArrayList<>();
    public List<String> clientLait = new ArrayList<>();
    public List<String> clientBiscuit = new ArrayList<>();
    public List<String> clientPate = new ArrayList<>();
    public List<String> clientCouche = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        wrapper = findViewById(R.id.wrapper);
        //pager = findViewById(R.id.pager);
        //pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //pager.setAdapter(pagerAdapter);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences = this.getSharedPreferences(getString(R.string.shared_name), Context.MODE_PRIVATE);

        verifyLogin();
    }

    private void verifyLogin() {
       String login = sharedPreferences.getString("login","0");
       String role = sharedPreferences.getString("role","0");

       if(login.equals("0")){
           Intent redirect = new Intent(MainActivity.this,Login.class);
           startActivity(redirect);
       }else if(login.equals("1")){
           if(role.equals("1")){
               isUserVond = true;
               isUserVis = false;
           }else if(role.equals("2")){
               isUserVond = false;
               isUserVis = true;
           }
       }

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(codeBarContent.equals("")){
            getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new Home(this,sharedPreferences)).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new Sealer(this,codeBarContent)).commit();
        }
        if(regionCode.size() == 0){
            String role = sharedPreferences.getString("role","0");
            if(role.equals("1")){
                getRegions();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new Home(this,sharedPreferences)).commit();
        } else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new About()).commit();
        } else if (id == R.id.nav_signOut) {
            signOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        editor = sharedPreferences.edit();
        editor.putString("login","0");
        editor.putString("role","0");
        editor.commit();
        Intent login = new Intent(MainActivity.this,Login.class);
        startActivity(login);
    }

    public void showAddUser(){
        //getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new Sealer(this,codeBarContent)).commit();
    }

    public void lunshScanner(){
        Intent scannerIntent = new Intent(this,Scanner.class);
        startActivityForResult(scannerIntent,105);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 105 && resultCode == RESULT_OK){
            try{
                String result = data.getExtras().getString("code");
                codeBarContent = result;
            }catch (Exception e){

            }
        }
    }

    public void connect(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "response is here ", Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String responce = jsonObject.getString("response");
                            Toast.makeText(MainActivity.this, "response is "+responce, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Failed To Create Json", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
                Log.d("error", "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name","hafid id-baha");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getRegions(){
        String regionUrl = "http://hafid.skandev.com/getRegions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, regionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i< jsonArray.length();i++){
                        regionCode.add(jsonArray.getJSONObject(i).getString("code"));
                        regionname.add(jsonArray.getJSONObject(i).getString("Region"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                Log.d("err", "onErrorResponse: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
