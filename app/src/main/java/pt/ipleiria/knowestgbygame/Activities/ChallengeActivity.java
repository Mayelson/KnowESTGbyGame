package pt.ipleiria.knowestgbygame.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.List;

import pt.ipleiria.knowestgbygame.Fragments.ChallengeFragment;
import pt.ipleiria.knowestgbygame.Fragments.ClassificationFragment;
import pt.ipleiria.knowestgbygame.Fragments.DashboardFragment;
import pt.ipleiria.knowestgbygame.Fragments.NotFoundFragment;
import pt.ipleiria.knowestgbygame.Fragments.NumberFragment;
import pt.ipleiria.knowestgbygame.Fragments.ProfileFragment;
import pt.ipleiria.knowestgbygame.Fragments.QrcodeFragment;
import pt.ipleiria.knowestgbygame.Fragments.TextFragment;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
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

    public void getAnswer(String text, int number) {
        Toast.makeText(this, "Challenge get data from fragment: " + text, Toast.LENGTH_SHORT).show();
        imageButton.setVisibility(View.VISIBLE);
    }


    public void startQRCodeScanner(){

        //CHECK PERMISSION CAMERA
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        cameraView.setZOrderMediaOverlay(true);
        surfaceHolder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        if (!barcode.isOperational()) {
            //analyse whats happens in this case
            Toast.makeText(this, "Não foi possível inicializar o Leitor de QRCode", Toast.LENGTH_SHORT).show();
            return;
        }

        cameraSource = new CameraSource.Builder(this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1024 )
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(ChallengeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                       cameraSource.start(cameraView.getHolder());
                    }else {
                        Toast.makeText(ChallengeActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() > 0) {
                    answer.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            }
                            challengeCardView.setCardBackgroundColor(getResources().getColor(R.color.colorBlueCustom));
                            answer.setText(barcodes.valueAt(0).displayValue);
                            cameraSource.stop();
                            //need validation of qrcode data, if data is valid, go to next challenge

                            nextChallenge();
                        }
                    });
                }
            }
        });
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
            case LABEL:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_answer, new NotFoundFragment()).commit();
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
                .setMessage(currentChallenge.getSugestions().get(0).getDescription());
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
