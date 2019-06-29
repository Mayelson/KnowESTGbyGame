package pt.ipleiria.knowestgbygame.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.R;

public class AllChallengeActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewDesc;
    private ImageView imageGame;
    private Game game;
    private ListView listViewChallenges;
    private ArrayList<Challenge> challenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);


    }
}