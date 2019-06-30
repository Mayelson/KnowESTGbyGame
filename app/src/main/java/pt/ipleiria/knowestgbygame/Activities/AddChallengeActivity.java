package pt.ipleiria.knowestgbygame.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Spinner spinnerAnswerType, spinnerAnswers;
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
        populateSpinnerAnswer();


        Intent i = getIntent();
        challenge = (Challenge) i.getSerializableExtra(Constant.CHALLENGE_TO_EDIT);
        position = i.getIntExtra(Constant.POSITION, -1);

        if  (challenge != null ) {
            btn_create.setText(getString(R.string.edit));
            this.setTitle(R.string.edit_challenge);

            editTextTitle.setText(challenge.getTitle());
            editTextDesc.setText(challenge.getDescription());
            if (challenge.getSuggestion() != null){
                editTextSuggestion.setText(challenge.getSuggestion());
            }

            if (challenge.getThumbnail() > 0) {
                imageGame.setImageResource(challenge.getThumbnail());
            }

            spinnerAnswerType.setSelection(HelperMethods.getPositionByAnswerType(challenge.getAnswerType()));
            if (challenge.getAnswerType() == AnswerType.FACEDETECTION){
                spinnerAnswers.setVisibility(View.VISIBLE);
                editTextAnswer.setVisibility(View.GONE);
                spinnerAnswers.setSelection(HelperMethods.getPositionOfAnswers(challenge.getAnswer()));

            } else {
                if (challenge.getAnswer() != null){
                    editTextAnswer.setText(challenge.getAnswer());
                }
            }

            double time = challenge.getTime()/60000.0;
            editTextTime.setText(Double.toString(time));
            editTextScore.setText(Long.toString(challenge.getPoints()));
        }

    }

    public void populateSpinner() {
        spinnerAnswerType = findViewById(R.id.spinner_answerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.answerType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnswerType.setAdapter(adapter);
        spinnerAnswerType.setOnItemSelectedListener(this);
    }

    public void populateSpinnerAnswer(){
        spinnerAnswers = findViewById(R.id.spinner_answers);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.answers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnswers.setAdapter(adapter);
        spinnerAnswers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 6){
            spinnerAnswers.setVisibility(View.VISIBLE);
            editTextAnswer.setVisibility(View.GONE);
        } else {
            if (editTextAnswer.getVisibility() == view.GONE){
                spinnerAnswers.setVisibility(View.GONE);
                editTextAnswer.setVisibility(View.VISIBLE);
            }
        }
      }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void btnPickImage(View view) {
        HelperMethods.optionToChoosePicture(this);
    }


    public void btnNewChallenge(View view) {
        //get data from form
        String title = editTextTitle.getText().toString();
        String description = editTextDesc.getText().toString();

        if (title.trim().isEmpty()) {
            editTextTitle.setError("Título é obrigatório");
            editTextTitle.requestFocus();
            return;
        }

        if (editTextScore.getText().toString().isEmpty()) {
            editTextScore.setError("Pontos é obrigatório");
            editTextScore.requestFocus();
            return;
        }

        if (editTextTime.getText().toString().isEmpty()) {
            editTextTime.setError("Tempo é obrigatório");
            editTextTime.requestFocus();
            return;
        }

        if (description.trim().isEmpty()) {
            editTextDesc.setError("Descrição é obrigatório");
            editTextDesc.requestFocus();
            return;
        }


        if (spinnerAnswerType.getSelectedItemPosition() == 0){
            HelperMethods.showAlertInfo("Tipo de respota", "Deve indicar um tipo de resposta", this);
            return;
        }


        AnswerType answerType = HelperMethods.getCategory(spinnerAnswerType.getSelectedItemPosition());
        String answer;
        if (answerType == AnswerType.FACEDETECTION){
            if (spinnerAnswers.getSelectedItemPosition() == 0){
                HelperMethods.showAlertInfo("Resposta", "Deve indicar uma resposta", this);
                return;
            }

            answer = HelperMethods.getAnswerOfPosition(spinnerAnswers.getSelectedItemPosition());
        } else {
            answer = editTextAnswer.getText().toString();
        }

        long points = Long.parseLong(editTextScore.getText().toString());
        long time = ((long)Float.parseFloat(editTextTime.getText().toString()))*60000;
        String suggestion = editTextSuggestion.getText().toString();
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
}
