package slartbart.punchanazi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayerMenue;
    private Animation animationFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation);

        Button buttonGame = (Button)findViewById(R.id.button_game);
        Button buttonInformation = (Button)findViewById(R.id.button_information);
        Button buttonFinish = (Button) findViewById(R.id.button_finish);


        mediaPlayerMenue = new MediaPlayer().create(MainActivity.this, R.raw.menuemusic);

        buttonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameMenueIntent = new Intent(MainActivity.this, GameMenueActivity.class);
                mediaPlayerMenue.stop();
                startActivity(gameMenueIntent);
            }
        });

        buttonInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent informationIntent = new Intent(MainActivity.this, InformationActivity.class);
                mediaPlayerMenue.stop();
                startActivity(informationIntent);
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onPause(){
        super.onPause();
        mediaPlayerMenue.stop();
    }

    @Override
    public void onResume(){
        mediaPlayerMenue = new MediaPlayer().create(MainActivity.this, R.raw.menuemusic);
        mediaPlayerMenue.setLooping(true);
        mediaPlayerMenue.start();
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }

    @Override
    public void onDestroy(){
        mediaPlayerMenue.release();
        super.onDestroy();
    }

}
