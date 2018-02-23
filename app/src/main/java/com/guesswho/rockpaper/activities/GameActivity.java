package com.guesswho.rockpaper.activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.guesswho.rockpaper.R;
import com.guesswho.rockpaper.ScoreDialog;
import java.util.Random;

/**
 * Created by GuessWh0o on 18.02.2018.
 * Email: developerint97@gmail.com
 */


/**
 *  0 ROCK
 *  1 PAPER
 *  2 SCISSORS
 *  3 LIZARD
 *  4 SPOCK
 **/
enum Figure {
    ROCK(0), PAPER(1), SCISSORS(2), LIZARD(3), SPOCK(4);

    private final int figure;

    Figure(int figure) {
        this.figure = figure;
    }

    int getFigure() {
        return figure;
    }
}


enum GameResult {
    WIN, LOSE, DRAW
}

public class GameActivity extends AppCompatActivity {


    //ImageViews
    private ImageView img_CPU;
    private ImageView img_Player;

//    Context context = getApplicationContext();
    private static final String TAG = "GameActivity";

    private SparseIntArray imageResources = new SparseIntArray(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initResources();
    }

    private void initResources() {
        imageResources.put(0, R.drawable.rock);
        imageResources.put(1, R.drawable.paper);
        imageResources.put(2, R.drawable.scissors);
        imageResources.put(3, R.drawable.lizard);
        imageResources.put(4, R.drawable.spock);

        //Assigning resources
        ImageButton btn_Rock = findViewById(R.id.rock_player);
        ImageButton btn_Paper = findViewById(R.id.paper_player);
        ImageButton btn_Scissors = findViewById(R.id.scissors_player);
        ImageButton btn_Lizard = findViewById(R.id.lizard_player);
        ImageButton btn_Spock = findViewById(R.id.spock_player);

        //Setting OnClickListeners
        btn_Rock.setOnClickListener(onClickListener);
        btn_Paper.setOnClickListener(onClickListener);
        btn_Scissors.setOnClickListener(onClickListener);
        btn_Lizard.setOnClickListener(onClickListener);
        btn_Spock.setOnClickListener(onClickListener);

        //Images
        img_CPU = findViewById(R.id.cpuDecision);
        img_Player = findViewById(R.id.playerDecision);

        //Clear Images
        img_CPU.setImageResource(0);
        img_Player.setImageResource(0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Figure cpuDecision = cpuDecision();

            switch (v.getId()) {
                case R.id.rock_player:
                    youWin(cpuDecision, Figure.ROCK);
                    break;
                case R.id.paper_player:
                    youWin(cpuDecision, Figure.PAPER);
                    break;
                case R.id.scissors_player:
                    youWin(cpuDecision, Figure.SCISSORS);
                    break;
                case R.id.lizard_player:
                    youWin(cpuDecision, Figure.LIZARD);
                    break;
                case R.id.spock_player:
                    youWin(cpuDecision, Figure.SPOCK);
                    break;
                default:
                    Toast.makeText(GameActivity.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Figure cpuDecision() {
        Figure figure;
        Random random = new Random();
        figure = Figure.values()[random.nextInt(4)];
        return figure;
    }

    /**
     *  0 ROCK
     *  1 PAPER
     *  2 SCISSORS
     *  3 LIZARD
     *  4 SPOCK
    **/

    private void youWin(Figure cpuDec, Figure playerDec) {
        Log.d(TAG, "youWin: cpu " + cpuDec + " you " + playerDec);

        if (cpuDec != playerDec) {
            switch (playerDec) {
                case ROCK:
                    if (cpuDec == Figure.SCISSORS || cpuDec == Figure.LIZARD) {
                        showImages(cpuDec, playerDec, GameResult.WIN);
                        Log.d(TAG, "youWin: ROCK beats SCISSORS OR LIZARD" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, GameResult.LOSE);
                        Log.d(TAG, "youLose: ROCK BET " + cpuDec);
                    }
                    break;
                case PAPER:
                    if(cpuDec == Figure.ROCK || cpuDec == Figure.SPOCK) {
                        showImages(cpuDec, playerDec, GameResult.WIN);
                        Log.d(TAG, "youWin: PAPER beats ROCK OR SPOCK" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, GameResult.LOSE);
                        Log.d(TAG, "youLose: PAPER BET " + cpuDec);
                    }
                    break;
                case SCISSORS:
                    if(cpuDec == Figure.PAPER || cpuDec == Figure.LIZARD) {
                        showImages(cpuDec, playerDec, GameResult.WIN);
                        Log.d(TAG, "youWin: SCISSORS beats PAPER OR LIZARD" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, GameResult.LOSE);
                        Log.d(TAG, "youLose: SCISSORS BET " + cpuDec);
                    }
                    break;
                case LIZARD:
                    if(cpuDec == Figure.PAPER || cpuDec == Figure.SPOCK) {
                        showImages(cpuDec, playerDec, GameResult.WIN);
                        Log.d(TAG, "youWin: LIZARD beats PAPER OR SPOCK" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, GameResult.LOSE);
                        Log.d(TAG, "youLose: LIZARD BET " + cpuDec);
                    }
                    break;
                case SPOCK:
                    if(cpuDec == Figure.SCISSORS || cpuDec == Figure.SPOCK) {
                        showImages(cpuDec, playerDec, GameResult.WIN);
                        Log.d(TAG, "youWin: SPOCK beats ROCK OR SCISSORS" + cpuDec);
                    } else {
                        showImages(cpuDec, playerDec, GameResult.LOSE);
                        Log.d(TAG, "youLose: SPOCK BET " + cpuDec);
                    }
                    break;
            }
        } else {
            Log.d(TAG, "DRAW: CPU "+ cpuDec + " YOU " + playerDec);
            showImages(cpuDec, playerDec, GameResult.DRAW);
        }
    }

    private void showImages(Figure cpuImg, Figure playerImg, GameResult gameResult) {
        img_CPU.setImageResource(imageResources.get(cpuImg.getFigure()));
        img_Player.setImageResource(imageResources.get(playerImg.getFigure()));
        showTheWinner(gameResult);
    }


    private void showTheWinner(GameResult gameResult) {
        String YOU_WIN = "OHHHH, YOU WIN :(";
        String YOU_LOSE = "BAZIINGA, YOU LOSE";
        String DRAW = "OHHHH, DRAW, BUT I STILL WIN";

        Log.d(TAG, "<<<<<<YOU WIN >>>>>>>>>> " + gameResult);

        String message;
        if(gameResult == GameResult.WIN) {
            message = YOU_WIN;
        } else if (gameResult == GameResult.DRAW) {
            message = DRAW;
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
