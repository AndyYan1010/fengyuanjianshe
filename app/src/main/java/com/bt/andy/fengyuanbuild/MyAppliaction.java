package com.bt.andy.fengyuanbuild;

import android.app.Activity;
import android.app.Application;

import com.bt.andy.fengyuanbuild.utils.ExceptionUtil;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/22 8:51
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyAppliaction extends Application {
    public static boolean             isRelease    = false;
    public static ArrayList<Activity> listActivity = new ArrayList<Activity>();
    public static int                 flag         = -1;//判断是否被回收
    public static String  userName;//用户名
    public static String  userpswd;//密码
    public static boolean userAllRight;//权限
    public static String  memID;//操作员ID
    public static String  userType;//用户(描述)类别  //员工，会计，经理
    public static String  userAccount;//用户账号
    public static String  bankNumber;//用户网银内码
    public static String  bankUserName;//用户网银名称
    //冯源中暂未用到
    public static String  userID;//用户id

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }

    public static void exit() {
        try {
            for (Activity activity : listActivity) {
                activity.finish();
            }
            // 结束进程
            System.exit(0);
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
    }
}
