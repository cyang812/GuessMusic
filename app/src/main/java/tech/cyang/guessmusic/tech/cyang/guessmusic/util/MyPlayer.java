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

    private static MediaPlayer mMusicMediaPlayer;

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

    public static void StopSong(Context context){
        if (mMusicMediaPlayer != null){
            mMusicMediaPlayer.stop();
        }
    }
}
