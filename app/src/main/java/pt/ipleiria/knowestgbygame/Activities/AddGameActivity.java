package pt.ipleiria.knowestgbygame.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Helpers.HelperMethods;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.ChallengesManager;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.Models.GamesManager;
import pt.ipleiria.knowestgbygame.Models.SessionManager;
import pt.ipleiria.knowestgbygame.R;

public class AddGameActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDesc;
    private ImageView imageGame;
    private Game game;
    private Button btnCreateGame;
    private TextView selectedChallenges;
    private int position;

    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        this.setTitle("Criar Novo Jogo");
        editTextTitle  = findViewById(R.id.editText_game_title);
        editTextDesc = findViewById(R.id.editText_game_description);
        imageGame = findViewById(R.id.img_game_thumbnail);
        selectedChallenges = findViewById(R.id.selected_challenges);
        btnCreateGame = findViewById(R.id.btn_create_new_game);

        listItems = ChallengesManager.manager().getChallengsNames();
        checkedItems = new boolean[listItems.length];

        Intent intent = getIntent();
        game = (Game) intent.getSerializableExtra(Constant.GAME_TO_EDIT);
        position = intent.getIntExtra(Constant.POSITION, -1);
        if (game != null ) {
            btnCreateGame.setText(getString(R.string.edit));
            this.setTitle(R.string.edit_game);
            editTextTitle.setText(game.getTitle());
            editTextDesc.setText(game.getDescription());
            if (game.getThumbnail() > 0) {
                imageGame.setImageResource(game.getThumbnail());
            }
            mUserItems = ChallengesManager.manager().getPositionOfSelectedsChallengs(game.getChallenges());
            for (int i = 0; i < mUserItems.size(); i++) {
                checkedItems[mUserItems.get(i)] = true;
            }
            populateSelectedChallenges();
        }
    }

    public void btnPickImage(View view) {
        HelperMethods.optionToChoosePicture(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get result of image
        if (requestCode == Constant.GALLERY_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null) {
            //uploadImage(data.getData());
            Toast.makeText(this, "Imagem da biblioteca carregada com suceeso", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Constant.CAMERA_IMAGE_REQUEST && resultCode == this.RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", HelperMethods.getCameraFile(this));
            Toast.makeText(this, "Imagem da camera carregada com suceeso", Toast.LENGTH_SHORT).show();
            //uploadImage(photoUri);
        }
    }

    public void btnNewGame(View view) {

        String title = editTextTitle.getText().toString();
        String description = editTextDesc.getText().toString();
        int thumb =  R.drawable.ic_launcher_foreground;
        Intent returnIntent = new Intent();
        int pos = 0;
        if (title.isEmpty()) {
            editTextTitle.setError("Título é obrigatório");
            editTextTitle.requestFocus();
            return;
        }
        if (description.isEmpty()) {
            editTextDesc.setError("Descrição é obrigatório");
            editTextDesc.requestFocus();
            return;
        }

        if (game != null) {
            GamesManager.manager().getGames().get(position).setTitle(title);
            GamesManager.manager().getGames().get(position).setDescription(description);
            GamesManager.manager().getGames().get(position).setChallenges(getSelectedsChallenges());
            pos = position;

        } else {
            game = new Game(title, description, getSelectedsChallenges(), SessionManager.manager().getUser().getName());
            GamesManager.manager().addGameAtPosition(0, game);
        }

        if (thumb > 0) {
            GamesManager.manager().getGames().get(pos).setThumbnail(thumb);
        }

        setResult(RESULT_OK, returnIntent);
        finish();

    }

    public void btnAssociateChallenge(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddGameActivity.this);
        mBuilder.setTitle("Desafios disponíveis");

        //select challenge
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if(isChecked){
                    mUserItems.add(position);
                } else{
                    mUserItems.remove((Integer.valueOf(position)));
                    if (mUserItems.size() <= 0) {
                        selectedChallenges.setVisibility(View.GONE);
                    }
                }
            }
        });

        //confirm select
        mBuilder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                populateSelectedChallenges();
            }
        });

        //clear
        mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    selectedChallenges.setVisibility(View.GONE);
                    mUserItems.clear();
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

    public ArrayList<Challenge> getSelectedsChallenges(){
        ArrayList<Challenge> list = new ArrayList<>();
        for (int i = 0; i < mUserItems.size(); i++) {
            list.add(ChallengesManager.manager().getChallenges().get(mUserItems.get(i)));
            System.out.println("\n" + ChallengesManager.manager().getChallenges().get(mUserItems.get(i)).getTitle());
        }
        return  list;
    }

    public void populateSelectedChallenges(){
        String item = "Lista de desafios a associar: \n_________________________________________\n\n";
        for (int i = 0; i < mUserItems.size(); i++) {
            item = item + listItems[mUserItems.get(i)];
            if (i != mUserItems.size() - 1) {
                item = item + "\n\n";
            }
        }
        selectedChallenges.setText(item);
        if (mUserItems.size() > 0) {
            selectedChallenges.setVisibility(View.VISIBLE);
        }
    }
}
