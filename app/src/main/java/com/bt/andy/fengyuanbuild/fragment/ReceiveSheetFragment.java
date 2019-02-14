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
import com.bt.andy.fengyuanbuild.adapter.RecyOrderAdapter;
import com.bt.andy.fengyuanbuild.messegeInfo.StockInfo;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.MyNumUtils;
import com.bt.andy.fengyuanbuild.utils.ProgressDialogUtil;
import com.bt.andy.fengyuanbuild.utils.SoapUtil;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.bt.andy.fengyuanbuild.viewmodle.CustomDatePicker;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/21 15:29
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ReceiveSheetFragment extends Fragment implements View.OnClickListener {
    private View               mRootView;
    private ImageView          img_back;
    private TextView           tv_title;
    private TextView           tv_right;
    private TextView           tv_start;
    private TextView           tv_end;
    private TextView           tv_kehu;
    private TextView           tv_search;
    private SwipeRefreshLayout swiperefresh;
    private RecyclerView       recyview;
    private RecyOrderAdapter   orderAdapter;
    private List<StockInfo>    mData;
    private String             mStartTime;//起始时间
    private String             mEndTime;//最后时间

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_receive_sheet, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        img_back = mRootView.findViewById(R.id.img_back);
        tv_title = mRootView.findViewById(R.id.tv_title);
        tv_right = mRootView.findViewById(R.id.tv_right);

        tv_start = mRootView.findViewById(R.id.tv_start);
        tv_end = mRootView.findViewById(R.id.tv_end);
        tv_kehu = mRootView.findViewById(R.id.tv_kehu);
        tv_search = mRootView.findViewById(R.id.tv_search);
        swiperefresh = mRootView.findViewById(R.id.swipe);
        recyview = mRootView.findViewById(R.id.recyview);
    }

    private void initData() {
        tv_title.setText("应收账款报表");
        img_back.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);

        //获取当前系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        mStartTime = df.format(new Date());
        mEndTime = df.format(new Date());
        tv_start.setText(mStartTime);
        tv_end.setText(mEndTime);
        //初始化列表数据
        initRecyclerview();

        img_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        tv_end.setOnClickListener(this);
        tv_kehu.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.blue_icon),
                getResources().getColor(R.color.yellow_40), getResources().getColor(R.color.red_160));
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchOrderList(mStartTime, mEndTime);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.tv_right://跳转新增界面

                break;
            case R.id.tv_start://选择起始时间
                chooseTime(tv_start);
                break;
            case R.id.tv_end://选择结束时间
                chooseTime(tv_end);
                break;
            case R.id.tv_kehu://选择客户

                break;
            case R.id.tv_search://查询账单列表
                mStartTime = String.valueOf(tv_start.getText()).trim();
                mEndTime = String.valueOf(tv_end.getText()).trim();
                searchOrderList(mStartTime, mEndTime);
                break;
        }
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

    private void searchOrderList(String startTime, String endTime) {
        if ("".equals(mStartTime) || "请选择日期".equals(mStartTime)) {
            ToastUtils.showToast(getContext(), "请选择开始日期");
            return;
        }
        if ("".equals(mEndTime) || "请选择日期".equals(mEndTime)) {
            ToastUtils.showToast(getContext(), "请选择结束日期");
            return;
        }
        //查询账单列表
        String sql = "exec [z_yingshou] '" + startTime + "','" + endTime + "'";//2019-01-01 00:00:00   2019-04-02 00:00:00
        mData.clear();
        orderAdapter.notifyDataSetChanged();
        new ItemTask(sql).execute();
    }

    private void initRecyclerview() {
        mData = new ArrayList();
        recyview.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderAdapter = new RecyOrderAdapter(R.layout.recy_adapter_receive_sheet, getContext(), mData);
        recyview.setAdapter(orderAdapter);
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
            swiperefresh.setRefreshing(true);
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
                    map.put("fcode", recordEle.elementTextTrim("客户代码"));//客户代码
                    map.put("fname", recordEle.elementTextTrim("客户名称"));//客户名称
                    map.put("fcqmoney", recordEle.elementTextTrim("期初余额"));//期初余额
                    map.put("fbqys", recordEle.elementTextTrim("本期应收"));//本期应收
                    map.put("fbqss", recordEle.elementTextTrim("本期实收"));//本期实收
                    map.put("fqmye", recordEle.elementTextTrim("期末余额"));//期末余额
                    StockInfo stockInfo = new StockInfo();
                    stockInfo.setFcode(map.get("fcode"));//客户代码
                    stockInfo.setFname(map.get("fname"));//客户名称
                    stockInfo.setFcqmoney(df.format(MyNumUtils.getDoubleNum(map.get("fcqmoney"))));//期初余额 df.format(MyNumUtils.getDoubleNum(map.get("fbqss")))
                    stockInfo.setFqmye(df.format(MyNumUtils.getDoubleNum(map.get("fqmye"))));//期末余额
                    stockInfo.setFbqys(df.format(MyNumUtils.getDoubleNum(map.get("fbqys"))));//本期应收
                    stockInfo.setFbqss(df.format(MyNumUtils.getDoubleNum(map.get("fbqss"))));//本期实收
                    mData.add(stockInfo);
                }
                //填充数据到页面
                orderAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showToast(getContext(), "Tips:未查到订单未出库信息");
            }
            ProgressDialogUtil.hideDialog();
            swiperefresh.setRefreshing(false);
        }
    }

}
