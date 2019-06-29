package pt.ipleiria.knowestgbygame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.R;

public class GameActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewDesc;
    private ImageView imageViewThumb;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewTitle = (TextView) findViewById(R.id.challenge_item_title);
        textViewDesc = (TextView) findViewById(R.id.challenge_item_descr);
        imageViewThumb = (ImageView) findViewById(R.id.challenge_item_thumbnail);


        //receive data
        Intent intent = getIntent();
       game = (Game)intent.getSerializableExtra(Constant.GAME);

        //setting values
        textViewTitle.setText(game.getTitle());
        imageViewThumb.setImageResource(game.getThumbnail());
        textViewDesc.setText(game.getDescription());


    }

    public void btnPlay(View view) {
        Intent intent = new Intent(this, ChallengeActivity.class);

        intent.putExtra(Constant.GAME, game);
        this.startActivity(intent);

    }

    public void btnBack(View view) {
        super.onBackPressed();
    }

    public void btnShare(View view) {
        Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
    }

    public void btnPause(View view) {
        Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
    }
}
