package com.bt.andy.fengyuanbuild.fragment;

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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.MyAppliaction;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.adapter.RecyApplyPayAdapter;
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
 * @创建时间 2019/2/11 10:01
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OrderListFragment extends Fragment {
    private View                mRootView;
    private SwipeRefreshLayout  swipe;
    private RecyclerView        recyview;
    private RecyApplyPayAdapter applyPayAdapter;
    private List<List>          mData;
    private PopupOpenHelper     popupOpenHelper;
    private boolean             mIsSelect;//当前的fragment是否选中
    private int                 mType;//0 已创建 1 已提交 2需审核  3已审核  4已报销

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_order_list, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        swipe = mRootView.findViewById(R.id.swipe);
        recyview = mRootView.findViewById(R.id.recyview);
    }

    private void initData() {
        //初始化swiperefresh
        initSwipeRefresh();
        //初始化recyclerview
        initRecyclerView();

        //查询付款单申请表列表数据
        getApplySheetListInfo();
    }

    private String middleSql = "";

    private void getApplySheetListInfo() {
        if (!mIsSelect) {
            swipe.setRefreshing(false);
            return;
        }
        if (null != swipe) {
            swipe.setRefreshing(true);
        }
        if (null == mData) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        //区别查询订单类别
                        if (0 == mType) {
                            middleSql = "FDOCUMENTSTATUS = 'A' or FDOCUMENTSTATUS ='D' or FDOCUMENTSTATUS ='Z' and FCREATORID ='" + MyAppliaction.memID + "'";
                        } else if (1 == mType) {
                            middleSql = "FDOCUMENTSTATUS = 'B' and FCREATORID ='" + MyAppliaction.memID + "'";
                        } else if (2 == mType) {
                            middleSql = "FDOCUMENTSTATUS = 'B' ";
                        } else if (3 == mType) {
                            middleSql = "FDOCUMENTSTATUS = 'C' and FCREATORID ='" + MyAppliaction.memID + "'";
                        } else if (4 == mType) {
                            middleSql = "FDOCUMENTSTATUS = 'C' and FCREATORID ='" + MyAppliaction.memID + "'";
                        }

                        String sql = "{\"FormId\": \"CN_PAYAPPLY\",\"FieldKeys\": \"FDATE,FBILLNO,FDOCUMENTSTATUS,FID,FUnpaidAmount\"," +
                                "    \"FilterString\": \"" + middleSql + "\",\"OrderString\": \"\"," +
                                "    \"TopRowCount\": 1000,\"StartRow\": 0,\"Limit\": 0}";
                        //查询列表单据
                        List<List<Object>> lists = client.executeBillQuery(sql);
                        //[["2019-01-10T00:00:00","FKSQ000001","C"],["2019-01-14T00:00:00","FKSQ000002","C"],["2019-01-15T00:00:00","FKSQ000003","C"],["2019-01-31T00:00:00","FKSQ000004","A"]]
                        if (3 == mType) {
                            for (List bean : lists) {
                                String money = null == bean.get(4).toString() ? "" : bean.get(4).toString();
                                if (!"".equals(money) && !money.startsWith("0")) {
                                    mData.add(bean);
                                }
                            }
                        } else if (4 == mType) {
                            for (List bean : lists) {
                                String money = null == bean.get(4).toString() ? "" : bean.get(4).toString();
                                if ("".equals(money) || money.startsWith("0")) {
                                    mData.add(bean);
                                }
                            }
                        } else {
                            mData.addAll(lists);
                        }
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
                popupOpenHelper = new PopupOpenHelper(getContext(), recyview, R.layout.popup_warn_title);
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

    public void setIsSelect(boolean select) {
        mIsSelect = select;
    }

    public void setType(int type) {
        mType = type;
    }
}
