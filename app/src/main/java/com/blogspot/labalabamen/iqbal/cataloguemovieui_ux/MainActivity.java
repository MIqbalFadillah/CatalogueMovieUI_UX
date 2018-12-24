package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailSearchFragment.SearchMovie;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.MainFragment.FavoriteHome;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView profileCircleImageView;
    String profileImageUrl = "https://pbs.twimg.com/profile_images/1041954283707543553/UfG7pLIa_400x400.jpg";


    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        profileCircleImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        Glide.with(MainActivity.this)
                .load(profileImageUrl)
                .into(profileCircleImageView);

        //        halaman fragment Null
        if (savedInstanceState == null) {
            setFragment(new HomeFragment(),getResources().getString(R.string.home));
        }
    }

    public void setFragment(Fragment fragment, String title) {
        if (fragment != null ){
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }
        getSupportActionBar().setTitle(title);
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    protected void onResume(){
        super.onResume();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause(){
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            if (item.getItemId() == R.id.action_settings){
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        String title ="";
        Fragment fragment = null;
        Bundle bundle = new Bundle();

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            title = getResources().getString(R.string.home);
            fragment = new HomeFragment();
        }
        else if (id == R.id.nav_search) {
            title = getResources().getString(R.string.search);
            fragment = new SearchMovie();

        }
         else if (id == R.id.nav_favorite) {
            title = getResources().getString(R.string.favorit);
            fragment = new FavoriteHome();

        } else if (id == R.id.nav_manage) {
            title = getResources().getString(R.string.action_settings);
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);

        }
        setFragment(fragment, title);
        return true;
    }
}
