package com.bt.andy.fengyuanbuild.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bt.andy.fengyuanbuild.BaseActivity;
import com.bt.andy.fengyuanbuild.MainActivity;
import com.bt.andy.fengyuanbuild.MyAppliaction;
import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.messegeInfo.AccountMemberInfo;
import com.bt.andy.fengyuanbuild.messegeInfo.LoginDetailInfo;
import com.bt.andy.fengyuanbuild.utils.Consts;
import com.bt.andy.fengyuanbuild.utils.ProgressDialogUtil;
import com.bt.andy.fengyuanbuild.utils.SoapUtil;
import com.bt.andy.fengyuanbuild.utils.SpUtils;
import com.bt.andy.fengyuanbuild.utils.ThreadUtils;
import com.bt.andy.fengyuanbuild.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kingdee.bos.webapi.client.K3CloudApiClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
                //是否记住账号密码
                isNeedRem(number, pass);
                //冯源建设
                loginFun(number, pass);
                //常州天宏
                //                loginFunCZ(number, pass);
                //                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //                startActivity(intent);

                //okhttp访问webservice
                //                testOkWeb();
                break;
        }
    }

    private void testOkWeb() {
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            //调用相册
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE);
        }
    }

    private static final int IMAGE                              = 1;//调用系统相册-选择图片n小于0
    private              int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相册返回，获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    private void showImage(String imagePath) {
        File file = new File(imagePath);
        if (null != file && file.exists()) {
            byte[] fileToByte = getFileToByte(file);
            try {
                contactByOkh(fileToByte, file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // contactByOkh(file);
        }
    }

    private void contactByOkh(byte[] fileToByte, String fileName) throws IOException {
        String url = "http://111.231.56.97:9091/WebService1_k3cloud.asmx/Append?fileName=" + fileName + "&buffer=" + fileToByte;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(LoginActivity.this, "网络错误");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (200 == response.code()) {
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(LoginActivity.this, "上传成功");
                            ResponseBody body = response.body();
                            System.out.println(body.toString());
                        }
                    });
                }
            }
        });

    }

    private void contactByOkh(File file) {
        // String url = "http://2366x63q23.51mypc.cn:36994/Service1.asmx/Login_CZ";
        String url = "http://111.231.56.97:9091/WebService1_k3cloud.asmx/Append";

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder1 = new Request.Builder();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), file);
        MultipartBody.Builder mtBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        mtBuilder.addFormDataPart("fileName", file.getName());
        mtBuilder.addFormDataPart("buffer", file.getName(), fileBody);
        RequestBody requestBody = mtBuilder.build();
        Request request = builder1.url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(LoginActivity.this, "网络错误");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (200 == response.code()) {
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(LoginActivity.this, "上传成功");
                            ResponseBody body = response.body();
                            System.out.println(body.toString());
                        }
                    });
                }
            }
        });
    }

    private byte[] getFileToByte(File testFile) {
        byte[] by = new byte[(int) testFile.length()];
        try {
            InputStream is = new FileInputStream(testFile);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin Array 出错", ex);
        }
        return by;
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
            //  Intent intent0 = new Intent(LoginActivity.this, MainActivity.class);
            //  startActivity(intent0);
            if ("0".equals(s)) {
                ToastUtils.showToast(LoginActivity.this, "登陆成功");
                MyAppliaction.userName = username;
                Intent intent0 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent0);
            } else if ("1".equals(s)) {
                ToastUtils.showToast(LoginActivity.this, "登陆失败，密码错误");
            } else if ("2".equals(s)) {
                ToastUtils.showToast(LoginActivity.this, "登陆失败，用户名错误");
            } else {
                ToastUtils.showToast(LoginActivity.this, "登陆失败，未知错误");
            }

            //            if (s.contains("成功")) {
            //                String[] split = s.split("/");
            //                String sId = split[0];
            //                String userid = sId.substring(2, sId.length());
            //                MyAppliaction.userID = userid;//用户id
            //                MyAppliaction.memID = username;//工号
            //                if (split.length >= 2) {
            //                    MyAppliaction.userName = split[1];//用户姓名
            //                } else {
            //                    MyAppliaction.userName = "";//用户姓名
            //                    ToastUtils.showToast(LoginActivity.this, "系统中未填写姓名");
            //                }
            //                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //                startActivity(intent);
            //                ToastUtils.showToast(LoginActivity.this, "登陆成功");
            //                finish();
            //            } else {
            //                ToastUtils.showToast(LoginActivity.this, "登陆失败");
            //            }
        }
    }

    private void loginFun(final String number, final String pass) {
        ProgressDialogUtil.startShow(this, "正在登录...");
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
                        MyAppliaction.userAccount = loginDetailInfo.getResult().getResult().getUserAccount();
                        //获取员工的银行账号信息
                        getUserBackInfo(MyAppliaction.userAccount);
                        List<?> fDescription = loginDetailInfo.getResult().getResult().getFDescription();
                        //从描述中获取账号权限
                        if (fDescription.size() > 0) {
                            MyAppliaction.userType = fDescription.get(0).toString();
                        }
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressDialogUtil.hideDialog();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressDialogUtil.hideDialog();
                                ToastUtils.showToast(LoginActivity.this, "登录失败！");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressDialogUtil.hideDialog();
                            ToastUtils.showToast(LoginActivity.this, "登录失败！");
                        }
                    });
                }
            }
        });
    }

    private void getUserBackInfo(String userAccount) {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                K3CloudApiClient client = new K3CloudApiClient(Consts.K3CloudURL);
                try {
                    result = client.login(Consts.dbId, MyAppliaction.userName, MyAppliaction.userpswd, Consts.lang);
                    if (result) {
                        //查询网银
                        String sqlSub = "{\"CreateOrgId\": 0,\"Number\": \"" + MyAppliaction.userAccount + "\",\"Id\": \"\"}";
                        String bd_empinfo = client.view("BD_Empinfo", sqlSub);
                        Gson gson = new Gson();
                        AccountMemberInfo accountMemberInfo = gson.fromJson(bd_empinfo, AccountMemberInfo.class);
                        MyAppliaction.bankNumber = accountMemberInfo.getResult().getResult().getEmpinfoBank().get(0).getBankCode();
                        MyAppliaction.bankUserName = accountMemberInfo.getResult().getResult().getEmpinfoBank().get(0).getBankHolder();
                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(LoginActivity.this, "未查找到对应网银");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(LoginActivity.this, "未查找到对应网银");
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
