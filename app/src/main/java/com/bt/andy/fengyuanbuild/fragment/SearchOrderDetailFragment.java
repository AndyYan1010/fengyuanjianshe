package com.bt.andy.fengyuanbuild.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.MyAppliaction;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.adapter.LvAddApplyAdapter;
import com.bt.andy.fengyuanbuild.messegeInfo.DeleteResultInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.OrderDataInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.SearchDetailInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.SubListInfo;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.MyFragmentManagerUtil;
import com.bt.andy.fengyuanbuild.utils.ThreadUtils;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.bt.andy.fengyuanbuild.viewmodle.MyListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import kingdee.bos.webapi.client.K3CloudApiClient;

/**
 * @创建者 AndyYan
 * @创建时间 2019/2/1 10:17
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SearchOrderDetailFragment extends Fragment implements View.OnClickListener {
    private View              mRootView;
    private ImageView         img_back;
    private TextView          tv_title;
    private TextView          tv_right;//驳回
    private TextView          tv_wl;
    private TextView          tv_dwlx;
    private TextView          tv_skdw;
    private TextView          tv_fplx;
    private TextView          tv_zdr;
    private TextView          tv_use;
    private TextView          tv_submit;//审核
    private MyListView        lv_order;
    private List<SubListInfo> mData;//子表数据
    private LvAddApplyAdapter addApplyAdapter;
    private String            mOrderNo;//单号
    private String            mKind;//显示类别
    private OrderDataInfo     mOrderDataInfo;//整个订单表信息

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_order_detail, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_title = mRootView.findViewById(R.id.tv_title);
        tv_right = mRootView.findViewById(R.id.tv_right);
        tv_wl = mRootView.findViewById(R.id.tv_wl);
        tv_dwlx = mRootView.findViewById(R.id.tv_dwlx);
        tv_skdw = mRootView.findViewById(R.id.tv_skdw);
        tv_fplx = mRootView.findViewById(R.id.tv_fplx);
        tv_zdr = mRootView.findViewById(R.id.tv_zdr);
        tv_use = mRootView.findViewById(R.id.tv_use);
        lv_order = mRootView.findViewById(R.id.lv_order);
        tv_submit = mRootView.findViewById(R.id.tv_submit);
    }

    private void initData() {
        tv_title.setText("付款申请单详情");
        img_back.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("驳回");
        if ("A".equals(mKind)) {
            tv_submit.setText("提交审核");
        } else if ("B".equals(mKind)) {
            tv_submit.setText("审核");
        } else if ("C".equals(mKind)) {
            tv_submit.setVisibility(View.GONE);
        } else if ("D".equals(mKind)) {
            tv_submit.setVisibility(View.GONE);
        } else {
            tv_submit.setVisibility(View.GONE);
        }
        mOrderDataInfo = new OrderDataInfo();
        img_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        //初始化子表list
        initListView();
        //获取付款申请单详情
        getOrderDetail();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                MyFragmentManagerUtil.closeTopFragment(this);
                break;
            case R.id.tv_right://驳回

                break;
            case R.id.tv_submit://审核
                String check = String.valueOf(tv_submit.getText());
                if ("审核".equals(check)) {
                    checkOrder();
                } else if ("提交审核".equals(check)) {
                    submitData();
                }
                break;
        }
    }

    private void submitData() {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        //提交审核
                        String sqlSub = "{\"CreateOrgId\": 0,\"Numbers\": [\"" + mOrderNo + "\"],\"Ids\": \"\"}";
                        String cn_payapply = client.submit("CN_PAYAPPLY", sqlSub);
                        Gson gson = new Gson();
                        final DeleteResultInfo resultInfo2 = gson.fromJson(cn_payapply, DeleteResultInfo.class);
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("true".equals(resultInfo2.getResult().getResponseStatus().getIsSuccess())) {
                                    ToastUtils.showToast(getContext(), "提交成功");
                                    //提交成功
                                    MyFragmentManagerUtil.closeTopFragment(SearchOrderDetailFragment.this);
                                } else {
                                    ToastUtils.showToast(getContext(), "提交失败");
                                }
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getContext(), "提交失败");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(getContext(), "提交失败");
                        }
                    });
                }
            }
        });
    }

    private void initListView() {
        mData = new ArrayList<>();
        addApplyAdapter = new LvAddApplyAdapter(getContext(), mData, "search");
        lv_order.setAdapter(addApplyAdapter);
    }

    private void checkOrder() {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        String sql = "{\"CreateOrgId\": 0,\"Numbers\": [\"" + mOrderNo + "\"],\"Ids\": \"\",\"InterationFlags\": \"\"}";
                        //查询列表单据
                        final String cn_payapply = client.audit("CN_PAYAPPLY", sql);
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                DeleteResultInfo deleteResultInfo = gson.fromJson(cn_payapply, DeleteResultInfo.class);
                                if ("true".equals(deleteResultInfo.getResult().getResponseStatus().getIsSuccess())) {
                                    ToastUtils.showToast(getContext(), "审核成功！");
                                    MyFragmentManagerUtil.closeTopFragment(SearchOrderDetailFragment.this);
                                } else {
                                    ToastUtils.showToast(getContext(), deleteResultInfo.getResult().getResponseStatus().getErrors().get(0).getMessage());
                                    ToastUtils.showToast(getContext(), "审核失败！");
                                }
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getContext(), "审核失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(getContext(), "审核失败！");
                        }
                    });
                }
            }
        });
    }

    private void getOrderDetail() {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        String sql = "{\"CreateOrgId\": \"CN_PAYAPPLY\",\"FieldName\":\"\",\"Number\": \"" + mOrderNo +
                                "\",\"Id\": \"\"}";
                        //查询
                        String cn_payapply = client.view("CN_PAYAPPLY", sql);
                        Gson gson = new Gson();
                        final SearchDetailInfo searchDetailInfo = gson.fromJson(cn_payapply, SearchDetailInfo.class);
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!"false".equals(searchDetailInfo.getResult().getResponseStatus())) {
                                    //  mOrderDataInfo.getHeadData().put("FBILLNo",searchDetailInfo.getResult().getResult().getFBILLNo());
                                    //  mOrderDataInfo.getHeadData().put("FCREATORID_name",searchDetailInfo.getResult().getResult().getFCREATORID().getName());
                                    //  mOrderDataInfo.getHeadData().put("FCONTACTUNIT",searchDetailInfo.getResult().getResult().getFCONTACTUNIT().getName().get(0).getValue());
                                    //  mOrderDataInfo.getHeadData().put("FRECTUNIT",searchDetailInfo.getResult().getResult().getFRECTUNIT().getName().get(0).getValue());
                                    //  mOrderDataInfo.getHeadData().put("F_ABC_Assistant",searchDetailInfo.getResult().getResult().getF_ABC_Assistant().getFDataValue().get(0).getValue());
                                    //  mOrderDataInfo.getHeadData().put("F_ABC_Text",searchDetailInfo.getResult().getResult().getF_ABC_Text());
                                    tv_zdr.setText(searchDetailInfo.getResult().getResult().getFCREATORID().getName());
                                    tv_wl.setText(searchDetailInfo.getResult().getResult().getFCONTACTUNIT().getName().get(0).getValue());
                                    tv_skdw.setText(searchDetailInfo.getResult().getResult().getFRECTUNIT().getName().get(0).getValue());
                                    tv_fplx.setText(searchDetailInfo.getResult().getResult().getF_ABC_Assistant().getFDataValue().get(0).getValue());
                                    tv_use.setText(searchDetailInfo.getResult().getResult().getF_ABC_Text());

                                    List<SearchDetailInfo.ResultBeanX.ResultBean.FPAYAPPLYENTRYBean> fpayapplyentry = searchDetailInfo.getResult().getResult().getFPAYAPPLYENTRY();
                                    for (SearchDetailInfo.ResultBeanX.ResultBean.FPAYAPPLYENTRYBean bean : fpayapplyentry) {
                                        SubListInfo subListInfo = new SubListInfo();
                                        if (null != bean.getFCOSTID())
                                            subListInfo.setFyType(bean.getFCOSTID().getName().get(0).getValue());
                                        subListInfo.setFyName(bean.getF_ABC_Text1());
                                        subListInfo.setFunit(bean.getF_ABC_Decimal());
                                        subListInfo.setFnum((int) bean.getF_ABC_Decimal2());
                                        subListInfo.setFprice(bean.getF_ABC_Decimal1());
                                        subListInfo.setSqmoney(bean.getFAPPLYAMOUNTFOR());
                                        mData.add(subListInfo);
                                    }
                                    addApplyAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getContext(), "查询失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(getContext(), "查询失败！");
                        }
                    });
                }
            }
        });
    }

    public void setOrderNo(String orderNo, String kind) {
        mOrderNo = orderNo;
        mKind = kind;
    }
}
