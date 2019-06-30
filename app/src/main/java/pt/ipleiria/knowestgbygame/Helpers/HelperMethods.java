package pt.ipleiria.knowestgbygame.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;

import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.R;

public class HelperMethods {

    public static AnswerType getCategory(int position) {
        switch (position) {
            case 1:
                return AnswerType.NUMBER;
            case 2:
                return AnswerType.TEXT;
            case 3:
                return AnswerType.QRCODE;
            case 4:
                return AnswerType.OBJECTDETECTION;
            case 5:
                return AnswerType.LOGODETECTION;
            case 6:
                return AnswerType.FACEDETECTION;
            case 7:
                return AnswerType.FITRUN;
            default:
                return AnswerType.TEXT;
        }
    }

    public static int getPositionByAnswerType(AnswerType type) {
        switch (type) {
            case NUMBER:
                return 1;
            case TEXT:
                return 2;
            case QRCODE:
                return 3;
            case OBJECTDETECTION:
                return 4;
            case LOGODETECTION:
                return 5;
            case FACEDETECTION:
                return 6;
            case FITRUN:
                return 7;
            default:
                return 2;
        }
    }


    public static String getAnswerOfPosition(int position) {
        switch (position) {
            case 1:
                return "sorriso";
            case 2:
                return "raiva";
            case 3:
                return "labelSurprise";
            case 4:
                return "chapeu";
            default:
                return "";
        }
    }

    public static int getPositionOfAnswers(String answer) {
        switch (answer) {
            case "sorriso":
                return 1;
            case "raiva":
                return 2;
            case "supresa":
                return 3;
            case "chapeu":
                return 4;
            default:
                return 0;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void startGalleryChooser(Activity activity) {
        if (PermissionUtils.requestPermission(activity, Constant.GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), Constant.GALLERY_IMAGE_REQUEST);
        }
    }

    public static void startCamera(Activity activity) {
        if (PermissionUtils.requestPermission(activity, Constant.CAMERA_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", getCameraFile(activity));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivityForResult(intent, Constant.CAMERA_IMAGE_REQUEST);
        }
    }


    public static File getCameraFile(Activity activity) {
        File dir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, Constant.FILE_NAME);
    }

    public static void optionToChoosePicture(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.dialog_select_prompt)
                .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startGalleryChooser(activity);
                    }
                })
                .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startCamera(activity);
                    }
                });

        builder.show();
    }


    public  static void showAlertInfo(String title, String message, Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title).setMessage(message);
        builder.setPositiveButton (R.string.close, null);
        builder.show();
    }


    public static boolean verifyEmailAndName(EditText editTextEmail, EditText editTextName, Activity activity){
        if (editTextName.getText().toString().isEmpty()) {
            editTextName.setError(activity.getString(R.string.required_name));
            editTextName.requestFocus();
            return false;
        }

        if (editTextEmail.getText().toString().isEmpty()) {
            editTextEmail.setError(activity.getString(R.string.required_email));
            editTextEmail.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean verifyEmail(EditText editTextEmail, Activity activity){
        if (editTextEmail.getText().toString().isEmpty()) {
            editTextEmail.setError(activity.getString(R.string.required_email));
            editTextEmail.requestFocus();
            return false;
        }
        return true;
    }


    public static boolean verifyName(EditText editTextName, Activity activity){
        if (editTextName.getText().toString().isEmpty()) {
            editTextName.setError(activity.getString(R.string.required_name));
            editTextName.requestFocus();
            return false;
        }
        return true;
    }



}
