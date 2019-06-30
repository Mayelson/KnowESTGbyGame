package pt.ipleiria.knowestgbygame.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.FaceAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pt.ipleiria.knowestgbygame.Activities.ChallengeActivity;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Helpers.PackageManagerUtils;
import pt.ipleiria.knowestgbygame.Helpers.PermissionUtils;
import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.R;

public class LabelFragment extends Fragment {

    private ChallengeActivity challengeActivity;
    private Button btnAnswer;
    private View view;
    private ImageView imgResult;
    private boolean isImageFitToScreen;
    private TextView infoTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_answer_foto, container, false);
        challengeActivity = (ChallengeActivity) getActivity();
        btnAnswer = view.findViewById(R.id.btn_to_answer);
        imgResult = view.findViewById(R.id.img_result);
        infoTextView = view.findViewById(R.id.label_info);

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LabelFragment.this.getContext());
                builder.setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startGalleryChooser();
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startCamera();
                            }
                        });

                builder.show();
            }
        });


        imgResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    imgResult.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imgResult.setAdjustViewBounds(true);
                } else {
                    isImageFitToScreen = true;
                    imgResult.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imgResult.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });

        return view;
    }

    public void detetcLogo(Uri uri){
        infoTextView.setText(R.string.loading_message);
        try {
            // scale the image to save on bandwidth
            Bitmap bitmap = scaleBitmapDown(MediaStore.Images.Media.getBitmap(challengeActivity.getContentResolver(), uri), Constant.MAX_DIMENSION);

            TextRecognizer textRecognizer = new TextRecognizer.Builder(challengeActivity).build();

            if (!textRecognizer.isOperational()){
                Toast.makeText(challengeActivity, "Não é possível obter o Texto! Tente novamente mais", Toast.LENGTH_SHORT).show();

            } else {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);
                StringBuilder stringBuilder = new StringBuilder();


                for (int i = 0; i < items.size(); ++i) {
                    stringBuilder.append(items.valueAt(i).getValue()+"\n");
                }

                System.out.println(stringBuilder.toString());
                challengeActivity.getAnswer(stringBuilder.toString());
                //Toast.makeText(challengeActivity, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                imgResult.setImageBitmap(bitmap);
                btnAnswer.setVisibility(View.INVISIBLE);
                infoTextView.setText(challengeActivity.getString(R.string.img_loaded));
            }
        } catch (IOException e) {
            Log.d(Constant.TAG, "Image picking failed because " + e.getMessage());
            Toast.makeText(LabelFragment.this.getContext(), R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }



    }

    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(challengeActivity, Constant.GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), Constant.GALLERY_IMAGE_REQUEST);
        }
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(challengeActivity, Constant.CAMERA_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(LabelFragment.this.getContext(), challengeActivity.getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, Constant.CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = challengeActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, Constant.FILE_NAME);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, Constant.CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case Constant.GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, Constant.GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       Uri uri = null;
        //get result of image
        if (requestCode == Constant.GALLERY_IMAGE_REQUEST && resultCode == challengeActivity.RESULT_OK && data != null) {
            uri = data.getData();
        } else if (requestCode == Constant.CAMERA_IMAGE_REQUEST && resultCode == challengeActivity.RESULT_OK) {
            uri = FileProvider.getUriForFile(LabelFragment.this.getContext(), challengeActivity.getPackageName() + ".provider", getCameraFile());
        }

        if (challengeActivity.currentAnseAnswerType() == AnswerType.LOGODETECTION){
            detetcLogo(uri);
        } else {
            uploadImage(uri);
        }
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap = scaleBitmapDown(MediaStore.Images.Media.getBitmap(challengeActivity.getContentResolver(), uri), Constant.MAX_DIMENSION);

                callCloudVision(bitmap);
                imgResult.setImageBitmap(bitmap);
                btnAnswer.setVisibility(View.INVISIBLE);

            } catch (IOException e) {
                Log.d(Constant.TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(LabelFragment.this.getContext(), R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(Constant.TAG, "Image picker gave us a null image.");
            Toast.makeText(LabelFragment.this.getContext(), R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private void callCloudVision(final Bitmap bitmap) {
        // Switch text to loading
        infoTextView.setText(R.string.loading_message);

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LableDetectionTask(challengeActivity, prepareAnnotationRequest(bitmap));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(Constant.TAG, "failed to make API request because of other IOException " + e.getMessage());
        }
    }

    private static class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<ChallengeActivity> challengeAtivityWeakReference;
        private Vision.Images.Annotate mRequest;
        private static final String TAG = ChallengeActivity.class.getSimpleName();

        LableDetectionTask(ChallengeActivity activity, Vision.Images.Annotate annotate) {
            challengeAtivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }

        protected void onPostExecute(String result) {
            ChallengeActivity activity = challengeAtivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                activity.getAnswer(result);
                TextView imageDetail = activity.findViewById(R.id.label_info);
                imageDetail.setText(activity.getString(R.string.img_loaded));
            }
        }

        private static String convertResponseToString(BatchAnnotateImagesResponse response) {
            String ms = "";
            StringBuilder message = new StringBuilder("I found these things:\n\n");
            List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();

            List<FaceAnnotation> faceAnnotations = response.getResponses().get(0).getFaceAnnotations();

            if (faceAnnotations != null) {
                for (FaceAnnotation annotation : faceAnnotations) {
                    ms += String.format("raiva:%s sorriso:%s supresa:%s chapeu:%s",
                            annotation.getAngerLikelihood(),
                            annotation.getJoyLikelihood(),
                            annotation.getSurpriseLikelihood(),
                            annotation.getHeadwearLikelihood());

                   // ms += String.format(annotation.getJoyLikelihood());
                }
                System.out.println(ms);
                return ms;
            } else if (labels != null) {
                for (EntityAnnotation label : labels) {
                    //message.append(String.format(Locale.US, "%.3f: %s", label.getScore(), label.getDescription()));
                    message.append(String.format(label.getDescription()));
                    message.append(":");
                }
                System.out.println(ms);
                return message.toString();
            }

            return "nothing";
        }
    }

    private Vision.Images.Annotate prepareAnnotationRequest(final Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(Constant.CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = challengeActivity.getPackageName();
                        visionRequest.getRequestHeaders().set(Constant.ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(challengeActivity.getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(Constant.ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
      /*      annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("LABEL_DETECTION");
                labelDetection.setMaxResults(Constant.MAX_LABEL_RESULTS);
                add(labelDetection);
            }});*/

            if (challengeActivity.currentAnseAnswerType() == AnswerType.FACEDETECTION){
                annotateImageRequest = addFaceDetection(annotateImageRequest);
            } else {
                annotateImageRequest = addLabelDetection(annotateImageRequest);
            }

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d(Constant.TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }


    public AnnotateImageRequest addLabelDetection( AnnotateImageRequest annotateImageRequest){
        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
            Feature labelDetection = new Feature();
            labelDetection.setType("LABEL_DETECTION");
            labelDetection.setMaxResults(Constant.MAX_LABEL_RESULTS);
            add(labelDetection);
        }});

        return annotateImageRequest;
    }


    public AnnotateImageRequest addFaceDetection( AnnotateImageRequest annotateImageRequest){
        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
            Feature faceDetection = new Feature();
            faceDetection.setType("FACE_DETECTION");
            faceDetection.setMaxResults(10);
            add(faceDetection);
        }});

        return annotateImageRequest;
    }


    // TODO: analyse this method to understand how to get Cloud Vision data
    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        String message = "";
        List<EntityAnnotation> annotations;
        AnnotateImageResponse annotateImageResponse = response.getResponses().get(0);

        message += "\n___\n# FACES\n";
        List<FaceAnnotation> faceAnnotations = annotateImageResponse.getFaceAnnotations();
        if (faceAnnotations != null) {
            for (FaceAnnotation annotation : faceAnnotations) {
                message += String.format(Locale.US, "> position:%s anger:%s joy:%s surprise:%s headwear:%s \n",
                        annotation.getBoundingPoly(),
                        annotation.getAngerLikelihood(),
                        annotation.getJoyLikelihood(),
                        annotation.getSurpriseLikelihood(),
                        annotation.getHeadwearLikelihood());
            }
        } else {
            message += "nothing\n";
        }

        return message;
    }
}
