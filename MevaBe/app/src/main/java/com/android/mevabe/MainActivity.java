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
import android.view.View;
import android.widget.Toast;

import com.android.mevabe.doctor.DoctorsMain;
import com.android.mevabe.common.AppData;
import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.common.view.FragmentBase;
import com.android.mevabe.common.view.FragmentLoginRequired;
import com.android.mevabe.dashboard.DashBoard;
import com.android.mevabe.profile.ProfileMain;
import com.android.mevabe.vaccinations.VaccinationsMain;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int TAB_DASHBOARD = 0;
    public static final int TAB_DOCTORS = 1;
    public static final int TAB_VACCINATIONS = 2;
    public static final int TAB_PROFILE = 3;

    private AccessTokenTracker accessTokenTracker;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private DashBoard fragmentDashBoard;
    private DoctorsMain fragmentDoctor;
    private VaccinationsMain fragmentVaccinations;
    private ProfileMain fragmentProfile;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Build GUI for view
        buildGUI();

    }

    /**
     * Build GUI for view
     */
    private void buildGUI() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(TAB_DASHBOARD).setIcon(R.drawable.ic_menu_dashboard);
        tabLayout.getTabAt(TAB_DOCTORS).setIcon(R.drawable.ic_menu_doctors);
        tabLayout.getTabAt(TAB_VACCINATIONS).setIcon(R.drawable.ic_menu_vaccinations);
        tabLayout.getTabAt(TAB_PROFILE).setIcon(R.drawable.ic_menu_profile);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    private TabLayout.Tab selectedTab;

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        selectedTab = tab;
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
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
                LogUtil.debug("AccessTokenTracker: onCurrentAccessTokenChanged");
                notifyAccountChanged();
            }
        };
    }

    // ***************** Activity control ****************** //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentBase screen = getCurrentScreen();
        if (screen != null) {
            screen.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_to_exit), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    // ***************** Action control ****************** //

    /**
     * Get current fragment
     *
     * @return FragmentBase
     */
    public void selectTab(int tab) {
        viewPager.setCurrentItem(tab, true);
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
        LogUtil.debug("MyActivity: notifyAccountChanged() - profile: " + profile);

        // Update data
        AppData.setLoginProfile(profile);

        // Notify to login required screen
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        for (Fragment fragment : adapter.getListFragment()) {
            if (fragment instanceof FragmentLoginRequired) {
                ((FragmentLoginRequired) fragment).onAccountChange(profile);
            }
        }
    }

    /**
     * Refresh view when user delete a child
     */
    public void notifyChildChange() {
        // Refresh vaccinations view
        fragmentVaccinations.onAccountChange(AppData.getMyProfile().getMyPro());
    }

    /**
     * Handle user click to start button to go profile to update child information
     *
     * @param view View
     */
    public void onBtnStartClick(View view) {
        selectTab(MainActivity.TAB_PROFILE);
    }

    // ***************** For view pager UI ****************** //
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentDashBoard = new DashBoard();
        fragmentDoctor = new DoctorsMain();
        fragmentVaccinations = new VaccinationsMain();
        fragmentProfile = new ProfileMain();

        adapter.addFragment(fragmentDashBoard, getString(R.string.left_menu_dashboard));
        adapter.addFragment(fragmentDoctor, getString(R.string.left_menu_bac_sy));
        adapter.addFragment(fragmentVaccinations, getString(R.string.left_menu_lich_tiem));
        adapter.addFragment(fragmentProfile, getString(R.string.left_menu_profile));
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