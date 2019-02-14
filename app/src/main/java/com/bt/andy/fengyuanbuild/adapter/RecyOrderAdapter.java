package com.bt.andy.fengyuanbuild.adapter;

import android.content.Context;

import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.messegeInfo.StockInfo;
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

public class RecyOrderAdapter extends BaseQuickAdapter<StockInfo, BaseViewHolder> {
    private Context mContext;

    public RecyOrderAdapter(int layoutResId, Context context, List data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final StockInfo item) {
        helper.setText(R.id.tv_name, item.getFname());
        helper.setText(R.id.tv_qcmoney, item.getFcqmoney());
        helper.setText(R.id.tv_qmmoney, item.getFqmye());
        helper.setText(R.id.tv_bqys, item.getFbqys());
        helper.setText(R.id.tv_bqss, item.getFbqss());

    }
}
