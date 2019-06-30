package pt.ipleiria.knowestgbygame.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.common.base.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pt.ipleiria.knowestgbygame.Fragments.ActivityRecognitionFragment;
import pt.ipleiria.knowestgbygame.Fragments.LabelFragment;
import pt.ipleiria.knowestgbygame.Fragments.NotFoundFragment;
import pt.ipleiria.knowestgbygame.Fragments.NumberFragment;
import pt.ipleiria.knowestgbygame.Fragments.QrcodeFragment;
import pt.ipleiria.knowestgbygame.Fragments.TextFragment;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.Models.GamesManager;
import pt.ipleiria.knowestgbygame.Models.SessionManager;
import pt.ipleiria.knowestgbygame.R;

public class ChallengeActivity extends AppCompatActivity {


    private TextView totalConcluded, timeLeft, challengeTitle, challengeDescription, answer;
    private List<Challenge> challenges;
    private Game game;
    private  long timeLeftInMilliseconds;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private Challenge currentChallenge;
    private int currentPosition;
    private int point = 0;
    private ImageView challengeStatus;
    private ImageButton pause;
    private ImageButton imageButtonNext, imageButtonHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //GET XML ELEMENTS
        totalConcluded = findViewById(R.id.total_challenge);
        timeLeft = findViewById(R.id.challenge_time);
        challengeTitle = findViewById(R.id.challenge_item_title);
        challengeDescription = findViewById(R.id.challenge_item_descr);

        pause =  findViewById(R.id.pause);
        answer = findViewById(R.id.challenge_answer);
        imageButtonHelp = findViewById(R.id.help);
        imageButtonNext = findViewById(R.id.next);
        challengeStatus = findViewById(R.id.challenge_status);
        currentPosition = 0;


        Intent intent = getIntent();
        String uuid = (String)intent.getSerializableExtra(Constant.GAME_UUID);
        game = GamesManager.manager().getGamesByUid(uuid);
        if (game != null && game.getChallenges().size() > 0){
            challenges = game.getChallenges();
            startChallenge();
        } else {
            Toast.makeText(this, "Não foi possivel obter o jogo selecionado!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public boolean verifyFaceAnser(String result){

        String text1 = currentChallenge.getAnswer()+":very_likely";
        String text2 = currentChallenge.getAnswer()+":likely";

        return currentChallenge.getAnswer() != null && (result.contains(text1) || result.contains(text2));
    }

    public void getAnswer(String result) {
        if (!timerRunning){
            return;
        }
        boolean ok = false;
        switch(currentChallenge.getAnswerType()) {
            case FITRUN:
                Toast.makeText(this, "Activity Recongition", Toast.LENGTH_SHORT).show();
                break;
            case FACEDETECTION:
                if (verifyFaceAnser(result.toLowerCase())){
                    ok = true;
                }
                break;
            case LOGODETECTION:
                if (currentChallenge.getAnswer() != null && result.toLowerCase().contains(currentChallenge.getAnswer().toLowerCase())){
                    ok = true;
                }
                break;
            case QRCODE:
                if (currentChallenge.getAnswer() != null && result.toLowerCase().contains(currentChallenge.getAnswer().toLowerCase())){
                    ok = true;
                }
                break;
            case TEXT:
                if (currentChallenge.getAnswer() != null && result.toLowerCase().contains(currentChallenge.getAnswer().toLowerCase())){
                    ok = true;
                }
                break;
            case NUMBER:
                if (currentChallenge.getAnswer() != null && result.equalsIgnoreCase(currentChallenge.getAnswer())){
                    point += currentChallenge.getPoints();
                    challengeStatus.setImageResource(R.drawable.ic_check_circle);
                    ok = true;
                }
                break;
            case OBJECTDETECTION:
                String[] textOptions = result.split(":");
                for (String text : textOptions)
                {
                    if ( text.toLowerCase().indexOf("keyboard".toLowerCase()) != -1 ) {
                        ok = true;
                        break;
                    }
                }
                break;
        }

        if (ok){
            point += currentChallenge.getPoints();
            challengeStatus.setImageResource(R.drawable.ic_check_circle);
        } else {
            challengeStatus.setImageResource(R.drawable.ic_cancel);
        }
        challengeStatus.setVisibility(View.VISIBLE);
        imageButtonNext.setVisibility(View.VISIBLE);
    }


    public void startChallenge(){
        currentChallenge = challenges.get(currentPosition);
        timeLeftInMilliseconds = currentChallenge.getTime();
        challengeTitle.setText(currentChallenge.getTitle());
        challengeDescription.setText(currentChallenge.getDescription());
        totalConcluded.setText(currentPosition +1 + "/" + challenges.size());
        if (currentChallenge.getSuggestion() == null){
            imageButtonHelp.setVisibility(View.GONE);
        }

        startTime();
        imageButtonNext.setVisibility(View.INVISIBLE);

        switch(currentChallenge.getAnswerType()) {
            case QRCODE:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new QrcodeFragment()).commit();
                break;
            case TEXT:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new TextFragment()).commit();
                break;
            case NUMBER:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new NumberFragment()).commit();
                break;
            case FITRUN:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new ActivityRecognitionFragment()).commit();
                break;
            case MAP:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new NotFoundFragment()).commit();
                break;
            case OBJECTDETECTION:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new LabelFragment()).commit();
                break;
            case LOGODETECTION:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new LabelFragment()).commit();
                break;
            case FACEDETECTION:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new LabelFragment()).commit();
                break;
        }
    }

    private void startTime() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerRunning = true;
        pause.setImageResource(R.drawable.ic_custom_pause);
    }

    private void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds  = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) {
            timeLeftText += "0";
        }

        timeLeftText += seconds;


        if (seconds == 0  && minutes == 0){
            timerRunning = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Fim do tempo")
                    .setMessage("Pretende continuar para o próximo desafio?");
            // Add the buttons
            builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    cancelGame();
                }
            });
            builder.setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    nextChallenge();
                }
            });
            builder.show();
        } else {
            timeLeft.setText(timeLeftText);
        }
    }


    public void cancelGame(){
        point = 0;
        stopTimer();
        finish();
    }

    public void nextChallenge() {
        challengeStatus.setVisibility(View.GONE);
        currentPosition = currentPosition + 1;
        if (currentPosition == challenges.size()){
            stopTimer();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.game_end)
                    .setMessage("Parabéns!\nPontos ganhos: "+ point);
            // Add the buttons
            builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.show();

            SessionManager.manager().getUser().setPoints(SessionManager.manager().getUser().getPoints()+point);
            SessionManager.manager().getUser().addGamePlayed(game);
        } else {
            currentChallenge = challenges.get(currentPosition);
            timeLeftInMilliseconds = currentChallenge.getTime();
            stopTimer();
            startChallenge();
        }
    }

    private  void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        pause.setImageResource(R.drawable.ic_custom_play);
    }


    public void back(View view) {
        stopTimer();
        super.onBackPressed();
    }

    public void btnPause(View view) {
        if (timerRunning) {
            stopTimer();
        } else {
            startTime();
        }
    }

    public void btnHelp(View view) {
        stopTimer();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.sugestion).setMessage(currentChallenge.getSuggestion());
        // Add the buttons
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startTime();
            }
        });
        builder.show();
    }

    public void btnNext(View view) {
        nextChallenge();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopTimer();
    }

    public AnswerType currentAnseAnswerType(){
        return currentChallenge.getAnswerType();
    }
}
