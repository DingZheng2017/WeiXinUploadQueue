package cn.ihealthbaby.weitaixin.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ihealthbaby.client.ApiManager;
import cn.ihealthbaby.client.HttpClientAdapter;
import cn.ihealthbaby.client.Result;
import cn.ihealthbaby.client.model.AdviceItem;
import cn.ihealthbaby.client.model.Information;
import cn.ihealthbaby.client.model.PageData;
import cn.ihealthbaby.client.model.User;
import cn.ihealthbaby.weitaixin.R;
import cn.ihealthbaby.weitaixin.WeiTaiXinApplication;
import cn.ihealthbaby.weitaixin.adapter.MyAdviceItemAdapter;
import cn.ihealthbaby.weitaixin.base.BaseFragment;
import cn.ihealthbaby.weitaixin.db.DataDBHelper;
import cn.ihealthbaby.weitaixin.db.DataDao;
import cn.ihealthbaby.weitaixin.library.log.LogUtil;
import cn.ihealthbaby.weitaixin.library.util.ToastUtil;
import cn.ihealthbaby.weitaixin.model.LocalAdviceItem;
import cn.ihealthbaby.weitaixin.tools.CustomDialog;
import cn.ihealthbaby.weitaixin.tools.DateTimeTool;
import cn.ihealthbaby.weitaixin.view.RoundImageView;


public class RecordFragment extends BaseFragment {

    private final static String TAG = "RecordFragment";

    @Nullable @Bind(R.id.back) RelativeLayout back;
    @Nullable @Bind(R.id.title_text) TextView title_text;
    @Nullable @Bind(R.id.function) TextView function;
//

    @Nullable @Bind(R.id.pullToRefresh) PullToRefreshListView pullToRefresh;
    @Nullable @Bind(R.id.ivWoHeadIcon) RoundImageView ivWoHeadIcon;
    @Nullable @Bind(R.id.tvWoHeadName) TextView tvWoHeadName;
    @Nullable @Bind(R.id.tvWoHeadDeliveryTime) TextView tvWoHeadDeliveryTime;
    @Nullable @Bind(R.id.tvUsedCount) TextView tvUsedCount;
    @Nullable @Bind(R.id.tvHospitalName) TextView tvHospitalName;



    private MyAdviceItemAdapter adapter;
    private ArrayList<Information> dataList=new ArrayList<Information>();
    private Context context;

    private int pageIndex=1, pageSize=5;
    private View view;
    private boolean isNoTwo=true;
    private DataDao dataDao;
    private int pageCountCache =1;
    private ArrayList<LocalAdviceItem> localAdviceItems;


