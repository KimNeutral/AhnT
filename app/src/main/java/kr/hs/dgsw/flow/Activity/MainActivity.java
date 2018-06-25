package kr.hs.dgsw.flow.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.Fragment.BaseFragment;
import kr.hs.dgsw.flow.Fragment.MealFragment;
import kr.hs.dgsw.flow.Fragment.NoticeDetailFragment;
import kr.hs.dgsw.flow.Fragment.NoticeFragment;
import kr.hs.dgsw.flow.Fragment.OutFragment;
import kr.hs.dgsw.flow.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        InitActionBarAndDrawer();

        String fragmentRequire = getIntent().getStringExtra("fragment");
        if (!TextUtils.isEmpty(fragmentRequire)) {
            switch(fragmentRequire) {
                case "GoOut":
                    addFragment(OutFragment.newInstance());
                    break;
                case "SleepOut":
                    break;
                case "Notice":
                    String idx = getIntent().getStringExtra("notice_idx");
                    addFragment(NoticeDetailFragment.newInstance(Integer.parseInt(idx)));
                    break;
            }
        }
    }

    private void InitActionBarAndDrawer() {
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setDrawerIndicatorEnabled(true);
                syncDrawerToggleState();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                syncDrawerToggleState();

            }
        };

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().addOnBackStackChangedListener(() -> syncDrawerToggleState());
    }

    private void syncDrawerToggleState() {
        ActionBarDrawerToggle drawerToggle = toggle;
        if (drawerToggle == null) {
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {     // BackStack에 화면이 있을 때
            showBackIcon();
            drawerToggle.setToolbarNavigationClickListener(v -> getSupportFragmentManager().popBackStack()); // pop backstack
        } else {        // 없을 때
            showHamburgerIcon();
            drawerToggle.setToolbarNavigationClickListener(drawerToggle.getToolbarNavigationClickListener()); // open nav menu drawer
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        BaseFragment fragment = null;

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_meal:
                fragment = new MealFragment();
                break;
            case R.id.nav_out:
                fragment = new OutFragment();
                break;
            case R.id.nav_sleep:
                break;
            case R.id.nav_notice:
                fragment = new NoticeFragment();
                break;
        }

        addFragment(fragment);

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 현재 화면에 보이는 Fragment를 가져온다
     * @return 화면에 보이는 Fragment
     */
    public BaseFragment getCurrentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        int ct = fm.getBackStackEntryCount();

        if (ct != 0) {
            String tag = fm.getBackStackEntryAt(ct - 1).getName();
            Fragment curFragment = fm.findFragmentByTag(tag);

            if (curFragment instanceof BaseFragment)
                return (BaseFragment) curFragment;
        }

        return null;
    }

    /**
     * Fragment를 화면에 추가한다.
     * 만약 화면에 보이는 프래그먼트와 동일한 Fragment면 다시 띄우지 않는다.
     * @param fragment 추가할 Fragment
     */
    public void addFragment(BaseFragment fragment) {
        if(fragment == null) return;

        BaseFragment cur = getCurrentFragment();
        if (cur != null && cur.getClass() == fragment.getClass()) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment, fragment.getTitle());
        ft.addToBackStack(fragment.getTitle());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(sendBackPressToDrawer()){
            // Drawer가 닫혔으니 여기서 스탑
            return;
        }

        super.onBackPressed();
    }

    /**
     * Drawer가 열려있으면 닫는다.
     * @return 닫히면 true, 아무변화 없으면 false
     */
    private boolean sendBackPressToDrawer() {
        DrawerLayout drawer = mDrawerLayout;
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    public void showHamburgerIcon() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle.setDrawerIndicatorEnabled(true);
    }

    public void showBackIcon() {
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
