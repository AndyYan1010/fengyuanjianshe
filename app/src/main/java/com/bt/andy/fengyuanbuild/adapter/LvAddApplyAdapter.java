package com.bt.andy.fengyuanbuild.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.messegeInfo.FeiYongXMInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.SubListInfo;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.EditTextUtils;
import com.bt.andy.fengyuanbuild.utils.PopupOpenHelper;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/7/13 9:12
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LvAddApplyAdapter extends BaseAdapter {
    private List<SubListInfo> mList;
    private Context           mContext;
    private String            mKind;

    public LvAddApplyAdapter(Context context, List<SubListInfo> list, String kind) {
        this.mContext = context;
        this.mList = list;
        this.mKind = kind;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final MyViewHolder viewHolder;
        if (null == view) {
            view = View.inflate(mContext, R.layout.lv_item_add_apply, null);
            viewHolder = new MyViewHolder();
            viewHolder.line_item = view.findViewById(R.id.line_item);
            viewHolder.tv_type = view.findViewById(R.id.tv_type);
            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_num = view.findViewById(R.id.tv_num);
            viewHolder.tv_unit = view.findViewById(R.id.tv_unit);
            viewHolder.tv_price = view.findViewById(R.id.tv_price);
            viewHolder.tv_fk = view.findViewById(R.id.tv_fk);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        viewHolder.tv_type.setText(mList.get(i).getFyType());
        viewHolder.tv_name.setText(mList.get(i).getFyName());
        viewHolder.tv_num.setText("" + mList.get(i).getFnum());
        viewHolder.tv_unit.setText("" + mList.get(i).getFunit());
        viewHolder.tv_price.setText("" + mList.get(i).getFprice());
        viewHolder.tv_fk.setText("" + mList.get(i).getSqmoney());
        viewHolder.line_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"search".equals(mKind))
                    //编辑子表
                    showPopUp2AddTable(viewHolder.tv_type, "change", i);
            }
        });
        return view;
    }

    private class MyViewHolder {
        LinearLayout line_item;
        TextView     tv_type, tv_price;
        TextView tv_name, tv_num, tv_unit, tv_fk;
    }

    private PopupOpenHelper popupOpenHelper;
    private String          fyxmID;

    private void showPopUp2AddTable(View tv_right, final String type, final int position) {
        popupOpenHelper = new PopupOpenHelper(mContext, tv_right, R.layout.popup_add_table);
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
                fyxmID = mList.get(position).getFyTypeID();
                tv_type.setText(mList.get(position).getFyType());
                et_name.setText(mList.get(position).getFyName());
                et_unit.setText("" + mList.get(position).getFunit());
                et_num.setText("" + mList.get(position).getFnum());
                tv_sum.setText("" + mList.get(position).getFprice());
                et_sqmoney.setText("" + mList.get(position).getSqmoney());
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
                            ToastUtils.showToast(mContext, "请选择费用项目");
                            return;
                        }
                        if (EditTextUtils.isEmpty(et_name, "请输入")) {
                            ToastUtils.showToast(mContext, "请填写费用名称");
                            return;
                        }
                        if (EditTextUtils.isEmpty(et_num, "0")) {
                            ToastUtils.showToast(mContext, "数量不能为0");
                            return;
                        }
                        if (EditTextUtils.isEmpty(et_unit, "0.00")) {
                            ToastUtils.showToast(mContext, "单价不能为0");
                            return;
                        }
                        if (EditTextUtils.isEmpty(et_sqmoney, "0.00")) {
                            ToastUtils.showToast(mContext, "申请金额不能为0");
                            return;
                        }
                        mList.get(position).setFyType(String.valueOf(tv_type.getText()).trim());
                        mList.get(position).setFyTypeID(fyxmID);
                        mList.get(position).setFyName(EditTextUtils.getContent(et_name));
                        mList.get(position).setFnum(Integer.parseInt(EditTextUtils.getContent(et_num)));
                        mList.get(position).setFunit(Double.parseDouble(EditTextUtils.getContent(et_unit)));
                        mList.get(position).setFprice(Double.parseDouble(String.valueOf(tv_sum.getText()).trim()));
                        mList.get(position).setSqmoney(Double.parseDouble(EditTextUtils.getContent(et_sqmoney)));
                        notifyDataSetChanged();
                        popupOpenHelper.dismiss();
                    }
                });
            }
        });
    }

    private void changeViewContent(final TextView tvcontent, final String title, final String writekind, final String whichkey) {
        //弹出dailog展示修改内容
        final EditText et = new EditText(mContext);
        //写入数据
        String oldContent = String.valueOf(tvcontent.getText());
        et.setText(oldContent);
        new AlertDialog.Builder(mContext).setView(et).setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //修改改变内容的textview
                        String content = String.valueOf(et.getText()).trim();
                        if ("search".equals(writekind)) {
                            //为空，弹出选择菜单列表
                            showMoreWriteInfo(tvcontent, title, content, whichkey);
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    private LvShowMoreAdapter showMoreAdapter;
    private List<List>        mShowData;//临时存放内码

    private void showMoreWriteInfo(final TextView tvcontent, String title, String content, final String whichkey) {
        View view = View.inflate(mContext, R.layout.view_only_list, null);
        ListView lv_showmore = view.findViewById(R.id.lv_showmore);
        if (null == mShowData) {
            mShowData = new ArrayList();
        } else {
            mShowData.clear();
        }
        if (null == showMoreAdapter) {
            showMoreAdapter = new LvShowMoreAdapter(mContext, mShowData);
        }
        lv_showmore.setAdapter(showMoreAdapter);
        //根据whichkey来查找
        searchKindByWhichKey(title);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).setTitle(title).show();
        lv_showmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvcontent.setText(mShowData.get(i).get(1).toString());
                fyxmID = mShowData.get(i).get(0).toString();
                dialog.dismiss();
            }
        });
    }

    private void searchKindByWhichKey(String title) {
        if ("费用项目".equals(title)) {
            //查询
            AreaTask areaTask = new AreaTask();
            areaTask.execute();
            return;
        }
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
                ToastUtils.showToast(mContext, "查询失败");
            }
            showMoreAdapter.notifyDataSetChanged();
        }
    }
}
