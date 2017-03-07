package com.android.mevabe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.mevabe.bacsi.BacSiMain;
import com.android.mevabe.common.AppConfig;
import com.android.mevabe.dashboard.DashBoard;
import com.android.mevabe.lichsuthuoc.LichSuThuocMain;
import com.android.mevabe.profile.ProfileMain;
import com.android.mevabe.vaccinations.VaccinationsMain;
import com.android.mevabe.view.FragmentBase;
import com.android.mevabe.view.FragmentLoginRequired;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private AccessTokenTracker accessTokenTracker;
    private TabLayout tabLayout;
    private ViewPager viewPager;

//    private DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu_dashboard);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu_bac_si);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_lich_tiem);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_menu_lich_su_thuoc);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_menu_profile);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    private TabLayout.Tab selectedTab;

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        Log.i(AppConfig.LOG_TAG, "onTabSelected");
                        super.onTabSelected(tab);
                        selectedTab = tab;
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        Log.i(AppConfig.LOG_TAG, "onTabReselected");
                        super.onTabReselected(tab);

                        // Fire on toolbar click event
                        if (selectedTab == null || selectedTab.equals(tab)) {
                            FragmentBase screen = getCurrentScreen();
                            screen.onToolBarClicked(null);
                        }
                    }
                });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Notify to list fragment in case account has changed
                notifyAccountChanged();
            }
        };

        // Create DB service
//        dbService = new DBService(this);
    }

    /**
     * Get current fragment
     *
     * @return FragmentBase
     */
    private FragmentBase getCurrentScreen() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        FragmentBase screen = (FragmentBase) adapter.getItem(viewPager.getCurrentItem());
        return screen;
    }

    /**
     * Notify to list fragment in case account has changed
     */
    private void notifyAccountChanged() {
        // Get current profile
        Profile profile = Profile.getCurrentProfile();

        // Update data
        ((MyApplication) getApplication()).setLoginProfile(profile);

        // Notify to login required screen
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        for (Fragment fragment : adapter.getListFragment()) {
            if (fragment instanceof FragmentLoginRequired) {
                ((FragmentLoginRequired) fragment).onAccountChange(profile);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentBase screen = getCurrentScreen();
        if (screen != null) {
            screen.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();

        // Close DB
//        dbService.closeDB();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashBoard(), getString(R.string.left_menu_dashboard));
        adapter.addFragment(new BacSiMain(), getString(R.string.left_menu_bac_sy));
        adapter.addFragment(new VaccinationsMain(), getString(R.string.left_menu_lich_tiem));
        adapter.addFragment(new LichSuThuocMain(), getString(R.string.left_menu_su_dung_thuoc));
        adapter.addFragment(new ProfileMain(), getString(R.string.left_menu_profile));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /**
         * Add item to fragment
         *
         * @param fragment Fragment
         * @param title    String
         */
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public List<Fragment> getListFragment() {
            return mFragmentList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}