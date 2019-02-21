package com.bt.andy.fengyuanbuild.adapter;

import android.content.Context;

import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.messegeInfo.StockInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/2/14 13:09
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class RecyNoOutStockAdapter extends BaseQuickAdapter<StockInfo, BaseViewHolder> {
    private Context mContext;

    public RecyNoOutStockAdapter(Context context, List data, int layoutResId) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StockInfo item) {
        helper.setText(R.id.tv_billno, item.getFbill());
        helper.setText(R.id.tv_date, item.getFdate().substring(0,9));
        helper.setText(R.id.tv_cusname, item.getFcusname());
        helper.setText(R.id.tv_goodsname, item.getFname());
        helper.setText(R.id.tv_unit, item.getFunit());
        helper.setText(R.id.tv_num, item.getFqty());
    }
}
