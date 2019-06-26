package pt.ipleiria.knowestgbygame.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.Models.Sugestion;
import pt.ipleiria.knowestgbygame.R;

public class AddChallengeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private EditText editTextTitle, editTextDesc, editTextTime, editTextScore, editTextSugestion;
    private ImageView imageGame;
    private ListView listViewChallenges;
    private Challenge challenge;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);
        this.setTitle("Criar Novo Desafio");
        editTextTitle  = findViewById(R.id.editText_challenge_title);
        editTextDesc = findViewById(R.id.editText_challenge_description);
        editTextSugestion = findViewById(R.id.editText_challenge_sugestion);
        editTextTime = findViewById(R.id.editText_challenge_time);
        editTextScore = findViewById(R.id.editText_challenge_points);
        imageGame = findViewById(R.id.img_game_thumbnail);
        listViewChallenges = findViewById(R.id.listView_challenges_associated);

        populateSpinner();
    }

    public void populateSpinner() {
        spinner = findViewById(R.id.spinner_answerType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.answerType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String sSelected=parent.getItemAtPosition(position).toString();
        //Toast.makeText(this,sSelected,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void btnPickImage(View view) {


    }



    public void btnAssociateChallenge(View view) {
        //Intent intent = new Intent(this, ChallengeFragment.class);
        //startActivity(intent);
    }

    public void btnNewChallenge(View view) {
        //get data from form
        String title = editTextTitle.getText().toString();
        String description = editTextDesc.getText().toString();
        String sugestion = editTextSugestion.getText().toString();
        long time = Long.parseLong(editTextTime.getText().toString());
        int points = Integer.parseInt(editTextScore.getText().toString());
       // AnswerType sd;

       // Switch()
        //if spinner.getSelectedItem();
        //Alterar para o codigo da imagem
        int thumb = 1;
        //String sugestion = textViewTitle.getText().toString();
        //challenge = new Challenge(title,  description, thumb, time, sugestion, answerType);
    }
}
