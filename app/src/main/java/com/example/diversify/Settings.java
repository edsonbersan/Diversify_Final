package com.example.diversify;

public class Settings
{
    private int volumeLevel;
    private boolean musicEnabled;

    public Settings()
    {
        volumeLevel = 50;
        musicEnabled = true;
    }

    public int getVolumeLevel()
    {
        return volumeLevel;
    }

    public void setVolumeLevel(int volumeLevel)
    {
        this.volumeLevel = volumeLevel;
    }

    public boolean isMusicEnabled()
    {
        return musicEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled)
    {
        this.musicEnabled = musicEnabled;
    }


}
