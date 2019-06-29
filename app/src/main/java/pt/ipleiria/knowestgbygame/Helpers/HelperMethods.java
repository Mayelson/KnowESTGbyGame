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

import java.io.File;
import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.Models.AnswerType;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
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
            default:
                return 2;
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





}
