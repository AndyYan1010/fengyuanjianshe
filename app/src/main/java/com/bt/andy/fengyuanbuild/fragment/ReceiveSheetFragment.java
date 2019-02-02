package com.bt.andy.fengyuanbuild.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.adapter.RecyOrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/21 15:29
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ReceiveSheetFragment extends Fragment implements View.OnClickListener {
    private View               mRootView;
    private ImageView          img_back;
    private TextView           tv_title;
    private TextView           tv_right;
    private TextView           tv_start;
    private TextView           tv_end;
    private TextView           tv_kehu;
    private TextView           tv_search;
    private SwipeRefreshLayout swiperefresh;
    private RecyclerView       recyview;
    private RecyOrderAdapter   orderAdapter;
    private List               mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_receive_sheet, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_title = mRootView.findViewById(R.id.tv_title);
        tv_right = mRootView.findViewById(R.id.tv_right);

        tv_start = mRootView.findViewById(R.id.tv_start);
        tv_end = mRootView.findViewById(R.id.tv_end);
        tv_kehu = mRootView.findViewById(R.id.tv_kehu);
        tv_search = mRootView.findViewById(R.id.tv_search);
        swiperefresh = mRootView.findViewById(R.id.swipe);
        recyview = mRootView.findViewById(R.id.recyview);
    }

    private void initData() {
        tv_title.setText("应收账款报表");
        img_back.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);

        //初始化列表数据
        initRecyclerview();

        img_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        tv_end.setOnClickListener(this);
        tv_kehu.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.blue_icon),
                getResources().getColor(R.color.yellow_40), getResources().getColor(R.color.red_160));
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchOrderList();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.tv_right://跳转新增界面

                break;
            case R.id.tv_start://选择起始时间

                break;
            case R.id.tv_end://选择结束时间

                break;
            case R.id.tv_kehu://选择客户

                break;
            case R.id.tv_search://查询账单列表
                searchOrderList();
                break;
        }
    }

    private void searchOrderList() {
        //查询账单列表
        swiperefresh.setRefreshing(true);

    }

    private void initRecyclerview() {
        mData = new ArrayList();
        mData.add("");
        mData.add("");
        mData.add("");
        mData.add("");
        mData.add("");
        recyview.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderAdapter = new RecyOrderAdapter(R.layout.recy_adapter_receive_sheet, getContext(), mData);
        recyview.setAdapter(orderAdapter);
    }
}
