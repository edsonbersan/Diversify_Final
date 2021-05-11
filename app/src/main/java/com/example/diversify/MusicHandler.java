package com.example.diversify;

import android.app.Activity;
import android.media.MediaPlayer;

public class MusicHandler
{
    private MediaPlayer mediaPlayer;
    private MainActivity mainActivity;
    private float volume;

    public MusicHandler(MainActivity mainActivity) {
       this.mainActivity = mainActivity;
       mediaPlayer = MediaPlayer.create(mainActivity.getApplicationContext(), R.raw.moneymoney);
       mediaPlayer.setLooping(true);
    }

    public void start()
    {
        mediaPlayer = MediaPlayer.create(mainActivity.getApplicationContext(), R.raw.moneymoney);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.start();
    }

    public void stop()
    {
        mediaPlayer.release();
    }

    public void setVolume(int volumeLevel)
    {
        volume = ((float)volumeLevel)/100;
        mediaPlayer.setVolume(volume, volume);
    }

}
