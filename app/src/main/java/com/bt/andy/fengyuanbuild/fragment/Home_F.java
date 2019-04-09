package com.bt.andy.fengyuanbuild.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.MyAppliaction;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.activity.NotOutOfStockActivity;
import com.bt.andy.fengyuanbuild.activity.ReceivableSheetActivity;
import com.bt.andy.fengyuanbuild.activity.SearchStockActivity;
import com.bt.andy.fengyuanbuild.adapter.MenuGridviewApater;
import com.bt.andy.fengyuanbuild.messegeInfo.MainMenuEntity;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/22 16:41
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class Home_F extends Fragment {
    private View               mRootView;
    private TextView           tv_title;
    private GridView           gv_menu;
    private List               dadaList;
    private MenuGridviewApater menuAdapter;
    private int[]    resArr  = new int[]{R.drawable.ruku, R.drawable.chuku, R.drawable.kucunchauxn, R.drawable.yingshoubaobiao, R.drawable.weichuku, R.drawable.shenpi, 0};
    private String[] textArr = new String[]{"入库登记", "出库登记", "库存查询", "应收账款报表", "订单未出库表", "付款申请单", ""};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.home_f, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        tv_title = mRootView.findViewById(R.id.tv_title);
        gv_menu = mRootView.findViewById(R.id.gv_menu);
    }

    private void initData() {
        //        TotalGoodsFragment totalFgm = new TotalGoodsFragment();
        //        FragmentTransaction ftt = getFragmentManager().beginTransaction();
        //        ftt.add(R.id.frame_home, totalFgm, "totalFgm");
        //        ftt.commit();

        tv_title.setText("首页导航");
        dadaList = new ArrayList<>();
        for (int i = 0; i < resArr.length; i++) {
            MainMenuEntity data = new MainMenuEntity();
            data.setResId(resArr[i]);
            data.setText(textArr[i]);
            dadaList.add(data);
        }
        menuAdapter = new MenuGridviewApater(getContext(), dadaList);
        gv_menu.setAdapter(menuAdapter);
        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 2) {
                    //库存查询界面
                    //                    ToastUtils.showToast(getContext(), "暂未开通");
                    Intent intent = new Intent(getContext(), SearchStockActivity.class);
                    startActivity(intent);
                } else if (i == 3) {
                    //应收账款报表界面
                    //                    ToastUtils.showToast(getContext(), "暂未开通");
                    if (MyAppliaction.userAllRight){
                        Intent intent = new Intent(getContext(), ReceivableSheetActivity.class);
                        startActivity(intent);
                    }else {
                        ToastUtils.showToast(getContext(), "您暂未开通此权限");
                    }
                } else if (i == 4) {
                    //订单未出库表界面
                    //                    ToastUtils.showToast(getContext(), "暂未开通");
                    if (MyAppliaction.userAllRight){
                        Intent intent = new Intent(getContext(), NotOutOfStockActivity.class);
                        startActivity(intent);
                    }else {
                        ToastUtils.showToast(getContext(), "您暂未开通此权限");
                    }
                } else if (i == 5) {
                    //付款申请单审批界面
                    ToastUtils.showToast(getContext(), "暂未开通");
                    //                    Intent intent = new Intent(getContext(), BillForPayActivity.class);
                    //                    startActivity(intent);
                } else {
                    ToastUtils.showToast(getContext(), "暂未开通");
                }
            }
        });
    }
}
