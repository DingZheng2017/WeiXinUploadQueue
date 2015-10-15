package cn.ihealthbaby.weitaixinpro.ui.record;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ihealthbaby.client.model.HClientUser;
import cn.ihealthbaby.weitaixin.library.data.database.dao.Record;
import cn.ihealthbaby.weitaixin.library.data.database.dao.RecordBusinessDao;
import cn.ihealthbaby.weitaixin.library.log.LogUtil;
import cn.ihealthbaby.weitaixin.library.util.ToastUtil;
import cn.ihealthbaby.weitaixinpro.R;
import cn.ihealthbaby.weitaixinpro.base.BaseFragment;

/**
 */
public class RecordFragment extends BaseFragment {
	private static final int PAGE_SIZE = 10;
	private final static String TAG = "RecordFragment";
	/**
	 * 起始页码 从1开始
	 */
	private static final int FIRST_PAGE = 1;
	public HClientUser hClientUser;
	//
	public int currentPage;
	public long count;
	private TextView tvCancel;
	private TextView title_text;
	private TextView function;
	private boolean isDeleteState = false;
	private ArrayList<Record> list;
	private boolean loading;
	private LocalRecordRecyclerViewAdapter adapter;
	private View viewLayout;
	private SwipeRefreshLayout swipeRefreshLayout;
	private RecyclerView recyclerView;
	private LinearLayoutManager layoutManager;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			swipeRefreshLayout.setRefreshing(false);
			adapter.notifyAllDataSetChanged();
		}
	};

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		viewLayout = ((View) inflater.inflate(R.layout.fragment_swipe_refresh_recycler2, null));
		swipeRefreshLayout = (SwipeRefreshLayout) viewLayout.findViewById(R.id.swipe_refresh_layout);
		recyclerView = (RecyclerView) viewLayout.findViewById(R.id.recycler_view);
		tvCancel = (TextView) viewLayout.findViewById(R.id.tvCancel);
		title_text = (TextView) viewLayout.findViewById(R.id.title_text);
		function = (TextView) viewLayout.findViewById(R.id.function);
		title_text.setText("监测记录");
		function.setVisibility(View.VISIBLE);
		function.setText("批量删除");
		function.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isDeleteState) {
					adapter.doDeleteAction();
				}
				if (!isDeleteState) {
					function.setText("确定删除");
					isDeleteState = true;
					adapter.setIsDelFalg(true);
					swipeRefreshLayout.setRefreshing(false);
					adapter.notifyDataSetChanged();
					tvCancel.setVisibility(View.VISIBLE);
					tvCancel.setText("取消");
				}
			}
		});
		tvCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isDeleteState = false;
				adapter.setIsDelFalg(false);
				swipeRefreshLayout.setRefreshing(false);
				adapter.initMap(adapter.getItemCount());
				adapter.notifyDataSetChanged();
				function.setText("批量删除");
				tvCancel.setVisibility(View.INVISIBLE);
				tvCancel.setText("取消");
			}
		});
		return viewLayout;
	}

	public ArrayList<Record> gg() {
		ArrayList<Record> adviceItems = new ArrayList<Record>();
		for (int i = 0; i < 20; i++) {
			Record record = new Record();
			record.setRecordStartTime(new Date());
			record.setUserName("UserName:" + i);
			record.setDuration(23);
			record.setUploadState(Record.UPLOAD_STATE_UPLOADING);
			adviceItems.add(record);
		}
		return adviceItems;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
		recyclerView.setLayoutManager(layoutManager);
		list = new ArrayList<Record>();
		adapter = new LocalRecordRecyclerViewAdapter(getActivity(), list);
		recyclerView.setAdapter(adapter);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				int itemCount = adapter.getItemCount();
				layoutManager.findFirstCompletelyVisibleItemPosition();
				swipeRefreshLayout.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);
				if (layoutManager.findLastVisibleItemPosition() > itemCount - 5) {
					request(currentPage + 1);
				}
			}
		});
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				LogUtil.d(TAG, "onrefresh isRefreshing %s", swipeRefreshLayout.isRefreshing());
				swipeRefreshLayout.setRefreshing(true);
				reset();
			}
		});
		reset();
	}

	public void reset() {
		list.clear();
		count = 0L;
		request(FIRST_PAGE);
	}

	private void request(final int page) {
		LogUtil.d(TAG, "request count: [%s],page:[%s]", count, page);
		if (count != 0 && page >= (count / PAGE_SIZE) + 1) {
			ToastUtil.show(getActivity().getApplicationContext(), "没有更多数据了");
			return;
		}
		new Thread() {
			@Override
			public void run() {
				try {
					RecordBusinessDao recordBusinessDao = RecordBusinessDao.getInstance(getActivity().getApplicationContext());
					long allCount = recordBusinessDao.count(Record.UPLOAD_STATE_LOCAL, Record.UPLOAD_STATE_UPLOADING, Record.UPLOAD_STATE_CLOUD);
					List<Record> records = recordBusinessDao.queryPagedRecord(page, PAGE_SIZE, Record.UPLOAD_STATE_LOCAL, Record.UPLOAD_STATE_UPLOADING, Record.UPLOAD_STATE_CLOUD);
					list.addAll(records);
					LogUtil.d(TAG, "count [%s], page [%s], pageSize [%s]", allCount, page, PAGE_SIZE);
					LogUtil.d(TAG, "list=%s", records);
					//成功查询加入list后,才更改总数,类似事务
					count = allCount;
					currentPage = page;
					handler.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
