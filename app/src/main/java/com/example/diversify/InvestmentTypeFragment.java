package com.example.diversify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvestmentTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvestmentTypeFragment extends Fragment {

    public InvestmentType investmentType;

    public InvestmentTypeFragment() {
        // Required empty public constructor
    }

    public MainActivity mainActivity;
    public static InvestmentTypeFragment newInstance(MainActivity mainActivity, String investmentTypeEnum) {
        InvestmentTypeFragment fragment = new InvestmentTypeFragment();
        fragment.mainActivity = mainActivity;
        fragment.investmentType = new Gson().fromJson(investmentTypeEnum, InvestmentType.class);
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
        return inflater.inflate(R.layout.fragment_investment_type, container, false);
    }
}