package com.example.diversify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditsFragment extends Fragment {

    private Button mainScreenButton;
    public MainActivity mainActivity;

    public CreditsFragment() {
        // Required empty public constructor
    }

    public static CreditsFragment newInstance() {
        return new CreditsFragment();
    }

    public static CreditsFragment newInstance(MainActivity mainActivity) {
        CreditsFragment creditsFragment = new CreditsFragment();
        creditsFragment.mainActivity = mainActivity;
        return creditsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View toBeReturned = inflater.inflate(R.layout.fragment_credits, container, false);

        this.mainScreenButton = (Button) toBeReturned.findViewById(R.id.buttonMain);

        mainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                Objects.requireNonNull(mainActivity).setFragment(FragmentName.MAIN_GAME_SCREEN, null);
            }
        });

        return toBeReturned;
    }
}