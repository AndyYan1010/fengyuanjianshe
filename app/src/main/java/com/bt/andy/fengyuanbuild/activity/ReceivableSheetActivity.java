package com.bt.andy.fengyuanbuild.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bt.andy.fengyuanbuild.BaseActivity;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.fragment.ReceiveSheetFragment;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/21 15:10
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ReceivableSheetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_base);
        initView();
        initData();
    }

    private void initView() {
        ReceiveSheetFragment recSheetFt = new ReceiveSheetFragment();
        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
        ftt.add(R.id.frame, recSheetFt, "recSheetFt");
        ftt.commit();
    }

    private void initData() {

    }
}
