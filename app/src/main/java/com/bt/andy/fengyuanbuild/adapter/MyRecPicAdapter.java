package com.bt.andy.fengyuanbuild.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bt.andy.fengyuanbuild.R;
import com.bt.andy.fengyuanbuild.utils.GlideLoaderUtil;

import java.io.File;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/15 16:49
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyRecPicAdapter extends RecyclerView.Adapter<MyRecPicAdapter.ViewHolder> {
    private Context mContext;
    private List    mData;
    private static final int IMAGE     = 1;//调用系统相册-选择图片
    private static final int SHOT_CODE = 20;//调用系统相册-选择图片
    private boolean mIschecked;

    public MyRecPicAdapter(Context context, List data, boolean isChecked) {
        this.mContext = context;
        this.mData = data;
        this.mIschecked = isChecked;
    }

    @Override
    public MyRecPicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_recy_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;
    private String mFilePath;

    @Override
    public void onBindViewHolder(final MyRecPicAdapter.ViewHolder holder, final int position) {
        //被审核过的图片不显示删除按钮
        if (mIschecked) {
            if (position <= mData.size() - 1) {
                holder.img_delet.setVisibility(View.GONE);
            } else {
                holder.img_delet.setVisibility(View.VISIBLE);
            }
        }
        if (position == 0) {
            //第一个条目不显示删除按键
            holder.img_delet.setVisibility(View.GONE);
            holder.img_photo.setImageBitmap((Bitmap) mData.get(position));
            // 设置点击事件添加图片
            holder.img_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出popupWindow，选择拍摄还是从相册选取
                    final PopupWindow popupWindow = new PopupWindow(mContext);
                    popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setContentView(LayoutInflater.from(mContext).inflate(R.layout.popup_shot_type, null));
                    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(false);
                    //设置动画
                    popupWindow.setAnimationStyle(R.style.PopupWindow);
                    //设置位置
                    popupWindow.showAtLocation(holder.img_photo, Gravity.BOTTOM, 0, 0);
                    //设置消失监听
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            //设置背景色
                            setBackgroundAlpha(1.0f);
                        }
                    });
                    //设置背景色
                    setBackgroundAlpha(0.5f);

                    TextView tv_choose = popupWindow.getContentView().findViewById(R.id.tv_choose);
                    TextView tv_shot = popupWindow.getContentView().findViewById(R.id.tv_shot);
                    //选择相册
                    tv_choose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //第二个参数是需要申请的权限
                            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //权限还没有授予，需要在这里写申请权限的代码
                                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                            } else {
                                //权限已经被授予，在这里直接写要执行的相应方法即可
                                //调用相册
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                Activity activity = (Activity) mContext;
                                activity.startActivityForResult(intent, IMAGE);
                                popupWindow.dismiss();
                            }
                        }
                    });
                    //拍摄图片
                    tv_shot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //第二个参数是需要申请的权限
                            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //权限还没有授予，需要在这里写申请权限的代码
                                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                            } else {
                                String filePath = Environment.getExternalStorageDirectory().getPath();// 获取SD卡路径
                                long photoTime = System.currentTimeMillis();
                                filePath = filePath + "/temp" + photoTime + ".jpg";// 指定路径
                                //权限已经被授予，在这里直接写要执行的相应方法即可
                                //调用相机
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                Uri photoUri = Uri.fromFile(new File(filePath)); // 传递路径
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径

                                //把指定路径传递给需保存的字段
                                mFilePath = filePath;
                                ((Activity) mContext).startActivityForResult(intent, SHOT_CODE);
                                popupWindow.dismiss();
                            }
                        }
                    });
                }
            });
        } else {
            Object o = mData.get(position);
            if (null != o) {
                if (o instanceof Bitmap) {
                    holder.img_photo.setImageBitmap((Bitmap) o);
                } else if (o instanceof String) {
                    GlideLoaderUtil.showImageView(mContext, (String) o, holder.img_photo);
                }
            }
            holder.img_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击展示

                }
            });

            //            holder.img_add_photo.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View view) {
            //                    final ArrayList markList = new ArrayList<>();
            //                    markList.addAll(mData);
            //                    markList.remove(0);
            //                    //弹出popupwindow展示照片
            //                    final PopupWindow popupWindow = new PopupWindow(mContext);
            //                    popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            //                    popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            //                    popupWindow.setContentView(LayoutInflater.from(mContext).inflate(R.layout.dialog_photo_view, null));
            //                    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            //                    popupWindow.setOutsideTouchable(false);
            //                    popupWindow.setFocusable(true);
            //                    //显示popupwindow,并指定位置
            //                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            //                    //找到pic展示条目
            //                    ViewPager viewpager = popupWindow.getContentView().findViewById(R.id.viewpager);
            //                    final TextView tv_title = popupWindow.getContentView().findViewById(R.id.tv_title);
            //                    MyViewPagerAdapter viewPagerAdapter;
            //                    viewPagerAdapter = new MyViewPagerAdapter(mContext, markList, popupWindow);
            //                    viewpager.setAdapter(viewPagerAdapter);
            //                    viewpager.setCurrentItem(position - 1);
            //                    tv_title.setText(position + "/" + markList.size());
            //                    viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //                        @Override
            //                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //
            //                        }
            //
            //                        @Override
            //                        public void onPageSelected(int position) {
            //                            tv_title.setText((position + 1) + "/" + markList.size());
            //                        }
            //
            //                        @Override
            //                        public void onPageScrollStateChanged(int state) {
            //
            //                        }
            //                    });
            //                }
            //            });
        }

        holder.img_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = alpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public String getFilePath() {
        return mFilePath;
    }

    @Override
    public int getItemCount() {
        //展示条目数
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_photo;
        ImageView img_delet;

        public ViewHolder(View itemView) {
            super(itemView);
            img_photo = (ImageView) itemView.findViewById(R.id.img_add_photo);
            img_delet = (ImageView) itemView.findViewById(R.id.img_delet);
        }
    }
}
