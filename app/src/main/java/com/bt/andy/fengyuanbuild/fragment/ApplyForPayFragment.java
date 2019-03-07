package com.bt.andy.fengyuanbuild.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.MyAppliaction;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.adapter.MyPagerAdapter;
import com.bt.andy.fengyuanbuild.viewmodle.MyFixedViewpager;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/25 8:31
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ApplyForPayFragment extends Fragment implements View.OnClickListener {
    private View                    mRootView;
    private ImageView               img_back;
    private TextView                tv_title;
    private TextView                tv_right;
    private TabLayout               tablayout;
    private MyFixedViewpager        view_pager;
    private List<OrderListFragment> fragmentsList;
    private MyPagerAdapter          myPagerAdapter;
    private List<String>            contsList;
    private AddApplyPayFragment     addApplyFt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_apply_pay, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_title = mRootView.findViewById(R.id.tv_title);
        tv_right = mRootView.findViewById(R.id.tv_right);
        tablayout = mRootView.findViewById(R.id.tablayout);
        view_pager = mRootView.findViewById(R.id.view_pager);

    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_title.setText("付款申请单");
        //初始化tab列表
        initTabLayout();

        img_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.tv_right://跳转新增界面
                addApplyFt = new AddApplyPayFragment();
                FragmentTransaction ftt = getActivity().getSupportFragmentManager().beginTransaction();
                ftt.add(R.id.frame, addApplyFt, "addApplyFt");
                ftt.addToBackStack("addApplyFt");
                ftt.commit();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addApplyFt.onActivityResult(requestCode, resultCode, data);
    }

    private void initTabLayout() {
        contsList = new ArrayList<>();
        fragmentsList = new ArrayList<>();

        contsList.add("已创建");
        contsList.add("审核中");
        OrderListFragment orderListFgt0 = new OrderListFragment();
        orderListFgt0.setType(0);
        orderListFgt0.setIsSelect(true);
        fragmentsList.add(orderListFgt0);
        OrderListFragment orderListFgt1 = new OrderListFragment();
        orderListFgt1.setType(1);
        fragmentsList.add(orderListFgt1);

        if (null != MyAppliaction.userType && !"".equals(MyAppliaction.userType) && !MyAppliaction.userType.contains("员工")) {
            contsList.add("需审核");
            OrderListFragment orderListFgt2 = new OrderListFragment();
            orderListFgt2.setType(2);
            fragmentsList.add(orderListFgt2);
        }
        contsList.add("已审核");
        contsList.add("已报销");
        OrderListFragment orderListFgt3 = new OrderListFragment();
        orderListFgt3.setType(3);
        fragmentsList.add(orderListFgt3);
        OrderListFragment orderListFgt4 = new OrderListFragment();
        orderListFgt4.setType(4);
        fragmentsList.add(orderListFgt4);

        // 创建ViewPager适配器
        myPagerAdapter = new MyPagerAdapter(getFragmentManager());//getChildFragmentManager()
        myPagerAdapter.setFragments((ArrayList<OrderListFragment>) fragmentsList);
        // 给ViewPager设置适配器
        view_pager.setAdapter(myPagerAdapter);
        //        view_pager.setCanScroll(true);
        tablayout.setupWithViewPager(view_pager);
        for (int i = 0; i < contsList.size(); i++) {
            tablayout.getTabAt(i).setText(contsList.get(i));
        }
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (OrderListFragment fragment : fragmentsList) {
                    fragment.setIsSelect(false);
                }
                fragmentsList.get(tab.getPosition()).setIsSelect(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                for (OrderListFragment fragment : fragmentsList) {
                    fragment.setIsSelect(false);
                }
                fragmentsList.get(tab.getPosition()).setIsSelect(true);
            }
        });
    }
}
