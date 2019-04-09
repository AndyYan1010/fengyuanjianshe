package com.bt.andy.fengyuanbuild.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @创建者 AndyYan
 * @创建时间 2019/3/14 9:06
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

/**
 * 创建数据库子类，继承自SQLiteOpenHelper类
 * 需 复写 onCreat（）、onUpgrade（）
 */
public class SQLiteDbHelper extends SQLiteOpenHelper {
    public static Context mContext;
    public static final  String DB_NAME                   = "database.db";
    public static final  int    DB_VERSION                = 1;
    public static final  String TABLE_PIC                 = "picAddress";
    private static final String STUDENTS_CREATE_TABLE_SQL = "create table" + TABLE_PIC + "("
            + "id integer primary key autoincrement,"
            + "name varchar(64))"
            + "address varchar(64));";

    //    //创建 students 表的 sql 语句
    //    private static final String STUDENTS_CREATE_TABLE_SQL = "create table" + TABLE_PIC + "("
    //            + "id integer primary key autoincrement,"
    //            + "name varchar(20) not null,"
    //            + "tel_no varchar(11) not null,"
    //            + "cls_id integer not null"
    //            + ");";

    public SQLiteDbHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 在这里通过 db.execSQL 函数执行 SQL 语句创建所需要的表
        // 创建 picAddress 表
        sqLiteDatabase.execSQL(STUDENTS_CREATE_TABLE_SQL);
        // 注：数据库实际上是没被创建 / 打开的（因该方法还没调用）
        // 直到getWritableDatabase() / getReadableDatabase() 第一次被调用时才会进行创建 / 打开
    }

    /**
     * 复写onUpgrade（）
     * 调用时刻：当数据库升级时则自动调用（即 数据库版本 发生变化时）
     * 作用：更新数据库表结构
     * 注：创建SQLiteOpenHelper子类对象时,必须传入一个version参数，该参数 = 当前数据库版本, 若该版本高于之前版本, 就调用onUpgrade()
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
            case 1:
                // do something
                // 使用 SQL的ALTER语句
                //                String sql = "alter table person add sex varchar(8)";//语句用于在已有的表中添加、删除或修改列。
                //                sqLiteDatabase.execSQL(sql);
                break;

            default:
                break;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //        if (!db.isReadOnly()) {
        //            // 启动外键
        //            db.execSQL("PRAGMA foreign_keys = 1;");
        //
        //            //或者这样写
        //            String query = String.format("PRAGMA foreign_keys = %s", "ON");
        //            db.execSQL(query);
        //        }
    }

    @Override
    public synchronized void close() {
        super.close();

    }

    //        SQLiteDbHelper helper = new SQLiteDbHelper(mContext);
    //        SQLiteDatabase database = helper.getWritableDatabase();
    public void insert2DB(SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("name", "carson");
        database.insert(TABLE_PIC, null, values);

        //
        String sql = "insert into " + TABLE_PIC + " (id,address) values (1,'carson')";
        database.execSQL(sql);
    }

    public void updata2DB(SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put("address", "zhangsan");
        database.update(TABLE_PIC, values, "id=?", new String[]{"1"});

        //
        String sql = "updata [" + TABLE_PIC + "] set address = 'zhangsan' where id='1'";
        database.execSQL(sql);
    }

    public void delete2DB(SQLiteDatabase database) {
        database.delete(TABLE_PIC, "id=?", new String[]{"1"});

        //
        String sql = "delete from " + TABLE_PIC + " where id='1'";
        database.execSQL(sql);
    }

    public void rawQuery2DB(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("select * from " + TABLE_PIC + " where id='?'", new String[]{"1"});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int anInt = cursor.getInt(0);
            String string = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
    }
}
