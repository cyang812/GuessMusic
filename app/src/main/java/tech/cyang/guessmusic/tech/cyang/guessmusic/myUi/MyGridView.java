package tech.cyang.guessmusic.tech.cyang.guessmusic.myUi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

import tech.cyang.guessmusic.R;
import tech.cyang.guessmusic.tech.cyang.guessmusic.model.IWordButtonClickListener;
import tech.cyang.guessmusic.tech.cyang.guessmusic.model.WordButton;
import tech.cyang.guessmusic.tech.cyang.guessmusic.util.Util;

/**
 * Created by cy101 on 2016/7/31.
 */

public class MyGridView extends GridView{

    public final static int COUNTS_WORDS = 24;

    private ArrayList<WordButton> mArrayList = new ArrayList<WordButton>();

    private MyGridAdpater myAdpater;

    private Context mContext;

    private Animation mScaleAnimation;

    private IWordButtonClickListener mWordButtonListener;

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        myAdpater = new MyGridAdpater();
        this.setAdapter(myAdpater);
    }

    public void updateData(ArrayList<WordButton> list){
        mArrayList = list;

        //重新设置数据源
        setAdapter(myAdpater);
    }

    class MyGridAdpater extends BaseAdapter{
        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return mArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final WordButton holder;

            if ( convertView == null){
                convertView = Util.getView(mContext, R.layout.self_ui_gridview_item);

                holder = mArrayList.get(position);

                //加载动画
                mScaleAnimation = AnimationUtils.loadAnimation(mContext,R.anim.scale);

                // 设置动画的延迟时间
                mScaleAnimation.setStartOffset(position * 100);


                holder.mIndex = position;
                holder.mViewButton = (Button)convertView.findViewById(R.id.item_btn);

                holder.mViewButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View arg0) {
                        mWordButtonListener.onWordButtonClick(holder);
                    }
                });

                convertView.setTag(holder);
            }else {
                holder = (WordButton)convertView.getTag();
            }

            holder.mViewButton.setText(holder.mWordString);

            // 播放动画
           // convertView.startAnimation(mScaleAnimation);
            return convertView;
        }
    }

    /**
     * 注册监听接口
     * @param listener
     */

    public void registOnWordButtonClick(IWordButtonClickListener listener){

        mWordButtonListener = listener;
    }
}
