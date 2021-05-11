package com.example.diversify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.TransactionTooLargeException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

public class InvestmentOpportunitiesFragment extends Fragment {

    private Button mainScreenButton;
    private Button assetsButton;
    private LinearLayout linearLayoutSCV;

    public InvestmentOpportunitiesFragment() {
        // Required empty public constructor
    }


    public MainActivity mainActivity;
    public static InvestmentOpportunitiesFragment newInstance(MainActivity mainActivity) {
        InvestmentOpportunitiesFragment fragment = new InvestmentOpportunitiesFragment();
        fragment.mainActivity = mainActivity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateGui() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity == null) {
            return;
        }
        GameState gameState = mainActivity.getGameState();
        if (gameState == null || linearLayoutSCV == null) {
            return;
        }

        linearLayoutSCV.removeAllViews();
        for (Integer i: gameState.getOpportunities().getIdArrayList()) {
            Tradeable t = mainActivity.getGameState().getTradeablePool().get(i);
            assert t != null;
            Button but = new Button(getContext());
            but.setText(t.toStringInvestmentOpportunity());
            linearLayoutSCV.addView(but);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.setFragment(FragmentName.INVESTMENT_DETAILS, Integer.toString(i));
                }
            });
        }
    }


    //////////////////// BIG ON CREATE VIEW /////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View toBeReturned = inflater.inflate(R.layout.fragment_investment_opportunities, container, false);

        ////////////////////// CREATING BUTTONS ///////////////////////////////
        this.mainScreenButton = (Button) toBeReturned.findViewById(R.id.Opportunities_mainScreenButton);
        this.assetsButton = (Button) toBeReturned.findViewById(R.id.Opportunities_assetsButton);
        this.linearLayoutSCV = (LinearLayout) toBeReturned.findViewById(R.id.Opportunities_LinearLayoutInsideScrollView);
        ////////////////////////////////////////////////////////////////////////

        ////////////////////// ADDING ASSETS TO LINEAR LAYOUT //////////////////////

        ////// Creating the view /////////
        mainActivity.updateGui();


        //////////////////// SETTING ON CLICK VIEWS //////////////////////////
        mainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setFragment(FragmentName.MAIN_GAME_SCREEN, null);
            }
        });

        assetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setFragment(FragmentName.ASSETS, null);
            }
        });
        return toBeReturned;
    }
}