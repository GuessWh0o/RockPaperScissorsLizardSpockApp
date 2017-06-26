package com.guesswho.rockpaper.activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.guesswho.rockpaper.R;
import com.guesswho.rockpaper.ScoreDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    //Buttons Image
    private ImageButton btn_Rock;
    private ImageButton btn_Paper;
    private ImageButton btn_Scissors;
    private ImageButton btn_Lizard;
    private ImageButton btn_Spock;

    //ImageViews
    private ImageView img_CPU;
    private ImageView img_Player;

//    Context context = getApplicationContext();
    private static final String TAG = "GameActivity";

    private String YOU_WIN = "OHHHH, YOU WIN :(";
    private String YOU_LOSE = "BAZIINGA, YOU LOSE";
    Map<Integer, Integer> imageResources = new HashMap<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initResources();
    }

    private void initResources() {

        imageResources.put(1, R.drawable.rock);
        imageResources.put(2, R.drawable.paper);
        imageResources.put(3, R.drawable.scissors);
        imageResources.put(4, R.drawable.lizard);
        imageResources.put(5, R.drawable.spock);

        //Asigning resources
        btn_Rock = (ImageButton) findViewById(R.id.rock_player);
        btn_Paper = (ImageButton) findViewById(R.id.paper_player);
        btn_Scissors = (ImageButton) findViewById(R.id.scissors_player);
        btn_Lizard = (ImageButton) findViewById(R.id.lizard_player);
        btn_Spock = (ImageButton) findViewById(R.id.spock_player);

        //Setting OnClickListeners
        btn_Rock.setOnClickListener(onClickListener);
        btn_Paper.setOnClickListener(onClickListener);
        btn_Scissors.setOnClickListener(onClickListener);
        btn_Lizard.setOnClickListener(onClickListener);
        btn_Spock.setOnClickListener(onClickListener);

        //Images
        img_CPU = (ImageView) findViewById(R.id.cpuDecision);
        img_Player = (ImageView) findViewById(R.id.playerDecision);

        //Clear Images
        img_CPU.setImageResource(0);
        img_Player.setImageResource(0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int cpuDecission = cpuDecission();

            switch (v.getId()) {
                case R.id.rock_player:
                    youWin(cpuDecission, 1);
                    break;
                case R.id.paper_player:
                    youWin(cpuDecission, 2);
                    break;
                case R.id.scissors_player:
                    youWin(cpuDecission, 3);
                    break;
                case R.id.lizard_player:
                    youWin(cpuDecission, 4);
                    break;
                case R.id.spock_player:
                    youWin(cpuDecission, 5);
                    break;
                default:
                    Toast.makeText(GameActivity.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private int cpuDecission() {
        int figure;
        Random random = new Random();
        figure = random.nextInt(5) + 1;
        return figure;
    }

    /**
     *  1 ROCK
     *  2 PAPER
     *  3 SCISSORS
     *  4 LIZARD
     *  5 SPOCK
    **/

    private void youWin(int cpuDec, int playerDec) {
        Log.d(TAG, "youWin: cpu " + cpuDec + " you " + playerDec);

        if (cpuDec != playerDec) {

            switch (playerDec) {
                case 1:
                    if (cpuDec == 3 || cpuDec == 4) {
                        showImages(cpuDec, playerDec, true);
                        Log.d(TAG, "youWin: ROCK beats SCISSORS OR LIZARD" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, false);
                        Log.d(TAG, "youLose: ROCK BET " + cpuDec);
                    }
                    break;
                case 2:
                    if(cpuDec == 1 || cpuDec == 5) {
                        showImages(cpuDec, playerDec, true);
                        Log.d(TAG, "youWin: PAPER beats ROCK OR SPOCK" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, false);
                        Log.d(TAG, "youLose: PAPER BET " + cpuDec);
                    }
                    break;
                case 3:
                    if(cpuDec == 2 || cpuDec == 4) {
                        showImages(cpuDec, playerDec, true);
                        Log.d(TAG, "youWin: SCISSORS beats PAPER OR LIZARD" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, false);
                        Log.d(TAG, "youLose: SCISSORS BET " + cpuDec);
                    }
                    break;
                case 4:
                    if(cpuDec == 2 || cpuDec == 5) {
                        showImages(cpuDec, playerDec, true);
                        Log.d(TAG, "youWin: LIZARD beats PAPER OR SPOCK" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, false);
                        Log.d(TAG, "youLose: LIZARD BET " + cpuDec);
                    }
                    break;
                case 5:
                    if(cpuDec == 3 || cpuDec == 5) {
                        showImages(cpuDec, playerDec, true);
                        Log.d(TAG, "youWin: SPOCK beats ROCK OR SCISSORS" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, false);
                        Log.d(TAG, "youLose: SPOCK BET " + cpuDec);
                    }

                    break;
            }

        } else {
            Log.d(TAG, "DRAW: CPU "+ cpuDec + " YOU " + playerDec);
            showImages(cpuDec, playerDec, false);
        }
    }

    private void showImages(int cpuImg, int playerImg, boolean youWin) {
        img_CPU.setImageResource(imageResources.get(cpuImg));
        img_Player.setImageResource(imageResources.get(playerImg));
        showTheWinner(youWin);
    }


    private void showTheWinner(boolean youWin) {

        Log.d(TAG, "<<<<<<YOU WIN >>>>>>>>>> " + youWin);

        String message;
        if(youWin) {
            message = YOU_WIN;
        } else {
            message = YOU_LOSE;
        }

        Log.d(TAG, "<<<<<<MESSAGE>>>> " +message);

        Bundle bundle = new Bundle();
        bundle.putString("win", message);
        FragmentManager fm = getFragmentManager();
        ScoreDialog scoreDialog = new ScoreDialog();
        scoreDialog.setArguments(bundle);
        scoreDialog.show(fm, "Sample Fragment");
    }

}
