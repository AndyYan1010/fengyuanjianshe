package com.bt.andy.fengyuanbuild.adapter;

import android.content.Context;

import com.bt.andy.fengyuanbuild.R;
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

public class RecyApplyPayAdapter extends BaseQuickAdapter<List, BaseViewHolder> {
    private Context mContext;

    public RecyApplyPayAdapter(int layoutResId, Context context, List data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final List item) {
        //(ImageView) helper.getView(R.id.img_call)
        helper.setText(R.id.tv_order, item.get(1).toString());
        helper.setText(R.id.tv_date, item.get(0).toString().substring(0,10)+" "+item.get(0).toString().substring(11,16));//2019-01-14T00:00:00
        String state = item.get(2).toString();
        if ("A".equals(state)){
            helper.setText(R.id.tv_state,"创建    ");
        }else if ("B".equals(state)){
            helper.setText(R.id.tv_state,"审核中");
        }else if ("C".equals(state)){
            helper.setText(R.id.tv_state,"已审核");
        }else if ("D".equals(state)){
            helper.setText(R.id.tv_state,"重新审核");
        }else {
            helper.setText(R.id.tv_state,"暂存    ");
        }


    }
}
