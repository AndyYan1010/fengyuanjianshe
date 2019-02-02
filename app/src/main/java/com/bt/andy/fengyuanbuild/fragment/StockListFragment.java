package com.bt.andy.fengyuanbuild.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.R;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/21 15:02
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class StockListFragment extends Fragment implements View.OnClickListener {
    private View      mRootView;
    private ImageView img_back;
    private TextView  tv_title;
    private TextView  tv_right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_stock, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = (ImageView) mRootView.findViewById(R.id.img_back);
        tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
        tv_right = (TextView) mRootView.findViewById(R.id.tv_right);
    }

    private void initData() {
        tv_title.setText("查询库存");
//        tv_right.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(this);
//        tv_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.tv_right:

                break;
        }
    }
}
