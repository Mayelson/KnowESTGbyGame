package pt.ipleiria.knowestgbygame.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Helpers.HelperMethods;
import pt.ipleiria.knowestgbygame.R;

public class RegisterActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText editTextName, editTextEmail, editTextPassword;
    private ImageView imageViewProfile;

    private FirebaseAuth mAuth;

    private Uri uriProfileImage;

    String profileImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editText_name_register);
        editTextEmail = findViewById(R.id.editText_email_register);
        editTextPassword = findViewById(R.id.editText_password_register);
        progressBar = findViewById(R.id.progressbar);
        imageViewProfile = findViewById(R.id.img_profile_register);

        mAuth = FirebaseAuth.getInstance();

    }

    public void goToLogin(View view) {
        finish();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void btnRegister(View view) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String displayName = editTextName.getText().toString().trim();

        if (displayName.isEmpty()) {
            editTextName.setError(getString(R.string.required_name));
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.required_email));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.invalid_email));
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.required_password));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.invalid_password));
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    //uploadImageToFirebaseStorage();

                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user != null) {
                        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                               // .setPhotoUri(Uri.parse(profileImageUrl))
                                .build();

                        user.updateProfile(profile)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            finish();
                                            startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                                        }
                                    }
                                });
                    }
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                       HelperMethods.showAlertInfo(getString(R.string.already_registered), getString(R.string.already_registered_message), RegisterActivity.this);
                    } else {
                        HelperMethods.showAlertInfo(getString(R.string.already_registered), task.getException().getMessage(), RegisterActivity.this);
                    }

                }
            }
        });
    }

    public void btnPickImage(View view) {
        HelperMethods.optionToChoosePicture(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get result of image
        if (requestCode == Constant.GALLERY_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                imageViewProfile.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == Constant.CAMERA_IMAGE_REQUEST && resultCode == this.RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", HelperMethods.getCameraFile(this));
            Toast.makeText(this, "Imagem da camera carregada com suceeso", Toast.LENGTH_SHORT).show();
            //uploadImage(photoUri);
        }
    }


    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
