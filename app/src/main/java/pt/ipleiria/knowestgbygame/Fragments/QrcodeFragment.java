package pt.ipleiria.knowestgbygame.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import pt.ipleiria.knowestgbygame.Activities.ChallengeActivity;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Helpers.PermissionUtils;
import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.R;

public class QrcodeFragment extends Fragment {

    private static final int PERMISSION_REQUEST = 200;
    private SurfaceView cameraView;
    private BarcodeDetector barcode;
    private CameraSource cameraSource;
    private SurfaceHolder surfaceHolder;
    private TextView answer;
    private ChallengeActivity challengeActivity;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_answer_qrcode, container, false);

        cameraView = view.findViewById(R.id.cameraView);
        answer = view.findViewById(R.id.challenge_answer);
        challengeActivity = (ChallengeActivity) getActivity();

        startQRCodeScanner();

        return view;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, Constant.CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startQRCodeScanner();
                }
                break;
        }
    }


    public void startQRCodeScanner(){

        //CHECK PERMISSION CAMERA
        if (ContextCompat.checkSelfPermission(challengeActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(challengeActivity, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        cameraView.setZOrderMediaOverlay(true);
        surfaceHolder = cameraView.getHolder();

       barcode = new BarcodeDetector.Builder(challengeActivity.getApplicationContext())
        .setBarcodeFormats(Barcode.QR_CODE)
        .build();



        if (!barcode.isOperational()) {
            //analyse whats happens in this case
            Toast.makeText(challengeActivity, "Não foi possível inicializar o Leitor de QRCode", Toast.LENGTH_SHORT).show();
            challengeActivity.finish();
        }

        cameraSource = new CameraSource.Builder(challengeActivity, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1024 )
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(challengeActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(cameraView.getHolder());
                    }else {
                        Toast.makeText(challengeActivity, "Permission Denied", Toast.LENGTH_SHORT).show();
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
                            Vibrator v = (Vibrator) QrcodeFragment.this.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            }
                            String text = barcodes.valueAt(0).displayValue;
                            answer.setText(text);
                            cameraSource.stop();
                            challengeActivity.getAnswer(text);
                            //need validation of qrcode data, if data is valid, go to next challenge

                            //ChallengeActivity.nextChallenge();
                        }
                    });
                }
            }
        });
    }
}
