package cn.ihealthbaby.weitaixin.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ihealthbaby.client.ApiManager;
import cn.ihealthbaby.client.model.User;
import cn.ihealthbaby.weitaixin.AbstractBusiness;
import cn.ihealthbaby.weitaixin.CustomDialog;
import cn.ihealthbaby.weitaixin.DefaultCallback;
import cn.ihealthbaby.weitaixin.R;
import cn.ihealthbaby.weitaixin.library.log.LogUtil;
import cn.ihealthbaby.weitaixin.library.util.SPUtil;
import cn.ihealthbaby.weitaixin.service.AdviceSettingService;
import cn.ihealthbaby.weitaixin.ui.login.InfoEditActivity;
import cn.ihealthbaby.weitaixin.ui.login.LoginActivity;
import cn.ihealthbaby.weitaixin.ui.mine.GradedActivity;

/**
 * Created by chenweihua on 2015/9/21.
 */
public class WelcomeActivity extends FragmentActivity {

    private ViewPager viewPagerWelcome;
    private TextView ivWelcomeStart;
    private LinearLayout llDot;


    private ArrayList<Fragment> mListViews=new ArrayList<Fragment>();
    private MyFragmentPagerAdapter startAdapter;

    private LayoutInflater mInflater;
    private TextView view01;
    private TextView view02;
    private TextView view03;
    private TextView view04;
    private LinearLayout view05;
    private TextView ivWelcome05;
    private TextView tvNextAction;

    private Handler handler;
    private int screenWidth;
    private int screenHeight;
    private WindowManager wm ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_welcome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏


        viewPagerWelcome = (ViewPager) this.findViewById(R.id.viewPagerWelcome);
        ivWelcomeStart = (TextView) this.findViewById(R.id.ivWelcomeStart);
        llDot = (LinearLayout) this.findViewById(R.id.llDot);


        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();



        ivWelcomeStart.setVisibility(View.VISIBLE);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SPUtil.isNoFirstStartApp(WelcomeActivity.this)) {
                    if (SPUtil.getUser(WelcomeActivity.this) != null) {
                        if (SPUtil.isLogin(WelcomeActivity.this)) {
                            if (SPUtil.getUser(WelcomeActivity.this).getIsInit()) {
//                                ivWelcomeStart.setVisibility(View.GONE);
                                Intent intentIsInit = new Intent(WelcomeActivity.this, InfoEditActivity.class);
                                startActivity(intentIsInit);
                                return;
                            }

                            if (!SPUtil.getUser(WelcomeActivity.this).getHasRiskscore()) {
                                if (SPUtil.getHospitalId(WelcomeActivity.this) != -1) {
//                                    ivWelcomeStart.setVisibility(View.GONE);
                                    Intent intentHasRiskscore = new Intent(WelcomeActivity.this, GradedActivity.class);
                                    startActivity(intentHasRiskscore);
                                    return;
                                }
                            }

                            Intent intent = new Intent(WelcomeActivity.this, MeMainFragmentActivity.class);
                            startActivity(intent);
//                            ivWelcomeStart.setVisibility(View.GONE);
                            finish();
                            return;
                        }
                    } else {
//                        ivWelcomeStart.setVisibility(View.GONE);
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                } else {
                    ivWelcomeStart.setVisibility(View.GONE);
                    initView();
                    initDataView();
                }


                if (!SPUtil.isNoFirstStartApp(WelcomeActivity.this)) {
                    SPUtil.setNoFirstStartApp(WelcomeActivity.this, true);
                }
            }
        }, 1500);


    }



    public void initDataView() {

        tvArrs = new TextView[mListViews.size()];
        for (int i = 0; i < mListViews.size(); i++) {
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.welcom_dot_unchecked);
            if (i == 0) {
                textView.setBackgroundResource(R.drawable.welcom_dot);
            }
            textView.setWidth(10);
            textView.setHeight(10);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 0, 0);
            textView.setLayoutParams(lp);
            tvArrs[i] = textView;
            llDot.addView(textView);
        }

    }

    public TextView[] tvArrs=null;

    public void checkDot(int position){
        for (int i = 0; i < tvArrs.length; i++) {
            tvArrs[i].setBackgroundResource(R.drawable.welcom_dot_unchecked);
            if (i == position) {
                tvArrs[i].setBackgroundResource(R.drawable.welcom_dot);
            }
        }
    }


    private void initView() {
        Fragment01 fragment01 = new Fragment01();
        Fragment02 fragment02 = new Fragment02();
        Fragment03 fragment03 = new Fragment03();
        Fragment04 fragment04 = new Fragment04();
//        fragment04.setResId(R.drawable.welcome_04);
        Fragment05 fragment05 = new Fragment05();
        mListViews.add(fragment01);
        mListViews.add(fragment02);
        mListViews.add(fragment03);
        mListViews.add(fragment04);

//        ArrayList<Integer> resIds=new ArrayList<Integer>();
//        resIds.add(R.drawable.welcome_06);
//        resIds.add(R.drawable.welcome_07);
//        resIds.add(R.drawable.welcome_08);
//        resIds.add(R.drawable.welcome_09);
//        resIds.add(R.drawable.welcome_10);
//        resIds.add(R.drawable.welcome_11);
//        resIds.add(R.drawable.welcome_12);
//        resIds.add(R.drawable.welcome_13);
//
//        for (int i = 0; i < resIds.size(); i++) {
//            Fragment04 fragment041 = new Fragment04();
//            fragment041.setResId(resIds.get(i));
//            mListViews.add(fragment041);
//        }


        mListViews.add(fragment05);




        startAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mListViews);
        viewPagerWelcome.setAdapter(startAdapter);
        viewPagerWelcome.setCurrentItem(0);

        viewPagerWelcome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                checkDot(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }



    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;
        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            LogUtil.d("destroyItem", "destroyItem============>"+position);
