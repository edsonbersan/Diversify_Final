package com.example.diversify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;



/**
 * Notes:
 * To implement TimerTask
 * Sources used: https://examples.javacodegeeks.com/android/core/activity/android-timertask-example/
 * To implement lot of the rest of the code:
 * Thanks a lot, "Coding in Flow"!
 * This is his channel: https://www.youtube.com/channel/UC_Fh8kvtkVPkeihBs42jGcA
 */

public class MainActivity extends AppCompatActivity {

    ///////////////////// FRAGMENTS /////////
    private MainGameScreenFragment mainGameScreenFragment;
    private InvestmentTypeFragment investmentTypeFragment;
    private InvestmentDetailsFragment investmentDetailsFragment;
    private InvestmentOpportunitiesFragment investmentOpportunitiesFragment;
    private AssetsFragment assetsFragment;
    private SpendingsFragment spendingsFragment;
    private SettingsFragment settingsFragment;
    private InstructionsFragment instructionsFragment;
    private CreditsFragment creditsFragment;
    //////////////////////////////////////////////////////

    //////////////////////// VIEWS ///////////////////////
    private TextView turnsTextView;
    private ImageView settingsImageView;
    private TextView cashTextView;
    ////////////////////////////////////////////////////

    ////////////////// GAME STATE ///////////////////////
    private GameState gameState;
    private static final String SAVE_FILE = "savegame.txt";
    private static final String TRADEABLE_FILE = "tradeable.txt";
    private FragmentName fragmentName;
    public boolean isWorkButtonAvailable;


    ///////////////// IDLE MODE /////////////////////////
    public static final boolean IDLE_MODE = true;
    private Handler handler = new Handler();

    //////////////// MUSIC //////////////////////////////
    private MusicHandler musicHandler;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); // Remove Top bar
        setContentView(R.layout.activity_main);

        //////////////// Mapping fields to views //////////////////////
        turnsTextView = findViewById(R.id.turnTextView);
        settingsImageView = findViewById(R.id.settingsImageView);
        cashTextView = findViewById(R.id.cashTextView);
        ///////////////////////////////////////////////////////////////

        // Loading Saved game
        gameState = loadGame();
        if (gameState == null) {
            gameState = new GameState();
        }
        else {
            gameState.updateTradeablePool(loadTradeablePool());
            int loginTime = (int) (Calendar.getInstance().getTimeInMillis()/1000);
            if (loginTime - gameState.getSaveTime() > 0)
            {
                gameState.simulateTurns(MainActivity.this, loginTime - gameState.getSaveTime());
                MainActivity.this.updateGui();
                saveGame();

            }
        }


