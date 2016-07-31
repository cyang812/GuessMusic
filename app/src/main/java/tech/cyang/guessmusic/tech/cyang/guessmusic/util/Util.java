package tech.cyang.guessmusic.tech.cyang.guessmusic.util;

import android.content.Context;
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

}
