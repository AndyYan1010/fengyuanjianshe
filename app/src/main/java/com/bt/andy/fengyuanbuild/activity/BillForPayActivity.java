package com.bt.andy.fengyuanbuild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bt.andy.fengyuanbuild.BaseActivity;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.fragment.ApplyForPayFragment;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/22 16:04
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class BillForPayActivity extends BaseActivity {
    private ApplyForPayFragment applyPayFt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_base);
        initView();
        initData();
    }

    private void initView() {
        applyPayFt = new ApplyForPayFragment();
        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
        ftt.add(R.id.frame, applyPayFt, "applyPayFt");
        ftt.commit();
    }

    private void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        applyPayFt.onActivityResult(requestCode, resultCode, data);
    }
}
