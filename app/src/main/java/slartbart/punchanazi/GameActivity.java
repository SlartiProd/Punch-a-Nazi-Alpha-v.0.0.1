package slartbart.punchanazi;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    int counter = 0;
    int punchesPerSecCounter;
    int points = 0;
    int round = 0;
    int coincidencePlaySound = 0;
    int time = 0;
    int timeMax = 0;
    boolean finished = true;

    ImageView gameImages;
    MediaPlayer mediaPlayer1;
    MediaPlayer mediaPlayer2;
    MediaPlayer mediaPlayer3;
    MediaPlayer mediaPlayer4;
    MediaPlayer mediaPlayer5;
    MediaPlayer mediaPlayer6;
    MediaPlayer mediaPlayerGameMusic;
    MediaPlayer mediaPlayerBeepShort;
    MediaPlayer mediaPlayerBeepLong;
    MediaPlayer mediaPlayerGameOver;
    MediaPlayer mediaPlayerBoxingBell;
    ProgressBar progressBarPunches;
    ProgressBar progressBarTime;
    Handler handler = new Handler();
    TextView textViewPoints;
    TextView textViewRound;
    TextView textViewTimeCounter;
    TextView textViewPunchesPerSec;
    private AnimationDrawable gameOverAnimation;
    private AnimationDrawable roundIntroductionAnimation;



    //App controll methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        mediaPlayer1 = MediaPlayer.create(GameActivity.this, R.raw.punchsound1);
        mediaPlayer2 = MediaPlayer.create(GameActivity.this, R.raw.punchsound2);
        mediaPlayer3 = MediaPlayer.create(GameActivity.this, R.raw.punchsound3);
        mediaPlayer4 = MediaPlayer.create(GameActivity.this, R.raw.punchsound4);
        mediaPlayer5 = MediaPlayer.create(GameActivity.this, R.raw.punchsound5);
        mediaPlayer6 = MediaPlayer.create(GameActivity.this, R.raw.punchsound6);

        mediaPlayerBeepLong = MediaPlayer.create(GameActivity.this, R.raw.longbeep);
        mediaPlayerBeepShort = MediaPlayer.create(GameActivity.this, R.raw.shortbeep);
        mediaPlayerGameMusic = MediaPlayer.create(GameActivity.this, R.raw.gamemusic);
        mediaPlayerGameOver = MediaPlayer.create(GameActivity.this, R.raw.gameover);
        mediaPlayerBoxingBell = MediaPlayer.create(GameActivity.this, R.raw.boxingbell);

        textViewPoints = (TextView) findViewById(R.id.textViewPoints);
        textViewRound = (TextView) findViewById(R.id.textViewRound);
        textViewTimeCounter = (TextView) findViewById(R.id.textViewTimeCounter);
        textViewPunchesPerSec = (TextView) findViewById(R.id.textViewPunchesPerSec);


        progressBarPunches = (ProgressBar) findViewById(R.id.progressBarPunches);
        progressBarTime = (ProgressBar) findViewById(R.id.progressBarTime);

        gameImages = (ImageView) findViewById(R.id.gameImageView);

        gameImages.setBackgroundResource(R.drawable.animation_list_game_over);
        gameOverAnimation = (AnimationDrawable) gameImages.getBackground();


        gameImages.setBackgroundResource(R.drawable.animation_list_round_introduction);
        roundIntroductionAnimation = (AnimationDrawable) gameImages.getBackground();

        gameImages.setOnClickListener(GameActivity.this);


        newRound();
        Log.i("GameActivity.onCreate", "GameActivity has started");
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer1.isPlaying()) {
            mediaPlayer1.pause();
            mediaPlayer1.stop();
        }
        mediaPlayer1.release();


        if (mediaPlayer2.isPlaying()) {
            mediaPlayer2.pause();
            mediaPlayer2.stop();
        }
        mediaPlayer2.release();


        if (mediaPlayer3.isPlaying()) {
            mediaPlayer3.pause();
            mediaPlayer3.stop();
        }
        mediaPlayer3.release();


        if (mediaPlayer4.isPlaying()) {
            mediaPlayer4.pause();
            mediaPlayer4.stop();
        }
        mediaPlayer4.release();


        if (mediaPlayer5.isPlaying()) {
            mediaPlayer5.pause();
            mediaPlayer5.stop();
        }
        mediaPlayer5.release();


        if (mediaPlayer6.isPlaying()) {
            mediaPlayer6.pause();
            mediaPlayer6.stop();
        }
        mediaPlayer6.release();


        if (mediaPlayerGameOver.isPlaying()) {
            mediaPlayerGameOver.pause();
            mediaPlayerGameOver.stop();
        }
        mediaPlayerGameOver.release();


        if (mediaPlayerBoxingBell.isPlaying()) {
            mediaPlayerBoxingBell.pause();
            mediaPlayerBoxingBell.stop();
        }
        mediaPlayerBoxingBell.release();


        if (mediaPlayerGameMusic.isPlaying()) {
            mediaPlayerGameMusic.pause();
            mediaPlayerGameMusic.stop();
        }
        mediaPlayerGameMusic.release();

        gameOverAnimation.stop();

        setResult(points);
        finished = true;
        time = 0;
        finish();
        super.onBackPressed();
    }

    @Override
    public void onStop() {

        mediaPlayer1.release();
        mediaPlayer2.release();
        mediaPlayer3.release();
        mediaPlayer4.release();
        mediaPlayer5.release();
        mediaPlayer6.release();
        mediaPlayerGameMusic.release();
        gameOverAnimation.stop();
        finished = true;
        time = 0;
        setResult(points);
        finish();
        super.onStop();
    }

    @Override
    public void onResume() {
        mediaPlayer1 = MediaPlayer.create(GameActivity.this, R.raw.punchsound1);
        mediaPlayer2 = MediaPlayer.create(GameActivity.this, R.raw.punchsound2);
        mediaPlayer3 = MediaPlayer.create(GameActivity.this, R.raw.punchsound3);
        mediaPlayer4 = MediaPlayer.create(GameActivity.this, R.raw.punchsound4);
        mediaPlayer5 = MediaPlayer.create(GameActivity.this, R.raw.punchsound5);
        mediaPlayer6 = MediaPlayer.create(GameActivity.this, R.raw.punchsound6);

        mediaPlayerGameMusic = MediaPlayer.create(GameActivity.this, R.raw.gamemusic);
        mediaPlayerGameMusic.setLooping(true);
        mediaPlayerGameMusic.start();

        mediaPlayerGameOver = MediaPlayer.create(GameActivity.this, R.raw.gameover);
        mediaPlayerBoxingBell = MediaPlayer.create(GameActivity.this, R.raw.boxingbell);


        Log.i("GameActivity", "In gameOverAnimation are " + gameOverAnimation.getNumberOfFrames() + " Frames");
        if (gameOverAnimation.getNumberOfFrames() == 0) {
            for (int x = 2; x < 57; x++) {
                try {
                    InputStream ims = getAssets().open("img/game_over/gameover" + x + ".png");
                    // load image as Drawable
                    Drawable d = Drawable.createFromStream(ims, null);
                    // set image to AnimationDrawable
                    gameOverAnimation.addFrame(d, 50);
                    Log.i("GameActivity", "Frame " + x + " added to gamOverAnimation");
                    ims.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("GameActivity", "IOException occured in gameOver()");
                }
            }
        }


        super.onResume();
    }

    @Override
    public void onDestroy() {
        mediaPlayerGameMusic.release();
        mediaPlayerGameOver.release();
        mediaPlayerBoxingBell.release();

        gameOverAnimation.stop();

        super.onDestroy();
    }




    //Game methods
    public void timeCounter() {
        //counts the time backwards
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                time--;
                Log.i("GameActivity", "Time -1 : " + Integer.toString(time));
                progressBarTime.setProgress(time);
                textViewTimeCounter.setText(Integer.toString(time));
                textViewPunchesPerSec.setText("P.p.s.: " + Integer.toString(punchesPerSecCounter));
                punchesPerSecCounter = 0;
                if (time < 6 && time > 0) {
                    if (time == 1) {
                        mediaPlayerBeepLong.start();
                    } else {
                        mediaPlayerBeepShort.start();
                    }
                }
                if (time == 0 && counter < round * 43) {
                    gameOver();
                }
                if (!finished && time > 0)
                    timeCounter();
            }
        }, 1000);

    }

    public void timeDelayedNewRound(long delay) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("GameActivity", "Start newRound() after Delay");
                newRound();
            }
        };
        handler.postDelayed(runnable, delay);
    }

    public void newRound() {
        //starts a new round
        counter = 0;
        timeMax = 25 + round * 5;
        time = timeMax;
        round = round + 1;
        //preparing Progress Bars and TextViews
        progressBarTime.setMax(timeMax);
        textViewTimeCounter.setText(Integer.toString(time));
        progressBarTime.setProgress(time);
        progressBarPunches.setMax(round * 42);
        textViewRound.setText(" Round: " + round);
        Log.i("GameActivitynewRound", "Round = " + Integer.toString(round));

        //preparing the roundintroduction Animation
        gameImages.setImageResource(R.drawable.empty);
        gameImages.setBackgroundResource(R.drawable.animation_list_round_introduction);
        roundIntroductionAnimation = (AnimationDrawable) gameImages.getBackground();
        roundIntroductionAnimation.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayerBoxingBell.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                roundIntroductionAnimation.stop();
                gameImages.setBackgroundResource(R.drawable.empty);
                gameImages.setImageResource(R.drawable.nazi1);
                finished = false;
                timeCounter();
            }
        }, 7000);
    }

    public void roundEnd() {
        //Initialises the end of a round
        progressBarPunches.setProgress(0);
        gameImages.setImageResource(R.drawable.nazi43);
        timeMax = 30;
        finished = true;
        points = points + time;
        textViewPoints.setText(" Points: " + points);

        timeDelayedNewRound(4000);

    }

    public void playPunchingSound() {

        coincidencePlaySound = (int) (Math.random() * 6 + 1);
        if (!finished) {
            switch (coincidencePlaySound) {
                case 1:
                    if (mediaPlayer1.isPlaying()) {
                        mediaPlayer1.seekTo(0);
                        mediaPlayer1.start();

                    } else {
                        mediaPlayer1.start();
                    }
                case 2:
                    if (mediaPlayer2.isPlaying()) {
                        mediaPlayer2.seekTo(0);
                        mediaPlayer2.start();

                    } else {
                        mediaPlayer2.start();
                    }
                case 3:
                    if (mediaPlayer3.isPlaying()) {
                        mediaPlayer3.seekTo(0);
                        mediaPlayer3.start();

                    } else {
                        mediaPlayer3.start();
                    }
                case 4:
                    if (mediaPlayer4.isPlaying()) {
                        mediaPlayer4.seekTo(0);
                        mediaPlayer4.start();

                    } else {
                        mediaPlayer4.start();
                    }
                case 5:
                    if (mediaPlayer5.isPlaying()) {
                        mediaPlayer5.seekTo(0);
                        mediaPlayer5.start();

                    } else {
                        mediaPlayer5.start();
                    }
                case 6:
                    if (mediaPlayer6.isPlaying()) {
                        mediaPlayer6.seekTo(0);
                        mediaPlayer6.start();

                    } else {
                        mediaPlayer6.start();
                    }
            }
        }
    }

    public void onClick(View v) {

        //if a round is running it increases the counter and plays sound when the screen is touched
        if (!finished) {
            counter++;
            playPunchingSound();
            punchesPerSecCounter++;
            progressBarPunches.setProgress(counter);
            progressBarTime.setProgress(time);
            actualizeScreen();
        }

    }

    public void actualizeScreen() {
        //loads images to the imageview while listening to the counter

        if (counter >= (round * 41) + 1) {
            if (!finished) {
                roundEnd();
            }
        } else {
            if (counter >= round * 41) {
                try {
                    InputStream ims = getAssets().open("img/nazi_skin/nazi42.png");
                    // load image as Drawable
                    Drawable d = Drawable.createFromStream(ims, null);
                    // set image to gameImages
                    gameImages.setImageDrawable(d);
                    Log.i("GameActivity", "Picture nazi42 loaded");
                    ims.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("GameActivity", "IOException occured in gameOver()");
                }
            } else {
                if (counter >= round * 40) {
                    try {
                        InputStream ims = getAssets().open("img/nazi_skin/nazi41.png");
                        // load image as Drawable
                        Drawable d = Drawable.createFromStream(ims, null);
                        // set image to gameImages
                        gameImages.setImageDrawable(d);
                        Log.i("GameActivity", "Picture nazi41 loaded");
                        ims.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.w("GameActivity", "IOException occured in gameOver()");
                    }
                } else {
                    if (counter >= round * 39) {
                        try {
                            InputStream ims = getAssets().open("img/nazi_skin/nazi40.png");
                            // load image as Drawable
                            Drawable d = Drawable.createFromStream(ims, null);
                            // set image to gameImages
                            gameImages.setImageDrawable(d);
                            Log.i("GameActivity", "Picture nazi40 loaded");
                            ims.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.w("GameActivity", "IOException occured in gameOver()");
                        }
                    } else {
                        if (counter >= round * 38) {
                            try {
                                InputStream ims = getAssets().open("img/nazi_skin/nazi39.png");
                                // load image as Drawable
                                Drawable d = Drawable.createFromStream(ims, null);
                                // set image to gameImages
                                gameImages.setImageDrawable(d);
                                Log.i("GameActivity", "Picture nazi39 loaded");
                                ims.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.w("GameActivity", "IOException occured in gameOver()");
                            }
                        } else {
                            if (counter >= round * 37) {
                                try {
                                    InputStream ims = getAssets().open("img/nazi_skin/nazi38.png");
                                    // load image as Drawable
                                    Drawable d = Drawable.createFromStream(ims, null);
                                    // set image to gameImages
                                    gameImages.setImageDrawable(d);
                                    Log.i("GameActivity", "Picture nazi38 loaded");
                                    ims.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                }
                            } else {
                                if (counter >= round * 36) {
                                    try {
                                        InputStream ims = getAssets().open("img/nazi_skin/nazi37.png");
                                        // load image as Drawable
                                        Drawable d = Drawable.createFromStream(ims, null);
                                        // set image to gameImages
                                        gameImages.setImageDrawable(d);
                                        Log.i("GameActivity", "Picture nazi37 loaded");
                                        ims.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                    }
                                } else {
                                    if (counter >= round * 35) {
                                        try {
                                            InputStream ims = getAssets().open("img/nazi_skin/nazi36.png");
                                            // load image as Drawable
                                            Drawable d = Drawable.createFromStream(ims, null);
                                            // set image to gameImages
                                            gameImages.setImageDrawable(d);
                                            Log.i("GameActivity", "Picture nazi36 loaded");
                                            ims.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                        }
                                    } else {
                                        if (counter >= round * 34) {
                                            try {
                                                InputStream ims = getAssets().open("img/nazi_skin/nazi35.png");
                                                // load image as Drawable
                                                Drawable d = Drawable.createFromStream(ims, null);
                                                // set image to gameImages
                                                gameImages.setImageDrawable(d);
                                                Log.i("GameActivity", "Picture nazi35 loaded");
                                                ims.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                            }
                                        } else {
                                            if (counter >= round * 33) {
                                                try {
                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi34.png");
                                                    // load image as Drawable
                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                    // set image to gameImages
                                                    gameImages.setImageDrawable(d);
                                                    Log.i("GameActivity", "Picture nazi34 loaded");
                                                    ims.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                }
                                            } else {
                                                if (counter >= round * 32) {
                                                    try {
                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi33.png");
                                                        // load image as Drawable
                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                        // set image to gameImages
                                                        gameImages.setImageDrawable(d);
                                                        Log.i("GameActivity", "Picture nazi33 loaded");
                                                        ims.close();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                    }
                                                } else {
                                                    if (counter >= round * 31) {
                                                        try {
                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi32.png");
                                                            // load image as Drawable
                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                            // set image to gameImages
                                                            gameImages.setImageDrawable(d);
                                                            Log.i("GameActivity", "Picture nazi32 loaded");
                                                            ims.close();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                        }
                                                    } else {
                                                        if (counter >= round * 30) {
                                                            try {
                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi31.png");
                                                                // load image as Drawable
                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                // set image to gameImages
                                                                gameImages.setImageDrawable(d);
                                                                Log.i("GameActivity", "Picture nazi31 loaded");
                                                                ims.close();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                            }
                                                        } else {
                                                            if (counter >= round * 29) {
                                                                try {
                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi30.png");
                                                                    // load image as Drawable
                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                    // set image to gameImages
                                                                    gameImages.setImageDrawable(d);
                                                                    Log.i("GameActivity", "Picture nazi30 loaded");
                                                                    ims.close();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                }
                                                            } else {
                                                                if (counter >= round * 28) {
                                                                    try {
                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi29.png");
                                                                        // load image as Drawable
                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                        // set image to gameImages
                                                                        gameImages.setImageDrawable(d);
                                                                        Log.i("GameActivity", "Picture nazi29 loaded");
                                                                        ims.close();
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                    }
                                                                } else {
                                                                    if (counter >= round * 27) {
                                                                        try {
                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi28.png");
                                                                            // load image as Drawable
                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                            // set image to gameImages
                                                                            gameImages.setImageDrawable(d);
                                                                            Log.i("GameActivity", "Picture nazi29 loaded");
                                                                            ims.close();
                                                                        } catch (IOException e) {
                                                                            e.printStackTrace();
                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                        }
                                                                    } else {
                                                                        if (counter >= round * 26) {
                                                                            try {
                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi27.png");
                                                                                // load image as Drawable
                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                // set image to gameImages
                                                                                gameImages.setImageDrawable(d);
                                                                                Log.i("GameActivity", "Picture nazi27 loaded");
                                                                                ims.close();
                                                                            } catch (IOException e) {
                                                                                e.printStackTrace();
                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                            }
                                                                        } else {
                                                                            if (counter >= round * 25) {
                                                                                try {
                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi26.png");
                                                                                    // load image as Drawable
                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                    // set image to gameImages
                                                                                    gameImages.setImageDrawable(d);
                                                                                    Log.i("GameActivity", "Picture nazi26 loaded");
                                                                                    ims.close();
                                                                                } catch (IOException e) {
                                                                                    e.printStackTrace();
                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                }
                                                                            } else {
                                                                                if (counter >= round * 24) {
                                                                                    try {
                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi25.png");
                                                                                        // load image as Drawable
                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                        // set image to gameImages
                                                                                        gameImages.setImageDrawable(d);
                                                                                        Log.i("GameActivity", "Picture nazi25 loaded");
                                                                                        ims.close();
                                                                                    } catch (IOException e) {
                                                                                        e.printStackTrace();
                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                    }
                                                                                } else {
                                                                                    if (counter >= round * 23) {
                                                                                        try {
                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi24.png");
                                                                                            // load image as Drawable
                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                            // set image to gameImages
                                                                                            gameImages.setImageDrawable(d);
                                                                                            Log.i("GameActivity", "Picture nazi24 loaded");
                                                                                            ims.close();
                                                                                        } catch (IOException e) {
                                                                                            e.printStackTrace();
                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                        }
                                                                                    } else {
                                                                                        if (counter >= round * 22) {
                                                                                            try {
                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi23.png");
                                                                                                // load image as Drawable
                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                // set image to gameImages
                                                                                                gameImages.setImageDrawable(d);
                                                                                                Log.i("GameActivity", "Picture nazi23 loaded");
                                                                                                ims.close();
                                                                                            } catch (IOException e) {
                                                                                                e.printStackTrace();
                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                            }
                                                                                        } else {
                                                                                            if (counter >= round * 21) {
                                                                                                try {
                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi22.png");
                                                                                                    // load image as Drawable
                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                    // set image to gameImages
                                                                                                    gameImages.setImageDrawable(d);
                                                                                                    Log.i("GameActivity", "Picture nazi22 loaded");
                                                                                                    ims.close();
                                                                                                } catch (IOException e) {
                                                                                                    e.printStackTrace();
                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                }
                                                                                            } else {
                                                                                                if (counter >= round * 20) {
                                                                                                    try {
                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi21.png");
                                                                                                        // load image as Drawable
                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                        // set image to gameImages
                                                                                                        gameImages.setImageDrawable(d);
                                                                                                        Log.i("GameActivity", "Picture nazi21 loaded");
                                                                                                        ims.close();
                                                                                                    } catch (IOException e) {
                                                                                                        e.printStackTrace();
                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                    }
                                                                                                } else {
                                                                                                    if (counter >= round * 19) {
                                                                                                        try {
                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi20.png");
                                                                                                            // load image as Drawable
                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                            // set image to gameImages
                                                                                                            gameImages.setImageDrawable(d);
                                                                                                            Log.i("GameActivity", "Picture nazi20 loaded");
                                                                                                            ims.close();
                                                                                                        } catch (IOException e) {
                                                                                                            e.printStackTrace();
                                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                        }
                                                                                                    } else {
                                                                                                        if (counter >= round * 18) {
                                                                                                            try {
                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi19.png");
                                                                                                                // load image as Drawable
                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                // set image to gameImages
                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                Log.i("GameActivity", "Picture nazi19 loaded");
                                                                                                                ims.close();
                                                                                                            } catch (IOException e) {
                                                                                                                e.printStackTrace();
                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                            }
                                                                                                        } else {
                                                                                                            if (counter >= round * 17) {
                                                                                                                try {
                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi18.png");
                                                                                                                    // load image as Drawable
                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                    // set image to gameImages
                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                    Log.i("GameActivity", "Picture nazi18 loaded");
                                                                                                                    ims.close();
                                                                                                                } catch (IOException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                }
                                                                                                            } else {
                                                                                                                if (counter >= round * 16) {
                                                                                                                    try {
                                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi17.png");
                                                                                                                        // load image as Drawable
                                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                        // set image to gameImages
                                                                                                                        gameImages.setImageDrawable(d);
                                                                                                                        Log.i("GameActivity", "Picture nazi17 loaded");
                                                                                                                        ims.close();
                                                                                                                    } catch (IOException e) {
                                                                                                                        e.printStackTrace();
                                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    if (counter >= round * 15) {
                                                                                                                        try {
                                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi16.png");
                                                                                                                            // load image as Drawable
                                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                            // set image to gameImages
                                                                                                                            gameImages.setImageDrawable(d);
                                                                                                                            Log.i("GameActivity", "Picture nazi16 loaded");
                                                                                                                            ims.close();
                                                                                                                        } catch (IOException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        if (counter >= round * 14) {
                                                                                                                            try {
                                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi15.png");
                                                                                                                                // load image as Drawable
                                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                // set image to gameImages
                                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                                Log.i("GameActivity", "Picture nazi15 loaded");
                                                                                                                                ims.close();
                                                                                                                            } catch (IOException e) {
                                                                                                                                e.printStackTrace();
                                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            if (counter >= round * 13) {
                                                                                                                                try {
                                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi14.png");
                                                                                                                                    // load image as Drawable
                                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                    // set image to gameImages
                                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                                    Log.i("GameActivity", "Picture nazi14 loaded");
                                                                                                                                    ims.close();
                                                                                                                                } catch (IOException e) {
                                                                                                                                    e.printStackTrace();
                                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                if (counter >= round * 12) {
                                                                                                                                    try {
                                                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi13.png");
                                                                                                                                        // load image as Drawable
                                                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                        // set image to gameImages
                                                                                                                                        gameImages.setImageDrawable(d);
                                                                                                                                        Log.i("GameActivity", "Picture nazi13 loaded");
                                                                                                                                        ims.close();
                                                                                                                                    } catch (IOException e) {
                                                                                                                                        e.printStackTrace();
                                                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                    }
                                                                                                                                } else {
                                                                                                                                    if (counter >= round * 11) {
                                                                                                                                        try {
                                                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi12.png");
                                                                                                                                            // load image as Drawable
                                                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                            // set image to gameImages
                                                                                                                                            gameImages.setImageDrawable(d);
                                                                                                                                            Log.i("GameActivity", "Picture nazi12 loaded");
                                                                                                                                            ims.close();
                                                                                                                                        } catch (IOException e) {
                                                                                                                                            e.printStackTrace();
                                                                                                                                        }
                                                                                                                                    } else {
                                                                                                                                        if (counter >= round * 10) {
                                                                                                                                            try {
                                                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi11.png");
                                                                                                                                                // load image as Drawable
                                                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                // set image to gameImages
                                                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                                                Log.i("GameActivity", "Picture nazi11 loaded");
                                                                                                                                                ims.close();
                                                                                                                                            } catch (IOException e) {
                                                                                                                                                e.printStackTrace();
                                                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                            }
                                                                                                                                        } else {
                                                                                                                                            if (counter >= round * 9) {
                                                                                                                                                try {
                                                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi10.png");
                                                                                                                                                    // load image as Drawable
                                                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                    // set image to gameImages
                                                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                                                    Log.i("GameActivity", "Picture nazi10 loaded");
                                                                                                                                                    ims.close();
                                                                                                                                                } catch (IOException e) {
                                                                                                                                                    e.printStackTrace();
                                                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                }
                                                                                                                                            } else {
                                                                                                                                                if (counter >= round * 8) {
                                                                                                                                                    try {
                                                                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi9.png");
                                                                                                                                                        // load image as Drawable
                                                                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                        // set image to gameImages
                                                                                                                                                        gameImages.setImageDrawable(d);
                                                                                                                                                        Log.i("GameActivity", "Picture nazi9 loaded");
                                                                                                                                                        ims.close();
                                                                                                                                                    } catch (IOException e) {
                                                                                                                                                        e.printStackTrace();
                                                                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                    }
                                                                                                                                                } else {
                                                                                                                                                    if (counter >= round * 7) {
                                                                                                                                                        try {
                                                                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi8.png");
                                                                                                                                                            // load image as Drawable
                                                                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                            // set image to gameImages
                                                                                                                                                            gameImages.setImageDrawable(d);
                                                                                                                                                            Log.i("GameActivity", "Picture nazi8 loaded");
                                                                                                                                                            ims.close();
                                                                                                                                                        } catch (IOException e) {
                                                                                                                                                            e.printStackTrace();
                                                                                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                        }
                                                                                                                                                    } else {
                                                                                                                                                        if (counter >= round * 6) {
                                                                                                                                                            try {
                                                                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi7.png");
                                                                                                                                                                // load image as Drawable
                                                                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                // set image to gameImages
                                                                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                                                                Log.i("GameActivity", "Picture nazi7 loaded");
                                                                                                                                                                ims.close();
                                                                                                                                                            } catch (IOException e) {
                                                                                                                                                                e.printStackTrace();
                                                                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                            }
                                                                                                                                                        } else {
                                                                                                                                                            if (counter >= round * 5) {
                                                                                                                                                                try {
                                                                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi6.png");
                                                                                                                                                                    // load image as Drawable
                                                                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                    // set image to gameImages
                                                                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                                                                    Log.i("GameActivity", "Picture nazi6 loaded");
                                                                                                                                                                    ims.close();
                                                                                                                                                                } catch (IOException e) {
                                                                                                                                                                    e.printStackTrace();
                                                                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                }
                                                                                                                                                            } else {
                                                                                                                                                                if (counter >= round * 4) {
                                                                                                                                                                    try {
                                                                                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi5.png");
                                                                                                                                                                        // load image as Drawable
                                                                                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                        // set image to gameImages
                                                                                                                                                                        gameImages.setImageDrawable(d);
                                                                                                                                                                        Log.i("GameActivity", "Picture nazi5 loaded");
                                                                                                                                                                        ims.close();
                                                                                                                                                                    } catch (IOException e) {
                                                                                                                                                                        e.printStackTrace();
                                                                                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                    }
                                                                                                                                                                } else {
                                                                                                                                                                    if (counter >= round * 3) {
                                                                                                                                                                        try {
                                                                                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi4.png");
                                                                                                                                                                            // load image as Drawable
                                                                                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                            // set image to gameImages
                                                                                                                                                                            gameImages.setImageDrawable(d);
                                                                                                                                                                            Log.i("GameActivity", "Picture nazi4 loaded");
                                                                                                                                                                            ims.close();
                                                                                                                                                                        } catch (IOException e) {
                                                                                                                                                                            e.printStackTrace();
                                                                                                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                        }
                                                                                                                                                                    } else {
                                                                                                                                                                        if (counter >= round * 2) {
                                                                                                                                                                            try {
                                                                                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi3.png");
                                                                                                                                                                                // load image as Drawable
                                                                                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                                // set image to gameImages
                                                                                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                                                                                Log.i("GameActivity", "Picture nazi3 loaded");
                                                                                                                                                                                ims.close();
                                                                                                                                                                            } catch (IOException e) {
                                                                                                                                                                                e.printStackTrace();
                                                                                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                            }
                                                                                                                                                                        } else {
                                                                                                                                                                            if (counter >= round * 1) {
                                                                                                                                                                                try {
                                                                                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi2.png");
                                                                                                                                                                                    // load image as Drawable
                                                                                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                                    // set image to gameImages
                                                                                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                                                                                    Log.i("GameActivity", "Picture nazi2 loaded");
                                                                                                                                                                                    ims.close();
                                                                                                                                                                                } catch (IOException e) {
                                                                                                                                                                                    e.printStackTrace();
                                                                                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                                }
                                                                                                                                                                            } else {

                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public void gameOver() {
        //shows game over animation and plays sound
        finished = true;
        gameImages.setImageResource(R.drawable.empty);
        gameImages.setBackgroundResource(R.drawable.animation_list_game_over);
        gameOverAnimation = (AnimationDrawable) gameImages.getBackground();
        try{
            mediaPlayerGameMusic.stop();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }


        mediaPlayerGameOver.start();
        gameOverAnimation.start();
        setResult(points);
        Log.i("GameActivity", "GameOver Aniamtion started");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                gameImages.setBackgroundResource(R.drawable.empty);
                gameOverAnimation.stop();
                mediaPlayerGameOver.pause();
                mediaPlayerGameOver.stop();
                mediaPlayerGameOver.release();
                finish();
            }
        }, 2750);



    }


}