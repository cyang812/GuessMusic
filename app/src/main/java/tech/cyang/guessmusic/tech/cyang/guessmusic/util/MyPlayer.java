package tech.cyang.guessmusic.tech.cyang.guessmusic.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * 音乐，音效播放
 * Created by cy101 on 2016/8/7.
 */

public class MyPlayer {

    public final static int INDEX_STONE__ENTER = 0;

    public final static int INDEX_STONE__CANCEL = 1;

    public final static int INDEX_STONE__COIN = 2;

    private final static String[] SONG_NAMES = {"enter.mp3","cancel.mp3","coin.mp3"};

    //音效
    private static MediaPlayer[] mToneMediaPlayer = new MediaPlayer[SONG_NAMES.length];

    private static MediaPlayer mMusicMediaPlayer;

    //播放音乐
    public static void playSong(Context context,String fileName){
        if (mMusicMediaPlayer == null){
            mMusicMediaPlayer = new MediaPlayer();
        }

        //强制重置
        mMusicMediaPlayer.reset();

        //加载声音文件
        AssetManager assetManager = context.getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(fileName);
            long startOffset = fileDescriptor.getStartOffset();
            mMusicMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),fileDescriptor.getLength());
            mMusicMediaPlayer.prepare();
            //播放声音
            mMusicMediaPlayer.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //停止播放
    public static void StopSong(Context context){
        if (mMusicMediaPlayer != null){
            mMusicMediaPlayer.stop();
        }
    }

    //播放音效
    public static void playTone(Context context,int index){
        //加载声音
        AssetManager assetManager = context.getAssets();

        if (mToneMediaPlayer[index] == null){
            mToneMediaPlayer[index] = new MediaPlayer();

            try {
                AssetFileDescriptor fileDescriptor = assetManager.openFd(SONG_NAMES[index]);

                mToneMediaPlayer[index].setDataSource(fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                mToneMediaPlayer[index].prepare();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        mToneMediaPlayer[index].start();
    }
}
