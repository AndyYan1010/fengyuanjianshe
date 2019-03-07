package com.bt.andy.fengyuanbuild.fragment;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.MyAppliaction;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.adapter.LvAddApplyAdapter;
import com.bt.andy.fengyuanbuild.adapter.LvShowMoreAdapter;
import com.bt.andy.fengyuanbuild.messegeInfo.DeleteResultInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.FeiYongXMInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.HeTongBianHaoInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.LoadPicInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.OrderDataInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.SaveForResultInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.SearchDetailInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.SubListInfo;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.EditTextUtils;
import com.bt.andy.fengyuanbuild.utils.MyFragmentManagerUtil;
import com.bt.andy.fengyuanbuild.utils.PopupOpenHelper;
import com.bt.andy.fengyuanbuild.utils.ProgressDialogUtil;
import com.bt.andy.fengyuanbuild.utils.ThreadUtils;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.bt.andy.fengyuanbuild.viewmodle.MyListView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Decoder.BASE64Decoder;
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
    private View                mRootView;
    private ImageView           img_back;
    private TextView            tv_title;
    private TextView            tv_right;//驳回
    private ImageView           img_noInt;//网络连接动画
    private ImageView           img_ysh;//已审核图片
    private ImageView           img_ybx;//已报销图片
    private TextView            tv_wl;
    private TextView            tv_dwlx;
    private TextView            tv_skdw;
    private TextView            tv_fplx;
    private TextView            tv_sqlx;
    private TextView            tv_htbh;
    private TextView            tv_sgdw;
    private TextView            tv_zdr;
    private TextView            tv_wy;//网银
    private EditText            et_use;//用途
    private TextView            tv_submit;//审核
    private MyListView          lv_order;
    private List<SubListInfo>   mData;//子表数据
    private LvAddApplyAdapter   addApplyAdapter;
    private String              mOrderNo;//单号
    private String              mKind;//显示类别
    private int                 mType;//显示审核类别
    private int                 mListType;//子表是否可点击//0不可点击，1可点击
    private Map<String, String> mBankMap;//银行卡号的信息
    private String              wlFnameid;//往来id
    private String              skdwFnameid;//送款单位id
    private String              content_use;//用途说明
    private String              fplxFnameid;//发票类型id
    private String              sqlxFnameid;//申请类型id
    private String              htbhFnameid;//合同编号id
    private String              sgdwFnameid;//施工队伍id
    private String              saveOrderNo;//提交后订单的单号
    private OrderDataInfo       mOrderDataInfo;//整个订单表信息
    private List<List>          mShowData;//临时存放内码
    private LvShowMoreAdapter   showMoreAdapter;
    private String              fyxmID;//临时记录费用项目的ID
    private PopupOpenHelper     popupOpenHelper;//弹出填写子表窗口

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
        img_noInt = mRootView.findViewById(R.id.img_noInt);
        img_ysh = mRootView.findViewById(R.id.img_ysh);
        img_ybx = mRootView.findViewById(R.id.img_ybx);
        tv_wl = mRootView.findViewById(R.id.tv_wl);
        tv_dwlx = mRootView.findViewById(R.id.tv_dwlx);
        tv_skdw = mRootView.findViewById(R.id.tv_skdw);
        tv_fplx = mRootView.findViewById(R.id.tv_fplx);
        tv_sqlx = mRootView.findViewById(R.id.tv_sqlx);
        tv_htbh = mRootView.findViewById(R.id.tv_htbh);
        tv_sgdw = mRootView.findViewById(R.id.tv_sgdw);
        tv_zdr = mRootView.findViewById(R.id.tv_zdr);
        tv_wy = mRootView.findViewById(R.id.tv_wy);
        et_use = mRootView.findViewById(R.id.et_use);
        lv_order = mRootView.findViewById(R.id.lv_order);
        tv_submit = mRootView.findViewById(R.id.tv_submit);
    }

    private void initData() {
        tv_title.setText("付款申请单详情");
        img_back.setVisibility(View.VISIBLE);
        if ("A".equals(mKind) || "D".equals(mKind) || "Z".equals(mKind)) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText("新增");
            tv_submit.setText("提交审核");
            //list类别0查询 1修改
            mListType = 1;
        } else if ("B".equals(mKind)) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText("驳回");
            tv_submit.setText("审核");
            if (null == MyAppliaction.userType || "".equals(MyAppliaction.userType) || MyAppliaction.userType.contains("员工")) {
                tv_submit.setVisibility(View.GONE);
            }
        } else if ("C".equals(mKind)) {
            if (3 == mType) {//已审核
                img_ysh.setVisibility(View.VISIBLE);
            } else {//已报销
                img_ysh.setVisibility(View.VISIBLE);
                img_ybx.setVisibility(View.VISIBLE);
            }
            tv_submit.setVisibility(View.GONE);
        } else {
            tv_submit.setVisibility(View.GONE);
        }
        mOrderDataInfo = new OrderDataInfo();
        //初始化子表list
        initListView();
        //获取付款申请单详情
        getOrderDetail();
        //查找图片
        searchPic();
        mBankMap = new HashMap<>();
        mBankMap.put("bankNumber", null == MyAppliaction.bankNumber ? "" : MyAppliaction.bankNumber);
        mBankMap.put("bankUserName", null == MyAppliaction.bankUserName ? "" : MyAppliaction.bankUserName);
        tv_wy.setText("".equals(mBankMap.get("bankUserName")) ? "未查找到网银" : (mBankMap.get("bankUserName") + "：" + mBankMap.get("bankNumber")));
        //查询网银
        //        searchBankNo();
        img_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_wl.setOnClickListener(this);
        tv_skdw.setOnClickListener(this);
        tv_fplx.setOnClickListener(this);
        tv_sqlx.setOnClickListener(this);
        tv_htbh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                MyFragmentManagerUtil.closeTopFragment(this);
                break;
            case R.id.tv_right://驳回
                String saveOrback = String.valueOf(tv_right.getText());
                if ("新增".equals(saveOrback)) {
                    //新增子表
                    showPopUp2AddTable("add", 0);
                } else if ("驳回".equals(saveOrback)) {
                    showPopUp2Back();
                }
                break;
            case R.id.tv_wl://往来
                changeViewContent(tv_wl, "往来", "search", "wlFname");
                break;
            case R.id.tv_skdw://收款单位
                changeViewContent(tv_skdw, "收款单位", "search", "skdwFname");
                break;
            case R.id.tv_fplx://发票类型
                changeViewContent(tv_fplx, "发票类型", "search", "fplxFname");
                break;
            case R.id.tv_sqlx://申请类型
                changeViewContent(tv_sqlx, "申请类型", "search", "sqlxFname");
                break;
            case R.id.tv_htbh://合同编号
                changeViewContent(tv_htbh, "合同编号", "search", "htbhFname");
                break;
            case R.id.tv_submit://提交审核
                String check = String.valueOf(tv_submit.getText());
                if ("审核".equals(check)) {
                    checkOrder();
                } else if ("提交审核".equals(check)) {
                    //提交审核
                    submitData();
                } else if ("保存".equals(check)) {
                    saveOrderInfo();
                }
                break;
        }
    }

    //驳回申请单
    private void showPopUp2Back() {
        //弹出提示框确认驳回
        popupOpenHelper = new PopupOpenHelper(getContext(), tv_right, R.layout.popup_warn_title);
        popupOpenHelper.openPopupWindow(true, Gravity.CENTER);
        popupOpenHelper.setOnPopupViewClick(new PopupOpenHelper.ViewClickListener() {
            @Override
            public void onViewClickListener(PopupWindow popupWindow, View inflateView) {
                //长按删除
                final TextView tv_cont = inflateView.findViewById(R.id.tv_cont);
                final TextView tv_cancle = inflateView.findViewById(R.id.tv_cancle);
                final TextView tv_sure = inflateView.findViewById(R.id.tv_sure);
                tv_cont.setText("您确定驳回这条申请单？");
                tv_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupOpenHelper.dismiss();
                    }
                });
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupOpenHelper.dismiss();
                        //驳回
                        backThisOrder();
                        MyFragmentManagerUtil.closeTopFragment(SearchOrderDetailFragment.this);
                    }
                });
            }
        });
    }

    private void backThisOrder() {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        //TODO:调驳回api
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast(getContext(), "驳回失败！");
                }
            }
        });
    }

    private void changeViewContent(final TextView tvcontent, final String title, final String writekind, final String whichkey) {
        if ("search".equals(writekind)) {
            //为空，弹出选择菜单列表
            showMoreWriteInfo(tvcontent, title, whichkey);
        }
    }

    private void showMoreWriteInfo(final TextView tvcontent, final String title, final String whichkey) {
        View view = View.inflate(getContext(), R.layout.view_only_list, null);
        ListView lv_showmore = view.findViewById(R.id.lv_showmore);
        if (null == mShowData) {
            mShowData = new ArrayList();
        } else {
            mShowData.clear();
        }
        if (null == showMoreAdapter) {
            showMoreAdapter = new LvShowMoreAdapter(getContext(), mShowData);
        }
        lv_showmore.setAdapter(showMoreAdapter);
        //根据whichkey来查找
        searchKindByWhichKey(title);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view).setTitle(title).show();
        lv_showmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvcontent.setText(mShowData.get(i).get(1).toString());
                fyxmID = mShowData.get(i).get(0).toString();
                if (null != whichkey && !"".equals(whichkey)) {
                    mOrderDataInfo.getHeadData().put(whichkey, mShowData.get(i).get(1).toString());
                    mOrderDataInfo.getHeadData().put(whichkey + "id", mShowData.get(i).get(0).toString());
                    if ("合同编号".equals(title)) {
                        mOrderDataInfo.getHeadData().put("sgdwFnameid", mShowData.get(i).get(2).toString());//number
                        mOrderDataInfo.getHeadData().put("sgdwFname", mShowData.get(i).get(3).toString());//name
                        tv_sgdw.setText(mShowData.get(i).get(3).toString());
                    }
                }
                //做了修改
                tv_submit.setText("保存");
                dialog.dismiss();
            }
        });
    }

    private void searchKindByWhichKey(String title) {
        final String sql;
        if ("往来".equals(title)) {
            //            sql = "{\"FormId\":\"BD_Supplier\",\"FieldKeys\":\"fnumber,fname\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":1000,\"StartRow\":0,\"Limit\":0}";
            sql = "{\"FormId\":\"BD_Empinfo\",\"FieldKeys\":\"fnumber,fname\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":1000,\"StartRow\":0,\"Limit\":0}";
        } else if ("收款单位".equals(title)) {
            sql = "{\"FormId\":\"BD_Empinfo\",\"FieldKeys\":\"fnumber,fname\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":1000,\"StartRow\":0,\"Limit\":0}";
        } else if ("费用项目".equals(title)) {
            //查询
            new AreaTask().execute();
            return;
        } else if ("发票类型".equals(title)) {
            List list1 = new ArrayList();
            list1.add("01");
            list1.add("已到");
            List list2 = new ArrayList();
            list2.add("02");
            list2.add("未到 预付款");
            List list3 = new ArrayList();
            list3.add("03");
            list3.add("无发票");
            mShowData.add(list1);
            mShowData.add(list2);
            mShowData.add(list3);
            showMoreAdapter.notifyDataSetChanged();
            return;
        } else if ("申请类型".equals(title)) {
            List list1 = new ArrayList();
            list1.add("01");
            list1.add("公司外部");
            List list2 = new ArrayList();
            list2.add("02");
            list2.add("公司内部");
            mShowData.add(list1);
            mShowData.add(list2);
            showMoreAdapter.notifyDataSetChanged();
            return;
        } else if ("合同编号".equals(title)) {
            ProgressDialogUtil.startShow(getContext(), "正在搜索...");
            new ContractNumTask().execute();
            return;
        } else {
            sql = "";
        }
        if ("".equals(sql)) {
            ToastUtils.showToast(getContext(), "查询出错");
            return;
        }
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        List<List<Object>> lists = client.executeBillQuery(sql);
                        mShowData.addAll(lists);
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                showMoreAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast(getContext(), "查询失败！");
                }
            }
        });
    }

    private void showPopUp2AddTable(final String type, final int position) {
        popupOpenHelper = new PopupOpenHelper(getContext(), tv_right, R.layout.popup_add_table);
        popupOpenHelper.openPopupWindow(true, Gravity.CENTER);
        popupOpenHelper.setOnPopupViewClick(new PopupOpenHelper.ViewClickListener() {
            @Override
            public void onViewClickListener(PopupWindow popupWindow, View inflateView) {
                final TextView tv_type = inflateView.findViewById(R.id.tv_type);
                final EditText et_name = inflateView.findViewById(R.id.et_name);
                final EditText et_unit = inflateView.findViewById(R.id.et_unit);
                final EditText et_num = inflateView.findViewById(R.id.et_num);
                final TextView tv_sum = inflateView.findViewById(R.id.tv_sum);
                final EditText et_sqmoney = inflateView.findViewById(R.id.et_sqmoney);
                TextView tv_sure = inflateView.findViewById(R.id.tv_sure);
                et_sqmoney.setEnabled(false);
                if ("change".equals(type)) {
                    fyxmID = mData.get(position).getFyTypeID();
                    tv_type.setText(mData.get(position).getFyType());
                    et_name.setText(mData.get(position).getFyName());
                    et_unit.setText("" + mData.get(position).getFunit());
                    et_num.setText("" + mData.get(position).getFnum());
                    tv_sum.setText("" + mData.get(position).getFprice());
                    et_sqmoney.setText("" + mData.get(position).getSqmoney());
                }
                et_unit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if ("".equals(EditTextUtils.getContent(et_unit)) || "".equals(EditTextUtils.getContent(et_num))) {
                            tv_sum.setText("" + 0.00);
                            et_sqmoney.setText("" + 0.00);
                            return;
                        }
                        double unit = Double.parseDouble(EditTextUtils.getContent(et_unit));
                        int num = Integer.parseInt(EditTextUtils.getContent(et_num));
                        tv_sum.setText("" + unit * num);
                        et_sqmoney.setText("" + unit * num);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                et_num.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if ("".equals(EditTextUtils.getContent(et_unit)) || "".equals(EditTextUtils.getContent(et_num))) {
                            tv_sum.setText("" + 0.00);
                            et_sqmoney.setText("" + 0.00);
                            return;
                        }
                        double unit = Double.parseDouble(EditTextUtils.getContent(et_unit));
                        int num = Integer.parseInt(EditTextUtils.getContent(et_num));
                        tv_sum.setText("" + unit * num);
                        et_sqmoney.setText("" + unit * num);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                tv_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //弹出选择列表，选择费用项目
                        changeViewContent(tv_type, "费用项目", "search", null);
                    }
                });

                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String type = String.valueOf(tv_type.getText()).trim();
                        if ("".equals(type) || "请选择".equals(type)) {
                            ToastUtils.showToast(getContext(), "请选择费用项目");
                            return;
                        }
                        if (EditTextUtils.isEmpty(et_name, "请输入")) {
                            ToastUtils.showToast(getContext(), "请填写费用名称");
                            return;
                        }
                        if (EditTextUtils.isEmpty(et_num, "0")) {
                            ToastUtils.showToast(getContext(), "数量不能为0");
                            return;
                        }
                        if (EditTextUtils.isEmpty(et_unit, "0.00")) {
                            ToastUtils.showToast(getContext(), "单价不能为0");
                            return;
                        }
                        if (EditTextUtils.isEmpty(et_sqmoney, "0.00")) {
                            ToastUtils.showToast(getContext(), "申请金额不能为0");
                            return;
                        }
                        if ("change".equals(type)) {
                            mData.get(position).setFyType(String.valueOf(tv_type.getText()).trim());
                            mData.get(position).setFyTypeID(fyxmID);
                            mData.get(position).setFyName(EditTextUtils.getContent(et_name));
                            mData.get(position).setFnum(Integer.parseInt(EditTextUtils.getContent(et_num)));
                            mData.get(position).setFunit(Double.parseDouble(EditTextUtils.getContent(et_unit)));
                            mData.get(position).setFprice(Double.parseDouble(String.valueOf(tv_sum.getText()).trim()));
                            mData.get(position).setSqmoney(Double.parseDouble(EditTextUtils.getContent(et_sqmoney)));
                        } else {
                            SubListInfo subListInfo = new SubListInfo();
                            subListInfo.setFyType(String.valueOf(tv_type.getText()).trim());
                            subListInfo.setFyTypeID(fyxmID);
                            subListInfo.setFyName(EditTextUtils.getContent(et_name));
                            subListInfo.setFnum(Integer.parseInt(EditTextUtils.getContent(et_num)));
                            subListInfo.setFunit(Double.parseDouble(EditTextUtils.getContent(et_unit)));
                            subListInfo.setFprice(Double.parseDouble(String.valueOf(tv_sum.getText()).trim()));
                            subListInfo.setSqmoney(Double.parseDouble(EditTextUtils.getContent(et_sqmoney)));
                            mData.add(subListInfo);
                        }
                        //做了修改
                        tv_submit.setText("保存");
                        addApplyAdapter.notifyDataSetChanged();
                        popupOpenHelper.dismiss();
                    }
                });
            }
        });
    }

    private void saveOrderInfo() {
        //获取最新的
        wlFnameid = mOrderDataInfo.getHeadData().get("wlFnameid");
        if (null == wlFnameid || "".equals(wlFnameid)) {
            ToastUtils.showToast(getContext(), "请选择往来");
            return;
        }
        skdwFnameid = mOrderDataInfo.getHeadData().get("skdwFnameid");
        if (null == skdwFnameid || "".equals(skdwFnameid)) {
            ToastUtils.showToast(getContext(), "请选择收款单位");
            return;
        }
        fplxFnameid = mOrderDataInfo.getHeadData().get("fplxFnameid");
        if (null == fplxFnameid || "".equals(fplxFnameid)) {
            ToastUtils.showToast(getContext(), "请选择发票类型");
            return;
        }
        sqlxFnameid = mOrderDataInfo.getHeadData().get("sqlxFnameid");
        if (null == sqlxFnameid || "".equals(sqlxFnameid)) {
            ToastUtils.showToast(getContext(), "请选择申请类型");
            return;
        }
        htbhFnameid = mOrderDataInfo.getHeadData().get("htbhFnameid");
        if (null == htbhFnameid | "".equals(htbhFnameid)) {
            ToastUtils.showToast(getContext(), "请选择合同编号");
            return;
        }
        if (EditTextUtils.isEmpty(et_use, "--")) {
            ToastUtils.showToast(getContext(), "用途不能为空，请填写");
            return;
        }

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String subStr = "";
        if (mData.size() == 1) {
            subStr = "   {\"FCOSTID\": {\"FNUMBER\": \"" + mData.get(0).getFyTypeID() + "\"},\"F_ABC_Text1\": \"" + mData.get(0).getFyName() + "\"," +
                    "  \"FPAYPURPOSEID\": {\"FNumber\": \"SFKYT09_SYS\"},\"FENDDATE\": \"" + sdf.format(dt) + "\"," +
                    "  \"FEXPECTPAYDATE\": \"" + sdf.format(dt) + "\",\"FAPPLYAMOUNTFOR\": " + mData.get(0).getSqmoney() + "," +
                    "  \"FEACHBANKACCOUNT\": \"" + MyAppliaction.bankNumber + "\",\"FEACHCCOUNTNAME\": \"" + MyAppliaction.bankUserName + "\",\"FEACHBANKNAME\": \" \"," +
                    "  \"FDescription\": \" \",\"F_ABC_Decimal2\": " + mData.get(0).getFnum() + ",\"F_ABC_Decimal\": " + mData.get(0).getFunit() + "," +
                    "  \"F_ABC_Decimal1\": " + mData.get(0).getFprice() + "}";
        } else {
            for (int i = 0; i < mData.size(); i++) {
                subStr = subStr + "   {\"FCOSTID\": {\"FNUMBER\": \"" + mData.get(0).getFyTypeID() + "\"},\"F_ABC_Text1\": \"" + mData.get(0).getFyName() + "\"," +
                        "  \"FPAYPURPOSEID\": {\"FNumber\": \"SFKYT09_SYS\"},\"FENDDATE\": \"" + sdf.format(dt) + "\"," +
                        "  \"FEXPECTPAYDATE\": \"" + sdf.format(dt) + "\",\"FAPPLYAMOUNTFOR\": " + mData.get(0).getSqmoney() + "," +
                        "  \"FEACHBANKACCOUNT\": \"" + MyAppliaction.bankNumber + "\",\"FEACHCCOUNTNAME\": \"" + MyAppliaction.bankUserName + "\",\"FEACHBANKNAME\": \" \"," +
                        "  \"FDescription\": \" \",\"F_ABC_Decimal2\": " + mData.get(0).getFnum() + ",\"F_ABC_Decimal\": " + mData.get(0).getFunit() + "," +
                        "  \"F_ABC_Decimal1\": " + mData.get(0).getFprice() + "}";
                if (i < mData.size() - 1) {
                    subStr = subStr + ",";
                }
            }
        }
        final String sql = "{" +
                "    \"Creator\": \"\"," +
                "    \"NeedUpDateFields\": []," +
                "    \"NeedReturnFields\": []," +
                "    \"IsDeleteEntry\": \"true\"," +
                "    \"SubSystemId\": \"\"," +
                "    \"IsVerifyBaseDataField\": \"false\"," +
                "    \"IsEntryBatchFill\": \"true\"," +
                "    \"ValidateFlag\": \"true\"," +
                "    \"NumberSearch\": \"true\"," +
                "    \"InterationFlags\": \"\"," +
                "    \"IsAutoSubmitAndAudit\": \"false\"," +
                "    \"Model\": {" +
                "        \"FID\": 0," +
                "        \"FBILLTYPEID\": {" +
                "            \"FNUMBER\": \"FKSQ005_SYS\"" +
                "        }," +
                "        \"FDATE\": \"" + sdf.format(dt) + "\"," +
                "        \"FCONTACTUNITTYPE\": \"BD_Empinfo\"," +
                "        \"FCONTACTUNIT\": {" +
                "            \"FNumber\": \"" + wlFnameid + "\"" +
                "        }," +
                "        \"FRECTUNITTYPE\": \"BD_Empinfo\"," +
                "        \"FRECTUNIT\": {" +
                "            \"FNumber\": \"" + skdwFnameid + "\"" +
                "        }," +
                "        \"FCURRENCYID\": {" +
                "            \"FNumber\": \"PRE001\"" +
                "        }," +
                "        \"FPAYORGID\": {" +
                "            \"FNumber\": \"100\"" +
                "        }," +
                "        \"FSETTLEORGID\": {" +
                "            \"FNumber\": \"100\"" +
                "        }," +
                "        \"FDOCUMENTSTATUS\": \"Z\"," +
                "        \"FCANCELSTATUS\": \"A\"," +
                "        \"FIsCredit\": false," +
                "        \"FAPPLYORGID\": {" +
                "            \"FNumber\": \"100\"" +
                "        }," +
                "        \"FSETTLECUR\": {" +
                "            \"FNUMBER\": \"PRE001\"" +
                "        }," +
                "        \"F_ABC_Text\": \"" + content_use + "\"," +
                "        \"F_ABC_Assistant\": {" +
                "            \"FNumber\": \"" + fplxFnameid + "\"" +
                "        }," +
                "\"F_ABC_Assistant1\": {" + " \"FNumber\": \"" + htbhFnameid + "\"" + " }," +
                "\"F_ABC_Assistant2\": {" + " \"FNumber\": \"" + sgdwFnameid + "\"" + " }," +
                "\"F_ABC_Assistant3\": {" + " \"FNumber\": \"" + sqlxFnameid + "\"" + " }," +
                //明细
                "        \"FPAYAPPLYENTRY\": [" + subStr + "]}}";

        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        String cn_payapply = client.save("CN_PAYAPPLY", sql);
                        Gson gson = new Gson();
                        final SaveForResultInfo saveForResultInfo = gson.fromJson(cn_payapply, SaveForResultInfo.class);
                        if (saveForResultInfo.getResult().getResponseStatus().isIsSuccess()) {
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    //保存成功
                                    saveOrderNo = saveForResultInfo.getResult().getNumber();
                                    mOrderNo = saveOrderNo;
                                    ToastUtils.showToast(getContext(), "保存成功。");
                                    tv_submit.setText("提交审核");
                                }
                            });
                        }
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getContext(), "保存失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(getContext(), "保存失败！");
                        }
                    });
                }
            }
        });
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
        if (0 == mListType) {
            addApplyAdapter = new LvAddApplyAdapter(getContext(), mData, "search");
        } else {
            addApplyAdapter = new LvAddApplyAdapter(getContext(), mData, "canChange");
        }
        lv_order.setAdapter(addApplyAdapter);
        lv_order.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if (0 != mListType) {
                    popupOpenHelper = new PopupOpenHelper(getContext(), tv_right, R.layout.popup_warn_title);
                    popupOpenHelper.openPopupWindow(true, Gravity.CENTER);
                    popupOpenHelper.setOnPopupViewClick(new PopupOpenHelper.ViewClickListener() {
                        @Override
                        public void onViewClickListener(PopupWindow popupWindow, View inflateView) {
                            //长按删除
                            final TextView tv_cont = inflateView.findViewById(R.id.tv_cont);
                            final TextView tv_cancle = inflateView.findViewById(R.id.tv_cancle);
                            final TextView tv_sure = inflateView.findViewById(R.id.tv_sure);
                            tv_cont.setText("您确定要删除该笔费用吗？");
                            tv_cancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupOpenHelper.dismiss();
                                }
                            });
                            tv_sure.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mData.remove(i);
                                    addApplyAdapter.notifyDataSetChanged();
                                    //做了修改
                                    tv_submit.setText("保存");
                                    popupOpenHelper.dismiss();
                                }
                            });
                        }
                    });
                }
                return false;
            }
        });
        //        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //                //编辑子表
        //                showPopUp2AddTable("change", i);
        //            }
        //        });
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
        ProgressDialogUtil.startShow(getContext(), "正在查找...");
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
                                ProgressDialogUtil.hideDialog();
                                if (!"false".equals(searchDetailInfo.getResult().getResponseStatus())) {
                                    //  mOrderDataInfo.getHeadData().put("FBILLNo",searchDetailInfo.getResult().getResult().getFBILLNo());
                                    //  mOrderDataInfo.getHeadData().put("FCREATORID_name",searchDetailInfo.getResult().getResult().getFCREATORID().getName());
                                    //  mOrderDataInfo.getHeadData().put("FCONTACTUNIT",searchDetailInfo.getResult().getResult().getFCONTACTUNIT().getName().get(0).getValue());
                                    //  mOrderDataInfo.getHeadData().put("FRECTUNIT",searchDetailInfo.getResult().getResult().getFRECTUNIT().getName().get(0).getValue());
                                    //  mOrderDataInfo.getHeadData().put("F_ABC_Assistant",searchDetailInfo.getResult().getResult().getF_ABC_Assistant().getFDataValue().get(0).getValue());
                                    //  mOrderDataInfo.getHeadData().put("F_ABC_Text",searchDetailInfo.getResult().getResult().getF_ABC_Text());
                                    tv_zdr.setText(searchDetailInfo.getResult().getResult().getFCREATORID().getName());
                                    tv_wl.setText(searchDetailInfo.getResult().getResult().getFCONTACTUNIT().getName().get(0).getValue());
                                    wlFnameid = "" + searchDetailInfo.getResult().getResult().getFCONTACTUNIT().getNumber();
                                    mOrderDataInfo.getHeadData().put("wlFnameid", wlFnameid);
                                    tv_skdw.setText(searchDetailInfo.getResult().getResult().getFRECTUNIT().getName().get(0).getValue());
                                    skdwFnameid = "" + searchDetailInfo.getResult().getResult().getFRECTUNIT().getNumber();
                                    mOrderDataInfo.getHeadData().put("skdwFnameid", skdwFnameid);
                                    tv_fplx.setText(searchDetailInfo.getResult().getResult().getF_ABC_Assistant().getFDataValue().get(0).getValue());
                                    fplxFnameid = searchDetailInfo.getResult().getResult().getF_ABC_Assistant().getFNumber();
                                    mOrderDataInfo.getHeadData().put("fplxFnameid", fplxFnameid);
                                    if (null != searchDetailInfo.getResult().getResult().getF_ABC_Assistant3()) {
                                        tv_sqlx.setText(searchDetailInfo.getResult().getResult().getF_ABC_Assistant3().getFDataValue().get(0).getValue());
                                        sqlxFnameid = searchDetailInfo.getResult().getResult().getF_ABC_Assistant3().getFNumber();
                                        mOrderDataInfo.getHeadData().put("sqlxFnameid", sqlxFnameid);
                                    }
                                    if (null != searchDetailInfo.getResult().getResult().getF_ABC_Assistant1()) {
                                        tv_htbh.setText(searchDetailInfo.getResult().getResult().getF_ABC_Assistant1().getFDataValue().get(0).getValue());
                                        htbhFnameid = searchDetailInfo.getResult().getResult().getF_ABC_Assistant1().getFNumber();
                                        mOrderDataInfo.getHeadData().put("htbhFnameid", htbhFnameid);
                                    }
                                    if (null != searchDetailInfo.getResult().getResult().getF_ABC_Assistant2()) {
                                        tv_sgdw.setText(searchDetailInfo.getResult().getResult().getF_ABC_Assistant2().getFDataValue().get(0).getValue());
                                        sgdwFnameid = searchDetailInfo.getResult().getResult().getF_ABC_Assistant2().getFNumber();
                                        mOrderDataInfo.getHeadData().put("sgdwFnameid", sgdwFnameid);
                                    }
                                    et_use.setText(searchDetailInfo.getResult().getResult().getF_ABC_Text());
                                    content_use = searchDetailInfo.getResult().getResult().getF_ABC_Text();

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
                                    try {
                                        mBankMap.put("bankNumber", fpayapplyentry.get(0).getFEACHBANKACCOUNT());
                                        mBankMap.put("bankUserName", fpayapplyentry.get(0).getFEACHCCOUNTNAME().get(0).getValue());
                                        tv_wy.setText("".equals(mBankMap.get("bankUserName")) ? "未查找到网银" : (mBankMap.get("bankUserName") + "：" + mBankMap.get("bankNumber")));
                                    } catch (Exception e) {
                                    }
                                    addApplyAdapter.notifyDataSetChanged();
                                    img_noInt.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressDialogUtil.hideDialog();
                                img_noInt.setImageResource(R.drawable.icon_nonet);
                                ToastUtils.showToast(getContext(), "查询失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressDialogUtil.hideDialog();
                            img_noInt.setImageResource(R.drawable.icon_nonet);
                            ToastUtils.showToast(getContext(), "查询失败！");
                        }
                    });
                }
            }
        });
    }

    private void searchPic() {//查找图片
        new SearchPicItemTask(mOrderNo).execute();
    }

    public void setOrderNo(String orderNo, String kind, int type) {
        mOrderNo = orderNo;
        mKind = kind;
        mType = type;
    }

    //查找图片
    class SearchPicItemTask extends AsyncTask<Void, String, String> {
        private String FBILLNO;

        SearchPicItemTask(String FBILLNO) {
            this.FBILLNO = FBILLNO;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.startShow(getContext(), "正在提交图片...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            // 命名空间
            String nameSpace = "k3clound";//webservice
            // 调用的方法名称
            String methodName = Consts.LOAD_PIC_INTER;
            // EndPoint
            String endPoint = Consts.PICPOINT;
            // SOAP Action
            String soapAction = "k3clound/BD_download_attchment";

            // 指定WebService的命名空间和调用的方法名
            SoapObject rpc = new SoapObject(nameSpace, methodName);

            // 设置需调用WebService接口需要传入的参数
            rpc.addProperty("FINTERID", 0);//FINTERID
            rpc.addProperty("FBILLNO", "FKSQ000047");//FBILLNO

            // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

            envelope.bodyOut = rpc;
            // 设置是否调用的是dotNet开发的WebService
            envelope.dotNet = true;
            // 等价于envelope.bodyOut = rpc;
            envelope.setOutputSoapObject(rpc);

            HttpTransportSE transport = new HttpTransportSE(endPoint);
            try {
                // 调用WebService
                transport.call(soapAction, envelope);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 获取返回的数据
            SoapObject object = (SoapObject) envelope.bodyIn;

            // 获取返回的结果
            Log.i("返回结果", object.getProperty(0).toString() + "=========================");
            String result = object.getProperty(0).toString();//[{"status":"01","message":"成功","FATTACHMENTNAME":"IMG_20180129_095450.jpg","FEXTNAME":".jpg","bufferstr":"wD9k="}]
            try {
                JSONArray jsonArray = new JSONArray(result);
                Gson gson = new Gson();
                LoadPicInfo loadPicInfo = gson.fromJson(jsonArray.get(0).toString(), LoadPicInfo.class);
                if ("01".equals(loadPicInfo.getStatus())) {
                    final String bufferstr = loadPicInfo.getBufferstr();
                    //将base64转图片
                    long photoTime = System.currentTimeMillis();
                    final String path = Environment.getExternalStorageDirectory().getPath() + "/temp" + photoTime + ".jpg";// 获取SD卡路径
                    ThreadUtils.runOnSubThread(new Runnable() {
                        @Override
                        public void run() {
                            boolean b = base64StrToImage(bufferstr, path);
                        }
                    });
                }
                ToastUtils.showToast(getContext(), loadPicInfo.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                return "1";
            }
            return "0";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ProgressDialogUtil.hideDialog();
        }
    }

    private boolean base64StrToImage(String bufferstr, String path) {
        if (bufferstr == null) {
            // 图像数据为空
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(bufferstr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = "D:\\tanbing2.jpg";// 新生成的图片
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //压缩图片文件
    public void compressFile() {

    }


    //查费用项目
    private class AreaTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            // 命名空间
            String nameSpace = "http://tempuri.org/";
            // 调用的方法名称
            String methodName = "JA_LIST";
            // EndPoint
            String endPoint = Consts.ENDPOINT;
            // SOAP Action
            String soapAction = "http://tempuri.org/JA_LIST";

            // 指定WebService的命名空间和调用的方法名
            SoapObject rpc = new SoapObject(nameSpace, methodName);

            // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
            String sql = "select a1.fnumber,b1.fname  from t_bd_expense a1 inner join T_BD_EXPENSE_L b1 on a1.FEXPID =b1.FEXPID";
            rpc.addProperty("FPSW", "hmbt@uiop123");
            rpc.addProperty("FSQL", sql);
            //            rpc.addProperty("FTable", "t_user");

            // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

            envelope.bodyOut = rpc;
            // 设置是否调用的是dotNet开发的WebService
            envelope.dotNet = true;
            // 等价于envelope.bodyOut = rpc;
            envelope.setOutputSoapObject(rpc);

            HttpTransportSE transport = new HttpTransportSE(endPoint);
            try {
                // 调用WebService
                transport.call(soapAction, envelope);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 获取返回的数据
            SoapObject object = (SoapObject) envelope.bodyIn;

            // 获取返回的结果
            Log.i("返回结果", object.getProperty(0).toString() + "=========================");
            String result = object.getProperty(0).toString();
            try {
                System.out.println(result);
                JSONArray jsonArray = new JSONArray(result);
                Gson gson = new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    FeiYongXMInfo info = gson.fromJson(jsonArray.get(i).toString(), FeiYongXMInfo.class);
                    List list = new ArrayList();
                    list.add(info.getFnumber());
                    list.add(info.getFname());
                    mShowData.add(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "1";
            }
            return "0";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("1".equals(s)) {
                ToastUtils.showToast(getContext(), "查询失败");
            }
            showMoreAdapter.notifyDataSetChanged();
        }
    }

    //查合同编号
    private class ContractNumTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (null == mShowData) {
                mShowData = new ArrayList<>();
            } else {
                mShowData.clear();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            // 命名空间
            String nameSpace = "http://tempuri.org/";
            // 调用的方法名称
            String methodName = "JA_LIST";
            // EndPoint
            String endPoint = Consts.ENDPOINT;
            // SOAP Action
            String soapAction = "http://tempuri.org/JA_LIST";

            // 指定WebService的命名空间和调用的方法名
            SoapObject rpc = new SoapObject(nameSpace, methodName);

            // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
            String sql = "select  HT_FID,HT_FNUMBER,HT_FNAME,SGDW_FID,SGDW_FNUMBER,SGDW_FNAME  from XV_HT_SGDW";
            rpc.addProperty("FPSW", "hmbt@uiop123");
            rpc.addProperty("FSQL", sql);

            // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

            envelope.bodyOut = rpc;
            // 设置是否调用的是dotNet开发的WebService
            envelope.dotNet = true;
            // 等价于envelope.bodyOut = rpc;
            envelope.setOutputSoapObject(rpc);

            HttpTransportSE transport = new HttpTransportSE(endPoint);
            try {
                // 调用WebService
                transport.call(soapAction, envelope);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 获取返回的数据
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 获取返回的结果
            Log.i("返回结果", object.getProperty(0).toString() + "=========================");
            String result = object.getProperty(0).toString();
            try {
                System.out.println(result);
                JSONArray jsonArray = new JSONArray(result);
                Gson gson = new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    HeTongBianHaoInfo info = gson.fromJson(jsonArray.get(i).toString(), HeTongBianHaoInfo.class);
                    List list = new ArrayList();
                    list.add(info.getHT_FNUMBER());
                    list.add(info.getHT_FNAME());
                    list.add(info.getSGDW_FNUMBER());
                    list.add(info.getSGDW_FNAME());
                    mShowData.add(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "1";
            }
            return "0";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ProgressDialogUtil.hideDialog();
            if ("1".equals(s)) {
                ToastUtils.showToast(getContext(), "查询失败");
            }
            showMoreAdapter.notifyDataSetChanged();
        }
    }


    private void searchBankNo() {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        //查询网银
                        String sqlSub = "{\"FormId\": \"CN_BANKACNT\",\"FieldKeys\": \"fnumber,fname\"," +
                                "    \"FilterString\": \"FName='" + MyAppliaction.userName + "'\"," +
                                "    \"OrderString\": \"\",\"TopRowCount\": 100," +
                                "    \"StartRow\": 0,\"Limit\": 0}";
                        final List<List<Object>> lists = client.executeBillQuery(sqlSub);
                        //                        mBankMap.put("bankNumber", lists.get(0).get(0).toString());
                        //                        mBankMap.put("bankUserName", lists.get(0).get(1).toString());
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                //    tv_wy.setText("".endsWith(mBankMap.get("bankName")) ? "未查找到网银" : mBankMap.get("bankName"));
                                //    tv_wy.setText("".endsWith(mBankMap.get("bankNumber")) ? "未查找到网银" : mBankMap.get("bankNumber"));
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getContext(), "未查找到对应网银");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(getContext(), "未查找到对应网银");
                        }
                    });
                }
            }
        });
    }
}
