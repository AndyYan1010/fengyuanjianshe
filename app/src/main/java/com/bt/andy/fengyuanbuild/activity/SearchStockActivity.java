package com.bt.andy.fengyuanbuild.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bt.andy.fengyuanbuild.BaseActivity;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.fragment.StockListFragment;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/21 13:55
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SearchStockActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_base);
        initView();
        initData();
    }

    private void initView() {
        StockListFragment orderListFt = new StockListFragment();
        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
        ftt.add(R.id.frame, orderListFt, "orderListFt");
        ftt.commit();
    }

    private void initData() {

    }
}