//            super.destroyItem(container, position, object);
//            container.removeView(list.get(position).getView());
//        }
//
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            LogUtil.d("instantiateItem", "instantiateItem============>"+position);
//            container.addView(list.get(position).getView());
//            return list.get(position).getView();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view==object;
//        }
    }




    public void nextAction() {
        if (SPUtil.isNoFirstStartApp(this)) {
            if (SPUtil.getUser(this) != null) {
                final CustomDialog customDialog = new CustomDialog();
                Dialog dialog = customDialog.createDialog1(this, "刷新用户数据...");
                dialog.show();

                Intent intentAdvice = new Intent(getApplicationContext(), AdviceSettingService.class);
                startService(intentAdvice);

                ApiManager.getInstance().userApi.refreshInfo(new DefaultCallback<User>(this, new AbstractBusiness<User>() {
                    @Override
                    public void handleData(User data) {
                        SPUtil.saveUser(WelcomeActivity.this, data);
                        customDialog.dismiss();
                    }

                    @Override
                    public void handleException(Exception e) {
                        customDialog.dismiss();
                        Intent intentHasRiskscore = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intentHasRiskscore);
                        finish();
                    }
                }), this);

                if (SPUtil.isLogin(this)) {
                    if (SPUtil.getUser(this).getIsInit()) {
                        Intent intentIsInit = new Intent(this, InfoEditActivity.class);
                        startActivity(intentIsInit);
                        return;
                    }

                    if (!SPUtil.getUser(this).getHasRiskscore()) {
                        if (SPUtil.getHospitalId(this) != -1) {
                            Intent intentHasRiskscore = new Intent(this, GradedActivity.class);
                            startActivity(intentHasRiskscore);
                            return;
                        }
                    }

                    Intent intent = new Intent(this, MeMainFragmentActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
    }


}















//                        CustomDialog customDialog = null;
//                        try {
//                            customDialog = new CustomDialog();
//                            Dialog dialog = customDialog.createDialog1(WelcomeActivity.this, "刷新用户数据...");
//                            dialog.show();
//
//                            Intent intentAdvice = new Intent(getApplicationContext(), AdviceSettingService.class);
//                            startService(intentAdvice);
//
//                            final CustomDialog finalCustomDialog = customDialog;
//                            ApiManager.getInstance().userApi.refreshInfo(new DefaultCallback<User>(WelcomeActivity.this, new AbstractBusiness<User>() {
//                                @Override
//                                public void handleData(User data) {
//                                    SPUtil.saveUser(WelcomeActivity.this, data);
//                                    if (finalCustomDialog != null) {
//                                        finalCustomDialog.dismiss();
//                                    }
//                                }
//
//                                @Override
//                                public void handleException(Exception e) {
//                                    if (finalCustomDialog != null) {
//                                        finalCustomDialog.dismiss();
//                                    }
//                                    Intent intentHasRiskscore = new Intent(WelcomeActivity.this, LoginActivity.class);
//                                    startActivity(intentHasRiskscore);
//                                    finish();
//                                }
//                            }), getRequestTag());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            if (customDialog != null) {
//                                customDialog.dismiss();
//                            }
//                        }



