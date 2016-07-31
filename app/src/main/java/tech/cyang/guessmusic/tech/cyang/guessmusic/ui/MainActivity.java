package tech.cyang.guessmusic.tech.cyang.guessmusic.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import tech.cyang.guessmusic.R;
import tech.cyang.guessmusic.tech.cyang.guessmusic.model.WordButton;
import tech.cyang.guessmusic.tech.cyang.guessmusic.myUi.MyGridView;

public class MainActivity extends AppCompatActivity {

    private Animation mPanAnim;
    private LinearInterpolator mPanLin;

    private Animation mBarInAnim;
    private LinearInterpolator mBarInLin;

    private Animation mBarOutAnim;
    private LinearInterpolator mBarOutLin;

    // 唱片控件
    private ImageView mViewPan;
    // 拨杆控件
    private ImageView mViewPanBar;

    // Play 按键事件
    private ImageButton mBtnPlayStart;

    // 当前动画是否正在运行
    private boolean mIsRunning = false;

    private ArrayList<WordButton> mAllWords;

    private MyGridView mMyGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnPlayStart = (ImageButton)findViewById(R.id.btn_play_start);
        mBtnPlayStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                handlePlayButton();
            }
        });

        //初始化控件
        mViewPan = (ImageView)findViewById(R.id.imageView1);
        mViewPanBar = (ImageView)findViewById(R.id.imageView2);

        mMyGridView = (MyGridView)findViewById(R.id.gridview);

        //初始化动画
        mPanAnim = AnimationUtils.loadAnimation(this,R.anim.rotate);
        mPanLin = new LinearInterpolator();
        mPanAnim.setInterpolator(mPanLin);
        mPanAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewPanBar.startAnimation(mBarOutAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mBarInAnim = AnimationUtils.loadAnimation(this,R.anim.rotate_45);
        mBarInLin = new LinearInterpolator();
        mBarInAnim.setFillAfter(true); //动画结束后保持在结束位置不复位
        mBarInAnim.setInterpolator(mBarInLin);
        mBarInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewPan.startAnimation(mPanAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        mBarOutAnim = AnimationUtils.loadAnimation(this,R.anim.rotate_d_45);
        mBarOutLin = new LinearInterpolator();
        mBarOutAnim.setFillAfter(true);//动画结束后保持在结束位置不复位
        mBarOutAnim.setInterpolator(mBarOutLin);
        mBarOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    mIsRunning = false;
                    mBtnPlayStart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //初始化游戏数据
        initCurrentStageData();
    }

    //播放按钮事件
    private void handlePlayButton() {
        if (mViewPanBar != null) {
            if (!mIsRunning) {
                mIsRunning = true;
                // 开始拨杆进入动画
                mViewPanBar.startAnimation(mBarInAnim);
                mBtnPlayStart.setVisibility(View.INVISIBLE);//隐藏播放按钮
            }
        }
    }

    @Override
    public void onPause() {
        mViewPan.clearAnimation();

        super.onPause();
    }


    private void initCurrentStageData(){
        mAllWords = initAllWord();
        mMyGridView.updateData(mAllWords);
    }

    //获取所有待选文字
    private ArrayList<WordButton> initAllWord(){
        ArrayList<WordButton> data = new ArrayList<WordButton>();

        for(int i = 0; i< MyGridView.COUNTS_WORDS; i++){
            WordButton button = new WordButton();
            button.mWordString = "好";
            data.add(button);
        }
        return data;
    }
}
