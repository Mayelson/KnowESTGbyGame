package pt.ipleiria.knowestgbygame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.Fragments.ChallengeFragment;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.R;

public class AddGameActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewDesc;
    private ImageView imageGame;
    private Game game;
    private ListView listViewChallenges;
    private ArrayList<Challenge> challenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        this.setTitle("Criar Novo Jogo");
        textViewTitle  = findViewById(R.id.editText_game_title);
        textViewDesc = findViewById(R.id.editText_game_description);
        imageGame = findViewById(R.id.img_game_thumbnail);
        listViewChallenges = findViewById(R.id.listView_challenges_associated);

    }

    public void btnPickImage(View view) {


    }

    public void btnNewGame(View view) {

    }

    public void btnAssociateChallenge(View view) {
        //Intent intent = new Intent(this, ChallengeFragment.class);
        //startActivity(intent);
    }
}
