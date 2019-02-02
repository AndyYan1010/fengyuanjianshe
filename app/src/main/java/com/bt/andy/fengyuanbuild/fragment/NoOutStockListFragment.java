package com.bt.andy.fengyuanbuild.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.R;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/21 16:18
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class NoOutStockListFragment extends Fragment implements View.OnClickListener {
    private View               mRootView;
    private ImageView          img_back;
    private TextView           tv_title;
    private SwipeRefreshLayout swipe;
    private RecyclerView       recyview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_noout_sheet, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_title = mRootView.findViewById(R.id.tv_title);
        swipe = mRootView.findViewById(R.id.swipe);
        recyview = mRootView.findViewById(R.id.recyview);
    }

    private void initData() {
        tv_title.setText("订单未出库列表");
        img_back.setVisibility(View.VISIBLE);


        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;

        }
    }
}
