package slartbart.punchanazi;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;


public class RelaxedActivity extends AppCompatActivity implements View.OnClickListener {

    int counter = 0;
    int round = 2;
    int coincidencePlaySound = 0;
    boolean finished = true;

    ImageView gameImages;
    MediaPlayer mediaPlayer1;
    MediaPlayer mediaPlayer2;
    MediaPlayer mediaPlayer3;
    MediaPlayer mediaPlayer4;
    MediaPlayer mediaPlayer5;
    MediaPlayer mediaPlayer6;
    MediaPlayer mediaPlayerMusic;
    AnimationDrawable roundIntroductionAnimation;
    private Animation animationFadeIn;



    //App-controll Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("RelaxedActivity", "omCreate started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relaxed_activity);

        animationFadeIn = AnimationUtils.loadAnimation(RelaxedActivity.this, R.anim.fade_in_animation);
        gameImages = (ImageView)findViewById(R.id.relaxedImageView);
        gameImages.setOnClickListener(RelaxedActivity.this);

        Log.i("RelaxedActivity", "onCreate finished");

        newRound();
    }

    @Override
    public void onBackPressed() {
        mediaPlayer1.pause();
        mediaPlayer1.stop();
        mediaPlayer1.release();
        mediaPlayer2.pause();
        mediaPlayer2.stop();
        mediaPlayer2.release();
        mediaPlayer3.pause();
        mediaPlayer3.stop();
        mediaPlayer3.release();
        mediaPlayer4.pause();
        mediaPlayer4.stop();
        mediaPlayer4.release();
        mediaPlayer5.pause();
        mediaPlayer5.stop();
        mediaPlayer5.release();
        mediaPlayer6.pause();
        mediaPlayer6.stop();
        mediaPlayer6.release();

        mediaPlayerMusic.pause();
        mediaPlayerMusic.stop();
        mediaPlayerMusic.release();
        finished = true;
        finish();
        super.onBackPressed();
    }

    @Override
    public void onStop(){

        mediaPlayer1.release();
        mediaPlayer2.release();
        mediaPlayer3.release();
        mediaPlayer4.release();
        mediaPlayer5.release();
        mediaPlayer6.release();
        mediaPlayerMusic.release();
        finished = true;
        finish();
        super.onStop();
    }

    @Override
    public void onResume(){
        mediaPlayer1 = MediaPlayer.create(RelaxedActivity.this, R.raw.punchsound1);
        mediaPlayer2 = MediaPlayer.create(RelaxedActivity.this, R.raw.punchsound2);
        mediaPlayer3 = MediaPlayer.create(RelaxedActivity.this, R.raw.punchsound3);
        mediaPlayer4 = MediaPlayer.create(RelaxedActivity.this, R.raw.punchsound4);
        mediaPlayer5 = MediaPlayer.create(RelaxedActivity.this, R.raw.punchsound5);
        mediaPlayer6 = MediaPlayer.create(RelaxedActivity.this, R.raw.punchsound6);
        mediaPlayerMusic = MediaPlayer.create(RelaxedActivity.this, R.raw.relaxing_music);

        mediaPlayerMusic.setLooping(true);
        mediaPlayerMusic.start();
        super.onResume();
    }

    @Override
    public void onDestroy(){
        mediaPlayerMusic.release();
        super.onDestroy();
    }



    //Game Methods
    public void newRound(){
        //starts a new round
        Log.i("RelaxedActivity", "Start new round");
        finished = true;
        gameImages.setImageResource(R.drawable.empty);
        gameImages.setImageResource(R.drawable.empty);
        //preparing round introduction
        gameImages.setBackgroundResource(R.drawable.animation_list_round_introduction);
        roundIntroductionAnimation = (AnimationDrawable) gameImages.getBackground();
        roundIntroductionAnimation.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                roundIntroductionAnimation.stop();
                gameImages.setBackgroundResource(R.drawable.empty);
                gameImages.setImageResource(R.drawable.nazi1);
                finished = false;

            }
        },6000);
    }

    public void timeDelayedNewRound(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newRound();
            }
        }, delay);
    }

    public void roundEnd(){
        //Initialises the end of a round
        Log.i("Relaxedactivity", "Round ended");
        gameImages.setImageResource(R.drawable.nazi43);
        finished = true;
        counter = 0;
        timeDelayedNewRound(3000);

    }

    public void onClick(View v) {
        if (!finished){
            playSound();
            counter++;
            actualizeScreen();
        }
    }

    public void playSound(){
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

    public void actualizeScreen(){
        //if a round is running it increases the counter and plays sound when the screen is touched

        if (counter >= (round*41)+1){
            if (!finished){
                roundEnd();
            }
        }
        else{
            if (counter >= round*41){
                try {
                    InputStream ims = getAssets().open("img/nazi_skin/nazi42.png");
                    // load image as Drawable
                    Drawable d = Drawable.createFromStream(ims, null);
                    // set image to gameImages
                    gameImages.setImageDrawable(d);
                    Log.i("GameActivity", "Picture nazi42 loaded");
                    ims.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                    Log.w("GameActivity", "IOException occured in gameOver()");
                }                }
            else{
                if (counter >= round*40){
                    try {
                        InputStream ims = getAssets().open("img/nazi_skin/nazi41.png");
                        // load image as Drawable
                        Drawable d = Drawable.createFromStream(ims, null);
                        // set image to gameImages
                        gameImages.setImageDrawable(d);
                        Log.i("GameActivity", "Picture nazi41 loaded");
                        ims.close();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                        Log.w("GameActivity", "IOException occured in gameOver()");
                    }                    }
                else{
                    if (counter >= round*39){
                        try {
                            InputStream ims = getAssets().open("img/nazi_skin/nazi40.png");
                            // load image as Drawable
                            Drawable d = Drawable.createFromStream(ims, null);
                            // set image to gameImages
                            gameImages.setImageDrawable(d);
                            Log.i("GameActivity", "Picture nazi40 loaded");
                            ims.close();
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                            Log.w("GameActivity", "IOException occured in gameOver()");
                        }                        }
                    else{
                        if (counter >= round*38){
                            try {
                                InputStream ims = getAssets().open("img/nazi_skin/nazi39.png");
                                // load image as Drawable
                                Drawable d = Drawable.createFromStream(ims, null);
                                // set image to gameImages
                                gameImages.setImageDrawable(d);
                                Log.i("GameActivity", "Picture nazi39 loaded");
                                ims.close();
                            }
                            catch(IOException e) {
                                e.printStackTrace();
                                Log.w("GameActivity", "IOException occured in gameOver()");
                            }                            }
                        else{
                            if (counter >= round*37){
                                try {
                                    InputStream ims = getAssets().open("img/nazi_skin/nazi38.png");
                                    // load image as Drawable
                                    Drawable d = Drawable.createFromStream(ims, null);
                                    // set image to gameImages
                                    gameImages.setImageDrawable(d);
                                    Log.i("GameActivity", "Picture nazi38 loaded");
                                    ims.close();
                                }
                                catch(IOException e) {
                                    e.printStackTrace();
                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                }                                }
                            else{
                                if (counter >= round*36){
                                    try {
                                        InputStream ims = getAssets().open("img/nazi_skin/nazi37.png");
                                        // load image as Drawable
                                        Drawable d = Drawable.createFromStream(ims, null);
                                        // set image to gameImages
                                        gameImages.setImageDrawable(d);
                                        Log.i("GameActivity", "Picture nazi37 loaded");
                                        ims.close();
                                    }
                                    catch(IOException e) {
                                        e.printStackTrace();
                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                    }                                    }
                                else{
                                    if (counter >= round*35){
                                        try {
                                            InputStream ims = getAssets().open("img/nazi_skin/nazi36.png");
                                            // load image as Drawable
                                            Drawable d = Drawable.createFromStream(ims, null);
                                            // set image to gameImages
                                            gameImages.setImageDrawable(d);
                                            Log.i("GameActivity", "Picture nazi36 loaded");
                                            ims.close();
                                        }
                                        catch(IOException e) {
                                            e.printStackTrace();
                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                        }                                        }
                                    else{
                                        if (counter >= round*34){
                                            try {
                                                InputStream ims = getAssets().open("img/nazi_skin/nazi35.png");
                                                // load image as Drawable
                                                Drawable d = Drawable.createFromStream(ims, null);
                                                // set image to gameImages
                                                gameImages.setImageDrawable(d);
                                                Log.i("GameActivity", "Picture nazi35 loaded");
                                                ims.close();
                                            }
                                            catch(IOException e) {
                                                e.printStackTrace();
                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                            }                                            }
                                        else{
                                            if (counter >= round*33){
                                                try {
                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi34.png");
                                                    // load image as Drawable
                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                    // set image to gameImages
                                                    gameImages.setImageDrawable(d);
                                                    Log.i("GameActivity", "Picture nazi34 loaded");
                                                    ims.close();
                                                }
                                                catch(IOException e) {
                                                    e.printStackTrace();
                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                }                                                }
                                            else{
                                                if (counter >= round*32){
                                                    try {
                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi33.png");
                                                        // load image as Drawable
                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                        // set image to gameImages
                                                        gameImages.setImageDrawable(d);
                                                        Log.i("GameActivity", "Picture nazi33 loaded");
                                                        ims.close();
                                                    }
                                                    catch(IOException e) {
                                                        e.printStackTrace();
                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                    }                                                    }
                                                else{
                                                    if (counter >= round*31){
                                                        try {
                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi32.png");
                                                            // load image as Drawable
                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                            // set image to gameImages
                                                            gameImages.setImageDrawable(d);
                                                            Log.i("GameActivity", "Picture nazi32 loaded");
                                                            ims.close();
                                                        }
                                                        catch(IOException e) {
                                                            e.printStackTrace();
                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                        }                                                        }
                                                    else{
                                                        if (counter >= round*30){
                                                            try {
                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi31.png");
                                                                // load image as Drawable
                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                // set image to gameImages
                                                                gameImages.setImageDrawable(d);
                                                                Log.i("GameActivity", "Picture nazi31 loaded");
                                                                ims.close();
                                                            }
                                                            catch(IOException e) {
                                                                e.printStackTrace();
                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                            }                                                            }
                                                        else{
                                                            if (counter >= round*29){
                                                                try {
                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi30.png");
                                                                    // load image as Drawable
                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                    // set image to gameImages
                                                                    gameImages.setImageDrawable(d);
                                                                    Log.i("GameActivity", "Picture nazi30 loaded");
                                                                    ims.close();
                                                                }
                                                                catch(IOException e) {
                                                                    e.printStackTrace();
                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                }                                                                }
                                                            else{
                                                                if (counter >= round*28){
                                                                    try {
                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi29.png");
                                                                        // load image as Drawable
                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                        // set image to gameImages
                                                                        gameImages.setImageDrawable(d);
                                                                        Log.i("GameActivity", "Picture nazi29 loaded");
                                                                        ims.close();
                                                                    }
                                                                    catch(IOException e) {
                                                                        e.printStackTrace();
                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                    }                                                                    }
                                                                else{
                                                                    if (counter >= round*27){
                                                                        try {
                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi28.png");
                                                                            // load image as Drawable
                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                            // set image to gameImages
                                                                            gameImages.setImageDrawable(d);
                                                                            Log.i("GameActivity", "Picture nazi29 loaded");
                                                                            ims.close();
                                                                        }
                                                                        catch(IOException e) {
                                                                            e.printStackTrace();
                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                        }                                                                        }
                                                                    else{
                                                                        if (counter >= round*26){
                                                                            try {
                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi27.png");
                                                                                // load image as Drawable
                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                // set image to gameImages
                                                                                gameImages.setImageDrawable(d);
                                                                                Log.i("GameActivity", "Picture nazi27 loaded");
                                                                                ims.close();
                                                                            }
                                                                            catch(IOException e) {
                                                                                e.printStackTrace();
                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                            }                                                                            }
                                                                        else{
                                                                            if (counter >= round*25){
                                                                                try {
                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi26.png");
                                                                                    // load image as Drawable
                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                    // set image to gameImages
                                                                                    gameImages.setImageDrawable(d);
                                                                                    Log.i("GameActivity", "Picture nazi26 loaded");
                                                                                    ims.close();
                                                                                }
                                                                                catch(IOException e) {
                                                                                    e.printStackTrace();
                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                }                                                                                }
                                                                            else{
                                                                                if (counter >= round*24){
                                                                                    try {
                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi25.png");
                                                                                        // load image as Drawable
                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                        // set image to gameImages
                                                                                        gameImages.setImageDrawable(d);
                                                                                        Log.i("GameActivity", "Picture nazi25 loaded");
                                                                                        ims.close();
                                                                                    }
                                                                                    catch(IOException e) {
                                                                                        e.printStackTrace();
                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                    }                                                                                    }
                                                                                else{
                                                                                    if (counter >= round*23){
                                                                                        try {
                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi24.png");
                                                                                            // load image as Drawable
                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                            // set image to gameImages
                                                                                            gameImages.setImageDrawable(d);
                                                                                            Log.i("GameActivity", "Picture nazi24 loaded");
                                                                                            ims.close();
                                                                                        }
                                                                                        catch(IOException e) {
                                                                                            e.printStackTrace();
                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                        }                                                                                        }
                                                                                    else{
                                                                                        if (counter >= round*22){
                                                                                            try {
                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi23.png");
                                                                                                // load image as Drawable
                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                // set image to gameImages
                                                                                                gameImages.setImageDrawable(d);
                                                                                                Log.i("GameActivity", "Picture nazi23 loaded");
                                                                                                ims.close();
                                                                                            }
                                                                                            catch(IOException e) {
                                                                                                e.printStackTrace();
                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                            }                                                                                            }
                                                                                        else{
                                                                                            if (counter >= round*21){
                                                                                                try {
                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi22.png");
                                                                                                    // load image as Drawable
                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                    // set image to gameImages
                                                                                                    gameImages.setImageDrawable(d);
                                                                                                    Log.i("GameActivity", "Picture nazi22 loaded");
                                                                                                    ims.close();
                                                                                                }
                                                                                                catch(IOException e) {
                                                                                                    e.printStackTrace();
                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                }                                                                                                }
                                                                                            else{
                                                                                                if (counter >= round*20){
                                                                                                    try {
                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi21.png");
                                                                                                        // load image as Drawable
                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                        // set image to gameImages
                                                                                                        gameImages.setImageDrawable(d);
                                                                                                        Log.i("GameActivity", "Picture nazi21 loaded");
                                                                                                        ims.close();
                                                                                                    }
                                                                                                    catch(IOException e) {
                                                                                                        e.printStackTrace();
                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                    }                                                                                                    }
                                                                                                else{
                                                                                                    if (counter >= round*19){
                                                                                                        try {
                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi20.png");
                                                                                                            // load image as Drawable
                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                            // set image to gameImages
                                                                                                            gameImages.setImageDrawable(d);
                                                                                                            Log.i("GameActivity", "Picture nazi20 loaded");
                                                                                                            ims.close();
                                                                                                        }
                                                                                                        catch(IOException e) {
                                                                                                            e.printStackTrace();
                                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                        }                                                                                                        }
                                                                                                    else{
                                                                                                        if (counter >= round*18){
                                                                                                            try {
                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi19.png");
                                                                                                                // load image as Drawable
                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                // set image to gameImages
                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                Log.i("GameActivity", "Picture nazi19 loaded");
                                                                                                                ims.close();
                                                                                                            }
                                                                                                            catch(IOException e) {
                                                                                                                e.printStackTrace();
                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                            }                                                                                                            }
                                                                                                        else{
                                                                                                            if (counter >= round*17){
                                                                                                                try {
                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi18.png");
                                                                                                                    // load image as Drawable
                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                    // set image to gameImages
                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                    Log.i("GameActivity", "Picture nazi18 loaded");
                                                                                                                    ims.close();
                                                                                                                }
                                                                                                                catch(IOException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                }                                                                                                                }
                                                                                                            else{
                                                                                                                if (counter >= round*16){
                                                                                                                    try {
                                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi17.png");
                                                                                                                        // load image as Drawable
                                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                        // set image to gameImages
                                                                                                                        gameImages.setImageDrawable(d);
                                                                                                                        Log.i("GameActivity", "Picture nazi17 loaded");
                                                                                                                        ims.close();
                                                                                                                    }
                                                                                                                    catch(IOException e) {
                                                                                                                        e.printStackTrace();
                                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                    }
                                                                                                                }
                                                                                                                else{
                                                                                                                    if (counter >= round*15){
                                                                                                                        try {
                                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi16.png");
                                                                                                                            // load image as Drawable
                                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                            // set image to gameImages
                                                                                                                            gameImages.setImageDrawable(d);
                                                                                                                            Log.i("GameActivity", "Picture nazi16 loaded");
                                                                                                                            ims.close();
                                                                                                                        }
                                                                                                                        catch(IOException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                        }                                                                                                                        }
                                                                                                                    else{
                                                                                                                        if (counter >= round*14){
                                                                                                                            try {
                                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi15.png");
                                                                                                                                // load image as Drawable
                                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                // set image to gameImages
                                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                                Log.i("GameActivity", "Picture nazi15 loaded");
                                                                                                                                ims.close();
                                                                                                                            }
                                                                                                                            catch(IOException e) {
                                                                                                                                e.printStackTrace();
                                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                            }                                                                                                                            }
                                                                                                                        else{
                                                                                                                            if (counter >= round*13){
                                                                                                                                try {
                                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi14.png");
                                                                                                                                    // load image as Drawable
                                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                    // set image to gameImages
                                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                                    Log.i("GameActivity", "Picture nazi14 loaded");
                                                                                                                                    ims.close();
                                                                                                                                }
                                                                                                                                catch(IOException e) {
                                                                                                                                    e.printStackTrace();
                                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                }                                                                                                                                }
                                                                                                                            else{
                                                                                                                                if (counter >= round*12){
                                                                                                                                    try {
                                                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi13.png");
                                                                                                                                        // load image as Drawable
                                                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                        // set image to gameImages
                                                                                                                                        gameImages.setImageDrawable(d);
                                                                                                                                        Log.i("GameActivity", "Picture nazi13 loaded");
                                                                                                                                        ims.close();
                                                                                                                                    }
                                                                                                                                    catch(IOException e) {
                                                                                                                                        e.printStackTrace();
                                                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                    }                                                                                                                                    }
                                                                                                                                else{
                                                                                                                                    if (counter >= round*11){
                                                                                                                                        try {
                                                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi12.png");
                                                                                                                                            // load image as Drawable
                                                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                            // set image to gameImages
                                                                                                                                            gameImages.setImageDrawable(d);
                                                                                                                                            Log.i("GameActivity", "Picture nazi12 loaded");
                                                                                                                                            ims.close();
                                                                                                                                        }
                                                                                                                                        catch(IOException e) {
                                                                                                                                            e.printStackTrace();
                                                                                                                                        }                                                                                                                                        }
                                                                                                                                    else{
                                                                                                                                        if (counter >= round*10){
                                                                                                                                            try {
                                                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi11.png");
                                                                                                                                                // load image as Drawable
                                                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                // set image to gameImages
                                                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                                                Log.i("GameActivity", "Picture nazi11 loaded");
                                                                                                                                                ims.close();
                                                                                                                                            }
                                                                                                                                            catch(IOException e) {
                                                                                                                                                e.printStackTrace();
                                                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                            }                                                                                                                                            }
                                                                                                                                        else{
                                                                                                                                            if (counter >= round*9){
                                                                                                                                                try {
                                                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi10.png");
                                                                                                                                                    // load image as Drawable
                                                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                    // set image to gameImages
                                                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                                                    Log.i("GameActivity", "Picture nazi10 loaded");
                                                                                                                                                    ims.close();
                                                                                                                                                }
                                                                                                                                                catch(IOException e) {
                                                                                                                                                    e.printStackTrace();
                                                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                }                                                                                                                                                }
                                                                                                                                            else{
                                                                                                                                                if (counter >= round*8){
                                                                                                                                                    try {
                                                                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi9.png");
                                                                                                                                                        // load image as Drawable
                                                                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                        // set image to gameImages
                                                                                                                                                        gameImages.setImageDrawable(d);
                                                                                                                                                        Log.i("GameActivity", "Picture nazi9 loaded");
                                                                                                                                                        ims.close();
                                                                                                                                                    }
                                                                                                                                                    catch(IOException e) {
                                                                                                                                                        e.printStackTrace();
                                                                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                    }                                                                                                                                                    }
                                                                                                                                                else{
                                                                                                                                                    if (counter >= round*7){
                                                                                                                                                        try {
                                                                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi8.png");
                                                                                                                                                            // load image as Drawable
                                                                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                            // set image to gameImages
                                                                                                                                                            gameImages.setImageDrawable(d);
                                                                                                                                                            Log.i("GameActivity", "Picture nazi8 loaded");
                                                                                                                                                            ims.close();
                                                                                                                                                        }
                                                                                                                                                        catch(IOException e) {
                                                                                                                                                            e.printStackTrace();
                                                                                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                    else{
                                                                                                                                                        if (counter >=round*6){
                                                                                                                                                            try {
                                                                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi7.png");
                                                                                                                                                                // load image as Drawable
                                                                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                // set image to gameImages
                                                                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                                                                Log.i("GameActivity", "Picture nazi7 loaded");
                                                                                                                                                                ims.close();
                                                                                                                                                            }
                                                                                                                                                            catch(IOException e) {
                                                                                                                                                                e.printStackTrace();
                                                                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                            }                                                                                                                                                            }
                                                                                                                                                        else{
                                                                                                                                                            if (counter >= round*5){
                                                                                                                                                                try {
                                                                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi6.png");
                                                                                                                                                                    // load image as Drawable
                                                                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                    // set image to gameImages
                                                                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                                                                    Log.i("GameActivity", "Picture nazi6 loaded");
                                                                                                                                                                    ims.close();
                                                                                                                                                                }
                                                                                                                                                                catch(IOException e) {
                                                                                                                                                                    e.printStackTrace();
                                                                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                }                                                                                                                                                                }
                                                                                                                                                            else{
                                                                                                                                                                if (counter >= round*4){
                                                                                                                                                                    try {
                                                                                                                                                                        InputStream ims = getAssets().open("img/nazi_skin/nazi5.png");
                                                                                                                                                                        // load image as Drawable
                                                                                                                                                                        Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                        // set image to gameImages
                                                                                                                                                                        gameImages.setImageDrawable(d);
                                                                                                                                                                        Log.i("GameActivity", "Picture nazi5 loaded");
                                                                                                                                                                        ims.close();
                                                                                                                                                                    }
                                                                                                                                                                    catch(IOException e) {
                                                                                                                                                                        e.printStackTrace();
                                                                                                                                                                        Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                                else{
                                                                                                                                                                    if (counter >= round*3){
                                                                                                                                                                        try {
                                                                                                                                                                            InputStream ims = getAssets().open("img/nazi_skin/nazi4.png");
                                                                                                                                                                            // load image as Drawable
                                                                                                                                                                            Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                            // set image to gameImages
                                                                                                                                                                            gameImages.setImageDrawable(d);
                                                                                                                                                                            Log.i("GameActivity", "Picture nazi4 loaded");
                                                                                                                                                                            ims.close();
                                                                                                                                                                        }
                                                                                                                                                                        catch(IOException e) {
                                                                                                                                                                            e.printStackTrace();
                                                                                                                                                                            Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                        }                                                                                                                                                                        }
                                                                                                                                                                    else{
                                                                                                                                                                        if (counter >= round*2){
                                                                                                                                                                            try {
                                                                                                                                                                                InputStream ims = getAssets().open("img/nazi_skin/nazi3.png");
                                                                                                                                                                                // load image as Drawable
                                                                                                                                                                                Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                                // set image to gameImages
                                                                                                                                                                                gameImages.setImageDrawable(d);
                                                                                                                                                                                Log.i("GameActivity", "Picture nazi3 loaded");
                                                                                                                                                                                ims.close();
                                                                                                                                                                            }
                                                                                                                                                                            catch(IOException e) {
                                                                                                                                                                                e.printStackTrace();
                                                                                                                                                                                Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                        else{
                                                                                                                                                                            if (counter >= round*1){
                                                                                                                                                                                try {
                                                                                                                                                                                    InputStream ims = getAssets().open("img/nazi_skin/nazi2.png");
                                                                                                                                                                                    // load image as Drawable
                                                                                                                                                                                    Drawable d = Drawable.createFromStream(ims, null);
                                                                                                                                                                                    // set image to gameImages
                                                                                                                                                                                    gameImages.setImageDrawable(d);
                                                                                                                                                                                    Log.i("GameActivity", "Picture nazi2 loaded");
                                                                                                                                                                                    ims.close();
                                                                                                                                                                                }
                                                                                                                                                                                catch(IOException e) {
                                                                                                                                                                                    e.printStackTrace();
                                                                                                                                                                                    Log.w("GameActivity", "IOException occured in gameOver()");
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                            else{

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



}
