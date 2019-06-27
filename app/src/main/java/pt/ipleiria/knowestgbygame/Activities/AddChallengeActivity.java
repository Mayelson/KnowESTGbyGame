package pt.ipleiria.knowestgbygame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Helpers.HelperMethods;
import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.ChallengesManager;
import pt.ipleiria.knowestgbygame.R;

public class AddChallengeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {


    private EditText editTextTitle, editTextDesc, editTextTime, editTextScore, editTextSuggestion, editTextAnswer;
    private ImageView imageGame;
    private Challenge challenge;
    private Spinner spinnerAnswerType;
    private Button btn_create;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);
        this.setTitle(R.string.new_challenge);
        editTextTitle  = findViewById(R.id.editText_challenge_title);
        editTextDesc = findViewById(R.id.editText_challenge_description);
        editTextSuggestion = findViewById(R.id.editText_challenge_sugestion);
        editTextAnswer = findViewById(R.id.editText_challenge_answer);
        editTextTime = findViewById(R.id.editText_challenge_time);
        editTextScore = findViewById(R.id.editText_challenge_points);
        imageGame = findViewById(R.id.img_challenge_thumbnail);
        btn_create = findViewById(R.id.btn_create_new_challenge);

        populateSpinner();

        Intent i = getIntent();
        challenge = (Challenge) i.getSerializableExtra(Constant.CHALLENGE_TO_EDIT);
        position = i.getIntExtra(Constant.POSITION, -1);

        if (challenge != null ) {
            btn_create.setText(getString(R.string.edit));
            this.setTitle(R.string.edit_challenge);

            editTextTitle.setText(challenge.getTitle());
            editTextDesc.setText(challenge.getDescription());
            if (challenge.getSuggestion() != null){
                editTextSuggestion.setText(challenge.getSuggestion());
            }
            if (challenge.getAnswer() != null){
                editTextAnswer.setText(challenge.getAnswer());
            }
            if (challenge.getThumbnail() > 0) {
                imageGame.setImageResource(challenge.getThumbnail());
            }

            spinnerAnswerType.setSelection(HelperMethods.getPositionByAnswerType(challenge.getAnswerType()));
            editTextTime.setText(Long.toString(challenge.getTime()));
            editTextScore.setText(Long.toString(challenge.getPoints()));

            //imageGame.setText(challenge.getTitle());

        }
    }

    public void populateSpinner() {
        spinnerAnswerType = findViewById(R.id.spinner_answerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.answerType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnswerType.setAdapter(adapter);
        spinnerAnswerType.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // category = parent.getItemAtPosition(position).toString();
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
        long points = Integer.parseInt(editTextScore.getText().toString());
        long time = Long.parseLong(editTextTime.getText().toString());
        String description = editTextDesc.getText().toString();
        String suggestion = editTextSuggestion.getText().toString();
        String answer = editTextAnswer.getText().toString();
        AnswerType answerType = HelperMethods.getCategory(spinnerAnswerType.getSelectedItemPosition());
        int thumb =  R.drawable.ic_challenge;

        Intent returnIntent = new Intent();
        int pos = 0;

        if (challenge != null) {
            ChallengesManager.manager().getChallenges().get(position).setTitle(title);
            ChallengesManager.manager().getChallenges().get(position).setPoints(points);
            ChallengesManager.manager().getChallenges().get(position).setTime(time);
            ChallengesManager.manager().getChallenges().get(position).setDescription(description);
            ChallengesManager.manager().getChallenges().get(position).setAnswerType(answerType);
            pos = position;

        } else {
            challenge = new Challenge(title, description, time, answerType, points);
            //returnIntent.putExtra(Constant.CHALLENGE_TO_ADD, challenge);
            ChallengesManager.manager().addChallengeAtPosition(0, challenge);

        }

        if (!suggestion.isEmpty()){
            ChallengesManager.manager().getChallenges().get(pos).setSuggestion(suggestion);
        }
        if (!answer.isEmpty()) {
            ChallengesManager.manager().getChallenges().get(pos).setAnswer(answer);
        }
        if (thumb > 0) {
            ChallengesManager.manager().getChallenges().get(pos).setThumbnail(thumb);
        }





        //returnIntent.putExtra(Constant.CHALLENGE_TO_ADD, challenge);
        setResult(RESULT_OK, returnIntent);
        finish();
    }


    public void setDataFromForm() {

    }

}
