package com.example.diversify;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainGameScreenFragment extends Fragment {

    ///////////////////////// BUTTONS AND OTHER VIEWS /////////////////////////////

    private Button assetsSpendingButton;
    private Button investButton;
    private Button workButton;
    private Button advanceTurnButton;
    private TextView netWorth;
    private TextView assetValue;
    ////////////////////////////////////////////////////////////////////////////////

    public MainGameScreenFragment() {
        // Required empty public constructor
    }

    public MainActivity mainActivity;
    public static MainGameScreenFragment newInstance(MainActivity mainActivity) {
        MainGameScreenFragment fragment = new MainGameScreenFragment();
        fragment.mainActivity = mainActivity;
        return fragment;
    }


    public void updateGui() {
        if (workButton != null) {
            if (mainActivity.isWorkButtonAvailable) {
                workButton.setText("Work!");
                workButton.setClickable(true);
            } else {
                workButton.setText("Work not available!");
                workButton.setClickable(false);
            }
        }
        double assets = getAssets();
        assetValue.setText("Total Assets: " + String.format("$%.2f", assets));
        netWorth.setText("\n\nNet Worth: " + String.format("$%.2f", getNetWorth(assets)));
    }

    public double getAssets()
    {
        Portfolio portfolio = mainActivity.getGameState().getFolio();
        double assets = 0.0;
        for (Integer i: portfolio.getInternalFolio())
        {
            assets += portfolio.tradablePool.get(i).getValue() * portfolio.tradablePool.get(i).getQuantity();
        }
        return assets;
    }

    public double getNetWorth(double assets)
    {
        return assets + mainActivity.getGameState().getCash();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View to be returned
        View toBeReturned = inflater.inflate(R.layout.fragment_main_game_screen, container, false);

        ////////////// Initializing Buttons and other views /////////////////////////
        this.assetsSpendingButton = (Button) toBeReturned.findViewById(R.id.assetsSpendingsButton);
        this.investButton = (Button) toBeReturned.findViewById(R.id.investButton);
        this.workButton = (Button) toBeReturned.findViewById(R.id.workButton);
        this.advanceTurnButton = (Button) toBeReturned.findViewById(R.id.advanceTurnButton);
        this.netWorth = (TextView) toBeReturned.findViewById(R.id.netWorth);
        this.assetValue = (TextView) toBeReturned.findViewById(R.id.assetValue);
        ////////////////////////////////////////////////////////////////

        mainActivity.updateGui();

        //////////////// Setting on Clicks ////////////////////////////
        assetsSpendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFragment(FragmentName.ASSETS, null);

            }
        });

        investButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFragment(FragmentName.INVESTMENT_OPPORTUNITIES, null);
            }
        });

        workButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainActivity.isWorkButtonAvailable) {
                    mainActivity.getGameState().earnOvertime();
                    mainActivity.isWorkButtonAvailable = false;
                    mainActivity.updateGui();
                }
            }
        });

        if (!MainActivity.IDLE_MODE) {
            advanceTurnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GameState gameState = mainActivity.getGameState();
                    gameState.simulateTurns(mainActivity, 1);
                    mainActivity.updateGui();
                }
            });
        }
        else {
            advanceTurnButton.setVisibility(View.INVISIBLE);
        }
        ///////////////////////////////////////// END SETTING ON CLICKS ///////////////////////////////////////////

        // Inflate the layout for this fragment
        return toBeReturned;
    } //////////////////////////////////////////// END ON CREATE VIEW //////////////////////////////////////////
}