package tech.cyang.guessmusic.tech.cyang.guessmusic.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import tech.cyang.guessmusic.R;
import tech.cyang.guessmusic.tech.cyang.guessmusic.data.Const;
import tech.cyang.guessmusic.tech.cyang.guessmusic.model.IAlertDialogButtonListener;

/**
 * Created by cy101 on 2016/7/31.
 */

//工具类，将方法定义为static，可直接调用，而不用创建其对象。

public class Util {


    public static AlertDialog mAlertDialog;

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

    //显示自定义的对话框
    public static void showDialog(final Context context, String message,
                                  final IAlertDialogButtonListener listener){

        View dialogView = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Theme_Transparent);
        dialogView = getView(context, R.layout.dialog_view);

        ImageButton btnOkView = (ImageButton) dialogView.findViewById(R.id.btn_dialog_ok);
        ImageButton btnCancleView  = (ImageButton)dialogView.findViewById(R.id.btn_dialog_cancel);
        TextView textMessageView = (TextView)dialogView.findViewById(R.id.text_dialog_message);

        textMessageView.setText(message);

        //设置点击确定事件
        btnOkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭对话框
                if (mAlertDialog != null){
                    mAlertDialog.cancel();
                }

                //事件回调
                if (listener != null) {
                    listener.onClick();
                }

                //播放音效
                MyPlayer.playTone(context,MyPlayer.INDEX_STONE__ENTER);
            }
        });

        //设置点击取消事件
        btnCancleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭对话框
                if (mAlertDialog != null) {
                    mAlertDialog.cancel();
                }

                //播放音效
                MyPlayer.playTone(context,MyPlayer.INDEX_STONE__CANCEL);
            }
        });

        //为Dialog设置View
        builder.setView(dialogView);
        mAlertDialog = builder.create();

        //显示对话框
        mAlertDialog.show();
    }

    //游戏数据保存
    public static void saveData(Context context,int stageIndex,int coins){

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = context.openFileOutput(Const.FILE_NAME_SAVE_DATA, Context.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fileOutputStream);

            dos.writeInt(stageIndex);
            dos.writeInt(coins);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //游戏数据读取
    public static int[] loadData(Context context){
        FileInputStream fileInputStream = null;

        int[] datas = {-1,Const.TOTAL_COINS};

        try {
            fileInputStream = context.openFileInput(Const.FILE_NAME_SAVE_DATA);
            DataInputStream dis = new DataInputStream(fileInputStream);

            datas[Const.INDEX_LOAD_DATA_STAGE] = dis.readInt();
            datas[Const.INDEX_LOAD_DATA_COINS] = dis.readInt();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch(IOException e){
                e.printStackTrace();
                }
            }
        }
        return datas;
    }
}
