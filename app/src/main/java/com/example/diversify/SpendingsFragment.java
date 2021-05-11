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

public class SpendingsFragment extends Fragment {

    private Button mainScreenButton;
    private Button assetsButton;
    private LinearLayout linearLayoutSCV;

    public SpendingsFragment() {
        // Required empty public constructor

    }

    public MainActivity mainActivity;
    public static SpendingsFragment newInstance(MainActivity mainActivity) {
        SpendingsFragment fragment = new SpendingsFragment();
        fragment.mainActivity = mainActivity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View toBeReturned = inflater.inflate(R.layout.fragment_spendings, container, false);

        ////////////////////// CREATING BUTTONS ///////////////////////////////
        this.mainScreenButton = (Button) toBeReturned.findViewById(R.id.Spendings_mainScreenButton);
        this.assetsButton = (Button) toBeReturned.findViewById(R.id.Spendings_assetsButton);
        this.linearLayoutSCV = (LinearLayout) toBeReturned.findViewById(R.id.Spendings_LinearLayoutInsideScrollView);
        ////////////////////////////////////////////////////////////////////////

        ////////////////////// ADDING ASSETS TO LINEAR LAYOUT //////////////////////
        MainActivity mainActivity = (MainActivity) getActivity();
        GameState gameState = mainActivity.getGameState();
        Gson gson = new Gson();

//        ///////////////// TODO: For Spending in Spendings ///////////////////
//        for (Tradeable t: gameState.getOpportunities().getTradebleList()) {
//            Button but = new Button(getContext());
//            but.setText(t.toStringInvestmentDetail());
//            linearLayoutSCV.addView(but);
//            but.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mainActivity.setFragment(FragmentName.INVESTMENT_DETAILS, Integer.toString(i));
//                }
//            });
//        }




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