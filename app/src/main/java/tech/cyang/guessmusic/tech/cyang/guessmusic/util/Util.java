package tech.cyang.guessmusic.tech.cyang.guessmusic.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by cy101 on 2016/7/31.
 */

//工具类，将方法定义为static，可直接调用，而不用创建其对象。

public class Util {

    public static View getView(Context context,int layoutId){
        LayoutInflater layoutInflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = layoutInflater.inflate(layoutId,null);

        return layout;
    }

    //实现activity跳转
    public static void startActivity(Context context,Class desti){
        Intent intent =new Intent();
        intent.setClass(context,desti);
        context.startActivity(intent);

        //关闭当前的activity
        ((Activity)context).finish();
    }

    //自定义的对话框
//    public static void showDialog(Context context,String message){
//
//    }
}
