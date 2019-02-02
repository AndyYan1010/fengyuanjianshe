package com.bt.andy.fengyuanbuild.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.messegeInfo.MainMenuEntity;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/10/8 14:55
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MenuGridviewApater extends BaseAdapter {
    private Context              mContext;
    private List<MainMenuEntity> mList;

    public MenuGridviewApater(Context context, List list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder;
        if (null == view) {
            view = View.inflate(mContext, R.layout.gv_menu_item, null);
            viewHolder = new MyViewHolder();
            viewHolder.img_icon = view.findViewById(R.id.img_icon);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        MainMenuEntity mainMenuEntity = mList.get(i);
        viewHolder.img_icon.setImageResource(mainMenuEntity.getResId());
        viewHolder.tv_title.setText(mainMenuEntity.getText());
        return view;
    }

    private class MyViewHolder {
        ImageView img_icon;
        TextView  tv_title;
    }
}