//        // IMPORTANT: Uncomment to start a new game every time we open the aapp
//         this.gameState = new GameState();


        //////////////// Setting Values and OnClicks ///////////////////
        updateGui();
        isWorkButtonAvailable = gameState.getTurnObject().isOvertimeAvailable();

        settingsImageView.setClickable(true);
        settingsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(FragmentName.SETTINGS, null);
            }
        });


        setFragment(FragmentName.MAIN_GAME_SCREEN, null);

        musicHandler = new MusicHandler(this);
        musicHandler.setVolume(gameState.getSettings().getVolumeLevel());
        if (gameState.getSettings().isMusicEnabled())
        {
            musicHandler.start();
        }

        if (IDLE_MODE) {
            handler.postDelayed(idleRunnable, 1000 * Turn.REFRESH_PERIOD_IN_SECONDS);
        }

        //////////////// SETTING UP IDLE ///////////////////////

    }

    private Runnable idleRunnable = new Runnable() {
        @Override
        public void run() {
            gameState.simulateTurns(MainActivity.this, 1);
            MainActivity.this.updateGui();
            handler.postDelayed(this, 1000 * Turn.REFRESH_PERIOD_IN_SECONDS);
            saveGame();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameState != null) {
            saveGame();
        }

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void updateGui() {
        turnsTextView.setText(Long.toString(gameState.getTurn()));
        gameState.getFolio().setTurn(gameState.getTurn());
        double cash = gameState.getFolio().getCash();
        cashTextView.setText(String.format("$%.2f", cash));


        if (this.fragmentName == null) {
            return;
        }

        switch (this.fragmentName) {
            case MAIN_GAME_SCREEN:
                mainGameScreenFragment.updateGui();
                break;
            case INVESTMENT_DETAILS:
                investmentDetailsFragment.updateGui();
                break;
            case INVESTMENT_OPPORTUNITIES:
                investmentOpportunitiesFragment.updateGui();
                break;
            case INVESTMENT_TYPE:
                break;
            case ASSETS:
                assetsFragment.updateGui();
                break;
            case SPENDINGS:
                break;
            case CREDITS:
                break;
            case SETTINGS:
                break;
            case INSTRUCTIONS:
                break;
        }
    }


    /**
     * Shamelessly copied from https://codinginflow.com/tutorials/android/write-text-file-to-internal-storage
     */
    public void saveGame() {
        saveTradeblePool();
        gameState.saveTime();
        Gson gson = new Gson();
        String writeToFile = gson.toJson(gameState);
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(SAVE_FILE, MODE_PRIVATE);
            fos.write(writeToFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Shamelessly copied from https://codinginflow.com/tutorials/android/write-text-file-to-internal-storage
     * @return
     */
    public GameState loadGame() {
        GameState toBeReturned = null;
        FileInputStream fis = null;
        try {
            fis = openFileInput(SAVE_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            Gson gson = new Gson();
            toBeReturned =  (GameState) gson.fromJson(sb.toString(), GameState.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return toBeReturned;
    }

    /**
     * Shamelessly copied from https://codinginflow.com/tutorials/android/write-text-file-to-internal-storage
     * @return
     */
    public HashMap<Integer, Tradeable> loadTradeablePool() {
        HashMap<Integer, Tradeable> toBeReturned = null;
        FileInputStream fis = null;
        try {
            fis = openFileInput(TRADEABLE_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            Gson gson = new Gson();
            Type typeToken = new TypeToken<HashMap<Integer, Tradeable>>() {}.getType();

            toBeReturned = gson.fromJson(sb.toString(), typeToken);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return toBeReturned;
    }


    /**
     * Shamelessly copied from https://codinginflow.com/tutorials/android/write-text-file-to-internal-storage
     */
    public void saveTradeblePool() {
        Gson gson = new Gson();
        String writeToFile = gson.toJson(gameState.getTradeablePool());
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(TRADEABLE_FILE, MODE_PRIVATE);
            fos.write(writeToFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setFragment(FragmentName fragmentName, String param) {
        saveGame();
        this.fragmentName = fragmentName;
        switch (fragmentName) {
            case MAIN_GAME_SCREEN: // Here, param does not matter
                this.mainGameScreenFragment = MainGameScreenFragment.newInstance(this);
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivityFragment, mainGameScreenFragment, null).commit();
                break;
            case INVESTMENT_DETAILS: // Here, param is the id of a tradeble
                assert(param != null);
                investmentDetailsFragment = InvestmentDetailsFragment.newInstance(this, Integer.parseInt(param));
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivityFragment, investmentDetailsFragment, null).commit();
                break;
            case INVESTMENT_OPPORTUNITIES: // Here, param does not matter
                investmentOpportunitiesFragment = InvestmentOpportunitiesFragment.newInstance(this);
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivityFragment, investmentOpportunitiesFragment, null).commit();
                break;
            case INVESTMENT_TYPE: // Here, param is a string corresponding to InvestmentTypes enum
                investmentTypeFragment = InvestmentTypeFragment.newInstance(this, param);
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivityFragment, investmentTypeFragment, null).commit();
                break;
            case ASSETS: // Here, param does not matter
                assetsFragment = AssetsFragment.newInstance(this);
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivityFragment, assetsFragment, null).commit();
                break;
            case SPENDINGS: // Here, param does not matter
                spendingsFragment = SpendingsFragment.newInstance(this);
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivityFragment, spendingsFragment, null).commit();
                break;
             case CREDITS: // Here, param does not matter
                creditsFragment = CreditsFragment.newInstance(this);
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivityFragment, creditsFragment, null).commit();
                break;
            case SETTINGS: // Here, param does not matter
                settingsFragment = SettingsFragment.newInstance(this);
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivityFragment, settingsFragment, null).commit();
                break;
            case INSTRUCTIONS: // Here, param does not matter
                instructionsFragment = InstructionsFragment.newInstance(this);
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivityFragment, instructionsFragment, null).commit();
                break;
        }
    }

    /////////////////////////////// GETTERS AND SETTERS ///////////////////////

    public GameState getGameState() {
        return this.gameState;
    }

    public void startNewGame()
    {
        this.gameState = new GameState();
        stopMusic();
        startMusic();
        setVolumeLevel(gameState.getSettings().getVolumeLevel());
        saveGame();
    }

    public void startMusic()
    {
        musicHandler.start();
    }

    public void stopMusic()
    {
        musicHandler.stop();
    }

    public void setVolumeLevel(int volumeLevel)
    {
        musicHandler.setVolume(volumeLevel);
    }

} ///// END MAIN ACTIVITY