    private static RecordFragment instance;
    public static RecordFragment getInstance(){
        if (instance==null) {
            instance=new RecordFragment();
            LogUtil.e("RecordFragment+Coco7", "RecordFragment+getInstance");
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (isNoTwo) {
            view = inflater.inflate(R.layout.fragment_record, null);
            ButterKnife.bind(this, view);
            back.setVisibility(View.INVISIBLE);
            function.setVisibility(View.VISIBLE);
            title_text.setText("记录");
            function.setText("编辑");

            dataDao=new DataDao(getActivity().getApplicationContext());
            localAdviceItems = new LocalAdviceItem().getDataLocal();
            context=getActivity();
            initView();
            pullHeadDatas();
            pullDatas();

            isNoTwo=false;
//        }
        LogUtil.e("RecordFragment+Coco7", "RecordFragment+Null");
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.e("RecordFragment+Coco7", "RecordFragment+Null44");
    }




    private void pullHeadDatas() {
        if (WeiTaiXinApplication.getInstance().isLogin&& WeiTaiXinApplication.user!=null) {
            User user=WeiTaiXinApplication.user;
            ImageLoader.getInstance().displayImage(user.getHeadPic(), ivWoHeadIcon, setDisplayImageOptions());
            tvWoHeadName.setText(user.getName());
            tvWoHeadDeliveryTime.setText(DateTimeTool.getGestationalWeeks(user.getDeliveryTime()));
            if (user.getServiceInfo()!=null) {
                tvHospitalName.setText("建档: "+user.getServiceInfo().getHospitalName());
            }
        }

//        setCount();
    }

    @Override
    public void onResume() {
        super.onResume();
//        setCount();
    }


//    public void setCount() {
//        ApiManager.getInstance().serviceApi.getByUser(new HttpClientAdapter.Callback<Service>() {
//            @Override
//            public void call(Result<Service> t) {
//                if (t.isSuccess()) {
//                    Service data = t.getData();
//                    if (data != null && tvUsedCount != null) {
//                        tvUsedCount.setText(data.getUsedCount() + "");
//                    } else {
//                        LogUtil.e("2getMsgMap2-2", t.getMsgMap()+"");
//                        ToastUtil.show(context, t.getMsg());
//                    }
//                } else {
//                    LogUtil.e("2getMsgMap2-3", t.getMsgMap()+"");
//                    ToastUtil.show(context, t.getMsg());
//                }
//            }
//        }, getRequestTag());
//    }

    private void initView() {
        adapter=new MyAdviceItemAdapter(context,null);
        pullToRefresh.setAdapter(adapter);
        pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefresh.setScrollingWhileRefreshingEnabled(false);
        init();

        pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) { //下拉刷新
                ApiManager.getInstance().adviceApi.getAdviceItems(1, pageSize, new HttpClientAdapter.Callback<PageData<AdviceItem>>() {
                    @Override
                    public void call(Result<PageData<AdviceItem>> t) {
                        if (t.isSuccess()) {
                            PageData<AdviceItem> data = t.getData();
                            ArrayList<AdviceItem> dataList = (ArrayList<AdviceItem>) data.getValue();

//                            pageCountCache = 1;
////                            dataDao.add("recodeList", dataList);
//                            WeiTaiXinApplication.getInstance().putValue("tvUsedCount", data.getCount() + "");

                            tvUsedCount.setText(data.getCount() + "");
                            adapter.datas.clear();
                            adapter.setDatas(dataList);
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.show(context, t.getMsg());
                        }
                        pageIndex = 1;
                        if (pullToRefresh != null) {
                            pullToRefresh.onRefreshComplete();
                        }
                    }
                }, getRequestTag());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) { //上拉加载更多

                ApiManager.getInstance().adviceApi.getAdviceItems((++pageIndex), pageSize, new HttpClientAdapter.Callback<PageData<AdviceItem>>() {
                    @Override
                    public void call(Result<PageData<AdviceItem>> t) {
                        if (t.isSuccess()) {
                            PageData<AdviceItem> data = t.getData();
                            ArrayList<AdviceItem> dataList = (ArrayList<AdviceItem>) data.getValue();
                            LogUtil.d("PageData", "ArrayList: " + dataList.size());

//                            if (pageCountCache <= 3 && dataList.size() > 0) {
//                                dataDao.add("recodeList", dataList);
//                                WeiTaiXinApplication.getInstance().putValue("tvUsedCount", data.getCount() + "");
//                            }
//                            ++pageCountCache;


                            tvUsedCount.setText(data.getCount() + "");
                            adapter.addDatas(dataList);
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.show(context, t.getMsg());
                            pageIndex--;
                        }
                        if (pullToRefresh != null) {
                            pullToRefresh.onRefreshComplete();
                        }
                    }
                }, getRequestTag());
            }
        });

        pullToRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //1提交但为咨询  2咨询未回复  3咨询已回复  4咨询已删除
                AdviceItem adviceItem = (AdviceItem) adapter.getItem(position - 1);
                int status= adviceItem.getStatus();
                if (status == 0) {

                } else if (status == 1) {

                } else if (status == 2) {

                } else if (status == 3) {

                }
            }
        });

    }

    private void pullDatas() {
        CustomDialog customDialog=new CustomDialog();
        Dialog dialog=customDialog.createDialog1(context,"从数据库中加载...");
        dialog.show();
        ArrayList<AdviceItem> adviceItems = dataDao.getAllRecord(pageSize);
        if (adviceItems.size()>0) {
            tvUsedCount.setText(WeiTaiXinApplication.getInstance().getValue("tvUsedCount", 0 + ""));
            adapter.setDatas(adviceItems);
            adapter.notifyDataSetChanged();
            customDialog.dismiss();
            customDialog=null;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pullFirstData(null);
                }
            }, 1000);
        }else{
            customDialog.dismiss();
            customDialog=null;
            CustomDialog customDialogTwo=new CustomDialog();
            Dialog dialogTwo=customDialogTwo.createDialog1(context,"网络数据中加载...");
            dialogTwo.show();
            pullFirstData(customDialogTwo);
        }
    }


    public void pullFirstData(final CustomDialog customDialogTwo){
        ApiManager.getInstance().adviceApi.getAdviceItems(1, 30, new HttpClientAdapter.Callback<PageData<AdviceItem>>() {
            @Override
            public void call(Result<PageData<AdviceItem>> t) {
                if (t.isSuccess()) {
                    PageData<AdviceItem> data = t.getData();
                    ArrayList<AdviceItem> dataList = (ArrayList<AdviceItem>) data.getValue();

                    //缓存前30条
                    pageCountCache =1;
                    dataDao.add(DataDBHelper.tableName, dataList);
                    WeiTaiXinApplication.getInstance().putValue("tvUsedCount", data.getCount() + "");
                    tvUsedCount.setText(data.getCount() + "");


                    ArrayList<AdviceItem> dataListPage=new ArrayList<AdviceItem>();
                    if (dataList.size() >= pageSize) {
                        for (int i = 0; i < pageSize; i++) {
                            dataListPage.add(dataList.get(i));
                        }
                    } else {
                        for (int i = 0; i < dataList.size(); i++) {
                            dataListPage.add(dataList.get(i));
                        }
                    }

                    //合并
                    sortAdviceItem(dataListPage);

                    adapter.datas.clear();
                    adapter.setDatas(dataListPage);
                    adapter.notifyDataSetChanged();
                } else {
                    LogUtil.e("2getMsgMap", t.getMsgMap()+"");
                    ToastUtil.show(context, t.getMsg());
                }
                if (pullToRefresh!=null){
                    pullToRefresh.onRefreshComplete();
                }
                if (customDialogTwo != null) {
                    customDialogTwo.dismiss();
                }
            }
        }, getRequestTag());
    }

    private void sortAdviceItem(ArrayList<AdviceItem> dataListPage) {
        for (LocalAdviceItem item : localAdviceItems) {
            AdviceItem advice = new AdviceItem();
            advice.setId(Integer.parseInt(item.mid));
            advice.setGestationalWeeks(item.gestationalWeeks);
            advice.setTestTime(item.testTime);
//            advice.setTestTime(DateTimeTool.str2Date(item.testTime));
            advice.setTestTimeLong(Integer.parseInt(item.testTimeLong));
            advice.setStatus(Integer.parseInt(item.status));
            dataListPage.add(advice);
        }
    }


    private void init() {
        ILoadingLayout startLabels = pullToRefresh.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        // 设置下拉刷新文本
        pullToRefresh.getLoadingLayoutProxy(false, true).setPullLabel("上拉刷新...");
        pullToRefresh.getLoadingLayoutProxy(false, true).setReleaseLabel("放开刷新...");
        pullToRefresh.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        // 设置上拉刷新文本
        pullToRefresh.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        pullToRefresh.getLoadingLayoutProxy(true, false).setReleaseLabel("放开刷新...");
        pullToRefresh.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
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
//				.displayer(new RoundedBitmapDisplayer(5))
                .build();
        return options;
    }


}




