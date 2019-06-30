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
import pt.ipleiria.knowestgbygame.Models.GamesManager;
import pt.ipleiria.knowestgbygame.R;

public class GameActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewDesc;
    private ImageView imageViewThumb;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewTitle = findViewById(R.id.challenge_item_title);
        textViewDesc = findViewById(R.id.challenge_item_descr);
        imageViewThumb = findViewById(R.id.challenge_item_thumbnail);


        //receive data
        Intent intent = getIntent();
        String uuid = (String)intent.getSerializableExtra(Constant.GAME_UUID);
        game = GamesManager.manager().getGamesByUid(uuid);
        if (game != null){
            textViewTitle.setText(game.getTitle());
            imageViewThumb.setImageResource(game.getThumbnail());
            textViewDesc.setText(game.getDescription());
        } else {
            Toast.makeText(this, "Não foi possivel obter o jogo selecionado!", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnPlay(View view) {
        if (game != null ){
            Intent intent = new Intent(this, ChallengeActivity.class);
            intent.putExtra(Constant.GAME_UUID, game.getUuid());
            this.startActivity(intent);
        } else{
            Toast.makeText(this, "Jogo não encontrado! Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
        }

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
