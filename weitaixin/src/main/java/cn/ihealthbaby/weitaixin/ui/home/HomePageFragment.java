package cn.ihealthbaby.weitaixin.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ihealthbaby.weitaixin.R;
import cn.ihealthbaby.weitaixin.base.BaseFragment;
import cn.ihealthbaby.weitaixin.library.log.LogUtil;
import cn.ihealthbaby.weitaixin.ui.monitor.GuardianStateActivity;
import cn.ihealthbaby.weitaixin.ui.pay.PayAccountActivity;
import cn.ihealthbaby.weitaixin.ui.pay.PayMimeOrderActivity;


public class HomePageFragment extends BaseFragment {
    private final static String TAG = "HomePageFragment";


    @Bind(R.id.back)
    RelativeLayout back;
    @Bind(R.id.title_text)
    TextView title_text;
    @Bind(R.id.function)
    TextView function;
//

    View view;

    private static HomePageFragment instance;

    public static HomePageFragment getInstance() {
        if (instance == null) {
            instance = new HomePageFragment();
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, null);
        ButterKnife.bind(this, view);
        init(view);
        back.setVisibility(View.INVISIBLE);
        return view;
    }

    private void init(View view) {
        title_text.setText("首页");
    }


    @OnClick(R.id.title_text)
    public void title_text() {
        Intent intent=new Intent(getActivity().getApplicationContext(), PayAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}




