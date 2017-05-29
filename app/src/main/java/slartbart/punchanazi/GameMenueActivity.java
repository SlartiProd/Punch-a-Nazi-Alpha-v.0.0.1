package slartbart.punchanazi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by slartibartfast on 22.05.17.
 */

public class GameMenueActivity extends Activity {
    MediaPlayer mediaPlayerGameMenue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menue_activity);
        Button buttonCompetitv = (Button) findViewById(R.id.button_competetive);
        Button buttonRelaxed = (Button) findViewById(R.id.button_relaxed);
        Button buttonBacktoStartScreen = (Button)findViewById(R.id.button_back);
        mediaPlayerGameMenue = new MediaPlayer().create(GameMenueActivity.this, R.raw.menuemusic);


        buttonCompetitv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(GameMenueActivity.this, GameActivity.class);
                startActivityForResult(gameIntent, 1);
                mediaPlayerGameMenue.pause();
                mediaPlayerGameMenue.stop();
            }
        });

        buttonRelaxed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent relaxedIntent = new Intent(GameMenueActivity.this, RelaxedActivity.class);
                startActivity(relaxedIntent);
                mediaPlayerGameMenue.pause();
                mediaPlayerGameMenue.stop();
            }
        });

        buttonBacktoStartScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView textViewHighscore = (TextView) findViewById(R.id.textView_highscore);
        textViewHighscore.setText(Integer.toString(readHighscore()));
        mediaPlayerGameMenue = new MediaPlayer().create(GameMenueActivity.this, R.raw.menuemusic);

        mediaPlayerGameMenue.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode > readHighscore()) {
                writeHighscore(resultCode);
            }
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        mediaPlayerGameMenue.stop();
    }

    @Override
    public void onDestroy(){
        mediaPlayerGameMenue.release();
        super.onDestroy();
    }


    public int readHighscore() {
        SharedPreferences preferenceHighscore = getSharedPreferences("HIGHSCORE", 0);
        return preferenceHighscore.getInt("HIGHSCORE", 0);
    }

    private void writeHighscore(int highscore) {
        SharedPreferences preferenceHighscore = getSharedPreferences("HIGHSCORE", 0);
        SharedPreferences.Editor editorHighscore = preferenceHighscore.edit();
        editorHighscore.putInt("HIGHSCORE", highscore);
        editorHighscore.commit();

    }

}