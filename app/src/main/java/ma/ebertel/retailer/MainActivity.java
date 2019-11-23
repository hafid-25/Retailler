package ma.ebertel.retailer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

import ma.ebertel.retailer.Adapters.ViewPagerAdapter;
import ma.ebertel.retailer.Fragments.About;
import ma.ebertel.retailer.Fragments.Home;
import ma.ebertel.retailer.Fragments.Sealer;
import ma.ebertel.retailer.Fragments.UserGenInfo;
import ma.ebertel.retailer.Fragments.UserPerInfo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    private FrameLayout wrapper;

    private String codeBarContent ="";
    public Bitmap clientImage;

    public String clientFullName ="";
    public String clientPhoneNumber="";
    public String clientEmail = "";
    public String clientCity="";
    public String clientRegion = "";
    public String clientAddress = "";
    public boolean clientPeriority = true;
    public boolean clientSatisfacion = true;
    public boolean clientInterssCnss = true;
    public String clientLocation = "";




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
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(codeBarContent.equals("")){
            getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new Home(this)).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new Sealer(this,codeBarContent)).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new Home(this)).commit();
        } else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new About()).commit();
        } else if (id == R.id.nav_signOut) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
