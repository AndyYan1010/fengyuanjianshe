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

import com.bt.andy.fengyuanbuild.MyAppliaction;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.adapter.RecyNoOutStockAdapter;
import com.bt.andy.fengyuanbuild.messegeInfo.StockInfo;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.MyNumUtils;
import com.bt.andy.fengyuanbuild.utils.ProgressDialogUtil;
import com.bt.andy.fengyuanbuild.utils.SoapUtil;
import com.bt.andy.fengyuanbuild.utils.ThreadUtils;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.bt.andy.fengyuanbuild.viewmodle.CustomDatePicker;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    private TextView              tv_start;
    private TextView              tv_end;
    private TextView              tv_search;
    private String                mStartTime;//起始时间
    private String                mEndTime;//最后时间

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
        tv_start = mRootView.findViewById(R.id.tv_start);
        tv_end = mRootView.findViewById(R.id.tv_end);
        tv_search = mRootView.findViewById(R.id.tv_search);
    }

    private void initData() {
        tv_title.setText("订单未出库列表");
        img_back.setVisibility(View.VISIBLE);
        //初始化列表
        initRecyList();
        //获取当前系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        mEndTime = df.format(new Date());
        tv_end.setText(mEndTime);
        //过去七天
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        tv_start.setText(df.format(d));
        mStartTime = df.format(d);


        //获取未出库列表信息
        getNoOutStockListInfo();
        img_back.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        tv_end.setOnClickListener(this);
        tv_search.setOnClickListener(this);

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
            case R.id.tv_start://选择起始时间
                chooseTime(tv_start);
                break;
            case R.id.tv_end://选择结束时间
                chooseTime(tv_end);
                break;
            case R.id.tv_search://查询账单列表
                mStartTime = String.valueOf(tv_start.getText()).trim();
                mEndTime = String.valueOf(tv_end.getText()).trim();
                getNoOutStockListInfo();
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

    private CustomDatePicker dpk1;

    private void chooseTime(final TextView tv_date) {
        //打开时间选择器
        dpk1 = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_date.setText(time);
            }
        }, "2000-01-01 00:00", "2090-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        dpk1.showSpecificTime(true); // 显示时和分
        dpk1.setIsLoop(true); // 允许循环滚动
        dpk1.show(mStartTime);
    }

    private void getNoOutStockListInfo() {
        swipe.setRefreshing(true);
        String sql2 = "select a.fbillno,a.fdate,d.fname,c.fname,b.fauxqty,e.fname from seorder a inner join seorderentry b on a.finterid=b.finterid inner join t_icitem c on c.fitemid=b.fitemid" +
                " inner join t_organization d on d.fitemid=a.fcustid inner join t_measureunit e on e.fitemid=c.funitid  where a.fclosed=0 and b.fmrpclosed=0";

        String sql = "select a.fbillno,a.fdate,d.fname,c.fname,b.fauxqty,e.fname from seorder a inner join seorderentry b on a.finterid=b.finterid inner join t_icitem c on c.fitemid=b.fitemid" +
                " inner join t_organization d on d.fitemid=a.fcustid inner join t_measureunit e on e.fitemid=c.funitid  where a.fclosed=0 and b.fmrpclosed=0";
        //        String sql = "select  * from z_seorder";

        //        String sds="select a.fbillno 单据编号,a.fdate 日期,d.fname 客户名称,c.fname 物料名称,b.fauxqty 数量,b.fauxqty-b.fauxstockqty 未出库数量,e.fname 单位 from seorder a inner join seorderentry b on a.finterid=b.finterid inner join t_icitem c on c.fitemid=b.fitemid\n" +
        //                "inner join t_organization d on d.fitemid=a.fcustid inner join t_measureunit e on e.fitemid=c.funitid  where a.fclosed=0 and b.fmrpclosed=0 and fbillno='SEORD024909'";

        String sds = "select f.fnumber, a.fbillno,a.fdate,d.fname,c.fname,b.fauxqty,b.fauxqty-b.fauxstockqty,e.fname from seorder a inner join seorderentry b on a.finterid=b.finterid inner join t_icitem c on c.fitemid=b.fitemid " +
                "inner join t_organization d on d.fitemid=a.fcustid inner join t_measureunit e on e.fitemid=c.funitid inner join t_item f on f.fitemid=d.fparentid where a.fclosed=0 and b.fmrpclosed=0 and f.fnumber='" + MyAppliaction.userName + "' and a.fdate between '" + mStartTime + "' and '" + mEndTime + "'";

        //根据助记码或者名称模糊查询
        new ItemTask(sds).execute();
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
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DecimalFormat df = new DecimalFormat(".00");
                        Document doc = DocumentHelper.parseText(s);
                        Element ele = doc.getRootElement();
                        Iterator iter = ele.elementIterator("Cust");
                        //                        HashMap<String, String> map = new HashMap<>();
                        while (iter.hasNext()) {
                            Element recordEle = (Element) iter.next();
                            //                            map.put("fbillno", );//编号
                            //                            map.put("fdate", );//日期
                            //                            map.put("fname", );//客户名称
                            //                            map.put("fname1", );//物料名称
                            //                            map.put("fauxqty", );//库存数量
                            //                            map.put("Column1", );//未出库数量
                            //                            map.put("fname2", );//单位

                            StockInfo stockInfo = new StockInfo();
                            stockInfo.setFbill(recordEle.elementTextTrim("fbillno"));//编号
                            stockInfo.setFdate(recordEle.elementTextTrim("fdate"));//日期
                            stockInfo.setFcusname(recordEle.elementTextTrim("fname"));//客户名称
                            stockInfo.setFname(recordEle.elementTextTrim("fname1"));//物料名称
                            stockInfo.setFqty(df.format(MyNumUtils.getDoubleNum(recordEle.elementTextTrim("fauxqty"))));//库存数量
                            stockInfo.setColumn1(df.format(MyNumUtils.getDoubleNum(recordEle.elementTextTrim("Column1"))));//未出库数量
                            stockInfo.setFunit(recordEle.elementTextTrim("fname2"));//单位
                            mData.add(stockInfo);
                        }
                        ListSort(mData);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getContext(), "Tips:未查到订单未出库信息");
                            }
                        });
                    }
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            //填充数据到页面
                            noOutStockAdapter.notifyDataSetChanged();
                            ProgressDialogUtil.hideDialog();
                            swipe.setRefreshing(false);
                        }
                    });
                }
            });
        }
    }

    private void ListSort(List<StockInfo> list) {
        Collections.sort(list, new Comparator<StockInfo>() {
            @Override
            public int compare(StockInfo o1, StockInfo o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    Date dt1 = format.parse(o1.getFdate());
                    Date dt2 = format.parse(o2.getFdate());
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
