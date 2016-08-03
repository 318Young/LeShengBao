package com.young.leshengbao.options;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.young.leshengbao.R;
import com.young.leshengbao.adapter.MainPagerAdapter;
import com.young.leshengbao.fragments.MainFragment;
import com.young.leshengbao.options.login.LoginActivity;
import com.young.leshengbao.options.userinfo.MyMsgActivity;
import com.young.leshengbao.options.userinfo.UserInfoActivity;
import com.young.leshengbao.options.userinfo.UserRecordActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class  MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private View headView;
    private CircleImageView iv_header;
    private TextView tv_name;
    public static final int LOGIN_REQUEST_CODE = 0;
    private boolean isLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            if (navigationView != null) {
                setupDrawerContent(navigationView);
            }
            headView = navigationView.getHeaderView(0);
            iv_header = (CircleImageView) headView.findViewById(R.id.iv_user_header);
            tv_name = (TextView) headView.findViewById(R.id.tv_name);

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            if (viewPager != null) {
                setupViewPager(viewPager);
            }
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);


            iv_header.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



    private void setupViewPager(ViewPager viewPager) {
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), "Category 1");
        adapter.addFragment(new MainFragment(), "Category 2");
//        adapter.addFragment(new MainFragment(), "Category 3");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.nav_home:
                                startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
                                break;
                            case R.id.nav_messages:/*我的消息*/
                                startActivity(new Intent(MainActivity.this, MyMsgActivity.class));
                                break;
                            case R.id.nav_discussion:/*流水账单*/
                                startActivity(new Intent(MainActivity.this, UserRecordActivity.class));
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            if (resultCode == 0){
                if (null != data){
                    isLogin = true;
                    tv_name.setText(data.getStringExtra("userName"));
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_user_header:
                if (false == isLogin){

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
