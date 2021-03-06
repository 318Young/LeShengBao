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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.young.leshengbao.R;
import com.young.leshengbao.adapter.MainPagerAdapter;
import com.young.leshengbao.fragments.MainFragment;
import com.young.leshengbao.options.about.AboutActivity;
import com.young.leshengbao.options.login.LoginActivity;
import com.young.leshengbao.options.money.TransferMoneyActivity;
import com.young.leshengbao.options.setting.SafeSettingActivity;
import com.young.leshengbao.options.userinfo.MyMsgActivity;
import com.young.leshengbao.options.userinfo.UserInfoActivity;
import com.young.leshengbao.options.userinfo.UserRecordActivity;
import com.young.leshengbao.utils.DateUtils;
import com.young.leshengbao.utils.PreConstants;
import com.young.leshengbao.utils.SharedPreferencesUtils;

import de.hdodenhof.circleimageview.CircleImageView;


public class  MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private View headView;
    private CircleImageView iv_header;
    private TextView tv_name;
    public static final int LOGIN_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("****",DateUtils.getWeekDayFromDate(2016,8, 7)+"-----");
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
//            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//            tabLayout.setupWithViewPager(viewPager);


            iv_header.setOnClickListener(this);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreferencesUtils.getBooleanValue(this, PreConstants.LSB_ISLOGIN, false)){
            tv_name.setText(SharedPreferencesUtils.getStringValue(this, PreConstants.LSB_USERPHONE, ""));
            iv_header.setImageResource(R.drawable.user_logo);
        } else {
            iv_header.setImageResource(R.drawable.login_logo);
            tv_name.setText("请登录");
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
//        adapter.addFragment(new MainFragment(), "Category 2");
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
                                if (SharedPreferencesUtils.getBooleanValue(MainActivity.this, PreConstants.LSB_ISLOGIN, false)){
                                    startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this,"please login", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.nav_recharge:/*充值*/
                                break;
                            case R.id.nav_transfer:/*转账*/
                                if (SharedPreferencesUtils.getBooleanValue(MainActivity.this, PreConstants.LSB_ISLOGIN, false)){
                                    startActivity(new Intent(MainActivity.this, TransferMoneyActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this,"please login", Toast.LENGTH_SHORT).show();
                                }

                                break;
                            case R.id.nav_record:/*记录*/
                                if (SharedPreferencesUtils.getBooleanValue(MainActivity.this, PreConstants.LSB_ISLOGIN, false)){
                                    startActivity(new Intent(MainActivity.this, UserRecordActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this,"please login", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.sub_setting:/*安全设置*/
                                if (SharedPreferencesUtils.getBooleanValue(MainActivity.this, PreConstants.LSB_ISLOGIN, false)){
                                    startActivity(new Intent(MainActivity.this, SafeSettingActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this,"please login", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.sub_about:/*关于*/
                                startActivity(new Intent(MainActivity.this, AboutActivity.class));
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
                    startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_user_header:
                if (!SharedPreferencesUtils.getBooleanValue(MainActivity.this, PreConstants.LSB_ISLOGIN, false)){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                } else {
//                    Toast.makeText(MainActivity.this,"allread login", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
