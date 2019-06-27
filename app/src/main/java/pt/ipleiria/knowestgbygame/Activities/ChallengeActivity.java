package pt.ipleiria.knowestgbygame.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.List;

import pt.ipleiria.knowestgbygame.Fragments.LabelFragment;
import pt.ipleiria.knowestgbygame.Fragments.NotFoundFragment;
import pt.ipleiria.knowestgbygame.Fragments.NumberFragment;
import pt.ipleiria.knowestgbygame.Fragments.QrcodeFragment;
import pt.ipleiria.knowestgbygame.Fragments.TextFragment;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.R;

public class ChallengeActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST = 200;
    private static final String TAG = ChallengeActivity.class.getSimpleName();

    private TextView totalConcluded, timeLeft, challengeTitle, challengeDescription, answer;
    private List<Challenge> challenges;
    private Game game;
    private  long timeLeftInMilliseconds;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private Challenge currentChallenge;
    private int currentPosition = 0;

    private SurfaceView cameraView;
    private BarcodeDetector barcode;
    private CameraSource cameraSource;
    private SurfaceHolder surfaceHolder;

    private ImageButton pause;
    private CardView challengeCardView;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //get data from intent
        Intent intent = getIntent();
        game = (Game)intent.getSerializableExtra(Constant.GAME);
        challenges = game.getChallenges();


        //GET XML ELEMENTS
        totalConcluded = findViewById(R.id.total_challenge);
        timeLeft = findViewById(R.id.challenge_time);
        challengeTitle = findViewById(R.id.challenge_item_title);
        challengeDescription = findViewById(R.id.challenge_item_descr);
        cameraView = findViewById(R.id.cameraView);
        pause =  findViewById(R.id.pause);
        answer = findViewById(R.id.challenge_answer);
        challengeCardView = findViewById(R.id.cardview_item);
        imageButton = findViewById(R.id.next);


        startChallenge();
    }

    public void getAnswer(String result, int number, AnswerType type) {
        switch(type) {
            case QRCODE:

                break;
            case TEXT:

                break;
            case NUMBER:
                result = Integer.toString(number);
                break;
            case FITRUN:

                break;
            case MAP:

                break;
            case OBJECTDETECTION:
                //
                String[] textOptions = result.split(":");
                for (String text : textOptions)
                {
                    if ( text.toLowerCase().indexOf("keyboard".toLowerCase()) != -1 ) {
                        result = "Foto correcta";
                        break;
                    }
                }
                break;
        }
        Toast.makeText(this, "Challenge get data from fragment: " + result, Toast.LENGTH_SHORT).show();
        imageButton.setVisibility(View.VISIBLE);
    }


    public void startChallenge(){
        currentChallenge = challenges.get(currentPosition);
        timeLeftInMilliseconds = currentChallenge.getTime();
        challengeTitle.setText(currentChallenge.getTitle());
        challengeDescription.setText(currentChallenge.getDescription());
        totalConcluded.setText(currentPosition +1 + "/" + challenges.size());

        startTime();
        imageButton.setVisibility(View.INVISIBLE);

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new NotFoundFragment()).commit();
                break;
            case MAP:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new NotFoundFragment()).commit();
                break;
            case OBJECTDETECTION:
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Fim do tempo")
                    .setMessage("Pretende continuar para o próximo desafio?");
            // Add the buttons
            builder.setPositiveButton(R.string.close, null);
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

    public void nextChallenge() {
        currentPosition = currentPosition + 1;
        if (currentPosition == challenges.size()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.game_end)
                    .setMessage("Parabéns, Ganhaste o jogo");
            // Add the buttons
            builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    stopTimer();
                    ChallengeActivity.super.onBackPressed();
                }
            });
            builder.show();
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
        builder.setTitle(R.string.soluction)
                .setMessage(currentChallenge.getSuggestion());
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
}
