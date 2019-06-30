package pt.ipleiria.knowestgbygame.Fragments;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.FenceState;
import com.google.android.gms.awareness.snapshot.DetectedActivityResponse;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.sql.Timestamp;

import pt.ipleiria.knowestgbygame.Activities.ChallengeActivity;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.R;

public class ActivityRecognitionFragment extends Fragment implements SensorEventListener {

    private Button btnAnswer;
    private TextView textViewActivityAnswer;
    private View view;
    private ChallengeActivity challengeActivity;
    boolean activityRunning;
    private SensorManager sensorManager;


/*    public static final String TAG_FENCES = "fences";
    private static final String FENCE_RECEIVER_ACTION = "FENCE_RECEIVER_ACTION";
    private PendingIntent myPendingIntent;
    private FenceReceiver fenceReceiver;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity_recognition, container, false);


        textViewActivityAnswer = view.findViewById(R.id.textView_activity_answer);
        btnAnswer = view.findViewById(R.id.btn_to_answer);

        challengeActivity = (ChallengeActivity) getActivity();

        sensorManager = (SensorManager) challengeActivity.getSystemService(Context.SENSOR_SERVICE);
     /*   Intent intent = new Intent(FENCE_RECEIVER_ACTION);
        myPendingIntent = PendingIntent.getBroadcast(ActivityRecognitionFragment.this.getContext(), 0, intent, 0);
        fenceReceiver = new FenceReceiver();
        challengeActivity.registerReceiver(fenceReceiver, new IntentFilter(FENCE_RECEIVER_ACTION));*/

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textViewActivityAnswer.setText("1000");
              //  printDetectedActivity();
            }


        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(ActivityRecognitionFragment.this.getContext(), "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(activityRunning) {
            textViewActivityAnswer.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



/*    private void printDetectedActivity() {
        Awareness.getSnapshotClient(challengeActivity).getDetectedActivity()
                .addOnSuccessListener(new OnSuccessListener<DetectedActivityResponse>() {
                    @Override
                    public void onSuccess(DetectedActivityResponse dar) {
                        ActivityRecognitionResult arr = dar.getActivityRecognitionResult();
                        DetectedActivity probableActivity = arr.getMostProbableActivity();
                        int confidence = probableActivity.getConfidence();
                        String activityStr = probableActivity.toString();
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        String text = "\n\n[DetectedActivity @ " + timestamp + "]\n"
                                + "\tActivity: "+ activityStr+ ", Confidence: " + confidence + "/100";
                        textViewActivityAnswer.setText(text);
                        //challengeActivity.getAnswer(answer);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(Constant.TAG, "Could not get Detected Activity: " + e);
                        Toast.makeText(challengeActivity, "Could not get Detected Activity: " + e,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class FenceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(FENCE_RECEIVER_ACTION)) {
                Log.e(TAG_FENCES, "Received an unsupported action in FenceReceiver: action="
                        + intent.getAction());
                return;
            }
            FenceState fenceState = FenceState.extract(intent);
            String fenceInfo = null;
            switch (fenceState.getFenceKey()) {

                case "headphoneFence":
                    switch (fenceState.getCurrentState()) {
                        case FenceState.TRUE:
                            fenceInfo = "TRUE | Headphones are plugged in.";
                            break;
                        case FenceState.FALSE:
                            fenceInfo = "FALSE | Headphones are unplugged.";
                            break;
                        case FenceState.UNKNOWN:
                            fenceInfo = "Error: unknown state.";
                            break;
                    }
                    break;
                case "walkingFence":
                    switch (fenceState.getCurrentState()) {
                        case FenceState.TRUE:
                            fenceInfo = "TRUE | Walking.";
                            break;
                        case FenceState.FALSE:
                            fenceInfo = "FALSE | Not walking.";
                            break;
                        case FenceState.UNKNOWN:
                            fenceInfo = "Error: unknown state.";
                            break;
                    }
                    break;
                case "timeFence":
                    switch (fenceState.getCurrentState()) {
                        case FenceState.TRUE:
                            fenceInfo = "TRUE | Within timeslot.";
                            break;
                        case FenceState.FALSE:
                            fenceInfo = "FALSE | Out of timeslot.";
                            break;
                        case FenceState.UNKNOWN:
                            fenceInfo = "Error: unknown state.";
                            break;
                    }
                    break;
                default:
                    fenceInfo = "Error: unknown fence: " + fenceState.getFenceKey();
                    break;
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String text = "\n\n[Fences @ " + timestamp + "]\n"
                    + fenceState.getFenceKey() + ": " + fenceInfo;
            textViewActivityAnswer.setText(text);
        }
    }*/

}
