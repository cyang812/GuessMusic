package tech.cyang.guessmusic.tech.cyang.guessmusic.model;

import android.widget.Button;

/**
 * Created by cy101 on 2016/7/31.
 */
public class WordButton {

    public int mIndex;
    public boolean mIsVisiable;
    public String mWordString;

    public Button mViewButton;

    public WordButton() {
        mIsVisiable = true;
        mWordString = "";
    }

}
