package omarbizreh.com.trainingapp;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

import omarbizreh.com.trainingapp.DataModels.UserModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment mFragment;

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Snackbar.make(findViewById(R.id.fab), "Low Memory!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DatabaseHandler handler = new DatabaseHandler(this);
        handler.InsertUser(new UserModel("First User", UUID.randomUUID().toString()));
        handler.InsertUser(new UserModel("Second User", UUID.randomUUID().toString()));
        handler.InsertUser(new UserModel("Third User", UUID.randomUUID().toString()));
        handler.InsertUser(new UserModel("Fourth User", UUID.randomUUID().toString()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Integer sizeBefore = InstanceReferences.getUsers().size();
                InstanceReferences.getUsers().addAll(handler.GetUsers());
                Integer sizeAfter = InstanceReferences.getUsers().size();
                if (sizeAfter > sizeBefore) {
                    Snackbar.make(view, "User Added", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "Failed to add User", Snackbar.LENGTH_LONG)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    InstanceReferences.getUsers().addAll(handler.GetUsers());
                                    if (InstanceReferences.getUsers().size() < sizeBefore) {
                                        Snackbar.make(view, "Still unable to add user", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(view, "User Added", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            }).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class mFragmentClass;

        if (id == R.id.nav_camera) {
            mFragmentClass = UsersFragment.class;
        } else if (id == R.id.nav_gallery) {
            mFragmentClass = UsersFragment.class;
        } else// if (id == R.id.nav_slideshow) {
            mFragmentClass = GroupsFragment.class;
       /* } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */
        try {
            // Create instance of our fragment.
            mFragment = (Fragment) mFragmentClass.newInstance();
            FragmentManager mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction().replace(R.id.main_frame, mFragment).commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
