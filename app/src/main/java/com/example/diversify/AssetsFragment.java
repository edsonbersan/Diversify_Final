package com.example.diversify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

public class AssetsFragment extends Fragment {


    private Button mainScreenButton;
    private LinearLayout linearLayoutSCV;

    public AssetsFragment() {
        // Required empty public constructor
    }

    public MainActivity mainActivity;
    public static AssetsFragment newInstance(MainActivity mainActivity) {
        AssetsFragment assetsFragment = new AssetsFragment();
        assetsFragment.mainActivity = mainActivity;
        return assetsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    public void updateGui() {
        if (mainActivity == null) {
            return;
        }
        GameState gameState = mainActivity.getGameState();
        if (gameState == null || linearLayoutSCV == null) {
            return;
        }
        linearLayoutSCV.removeAllViews();
        for (Integer i: gameState.getFolio().getInternalFolio()) {
            Tradeable t = mainActivity.getGameState().getTradeablePool().get(i);
            Button but = new Button(mainActivity.getApplicationContext());
            but.setText(t.toString());
            linearLayoutSCV.addView(but);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.setFragment(FragmentName.INVESTMENT_DETAILS, Integer.toString(i));
                }
            });
        }
    }

    ////////////////// BIG ON CREATE VIEW ////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View toBeReturned = inflater.inflate(R.layout.fragment_assets, container, false);

        ////////////////////// CREATING BUTTONS ///////////////////////////////
        this.mainScreenButton = (Button) toBeReturned.findViewById(R.id.Assets_mainScreenButton);
        this.linearLayoutSCV = (LinearLayout) toBeReturned.findViewById(R.id.Assets_LinearLayoutInsideScrollView);
        ////////////////////////////////////////////////////////////////////////

        ////////////////////// ADDING ASSETS TO LINEAR LAYOUT //////////////////////
        mainActivity.updateGui();

        //////////////////// SETTING ON CLICK VIEWS //////////////////////////
        mainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MainActivity mainActivity = (MainActivity) getActivity();
               mainActivity.setFragment(FragmentName.MAIN_GAME_SCREEN, null);
            }
        });



        return toBeReturned;
    }
}