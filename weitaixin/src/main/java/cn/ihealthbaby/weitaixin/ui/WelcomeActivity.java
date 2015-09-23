package cn.ihealthbaby.weitaixin.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ihealthbaby.client.ApiManager;
import cn.ihealthbaby.client.model.User;
import cn.ihealthbaby.weitaixin.AbstractBusiness;
import cn.ihealthbaby.weitaixin.CustomDialog;
import cn.ihealthbaby.weitaixin.DefaultCallback;
import cn.ihealthbaby.weitaixin.R;
import cn.ihealthbaby.weitaixin.base.BaseActivity;
import cn.ihealthbaby.weitaixin.library.log.LogUtil;
import cn.ihealthbaby.weitaixin.library.util.SPUtil;
import cn.ihealthbaby.weitaixin.ui.login.InfoEditActivity;
import cn.ihealthbaby.weitaixin.ui.login.LoginActivity;
import cn.ihealthbaby.weitaixin.ui.mine.GradedActivity;

/**
 * Created by chenweihua on 2015/9/21.
 */
public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.viewPagerWelcome) ViewPager viewPagerWelcome;
    @Bind(R.id.ivWelcomeStart) ImageView ivWelcomeStart;


    private ArrayList<View> mListViews;
    private StartPagerAdapter startAdapter;

    private LayoutInflater mInflater;
    private ImageView view01;
    private ImageView view02;
    private ImageView view03;
    private ImageView view04;
    private View view05;
    private ImageView ivWelcome05;
    private TextView tvNextAction;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_welcome);
        ButterKnife.bind(this);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivWelcomeStart.setVisibility(View.GONE);
            }
        }, 1000);

//        initView();


        if (SPUtil.isNoFirstStartApp(this)) {
            if (SPUtil.getUser(this) != null) {
                final CustomDialog customDialog = new CustomDialog();
                Dialog dialog = customDialog.createDialog1(this, "刷新用户数据...");
                dialog.show();

                ApiManager.getInstance().userApi.refreshInfo(new DefaultCallback<User>(this, new AbstractBusiness<User>() {
                    @Override
                    public void handleData(User data)   {
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
                }), getRequestTag());

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
        } else {
            initView();
        }




        if (!SPUtil.isNoFirstStartApp(this)) {
            SPUtil.setNoFirstStartApp(this);
        }

    }


    private void initView() {

        mListViews = new ArrayList<View>();
        mInflater = this.getLayoutInflater();

        view01 = (ImageView) mInflater.inflate(R.layout.viewpager_item, null);
        view02 = (ImageView) mInflater.inflate(R.layout.viewpager_item, null);
        view03 = (ImageView) mInflater.inflate(R.layout.viewpager_item, null);
        view04 = (ImageView) mInflater.inflate(R.layout.viewpager_item, null);
        view05 = (View) mInflater.inflate(R.layout.viewpager_item_last, null);
        ivWelcome05 = (ImageView)view05.findViewById(R.id.ivWelcome05);
        tvNextAction = (TextView)view05.findViewById(R.id.tvNextAction);

        view01.setImageResource(R.drawable.pay_choose);
        view02.setImageResource(R.drawable.pay_choose);
        view03.setImageResource(R.drawable.pay_choose);
        view04.setImageResource(R.drawable.pay_choose);
        ivWelcome05.setImageResource(R.drawable.pay_choose);

        tvNextAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAction();
                finish();
            }
        });

        mListViews.add(view01);
        mListViews.add(view02);
        mListViews.add(view03);
        mListViews.add(view04);
        mListViews.add(view05);


        startAdapter = new StartPagerAdapter();
        viewPagerWelcome.setAdapter(startAdapter);
        viewPagerWelcome.setCurrentItem(0);


        viewPagerWelcome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if(arg0==startAdapter.getCount()-1){

                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private class StartPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            LogUtil.d("mListViews.size()","mListViews.size()"+mListViews.size());
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View collection, int position) {
            ((ViewPager) collection).addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView(mListViews.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

    }



    public void tv_enter() {
        if (SPUtil.getUser(this) != null) {
            final CustomDialog customDialog = new CustomDialog();
            Dialog dialog = customDialog.createDialog1(this, "刷新用户数据...");
            dialog.show();

            ApiManager.getInstance().userApi.refreshInfo(new DefaultCallback<User>(this, new AbstractBusiness<User>() {
                @Override
                public void handleData(User data)   {
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
            }), getRequestTag());

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

    public void nextAction() {
        if (SPUtil.isNoFirstStartApp(this)) {
            if (SPUtil.getUser(this) != null) {
                final CustomDialog customDialog = new CustomDialog();
                Dialog dialog = customDialog.createDialog1(this, "刷新用户数据...");
                dialog.show();

                ApiManager.getInstance().userApi.refreshInfo(new DefaultCallback<User>(this, new AbstractBusiness<User>() {
                    @Override
                    public void handleData(User data)   {
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
                }), getRequestTag());

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



