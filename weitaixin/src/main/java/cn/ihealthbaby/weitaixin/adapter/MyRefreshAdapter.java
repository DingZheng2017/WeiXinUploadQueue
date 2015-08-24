package cn.ihealthbaby.weitaixin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ihealthbaby.client.model.Information;
import cn.ihealthbaby.weitaixin.R;
import cn.ihealthbaby.weitaixin.library.util.RelativeDateFormat;
import cn.ihealthbaby.weitaixin.view.RoundImageView;


public class MyRefreshAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Information> datas;

    private LayoutInflater mInflater;

    public MyRefreshAdapter(Context context, ArrayList<Information> datas) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        setDatas(datas);
    }

    public void setDatas(ArrayList<Information> datas) {
        if (datas == null) {
            this.datas = new ArrayList<Information>();
        } else {
            this.datas = datas;
        }
    }

    public void addDatas(ArrayList<Information> datas) {
        if (this.datas != null) {
            this.datas.addAll(datas);
        }
    }


    @Override
    public int getCount() {
        return this.datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_information, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Information data = datas.get(position);
        ImageLoader.getInstance().displayImage(data.getPicPath(), viewHolder.iv_head_icon, setDisplayImageOptions());
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_message.setText(data.getContext());
        viewHolder.tv_create_time.setText(RelativeDateFormat.format(data.getCreateTime()));

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_head_icon)
        RoundImageView iv_head_icon;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_message)
        TextView tv_message;
        @Bind(R.id.tv_create_time)
        TextView tv_create_time;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


    public DisplayImageOptions setDisplayImageOptions() {
        DisplayImageOptions options = null;
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