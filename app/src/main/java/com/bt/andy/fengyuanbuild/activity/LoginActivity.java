package com.bt.andy.fengyuanbuild.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bt.andy.fengyuanbuild.BaseActivity;
import com.bt.andy.fengyuanbuild.MainActivity;
import com.bt.andy.fengyuanbuild.MyAppliaction;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.messegeInfo.LoginDetailInfo;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.ProgressDialogUtil;
import com.bt.andy.fengyuanbuild.utils.SoapUtil;
import com.bt.andy.fengyuanbuild.utils.SpUtils;
import com.bt.andy.fengyuanbuild.utils.ThreadUtils;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kingdee.bos.webapi.client.K3CloudApiClient;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/22 9:05
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEdit_num;
    private EditText mEdit_psd;
    private CheckBox ck_remPas;
    private Button   mBt_submit;
    private boolean isRem = false;//记录是否保存账号密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_actiivty);
        MyAppliaction.flag = 0;
        getView();
        setData();
    }

    private void getView() {
        mEdit_num = (EditText) findViewById(R.id.edit_num);
        mEdit_psd = (EditText) findViewById(R.id.edit_psd);
        ck_remPas = (CheckBox) findViewById(R.id.ck_remPas);
        mBt_submit = (Button) findViewById(R.id.bt_login);
    }

    private void setData() {
        Boolean isRemem = SpUtils.getBoolean(LoginActivity.this, "isRem", false);
        if (isRemem) {
            isRem = true;
            ck_remPas.setChecked(true);
            String name = SpUtils.getString(LoginActivity.this, "name");
            String psd = SpUtils.getString(LoginActivity.this, "psd");
            mEdit_num.setText(name);
            mEdit_psd.setText(psd);
        }
        ck_remPas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRem = b;
            }
        });
        mBt_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String number = mEdit_num.getText().toString().trim();
                String pass = mEdit_psd.getText().toString().trim();
                if ("".equals(number) || "请输入工号".equals(number)) {
                    ToastUtils.showToast(LoginActivity.this, "请输入工号");
                    return;
                }
                if ("请输入密码".equals(pass)) {
                    ToastUtils.showToast(LoginActivity.this, "请输入密码");
                    return;
                }
                //number = "席会计";
                //pass = "123456";
                //是否记住账号密码
                isNeedRem(number, pass);
                //冯源建设
                //loginFun(number, pass);
                //常州天宏
//                loginFunCZ(number, pass);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void loginFunCZ(String number, String pass) {
        new LoginTask(number, pass).execute();
    }

    class LoginTask extends AsyncTask<Void, String, String> {
        String username;
        String password;

        LoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.startShow(LoginActivity.this, "正在登录，请稍后...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            Map<String, String> map = new HashMap<>();
            map.put("UserName", username);
            map.put("PassWord", password);
            return SoapUtil.requestWebService(Consts.Login, map);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ProgressDialogUtil.hideDialog();
            if (s.contains("成功")) {
                String[] split = s.split("/");
                String sId = split[0];
                String userid = sId.substring(2, sId.length());
                MyAppliaction.userID = userid;//用户id
                MyAppliaction.memID = username;//工号
                if (split.length >= 2) {
                    MyAppliaction.userName = split[1];//用户姓名
                } else {
                    MyAppliaction.userName = "";//用户姓名
                    ToastUtils.showToast(LoginActivity.this, "系统中未填写姓名");
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                ToastUtils.showToast(LoginActivity.this, "登陆成功");
                finish();
            } else {
                ToastUtils.showToast(LoginActivity.this, "登陆失败");
            }
        }
    }

    private void loginFun(final String number, final String pass) {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, number, pass, Consts.lang);
                    if (result) {
                        MyAppliaction.userName = number;
                        MyAppliaction.userpswd = pass;
                        //查询操作员信息
                        //                        String sql = "{\"FormId\": \"BD_Empinfo\",\"FieldKeys\": \"FName,FNumber\"," +
                        //                                "    \"FilterString\": \"FName='" + number + "'\",\"OrderString\": \"\"," +
                        //                                "    \"TopRowCount\": 100,\"StartRow\": 0,\"Limit\": 0}";
                       /* String sql = "{\"FormId\": \"SEC_User\",\"FieldKeys\": \"FName,FUserID \"," +
                                "    \"FilterString\": \"FName='" + number + "'\"," +
                                "    \"OrderString\": \"\",\"TopRowCount\": 100," +
                                "    \"StartRow\": 0,\"Limit\": 0}";*/
                        //                        List<List<Object>> lists = client.executeBillQuery(sql);
                        //                        MyAppliaction.memID = lists.get(0).get(1).toString();
                        // String searchSql = "{\"CreateOrgId\":\"BD_Empinfo\",\"Number\": \"" + MyAppliaction.memID + "\",\"Id\": \"\"}";
                        String searchSql = "{\"CreateOrgId\": \"SEC_User\",\"Number\": \"" + number + "\",\"Id\": \"\"}";
                        String bd_empinfo = client.view("SEC_User", searchSql);
                        Gson gson = new Gson();
                        LoginDetailInfo loginDetailInfo = gson.fromJson(bd_empinfo, LoginDetailInfo.class);
                        MyAppliaction.memID = "" + loginDetailInfo.getResult().getResult().getId();
                        List<?> fDescription = loginDetailInfo.getResult().getResult().getFDescription();
                        //从描述中获取账号权限
                        if (fDescription.size() > 0) {
                            MyAppliaction.userType = fDescription.get(0).toString();
                        }
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(LoginActivity.this, "登录失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(LoginActivity.this, "登录失败！");
                        }
                    });
                }
            }
        });
    }

    private void isNeedRem(String name, String psd) {
        SpUtils.putBoolean(LoginActivity.this, "isRem", isRem);
        if (isRem) {
            SpUtils.putString(LoginActivity.this, "name", name);
            SpUtils.putString(LoginActivity.this, "psd", psd);
        }
    }
}
