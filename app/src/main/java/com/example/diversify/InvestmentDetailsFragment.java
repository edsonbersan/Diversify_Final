package com.example.diversify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class InvestmentDetailsFragment extends Fragment {

    public Tradeable tradeable;
    public int tradeableID;
    public MainActivity mainActivity;

    /////////////////////////// BUTTONS AND OTHER VIEWS ///////////////////////////
    private Button backButton;
    private Button buyButton;
    private Button sellButton;

    private TextView typeText;
    private TextView nameText;
    private TextView dateText;
    private TextView returnText;
    private TextView volatilityText;
    private TextView PLText;
    private TextView ownedQuantityText;

    private EditText quantityEditText;
    ////////////////////////////////////////////////////////////////////////////


    public InvestmentDetailsFragment() {
        // Required empty public constructor
    }


    public static InvestmentDetailsFragment newInstance(MainActivity mainActivity, int id) {
        InvestmentDetailsFragment fragment = new InvestmentDetailsFragment();
        fragment.tradeableID = id;
        fragment.mainActivity = mainActivity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateGui() {
        // DO NOTHING...
        nameText.setText(tradeable.getName());
        dateText.setText(tradeable.getDatePurchased());
        returnText.setText(tradeable.getRateOfReturnString());
        volatilityText.setText(tradeable.getVolatilityString());
        PLText.setText(tradeable.getPLString());
        ownedQuantityText.setText(Integer.toString(tradeable.getQuantity()));

        typeText.setText(tradeable.getTypeString() + " Details");
    }

    ///////////////////////// BIG "ON CREATE VIEW" ///////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View toBeReturned = inflater.inflate(R.layout.fragment_investment_details, container, false);

        ////////////////////// CREATING BUTTONS AND TEXT VIEWS ///////////////////////////////
        this.backButton = (Button) toBeReturned.findViewById(R.id.Details_back);
        this.buyButton = (Button) toBeReturned.findViewById(R.id.Details_Buy);
        this.sellButton = (Button) toBeReturned.findViewById(R.id.Details_sell);

        this.typeText = (TextView) toBeReturned.findViewById(R.id.Details_type);
        this.nameText = (TextView) toBeReturned.findViewById(R.id.Details_name);
        this.dateText = (TextView) toBeReturned.findViewById(R.id.Details_Date);
        this.returnText = (TextView) toBeReturned.findViewById(R.id.Details_return);
        this.volatilityText = (TextView) toBeReturned.findViewById(R.id.Details_volatility);
        this.PLText = (TextView) toBeReturned.findViewById(R.id.Details_PL);
        this.ownedQuantityText = (TextView) toBeReturned.findViewById(R.id.Details_QuantityOwned);

        this.quantityEditText = (EditText) toBeReturned.findViewById(R.id.Details_enterQuantity);
        ////////////////////////////////////////////////////////////////////////

        //////////////////////// Settings proper Text //////////////////////////////
        tradeable = mainActivity.getGameState().getTradeablePool().get(tradeableID);
        assert tradeable != null;
        mainActivity.updateGui();
        ////////////////////////////////////////////////////////////////////////////
        GameState gameState = (GameState) mainActivity.getGameState();
        //////////////////////// SETTING ON CLICKS /////////////////////////////////
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setFragment(FragmentName.INVESTMENT_OPPORTUNITIES, null);
            }
        });

        if (gameState.getOpportunities().getIdArrayList().contains(tradeable.getMyId())){ // If tradeble is buyable
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str = quantityEditText.getText().toString();
                    if (str.equals("")) {
                        return;
                    }
                    int quantity = Integer.parseInt(str);
                    Toast.makeText(getContext(), gameState.buyTradeble(tradeable, quantity), Toast.LENGTH_SHORT).show();
                    mainActivity.updateGui();
                }
            });
        }
        else {
            buyButton.setVisibility(View.INVISIBLE);
        }

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = quantityEditText.getText().toString();
                if (str.equals("")) {
                    return;
                }
                int quantity = Integer.parseInt(str);
                Toast.makeText(getContext(), gameState.sellTradeble(tradeable, quantity), Toast.LENGTH_SHORT).show();
                mainActivity.updateGui();
            }
        });

        return toBeReturned;
    } ////////////////////////// END ON CREATE VIEW /////////////////////////////
}