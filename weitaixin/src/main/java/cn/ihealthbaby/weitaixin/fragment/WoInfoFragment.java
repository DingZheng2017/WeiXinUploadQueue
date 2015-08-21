package cn.ihealthbaby.weitaixin.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ihealthbaby.weitaixin.R;
import cn.ihealthbaby.weitaixin.WeiTaiXinApplication;
import cn.ihealthbaby.weitaixin.activity.GradedActivity;
import cn.ihealthbaby.weitaixin.activity.InfoEditActivity;
import cn.ihealthbaby.weitaixin.activity.SetSystemActivity;
import cn.ihealthbaby.weitaixin.activity.WoGoldenActivity;
import cn.ihealthbaby.weitaixin.activity.WoInformationActivity;
import cn.ihealthbaby.weitaixin.activity.WoMessageActivity;
import cn.ihealthbaby.weitaixin.base.BaseFragment;
import cn.ihealthbaby.weitaixin.tools.DateTimeTool;
import cn.ihealthbaby.weitaixin.view.RoundImageView;


public class WoInfoFragment extends BaseFragment {
    private final static String TAG = "WoInfoFragment";

    @Bind(R.id.back)
    RelativeLayout back;
    @Bind(R.id.title_text)
    TextView title_text;
    @Bind(R.id.function)
    TextView function;
//

    @Nullable
    @Bind(R.id.ll_1)
    LinearLayout ll_1;
    @Nullable
    @Bind(R.id.ll_2)
    LinearLayout ll_2;
    @Nullable
    @Bind(R.id.ll_3)
    LinearLayout ll_3;
    @Nullable
    @Bind(R.id.ll_4)
    LinearLayout ll_4;
    @Bind(R.id.rl_head_img)
    RelativeLayout rl_head_img;

    @Bind(R.id.tv_wo_head_name)
    TextView tv_wo_head_name;
    @Bind(R.id.tv_wo_head_breed_date)
    TextView tv_wo_head_breed_date;
    @Bind(R.id.tv_wo_head_deliveryTime)
    TextView tv_wo_head_deliveryTime;
    @Bind(R.id.iv_wo_head_icon)
    RoundImageView iv_wo_head_icon;


    private static WoInfoFragment instance;
    public static WoInfoFragment getInstance(){
        if (instance==null) {
            instance=new WoInfoFragment();
        }
        return instance;
    }


    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wo_info, null);
        ButterKnife.bind(this, view);
        back.setVisibility(View.INVISIBLE);
        title_text.setText("我的");
        init();
        return view;
    }

    private void init() {
        System.err.println("我的--ment22");
        setTextHead();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.err.println("我的onViewCreated--ment22");
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        setTextHead();
//    }

    private void setTextHead(){
        if (WeiTaiXinApplication.getInstance().isLogin&& WeiTaiXinApplication.user!=null) {
            ImageLoader.getInstance().displayImage(WeiTaiXinApplication.user.getHeadPic(), iv_wo_head_icon, setDisplayImageOptions());
            tv_wo_head_name.setText(WeiTaiXinApplication.user.getName() + "");
            tv_wo_head_breed_date.setText("已孕：" + DateTimeTool.getGestationalWeeks(WeiTaiXinApplication.user.getDeliveryTime()));
            tv_wo_head_deliveryTime.setText("预产：" + DateTimeTool.date2Str(WeiTaiXinApplication.user.getDeliveryTime()));
        }
    }

    @OnClick(R.id.ll_1)
    public void ll_1() {
        Intent intent = new Intent(getActivity().getApplicationContext(), WoMessageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_2)
    public void ll_2() {
        Intent intent = new Intent(getActivity().getApplicationContext(), WoGoldenActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_3)
    public void ll_3() {
        Intent intent = new Intent(getActivity().getApplicationContext(), SetSystemActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_4)
    public void ll_4() {
//        Intent intent = new Intent(getActivity().getApplicationContext(), InfoEditActivity.class);
        Intent intent = new Intent(getActivity().getApplicationContext(), GradedActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.rl_head_img)
    public void rlHeadImg() {
        Intent intent = new Intent(getActivity().getApplicationContext(), WoInformationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public DisplayImageOptions setDisplayImageOptions() {
        DisplayImageOptions options=null;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.button_monitor_helper)
                .showImageForEmptyUri(R.drawable.button_monitor_helper)
                .showImageOnFail(R.drawable.button_monitor_helper)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        return options;
    }
}




