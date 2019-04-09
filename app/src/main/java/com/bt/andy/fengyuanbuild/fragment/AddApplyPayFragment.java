package com.bt.andy.fengyuanbuild.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.bt.andy.fengyuanbuild.adapter.MyRecPicAdapter;
import com.bt.andy.fengyuanbuild.messegeInfo.DeleteResultInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.FeiYongXMInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.HeTongBianHaoInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.OrderDataInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.SaveForResultInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.SubListInfo;
import com.bt.andy.fengyuanbuild.utils.CompressImageUtil;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.EditTextUtils;
import com.bt.andy.fengyuanbuild.utils.MyFragmentManagerUtil;
import com.bt.andy.fengyuanbuild.utils.PopupOpenHelper;
import com.bt.andy.fengyuanbuild.utils.ProgressDialogUtil;
import com.bt.andy.fengyuanbuild.utils.ThreadUtils;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.bt.andy.fengyuanbuild.viewmodle.MyListView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kingdee.bos.webapi.client.K3CloudApiClient;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/25 8:51
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AddApplyPayFragment extends Fragment implements View.OnClickListener {
    private View                mRootView;
    private ImageView           img_back;
    private TextView            tv_title;
    private TextView            tv_right;//添加
    private TextView            tv_wl;//往来
    private TextView            tv_dwlx;//单位类型
    private TextView            tv_skdw;//收款单位
    private TextView            tv_wy;//网银账户
    private TextView            tv_wyNo;//网银账号
    private TextView            tv_fplx;//发票类型
    private TextView            tv_sqlx;//申请类型
    private TextView            tv_htbh;//合同编号类型
    private TextView            tv_sgdw;//施工队伍
    private TextView            tv_zdr;//制单人
    private TextView            tv_submit;//提交申请单
    private TextView            tv_subPic;//提交图片
    private EditText            et_use;//用途
    private MyListView          lv_order;
    private LvAddApplyAdapter   addApplyAdapter;
    private List<SubListInfo>   mData;//子表数据
    private PopupOpenHelper     popupOpenHelper;
    private String              saveOrderNo;//提交后订单的单号
    private int                 saveOrderID;//提交后订单的内码
    private Map<String, String> mBankMap;//银行卡号的信息
    private RecyclerView        recy_addpic;//附件图片

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_apply_pay, null);
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
        tv_wy = mRootView.findViewById(R.id.tv_wy);
        tv_wyNo = mRootView.findViewById(R.id.tv_wyNo);
        tv_fplx = mRootView.findViewById(R.id.tv_fplx);
        tv_sqlx = mRootView.findViewById(R.id.tv_sqlx);
        tv_htbh = mRootView.findViewById(R.id.tv_htbh);
        tv_sgdw = mRootView.findViewById(R.id.tv_sgdw);
        tv_zdr = mRootView.findViewById(R.id.tv_zdr);
        et_use = mRootView.findViewById(R.id.et_use);
        recy_addpic = mRootView.findViewById(R.id.recy_addpic);
        lv_order = mRootView.findViewById(R.id.lv_order);
        tv_submit = mRootView.findViewById(R.id.tv_submit);
        tv_subPic = mRootView.findViewById(R.id.tv_subPic);
    }

    private void initData() {
        img_back.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_title.setText("新增付款申请单");
        tv_right.setText("添加");
        tv_zdr.setText(MyAppliaction.userName);
        //记录标题头部信息
        mOrderDataInfo = new OrderDataInfo();
        mBankMap = new HashMap<>();
        mBankMap = new HashMap<>();
        mBankMap.put("bankNumber", null == MyAppliaction.bankNumber ? "" : MyAppliaction.bankNumber);
        mBankMap.put("bankUserName", null == MyAppliaction.bankUserName ? "" : MyAppliaction.bankUserName);
        tv_wy.setText("".equals(mBankMap.get("bankUserName")) ? "未查找到网银" : (mBankMap.get("bankUserName")));
        tv_wyNo.setText("".equals(mBankMap.get("bankNumber")) ? "--" : (mBankMap.get("bankNumber")));
        //初始化子表list
        initListView();
        img_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_wl.setOnClickListener(this);
        tv_skdw.setOnClickListener(this);
        tv_fplx.setOnClickListener(this);
        tv_sqlx.setOnClickListener(this);
        tv_htbh.setOnClickListener(this);
        tv_subPic.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        //查询网银
        //searchBankNo();
        //初始化recyclerview数据
        initRecyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_subPic:
                //上传图片
                next2SendPic();
                break;
            case R.id.img_back:
                showDialog2Close();
                break;
            case R.id.tv_right://弹出popupwindow 添加子表
                showPopUp2AddTable("add", 0);
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
                //先弹出输入框，输入合同编号或者名称，模糊搜索
                showDialog2ForSearch("合同编号");
                break;
            case R.id.tv_submit://提交
                String sub_type = String.valueOf(tv_submit.getText());
                if ("保存".equals(sub_type)) {//
                    saveData();
                } else {//提交审核
                    submitData();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    //提示将关闭页面
                    showDialog2Close();
                    return true;
                }
                return false;
            }
        });
    }

    private static final int IMAGE     = 1;//调用系统相册-选择图片n小于0
    private static final int SHOT_CODE = 20;//调用系统相机-选择图片n小于0

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相册返回，获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
        //相机返回
        if (requestCode == SHOT_CODE && resultCode == Activity.RESULT_OK) {
            if (null != recPicAdapter.getFilePath())
                showImage(recPicAdapter.getFilePath());
        }
    }


    //加载图片
    private void showImage(String imgPath) {
        //压缩图片
        //        File file = new File(imgPath);
        //        File newFile = new CompressHelper.Builder(this)
        //                .setMaxWidth(1080)  // 默认最大宽度为720
        //                .setMaxHeight(1920) // 默认最大高度为960
        //                .setQuality(100)    // 默认压缩质量为80
        //                .setFileName("sendPic") // 设置你需要修改的文件名
        //                .setCompressFormat(Bitmap.CompressFormat.PNG) // 设置默认压缩为jpg格式
        //                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
        //                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
        //                .build()
        //                .compressToFile(file);
        //        Bitmap bm = BitmapFactory.decodeFile(newFile.getPath());
        //添加到bitmap集合中
        mBitmapList.add(imgPath);
        recPicAdapter.notifyDataSetChanged();
    }

    private List            mBitmapList;
    private MyRecPicAdapter recPicAdapter;

    private void initRecyView() {
        //添加初始展示的图片
        Bitmap mBm = BitmapFactory.decodeResource(getResources(), R.drawable.add_picture);
        mBitmapList = new ArrayList<>();
        mBitmapList.add(mBm);
        recy_addpic.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        recPicAdapter = new MyRecPicAdapter(getContext(), mBitmapList, false);
        recy_addpic.setAdapter(recPicAdapter);
    }

    private boolean isSearchALL = true;
    private String htSearchcont;

    private void showDialog2ForSearch(String title) {
        final EditText et = new EditText(getContext());
        et.setText("");
        new AlertDialog.Builder(getContext()).setView(et).setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //模糊搜索
                        String content = EditTextUtils.getContent(et);
                        if ("".equals(content)) {
                            //获取所有合同编号
                            isSearchALL = true;

                        } else {
                            //模糊搜索对应合同编号
                            isSearchALL = false;
                            htSearchcont = content;
                        }
                        changeViewContent(tv_htbh, "合同编号", "search", "htbhFname");
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void showDialog2Close() {
        popupOpenHelper = new PopupOpenHelper(getContext(), tv_right, R.layout.popup_warn_title);
        popupOpenHelper.openPopupWindow(true, Gravity.CENTER);
        popupOpenHelper.setOnPopupViewClick(new PopupOpenHelper.ViewClickListener() {
            @Override
            public void onViewClickListener(PopupWindow popupWindow, View inflateView) {
                //长按删除
                final TextView tv_cont = inflateView.findViewById(R.id.tv_cont);
                final TextView tv_cancle = inflateView.findViewById(R.id.tv_cancle);
                final TextView tv_sure = inflateView.findViewById(R.id.tv_sure);
                tv_cont.setText("您确定要关闭当前页面？");
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
                        MyFragmentManagerUtil.closeTopFragment(AddApplyPayFragment.this);
                    }
                });
            }
        });
    }

    private void submitData() {
        ProgressDialogUtil.startShow(getContext(), "正在提交");
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        //提交审核
                        String sqlSub = "{\"CreateOrgId\": 0,\"Numbers\": [\"" + saveOrderNo + "\"],\"Ids\": \"\"}";
                        String cn_payapply = client.submit("CN_PAYAPPLY", sqlSub);
                        Gson gson = new Gson();
                        final DeleteResultInfo resultInfo2 = gson.fromJson(cn_payapply, DeleteResultInfo.class);
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressDialogUtil.hideDialog();
                                if ("true".equals(resultInfo2.getResult().getResponseStatus().getIsSuccess())) {
                                    ToastUtils.showToast(getContext(), "提交成功");
                                    //提交成功
                                    getActivity().finish();
                                } else {
                                    ToastUtils.showToast(getContext(), "提交失败");
                                }
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressDialogUtil.hideDialog();
                                ToastUtils.showToast(getContext(), "提交失败");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressDialogUtil.hideDialog();
                            ToastUtils.showToast(getContext(), "提交失败");
                        }
                    });
                }
            }
        });
    }

    private void saveData() {
        String wlFnameid = mOrderDataInfo.getHeadData().get("wlFnameid");
        if (null == wlFnameid || "".equals(wlFnameid)) {
            ToastUtils.showToast(getContext(), "请选择往来");
            return;
        }
        String skdwFnameid = mOrderDataInfo.getHeadData().get("skdwFnameid");
        if (null == skdwFnameid || "".equals(skdwFnameid)) {
            ToastUtils.showToast(getContext(), "请选择收款单位");
            return;
        }
        String fplxFnameid = mOrderDataInfo.getHeadData().get("fplxFnameid");
        if (null == fplxFnameid || "".equals(fplxFnameid)) {
            ToastUtils.showToast(getContext(), "请选择发票类型");
            return;
        }
        if (EditTextUtils.isEmpty(et_use, "请填写")) {
            ToastUtils.showToast(getContext(), "用途不能为空，请填写");
            return;
        }
        String sqlxFnameid = mOrderDataInfo.getHeadData().get("sqlxFnameid");
        if (null == sqlxFnameid || "".equals(sqlxFnameid)) {
            ToastUtils.showToast(getContext(), "请选择申请类型");
            return;
        }
        String htbhFnameid = mOrderDataInfo.getHeadData().get("htbhFnameid");
        if (null == htbhFnameid || "".equals(htbhFnameid)) {
            ToastUtils.showToast(getContext(), "请选择合同编号");
            return;
        }
        //保存
        saveData2Service(wlFnameid, skdwFnameid, fplxFnameid, EditTextUtils.getContent(et_use), sqlxFnameid, htbhFnameid, mOrderDataInfo.getHeadData().get("sgdwFnameid"));
    }

    private void saveData2Service(String wlFnameid, String skdwFnameid, String fplxFnameid, String content_use, String sqlxFnameid, String htbhFnameid, String sgdwFnameid) {
        ProgressDialogUtil.startShow(getContext(), "正在保存");
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String subStr = "";
        if (mData.size() == 1) {
            subStr = "   {\"FCOSTID\": {\"FNUMBER\": \"" + mData.get(0).getFyTypeID() + "\"},\"F_ABC_Text1\": \"" + mData.get(0).getFyName() + "\"," +
                    "  \"FPAYPURPOSEID\": {\"FNumber\": \"SFKYT09_SYS\"},\"FENDDATE\": \"" + sdf.format(dt) + "\"," +
                    "  \"FEXPECTPAYDATE\": \"" + sdf.format(dt) + "\",\"FAPPLYAMOUNTFOR\": " + mData.get(0).getSqmoney() + "," +
                    "  \"FEACHBANKACCOUNT\": \"" + mBankMap.get("bankNumber") + "\",\"FEACHCCOUNTNAME\": \"" + mBankMap.get("bankUserName") + "\",\"FEACHBANKNAME\": \" \"," +
                    "  \"FDescription\": \" \",\"F_ABC_Decimal2\": " + mData.get(0).getFnum() + ",\"F_ABC_Decimal\": " + mData.get(0).getFunit() + "," +
                    "  \"F_ABC_Decimal1\": " + mData.get(0).getFprice() + "}";
        } else {
            for (int i = 0; i < mData.size(); i++) {
                subStr = subStr + "   {\"FCOSTID\": {\"FNUMBER\": \"" + mData.get(0).getFyTypeID() + "\"},\"F_ABC_Text1\": \"" + mData.get(0).getFyName() + "\"," +
                        "  \"FPAYPURPOSEID\": {\"FNumber\": \"SFKYT09_SYS\"},\"FENDDATE\": \"" + sdf.format(dt) + "\"," +
                        "  \"FEXPECTPAYDATE\": \"" + sdf.format(dt) + "\",\"FAPPLYAMOUNTFOR\": " + mData.get(0).getSqmoney() + "," +
                        "  \"FEACHBANKACCOUNT\": \"" + mBankMap.get("bankNumber") + "\",\"FEACHCCOUNTNAME\": \"" + mBankMap.get("bankUserName") + "\",\"FEACHBANKNAME\": \" \"," +
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
                "        \"FAPPLYORGID\": {" + "\"FNumber\": \"100\"" + "}," +
                "        \"FSETTLECUR\": {" + "\"FNUMBER\": \"PRE001\"" + "}," +
                "        \"F_ABC_Text\": \"" + content_use + "\"," +
                "        \"F_ABC_Assistant\": {" + "\"FNumber\": \"" + fplxFnameid + "\"" + "}," +
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
                                    ProgressDialogUtil.hideDialog();
                                    //新建成功
                                    saveOrderNo = saveForResultInfo.getResult().getNumber();
                                    saveOrderID = saveForResultInfo.getResult().getId();
                                    ToastUtils.showToast(getContext(), "保存成功。");
                                    tv_subPic.setVisibility(View.VISIBLE);
                                    tv_submit.setText("提交审核");
                                }
                            });
                        }
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressDialogUtil.hideDialog();
                                ToastUtils.showToast(getContext(), "提交失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressDialogUtil.hideDialog();
                            ToastUtils.showToast(getContext(), "提交失败！");
                        }
                    });
                }
            }
        });
    }

    private void next2SendPic() {
        if (mBitmapList.size() <= 1) {
            Log.i("上传图片", "无需上传图片");
        } else {
            for (int i = 0; i < mBitmapList.size() - 1; i++) {
                upImageFile((String) mBitmapList.get(i + 1));
            }
        }
    }

    private void upImageFile(String filePath) {
        File file = new File(filePath);
        if (null != file && file.exists()) {
            //图片文件直接转的base64
            //String imgStr = getImgStr(filePath);

            //二、文件转bitmap，压缩后，再上传bitmap的base64
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            Bitmap bit = BitmapFactory.decodeFile(filePath, newOpts);
            Bitmap bitmap = CompressImageUtil.comprUSLarge(bit, 200);
            String imgStr = bitmap2StrByBase64(bitmap);

            String name = file.getName();
            long length = file.length();
            String suffix = "." + name.substring(name.lastIndexOf(".") + 1);
            new UpPicItemTask(imgStr, name, length, saveOrderID, saveOrderNo, 0, "", suffix).execute();
        } else {
            ToastUtils.showToast(getContext(), "图片文件不存在，请重新选择！");
        }
    }

    private void changeViewContent(final TextView tvcontent, final String title, final String writekind, final String whichkey) {
        if ("search".equals(writekind)) {
            //为空，弹出选择菜单列表
            showMoreWriteInfo(tvcontent, title, whichkey);
        }
    }

    private LvShowMoreAdapter showMoreAdapter;
    private List<List>        mShowData;//临时存放内码
    private OrderDataInfo     mOrderDataInfo;//整个订单表信息

    private void showMoreWriteInfo(final TextView tvcontent, final String title, final String whichkey) {
        View view = View.inflate(getContext(), R.layout.view_only_list, null);
        ImageView img_load = view.findViewById(R.id.img_load);
        ListView lv_showmore = view.findViewById(R.id.lv_showmore);
        //初始化等待条
        Glide.with(getContext()).load(R.drawable.loadgif).into(img_load);

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
        searchKindByWhichKey(title, img_load);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view).setTitle(title).show();
        lv_showmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvcontent.setText(mShowData.get(i).get(1).toString());
                fyxmID = mShowData.get(i).get(0).toString();
                if (null != whichkey && !"".equals(whichkey)) {
                    mOrderDataInfo.getHeadData().put(whichkey + "id", mShowData.get(i).get(0).toString());//number
                    mOrderDataInfo.getHeadData().put(whichkey, mShowData.get(i).get(1).toString());//name
                    if ("合同编号".equals(title)) {
                        mOrderDataInfo.getHeadData().put("sgdwFnameid", mShowData.get(i).get(2).toString());//number
                        mOrderDataInfo.getHeadData().put("sgdwFname", mShowData.get(i).get(3).toString());//name
                        tv_sgdw.setText(mShowData.get(i).get(3).toString());
                    }
                }
                dialog.dismiss();
            }
        });
    }

    private void searchKindByWhichKey(String title, final ImageView img_load) {
        final String sql;
        if ("往来".equals(title)) {
            // sql = "{\"FormId\":\"BD_Supplier\",\"FieldKeys\":\"fnumber,fname\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":1000,\"StartRow\":0,\"Limit\":0}";
            sql = "{\"FormId\":\"BD_Empinfo\",\"FieldKeys\":\"fnumber,fname\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":1000,\"StartRow\":0,\"Limit\":0}";
        } else if ("收款单位".equals(title)) {
            sql = "{\"FormId\":\"BD_Empinfo\",\"FieldKeys\":\"fnumber,fname\",\"FilterString\":\"\",\"OrderString\":\"\",\"TopRowCount\":1000,\"StartRow\":0,\"Limit\":0}";
        } else if ("费用项目".equals(title)) {
            //查询
            new AreaTask(img_load).execute();
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
            new ContractNumTask(img_load).execute();
            return;
        } else {
            sql = "";
        }
        if ("".equals(sql)) {
            ToastUtils.showToast(getContext(), "查询出错");
            return;
        }
        img_load.setVisibility(View.VISIBLE);
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
                } finally {
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (null != img_load)
                                img_load.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private String fyxmID;//临时记录费用项目的ID

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
                        addApplyAdapter.notifyDataSetChanged();
                        popupOpenHelper.dismiss();
                    }
                });
            }
        });
    }

    private void initListView() {
        mData = new ArrayList<>();
        addApplyAdapter = new LvAddApplyAdapter(getContext(), mData, "add");
        lv_order.setAdapter(addApplyAdapter);
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //编辑子表
                showPopUp2AddTable("change", i);
            }
        });
        lv_order.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
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
                                popupOpenHelper.dismiss();
                            }
                        });
                    }
                });
                return true;
            }
        });
    }

    //查费用项目
    private class AreaTask extends AsyncTask<Void, String, String> {
        private ImageView img_load;

        public AreaTask(ImageView img_load) {
            this.img_load = img_load;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (null == mShowData) {
                mShowData = new ArrayList<>();
            } else {
                mShowData.clear();
            }
            if (null != img_load)
                img_load.setVisibility(View.VISIBLE);
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
            if (null != img_load)
                img_load.setVisibility(View.GONE);
            if ("1".equals(s)) {
                ToastUtils.showToast(getContext(), "查询失败");
            }
            showMoreAdapter.notifyDataSetChanged();
        }
    }

    //查合同编号
    private class ContractNumTask extends AsyncTask<Void, String, String> {
        private ImageView img_load;

        public ContractNumTask(ImageView img_load) {
            this.img_load = img_load;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (null == mShowData) {
                mShowData = new ArrayList<>();
            } else {
                mShowData.clear();
            }
            if (null != img_load)
                img_load.setVisibility(View.VISIBLE);
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
            String sql;
            if (isSearchALL) {
                sql = "select  HT_FID,HT_FNUMBER,HT_FNAME,SGDW_FID,SGDW_FNUMBER,SGDW_FNAME  from XV_HT_SGDW";
            } else {
                sql = "select  HT_FID,HT_FNUMBER,HT_FNAME,SGDW_FID,SGDW_FNUMBER,SGDW_FNAME  from XV_HT_SGDW " +
                        "where HT_FNUMBER like '%" + htSearchcont + "%' or HT_FNAME like '%" + htSearchcont + "%'";
            }

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
            if (null != img_load)
                img_load.setVisibility(View.GONE);
            if ("1".equals(s)) {
                ToastUtils.showToast(getContext(), "查询失败");
            }
            showMoreAdapter.notifyDataSetChanged();
        }
    }

    //提交单个图片
    class UpPicItemTask extends AsyncTask<Void, String, String> {
        private String buffer;
        private String sFAttachmentName;
        private long   sFAttachmentSize;
        private int    FINTERID;
        private String FBILLNO;
        private int    USERID;
        private String FNOTE;
        private String FILETYPE;

        UpPicItemTask(String buffer, String sFAttachmentName, long sFAttachmentSize, int FINTERID, String FBILLNO, int USERID, String FNOTE, String FILETYPE) {
            this.buffer = buffer;
            this.sFAttachmentName = sFAttachmentName;
            this.sFAttachmentSize = sFAttachmentSize;
            this.FINTERID = FINTERID;
            this.FBILLNO = FBILLNO;
            this.USERID = USERID;
            this.FNOTE = FNOTE;
            this.FILETYPE = FILETYPE;
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
            String methodName = Consts.UP_PIC_INTER;
            // EndPoint
            String endPoint = Consts.PICPOINT;
            // SOAP Action
            String soapAction = "k3clound/BD_upload_attchment";

            // 指定WebService的命名空间和调用的方法名
            SoapObject rpc = new SoapObject(nameSpace, methodName);

            // 设置需调用WebService接口需要传入的参数
            rpc.addProperty("bufferstr", buffer);
            rpc.addProperty("sFAttachmentName", sFAttachmentName);
            rpc.addProperty("sFAttachmentSize", sFAttachmentSize);
            rpc.addProperty("FINTERID", 100096);//FINTERID
            rpc.addProperty("FBILLNO", "FKSQ000047");//FBILLNO
            rpc.addProperty("USERID", MyAppliaction.memID);
            rpc.addProperty("FNOTE", "");
            rpc.addProperty("FILETYPE", FILETYPE);

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
            String result = object.getProperty(0).toString();//[{"status":"01","message":"成功"}]
            try {
                System.out.println(result);
                if (null != result && result.contains("成功")) {
                    ToastUtils.showToast(getContext(), "图片上传成功");
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
        }
    }

    /**
     * * 将图片转换成Base64编码
     * * @param imgFile 待处理图片
     * * @return
     */
    public static String getImgStr(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(data));
    }

    /*bitmap转base64*/
    public String bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 80, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
        //return new String(Base64.encodeToString(bytes, Base64.NO_WRAP));
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
                        mBankMap.put("bankNumber", lists.get(0).get(0).toString());
                        mBankMap.put("bankUserName", lists.get(0).get(1).toString());
                        //                        mBankMap.put("bankName", lists.get(0).get(1).toString());
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_wy.setText("".endsWith(mBankMap.get("bankUserName")) ? "未查找到网银" : mBankMap.get("bankUserName"));
                                tv_wy.setText("".endsWith(mBankMap.get("bankNumber")) ? "未查找到网银" : mBankMap.get("bankNumber"));
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
