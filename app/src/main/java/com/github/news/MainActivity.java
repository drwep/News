package com.github.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.news.base.BaseDelegate;
import com.github.news.event.SignInEvent;
import com.github.news.fragment.news.NewsTabFragment;
import com.github.news.fragment.setting.SettingFragment;
import com.github.news.fragment.sign.SignInFragment;

import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseDelegate.OnFragmentOpenDrawerListener {

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    DrawerLayout mDrawer;
    NavigationView mNavigationView;
    TextView mTvName;   // NavigationView上的名字
    ImageView mImgNav;  // NavigationView上的头像
    private long TOUCH_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBusActivityScope.getDefault(this).register(this);
        initView();

    }

    private void initView() {
        SupportFragment fragment = findFragment(NewsTabFragment.class);

        /*
         加载主页面NewsTabFragment
         */
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, NewsTabFragment.newInstance());
        }

        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_news);

        LinearLayout llNavHeader = (LinearLayout) mNavigationView.getHeaderView(0);
        mImgNav = llNavHeader.findViewById(R.id.iv_header);
        mTvName = llNavHeader.findViewById(R.id.tv_name);
        llNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                mDrawer.post(new Runnable() {
                    @Override
                    public void run() {
                        start(SignInFragment.newInstance());
                    }
                });
            }
        });
        //显示彩色Menu图标
        mNavigationView.setItemIconTintList(null);
    }


    @Override
    public void onBackPressedSupport() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            ISupportFragment topFragment = getTopFragment();

            // 主页的Fragment
            if (topFragment instanceof NewsTabFragment) {
                mNavigationView.setCheckedItem(R.id.nav_news);
            }

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                pop();
            } else {
                if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                    finish();
                } else {
                    TOUCH_TIME = System.currentTimeMillis();
                    Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

        mDrawer.closeDrawer(GravityCompat.START);
        mDrawer.post(new Runnable() {
            @Override
            public void run() {
                final ISupportFragment topFragment = getTopFragment();
                SupportFragment myHome = (SupportFragment) topFragment;
                int id = item.getItemId();
                if (id == R.id.nav_news) {
                    NewsTabFragment fragment = findFragment(NewsTabFragment.class);
                    Bundle newBundle = new Bundle();
                    newBundle.putString("from", "From:" + topFragment.getClass().getSimpleName());
                    fragment.putNewBundle(newBundle);
                    myHome.start(fragment, SupportFragment.SINGLETASK);
                } else if (id == R.id.nav_my) {
                    SettingFragment fragment = findFragment(SettingFragment.class);
                    if (fragment == null) {
                        myHome.startWithPopTo(
                                SettingFragment.newInstance(),
                                NewsTabFragment.class, false);
                    } else {
                        // 如果已经在栈内,则以SingleTask模式start 或者popTo方式
                        myHome.popTo(SettingFragment.class, false);
                    }
                }
            }
        });
        return true;
    }

    @Override
    public void onOpenDrawer() {
        if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }


    /**
     * 处理登录成功后的事件
     * 这里改变了mTvName
     */
    @Subscribe
    public void onSignInEvent(SignInEvent event) {
        mTvName.setText(event.username);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusActivityScope.getDefault(this).unregister(this);
    }
}
