package com.bt.andy.fengyuanbuild.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.adapter.LvStockAdapter;
import com.bt.andy.fengyuanbuild.messegeInfo.StockInfo;
import com.bt.andy.fengyuanbuild.myTools.DropBean;
import com.bt.andy.fengyuanbuild.myTools.DropdownButton;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.MyNumUtils;
import com.bt.andy.fengyuanbuild.utils.ProgressDialogUtil;
import com.bt.andy.fengyuanbuild.utils.SoapUtil;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.bt.andy.fengyuanbuild.viewmodle.MyListView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/21 15:02
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class StockListFragment extends Fragment implements View.OnClickListener {
    private View                          mRootView;
    private ImageView                     img_back;
    private TextView                      tv_title;
    private TextView                      tv_right;
    private EditText                      edit_goods_id;
    private ImageView                     img_scan;//扫描物品的条码
    private TextView                      tv_surema;//查询
    private LinearLayout                  linear_drop;
    private DropdownButton                downbt;//模糊查询下拉列表
    private MyListView                    lv_stock;//库存列表
    private List<StockInfo>               mStockInfos;
    private LvStockAdapter                stockAdapter;
    private List<DropBean>                mGoodsNameList;//放置商品名称
    private List<HashMap<String, String>> mHTot;//记录模糊查询结果（商品名:商品id）
    private int REQUEST_CODE                       = 1002;//接收扫描结果
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;//申请照相机权限结果

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

        edit_goods_id = mRootView.findViewById(R.id.edit_goods_id);
        img_scan = mRootView.findViewById(R.id.img_scan);
        tv_surema = mRootView.findViewById(R.id.tv_surema);
        linear_drop = mRootView.findViewById(R.id.linear_drop);
        downbt = mRootView.findViewById(R.id.downbt);
        lv_stock = mRootView.findViewById(R.id.lv_stock);
    }

    private void initData() {
        tv_title.setText("查询库存");
        img_back.setOnClickListener(this);
        img_scan.setOnClickListener(this);
        tv_surema.setOnClickListener(this);
        //初始化物品列表
        initListViewInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.tv_surema:
                String goodsMid = String.valueOf(edit_goods_id.getText()).trim();
                linear_drop.setVisibility(View.INVISIBLE);
                if (null == goodsMid || "".equals(goodsMid) || "商品编码".equals(goodsMid)) {
                    mStockInfos.clear();
                    stockAdapter.notifyDataSetChanged();
                    ToastUtils.showToast(getContext(), "请输入商品编码");
                    return;
                }
                //查询之前关闭输入法，防止bug
                hintKeyBoard(edit_goods_id);
                new Task(goodsMid).execute();
                break;
            case R.id.img_scan:
                //动态申请照相机权限
                //第二个参数是需要申请的权限
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                } else {
                    Intent intent = new Intent(getContext(), CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;
        }
    }

    private void initListViewInfo() {
        //模糊查询结果
        mGoodsNameList = new ArrayList<DropBean>();
        downbt.setData(mGoodsNameList);
        downbt.setOnDropItemSelectListener(new DropdownButton.OnDropItemSelectListener() {
            @Override
            public void onDropItemSelect(int Postion) {
                if (Postion == 0) {
                    ToastUtils.showToast(getContext(), "请选择商品");
                    return;
                }
                HashMap<String, String> goodsMap = mHTot.get(Postion - 1);
                String fnumber = goodsMap.get("fnumber");
                downbt.setChecked(false);
                linear_drop.setVisibility(View.INVISIBLE);
                //查询商品id，写入商品库存
                writeGoodsStock(fnumber);
            }
        });
        mStockInfos = new ArrayList<>();
        stockAdapter = new LvStockAdapter(getContext(), mStockInfos);
        lv_stock.setAdapter(stockAdapter);
    }

    private void writeGoodsStock(String fnumber) {
        System.out.println(fnumber);
        if (null != linear_drop) {
            linear_drop.setVisibility(View.INVISIBLE);
        }
        //访问网络，获取详情
        //根据扫描的代码查询
        //String sql2 = "select b.fnumber,b.fname,c.fnumber,c.fname,a.FQty,b.FGoodsBarCode from ICInventory a inner join t_ICItem b on a.FItemID=b.FItemID inner join t_Stock c on c.fitemid=a.FStockID  where b.FGoodsBarCode='" + fnumber + "' or b.fnumber='" + fnumber + "'";
        String sql2 = "select b.fnumber,b.fname,b.FHelpCode,c.fname,d.fname,a.fqty,a.fbatchno  from icinventory a inner join t_icitem b on a.fitemid=b.fitemid inner join t_stock c on c.fitemid=a.fstockid inner join t_MeasureUnit d on d.fitemid=b.FUnitID where b.fnumber ='" + fnumber + "'";

        //根据助记码或者名称模糊查询
        new ItemTask(sql2).execute();
    }

    class ItemTask extends AsyncTask<Void, String, String> {
        String sql;

        ItemTask(String sql) {
            this.sql = sql;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.startShow(getContext(), "正在查找,请稍等...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            Map<String, String> map = new HashMap<>();
            map.put("FSql", sql);
            map.put("FTable", "t_icitem");
            return SoapUtil.requestWebService(Consts.JA_select, map);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (null == mStockInfos) {
                    mStockInfos = new ArrayList<>();
                } else {
                    mStockInfos.clear();
                }
                DecimalFormat df = new DecimalFormat(".00");
                Document doc = DocumentHelper.parseText(s);
                Element ele = doc.getRootElement();
                Iterator iter = ele.elementIterator("Cust");
                HashMap<String, String> map = new HashMap<>();
                while (iter.hasNext()) {
                    Element recordEle = (Element) iter.next();
                    map.put("fbatchno", recordEle.elementTextTrim("fbatchno"));//批次
                    map.put("fname", recordEle.elementTextTrim("fname"));//物料名称
                    map.put("fqty", recordEle.elementTextTrim("fqty"));//库存数量
                    map.put("fname1", recordEle.elementTextTrim("fname1"));//仓库
                    map.put("fname2", recordEle.elementTextTrim("fname2"));//单位
                    StockInfo stockInfo = new StockInfo();
                    stockInfo.setFpici(map.get("fbatchno"));//批次
                    stockInfo.setFname(map.get("fname"));//名称
                    stockInfo.setAddress(map.get("fname1"));//哪个仓库
                    stockInfo.setFunit(map.get("fname2"));//货物单位
                    stockInfo.setFqty(df.format(MyNumUtils.getDoubleNum(map.get("fqty"))));
                    if (!".00".equals(stockInfo.getFqty())) {
                        mStockInfos.add(stockInfo);
                    }
                }
                if (mStockInfos.size() > 0) {
                    linear_drop.setVisibility(View.GONE);
                } else {
                    linear_drop.setVisibility(View.INVISIBLE);
                }
                //填充数据到页面
                stockAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showToast(getContext(), "Tips:未查到此商品");
            }
            ProgressDialogUtil.hideDialog();
        }
    }

    class Task extends AsyncTask<Void, String, String> {
        //输入框里获得
        String text;

        public Task(String text) {
            this.text = text;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            //            String sql = "select fnumber,fname from t_icitem where FHelpCode like'%" + text + "%' or fname like '%" + text + "%' or FGoodsBarCode like '%" + text + "%'";
            String sql = "select fnumber,fname,fhelpCode from t_icitem where  FName like '%" + text + "%' or FNumber like '%" + text + "%'";
            //            String sql = "select fnumber,fname,fhelpCode from t_icitem ";
            Map<String, String> map = new HashMap<>();
            map.put("FSql", sql);
            map.put("FTable", "t_icitem");
            return SoapUtil.requestWebService(Consts.JA_select, map);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (null == mHTot) {
                    mHTot = new ArrayList();
                } else {
                    mHTot.clear();
                }
                Document doc = DocumentHelper.parseText(s);
                Element ele = doc.getRootElement();
                Iterator iter = ele.elementIterator("Cust");
                while (iter.hasNext()) {
                    HashMap<String, String> map = new HashMap<>();
                    Element recordEle = (Element) iter.next();
                    map.put("fnumber", recordEle.elementTextTrim("fnumber"));//物料条码
                    map.put("fname", recordEle.elementTextTrim("fname"));//物料名称
                    map.put("fhelpCode", recordEle.elementTextTrim("fhelpCode"));//物料规格
                    mHTot.add(map);
                }
                //填充数据到页面
                linear_drop.setVisibility(View.VISIBLE);
                if (null == mGoodsNameList) {
                    mGoodsNameList = new ArrayList<>();
                } else {
                    mGoodsNameList.clear();
                }
                mGoodsNameList.add(new DropBean("请选择查询结果"));
                for (HashMap<String, String> map : mHTot) {
                    String fname = map.get("fname");
                    String fhelpCode = map.get("fhelpCode");
                    mGoodsNameList.add(new DropBean(fname + "    规格：" + fhelpCode));
                }
                downbt.setData(mGoodsNameList);
                downbt.setChecked(true);
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showToast(getContext(), "未查询到类似商品助记码");
            }
        }
    }

    //关闭输入法
    private void hintKeyBoard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
}
