package com.android.mevabe;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.mevabe.common.Screen;
import com.android.mevabe.common.OnDoubleClickListener;
import com.android.mevabe.dashboard.DashBoard;
import com.android.mevabe.lichsuthuoc.LichSuThuocMain;
import com.android.mevabe.lichtiem.LichTiemMain;

/**
 * MainActivity class controls main application activity
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout mainLayout;
    private Screen currentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up
        setContentView(R.layout.app_main_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Listener toolbar
        toolbar.setClickable(true);
        toolbar.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onDoubleClick(View v) {
                currentContent.onToolBarClicked(v);
            }
        });

        // Add feed back button
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Build left menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mainLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mainLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Default create dashboard fragment
        currentContent = new DashBoard();

        // Add the dashboard to content view
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_area, currentContent).commit();
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
        if (id == R.id.nav_dashboard) {
            currentContent = new DashBoard();
            toolbar.setTitle(R.string.left_menu_dashboard);
        } else if (id == R.id.nav_lich_tiem) {
            currentContent = new LichTiemMain();
            toolbar.setTitle(R.string.left_menu_lich_tiem);
        } else if (id == R.id.nav_su_dung_thuoc) {
            currentContent = new LichSuThuocMain();
            toolbar.setTitle(R.string.left_menu_su_dung_thuoc);
        } else if (id == R.id.nav_kien_thuc) {
            return false;
        } else if (id == R.id.nav_bac_sy) {
            return false;
        } else if (id == R.id.nav_share) {
            return false;
        } else if (id == R.id.nav_send) {
            return false;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_area, currentContent);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();


        mainLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
