package com.bt.andy.fengyuanbuild.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.MyAppliaction;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.adapter.RecyApplyPayAdapter;
import com.bt.andy.fengyuanbuild.fragment.AddApplyPayFragment;
import com.bt.andy.fengyuanbuild.fragment.SearchOrderDetailFragment;
import com.bt.andy.fengyuanbuild.messegeInfo.DeleteResultInfo;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.PopupOpenHelper;
import com.bt.andy.fengyuanbuild.utils.ThreadUtils;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import kingdee.bos.webapi.client.K3CloudApiClient;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/25 8:31
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ApplyForPayFragment extends Fragment implements View.OnClickListener {
    private View                mRootView;
    private ImageView           img_back;
    private TextView            tv_title;
    private TextView            tv_right;
    private SwipeRefreshLayout  swipe;
    private RecyclerView        recyview;
    private RecyApplyPayAdapter applyPayAdapter;
    private List<List>          mData;
    private PopupOpenHelper     popupOpenHelper;

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
        swipe = mRootView.findViewById(R.id.swipe);
        recyview = mRootView.findViewById(R.id.recyview);
    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_title.setText("付款申请单");

        //初始化swiperefresh
        initSwipeRefresh();
        //初始化recyclerview
        initRecyclerView();

        img_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        //查询付款单申请表列表数据
        getApplySheetListInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.tv_right://跳转新增界面
                AddApplyPayFragment addApplyFt = new AddApplyPayFragment();
                FragmentTransaction ftt = getActivity().getSupportFragmentManager().beginTransaction();
                ftt.add(R.id.frame, addApplyFt, "addApplyFt");
                ftt.addToBackStack("addApplyFt");
                ftt.commit();
                break;
        }
    }

    private void getApplySheetListInfo() {
        swipe.setRefreshing(true);
        mData.clear();
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        String sql = "{\"FormId\": \"CN_PAYAPPLY\",\"FieldKeys\": \"FDATE,FBILLNO,FDOCUMENTSTATUS,FID\"," +
                                "    \"FilterString\": \"\",\"OrderString\": \"\"," +
                                "    \"TopRowCount\": 1000,\"StartRow\": 0,\"Limit\": 0}";
                        //查询列表单据
                        List<List<Object>> lists = client.executeBillQuery(sql);
                        //[["2019-01-10T00:00:00","FKSQ000001","C"],["2019-01-14T00:00:00","FKSQ000002","C"],["2019-01-15T00:00:00","FKSQ000003","C"],["2019-01-31T00:00:00","FKSQ000004","A"]]
                        mData.addAll(lists);
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                swipe.setRefreshing(false);
                                applyPayAdapter.notifyDataSetChanged();
                                ToastUtils.showToast(getContext(), "查询成功。");
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                swipe.setRefreshing(false);
                                ToastUtils.showToast(getContext(), "查询失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            swipe.setRefreshing(false);
                            ToastUtils.showToast(getContext(), "查询失败！");
                        }
                    });
                }
            }
        });
    }

    private void initSwipeRefresh() {
        swipe.setColorSchemeColors(getResources().getColor(R.color.blue_icon),
                getResources().getColor(R.color.yellow_40), getResources().getColor(R.color.red_160));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getApplySheetListInfo();
            }
        });
    }

    private void initRecyclerView() {
        mData = new ArrayList();
        recyview.setLayoutManager(new LinearLayoutManager(getContext()));
        applyPayAdapter = new RecyApplyPayAdapter(R.layout.recy_adapter_apply_pay, getContext(), mData);
        recyview.setAdapter(applyPayAdapter);
        applyPayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //查看详情
                SearchOrderDetailFragment orderDetailFt = new SearchOrderDetailFragment();
                orderDetailFt.setOrderNo(mData.get(position).get(1).toString(), mData.get(position).get(2).toString());
                FragmentTransaction ftt = getActivity().getSupportFragmentManager().beginTransaction();
                ftt.add(R.id.frame, orderDetailFt, "orderDetailFt");
                ftt.addToBackStack("orderDetailFt");
                ftt.commit();
            }
        });
        applyPayAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                popupOpenHelper = new PopupOpenHelper(getContext(), tv_right, R.layout.popup_warn_title);
                popupOpenHelper.openPopupWindow(true, Gravity.CENTER);
                popupOpenHelper.setOnPopupViewClick(new PopupOpenHelper.ViewClickListener() {
                    @Override
                    public void onViewClickListener(PopupWindow popupWindow, View inflateView) {
                        //长按删除
                        final TextView tv_cont = inflateView.findViewById(R.id.tv_cont);
                        final TextView tv_cancle = inflateView.findViewById(R.id.tv_cancle);
                        final TextView tv_sure = inflateView.findViewById(R.id.tv_sure);
                        tv_cont.setText("您确定要删除该条申请单吗？");
                        tv_cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupOpenHelper.dismiss();
                            }
                        });
                        tv_sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if ("B".equals(mData.get(position).get(2).toString()) || "C".equals(mData.get(position).get(2).toString())) {
                                    ToastUtils.showToast(getContext(), "只能删除创建，暂存，重新审核状态的单子");
                                } else {
                                    //删除申请单
                                    deleteThisOrder(position);
                                }
                                popupOpenHelper.dismiss();
                            }
                        });
                    }
                });

                //长按删除
                //                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_LIGHT);
                //                builder.setTitle("温馨提示");
                //                builder.setMessage("您确定要删除该条申请单吗？");
                //                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                //
                //                    @Override
                //                    public void onClick(DialogInterface dialog, int which) {
                //
                //                    }
                //                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                //                    @Override
                //                    public void onClick(DialogInterface dialog, int which) {
                //                        dialog.cancel();
                //                    }
                //                }).create().show();
                return false;
            }
        });
    }

    private void deleteThisOrder(final int position) {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        String sql = "{\"CreateOrgId\": 0,\"Numbers\": [],\"Ids\": \"" + mData.get(position).get(3).toString() + "\"}";
                        //删除单据
                        String cn_payapply = client.delete("CN_PAYAPPLY", sql);
                        Gson gson = new Gson();
                        final DeleteResultInfo deleteResultInfo = gson.fromJson(cn_payapply, DeleteResultInfo.class);
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("ture".equals(deleteResultInfo.getResult().getResponseStatus().getIsSuccess())) {
                                    mData.remove(position);
                                    applyPayAdapter.notifyDataSetChanged();
                                } else {
                                    ToastUtils.showToast(getContext(), "删除失败！");
                                }
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getContext(), "删除失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(getContext(), "删除失败！");
                        }
                    });
                }
            }
        });
    }
}
