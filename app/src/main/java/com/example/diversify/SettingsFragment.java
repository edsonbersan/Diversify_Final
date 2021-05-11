package com.example.diversify;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.google.android.material.slider.Slider;

import java.util.Objects;

public class SettingsFragment extends Fragment
{
    private Button credits;
    private Button instructions;
    private Button main;
    private SeekBar volume;
    private Switch music;
    private Button startNewGame;
    private Settings settings;

    public SettingsFragment()
    {

    }

    public MainActivity mainActivity;
    public static SettingsFragment newInstance(MainActivity mainActivity) {
        SettingsFragment fragment = new SettingsFragment();
        fragment.mainActivity = mainActivity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View toBeReturned = inflater.inflate(R.layout.fragment_settings, container, false);
        startNewGame = toBeReturned.findViewById(R.id.startNewGame);
        credits = toBeReturned.findViewById(R.id.Credits);
        instructions = toBeReturned.findViewById(R.id.Instructions);
        main = toBeReturned.findViewById(R.id.MainScreen);
        volume = toBeReturned.findViewById(R.id.VolumeSlider);
        music = toBeReturned.findViewById(R.id.Music);
        settings = mainActivity.getGameState().getSettings();
        music.setChecked(settings.isMusicEnabled());
        volume.setProgress(settings.getVolumeLevel());

        startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setFragment(FragmentName.MAIN_GAME_SCREEN, null);
                mainActivity.startNewGame();
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setFragment(FragmentName.CREDITS, null);
            }
        });
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setFragment(FragmentName.INSTRUCTIONS, null);
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setFragment(FragmentName.MAIN_GAME_SCREEN, null);
            }
        });
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                settings.setVolumeLevel(progress);
                mainActivity.setVolumeLevel(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    settings.setMusicEnabled(true);
                    mainActivity.startMusic();
                }
                else
                {
                    settings.setMusicEnabled(false);
                    mainActivity.stopMusic();
                }
            }
        });

        return toBeReturned;
    }
}