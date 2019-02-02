package com.bt.andy.fengyuanbuild.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/11/20 14:07
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class RecyOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;

    public RecyOrderAdapter(int layoutResId, Context context, List data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final String item) {
        //        (ImageView) helper.getView(R.id.img_call)
        //        helper.setText(R.id.tv_place, item.getFhAddress() + "  →  " + item.getShAddress());

    }
}
