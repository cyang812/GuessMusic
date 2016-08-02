package tech.cyang.guessmusic.tech.cyang.guessmusic.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import tech.cyang.guessmusic.R;
import tech.cyang.guessmusic.tech.cyang.guessmusic.data.Const;
import tech.cyang.guessmusic.tech.cyang.guessmusic.model.IWordButtonClickListener;
import tech.cyang.guessmusic.tech.cyang.guessmusic.model.Song;
import tech.cyang.guessmusic.tech.cyang.guessmusic.model.WordButton;
import tech.cyang.guessmusic.tech.cyang.guessmusic.myUi.MyGridView;
import tech.cyang.guessmusic.tech.cyang.guessmusic.util.MyLog;
import tech.cyang.guessmusic.tech.cyang.guessmusic.util.Util;

public class MainActivity extends AppCompatActivity implements IWordButtonClickListener{

    public final static String TAG = "MainActivity";

    /** 答案真确 */
    public final static int STATUS_ANSWER_RIGHT = 1;
    /** 答案错误 */
    public final static int STATUS_ANSWER_WRONG = 2;
    /** 答案不完整 */
    public final static int STATUS_ANSWER_LACK = 3;



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

    private ArrayList<WordButton> mBtnSelectWords;

    //已选择文字款UI容器
    private LinearLayout mViewsWordsContainer;

    private MyGridView mMyGridView;

    // 当前的歌曲
    private Song mCurrentSong;

    // 当前关的索引
    private int mCurrentStageIndex = -1;

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

        //初始化视图
        mViewsWordsContainer =(LinearLayout)findViewById(R.id.word_select_container);
        mMyGridView = (MyGridView)findViewById(R.id.gridview);

        mMyGridView.registOnWordButtonClick(this);

        //初始化控件
        mViewPan = (ImageView)findViewById(R.id.imageView1);
        mViewPanBar = (ImageView)findViewById(R.id.imageView2);


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

    @Override
    public void onWordButtonClick(WordButton wordButton){
        Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
        setSelectWord(wordButton);
        //检测答案
        int checkResult = checkTheAnswer();
        if (checkResult == STATUS_ANSWER_RIGHT){
            //过关获得奖励

        }
        else if (checkResult == STATUS_ANSWER_WRONG){
            //错误提示

        }else if (checkResult == STATUS_ANSWER_LACK){
            
        }
    }


    private void clearTheAnswer(WordButton wordButton) {
        wordButton.mViewButton.setText("");
        wordButton.mWordString = "";
        wordButton.mIsVisiable = false;

        // 设置待选框可见性
        setButtonVisiable(mAllWords.get(wordButton.mIndex), View.VISIBLE);
    }

    /**
     * 设置答案
     *
     * @param wordButton
     */
    private void setSelectWord(WordButton wordButton) {
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            if (mBtnSelectWords.get(i).mWordString.length() == 0) {
                // 设置答案文字框内容及可见性
                mBtnSelectWords.get(i).mViewButton.setText(wordButton.mWordString);
                mBtnSelectWords.get(i).mIsVisiable = true;
                mBtnSelectWords.get(i).mWordString = wordButton.mWordString;
                // 记录索引
                mBtnSelectWords.get(i).mIndex = wordButton.mIndex;

                MyLog.d("TAG", mBtnSelectWords.get(i).mIndex + "");

                // 设置待选框可见性
                setButtonVisiable(wordButton, View.INVISIBLE);

                break;
            }
        }
    }

    /**
     * 设置待选文字框是否可见
     *
     * @param button
     * @param visibility
     */
    private void setButtonVisiable(WordButton button, int visibility) {
        button.mViewButton.setVisibility(visibility);
        button.mIsVisiable = (visibility == View.VISIBLE) ? true : false;

        MyLog.d(TAG, button.mIsVisiable + "");
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

    //读取当前关的歌曲信息
    private Song loadStageSongInfo(int stageIndex){
        Song song = new Song();

        String[] stage = Const.SONG_INFO[stageIndex];
        song.setSongFileName(stage[Const.INDEX_FILE_NAME]);
        song.setSongName(stage[Const.INDEX_SONG_NAME]);

        return song;
    }


    private void initCurrentStageData(){
        //读取当前关的歌曲信息
        mCurrentSong = loadStageSongInfo(++mCurrentStageIndex);

        //初始化已选择框
        mBtnSelectWords = initWordSelect();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(140,140);
        for(int i=0;i<mCurrentSong.getNameLength();i++){
            mViewsWordsContainer.addView(mBtnSelectWords.get(i).mViewButton,params);
        }

        //获得文字
        mAllWords = initAllWord();
        //更新文字
        mMyGridView.updateData(mAllWords);
    }

    //初始化文字待选框
    private ArrayList<WordButton> initAllWord(){
        ArrayList<WordButton> data = new ArrayList<WordButton>();

        //获取待选文字
        String[] words = generateWords();

        for(int i = 0; i< MyGridView.COUNTS_WORDS; i++){
            WordButton button = new WordButton();
            button.mWordString = words[i];
            data.add(button);
        }
        return data;
    }


    private ArrayList<WordButton> initWordSelect(){
        ArrayList<WordButton> data = new ArrayList<WordButton>();

        for(int i = 0; i<mCurrentSong.getNameLength();i++){
            View view = Util.getView(MainActivity.this,R.layout.self_ui_gridview_item);

            final WordButton holder = new WordButton();
            holder.mViewButton = (Button)view.findViewById(R.id.item_btn);
            holder.mViewButton.setText("");
            holder.mViewButton.setTextColor(Color.WHITE);
            holder.mIsVisiable = false;

            holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
            holder.mViewButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0){
                    clearTheAnswer(holder);
                }
            });

            data.add(holder);
        }

        return data;
    }

    /**
     * 生成所有的待选文字
     *
     * @return
     */
    private String[] generateWords() {
        Random random = new Random();

        String[] words = new String[MyGridView.COUNTS_WORDS];

        // 存入歌名
        for (int i = 0; i < mCurrentSong.getNameLength(); i++) {
            words[i] = mCurrentSong.getNameCharacters()[i] + "";
        }

        // 获取随机文字并存入数组
        for (int i = mCurrentSong.getNameLength();
             i < MyGridView.COUNTS_WORDS; i++) {
            words[i] = getRandomChar() + "";
        }

        // 打乱文字顺序：首先从所有元素中随机选取一个与第一个元素进行交换，
        // 然后在第二个之后选择一个元素与第二个交换，知道最后一个元素。
        // 这样能够确保每个元素在每个位置的概率都是1/n。
        for (int i = MyGridView.COUNTS_WORDS - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);

            String buf = words[index];
            words[index] = words[i];
            words[i] = buf;
        }

        return words;
    }

    /**
     * 生成随机汉字
     *
     * @return
     */
    private char getRandomChar() {
        String str = "";
        int hightPos;
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str.charAt(0);
    }

    /**
     * 检查答案
     *
     */
    private int checkTheAnswer(){
        //先检车长度
        for (int i = 0; i < mBtnSelectWords.size();i++){
            //如果有空的，说明答案不完整
            if (mBtnSelectWords.get(i).mWordString.length() == 0){
                return STATUS_ANSWER_LACK;
            }
        }

        //答案完整，再比对文字
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i<mBtnSelectWords.size(); i++){
            sb.append(mBtnSelectWords.get(i).mWordString);
        }

        return (sb.toString().equals(mCurrentSong.getSongName()))?
                STATUS_ANSWER_RIGHT:STATUS_ANSWER_WRONG;
    }
}
