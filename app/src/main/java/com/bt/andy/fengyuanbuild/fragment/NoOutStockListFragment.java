package com.bt.andy.fengyuanbuild.fragment;

import android.os.AsyncTask;
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
import com.bt.andy.fengyuanbuild.adapter.RecyNoOutStockAdapter;
import com.bt.andy.fengyuanbuild.messegeInfo.StockInfo;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.MyNumUtils;
import com.bt.andy.fengyuanbuild.utils.ProgressDialogUtil;
import com.bt.andy.fengyuanbuild.utils.SoapUtil;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;

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
 * @创建时间 2019/1/21 16:18
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class NoOutStockListFragment extends Fragment implements View.OnClickListener {
    private View                  mRootView;
    private ImageView             img_back;
    private TextView              tv_title;
    private SwipeRefreshLayout    swipe;
    private RecyclerView          recyview;
    private RecyNoOutStockAdapter noOutStockAdapter;
    private List                  mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_noout_sheet, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_title = mRootView.findViewById(R.id.tv_title);
        swipe = mRootView.findViewById(R.id.swipe);
        recyview = mRootView.findViewById(R.id.recyview);
    }

    private void initData() {
        tv_title.setText("订单未出库列表");
        img_back.setVisibility(View.VISIBLE);
        //初始化列表
        initRecyList();
        //获取未出库列表信息
        getNoOutStockListInfo();
        img_back.setOnClickListener(this);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //获取未出库列表信息
                getNoOutStockListInfo();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;

        }
    }

    private void initRecyList() {
        mData = new ArrayList();
        recyview.setLayoutManager(new LinearLayoutManager(getContext()));
        //解决数据加载完成后, 没有停留在顶部的问题
        recyview.setNestedScrollingEnabled(false);
        recyview.setHasFixedSize(true);
        recyview.setFocusable(false);
        noOutStockAdapter = new RecyNoOutStockAdapter(getContext(), mData, R.layout.recy_noout_item);
        recyview.setAdapter(noOutStockAdapter);
    }

    private void getNoOutStockListInfo() {
        swipe.setRefreshing(true);
        String sql2 = "select a.fbillno,a.fdate,d.fname,c.fname,b.fauxqty,e.fname from seorder a inner join seorderentry b on a.finterid=b.finterid inner join t_icitem c on c.fitemid=b.fitemid" +
                " inner join t_organization d on d.fitemid=a.fcustid inner join t_measureunit e on e.fitemid=c.funitid  where a.fclosed=0 and b.fmrpclosed=0";

        String sql = "select a.fbillno,a.fdate,d.fname,c.fname,b.fauxqty,e.fname from seorder a inner join seorderentry b on a.finterid=b.finterid inner join t_icitem c on c.fitemid=b.fitemid" +
                " inner join t_organization d on d.fitemid=a.fcustid inner join t_measureunit e on e.fitemid=c.funitid  where a.fclosed=0 and b.fmrpclosed=0";
        //        String sql = "select  * from z_seorder";
        //根据助记码或者名称模糊查询
        new ItemTask(sql).execute();
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
            if (null == mData) {
                mData = new ArrayList();
            } else {
                mData.clear();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            Map<String, String> map = new HashMap<>();
            map.put("FSql", sql);
            map.put("FTable", "seorderentry");//seorderentry  t_icitem
            return SoapUtil.requestWebService(Consts.JA_select, map);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                DecimalFormat df = new DecimalFormat(".00");
                Document doc = DocumentHelper.parseText(s);
                Element ele = doc.getRootElement();
                Iterator iter = ele.elementIterator("Cust");
                HashMap<String, String> map = new HashMap<>();
                while (iter.hasNext()) {
                    Element recordEle = (Element) iter.next();
                    map.put("fbillno", recordEle.elementTextTrim("fbillno"));//编号
                    map.put("fdate", recordEle.elementTextTrim("fdate"));//日期
                    map.put("fname", recordEle.elementTextTrim("fname"));//客户名称
                    map.put("fname1", recordEle.elementTextTrim("fname1"));//物料名称
                    map.put("fauxqty", recordEle.elementTextTrim("fauxqty"));//库存数量
                    map.put("fname2", recordEle.elementTextTrim("fname2"));//单位
                    StockInfo stockInfo = new StockInfo();
                    stockInfo.setFbill(map.get("fbillno"));//编号
                    stockInfo.setFdate(map.get("fdate"));//日期
                    stockInfo.setFcusname(map.get("fname"));//客户名称
                    stockInfo.setFname(map.get("fname1"));//物料名称
                    stockInfo.setFqty(df.format(MyNumUtils.getDoubleNum(map.get("fauxqty"))));//库存数量
                    stockInfo.setFunit(map.get("fname2"));//单位
                    mData.add(stockInfo);
                }
                //填充数据到页面
                noOutStockAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showToast(getContext(), "Tips:未查到订单未出库信息");
            }
            ProgressDialogUtil.hideDialog();
            swipe.setRefreshing(false);
        }
    }
}
