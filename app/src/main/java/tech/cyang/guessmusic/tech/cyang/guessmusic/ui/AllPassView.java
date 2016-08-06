package tech.cyang.guessmusic.tech.cyang.guessmusic.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import tech.cyang.guessmusic.R;

/**
 * Created by cy101 on 2016/8/6.
 */

//通过界面

public class AllPassView extends Activity{
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.all_pass_view);

        //隐藏右上角的金币
        FrameLayout view = (FrameLayout)findViewById(R.id.layout_bar_coin);
        view.setVisibility(View.INVISIBLE);
    }
}
