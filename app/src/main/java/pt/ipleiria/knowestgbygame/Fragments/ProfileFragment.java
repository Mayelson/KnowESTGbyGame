package pt.ipleiria.knowestgbygame.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import pt.ipleiria.knowestgbygame.Activities.DashboardActivity;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Helpers.HelperMethods;
import pt.ipleiria.knowestgbygame.Models.SessionManager;
import pt.ipleiria.knowestgbygame.R;

public class ProfileFragment extends Fragment {

    private EditText editTextEmail, editTextName;
    private TextView points, games_played, profileName, profileEmail;
    private View view;
    private ImageView img_profile, img_config, imgEdit;
    private RelativeLayout editProfileContainer, containerProgressbar;
    private Button editButton;
    private FirebaseUser authUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle(R.string.my_account);
        setHasOptionsMenu(true);

        authUser = FirebaseAuth.getInstance().getCurrentUser();


        editTextEmail = view.findViewById(R.id.editText_email_profile);
        editTextName = view.findViewById(R.id.editText_name_profile);

        profileEmail = view.findViewById(R.id.textView_email_address);
        profileName = view.findViewById(R.id.textView_profile_name);
        points = view.findViewById(R.id.user_total_points);
        games_played = view.findViewById(R.id.user_games_played);

        imgEdit = view.findViewById(R.id.img_edit_profile);
        img_profile = view.findViewById(R.id.img_profile);
        editButton = view.findViewById(R.id.btn_edit_profile);
        containerProgressbar = view.findViewById(R.id.container_progressbar);

        editProfileContainer = view.findViewById(R.id.edit_profile_container);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editProfileContainer.getVisibility() == view.VISIBLE){
                    editProfileContainer.setVisibility(View.GONE);
                } else {
                    editProfileContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInFirebase();
            }
        });


        updateUIProfile();

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(menu!=null){
            menu.clear();
        }
    }

    public void updateUIProfile(){
        if (authUser != null) {
            if (authUser.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(authUser.getPhotoUrl().toString())
                        .into(img_profile);
            }

            if (authUser.getDisplayName() != null) {
                editTextName.setText(authUser.getDisplayName());
                profileName.setText(authUser.getDisplayName());
            }
            if (authUser.getEmail() != null) {
                editTextEmail.setText(authUser.getEmail());
                profileEmail.setText(authUser.getEmail());
            }

            if (authUser.getPhotoUrl() != null){
               Glide.with(ProfileFragment.this.getContext())
                        .load(authUser.getPhotoUrl().toString())
                       .into(img_profile);
            }

            points.setText(Integer.toString(SessionManager.manager().getUser().getPoints()));
            games_played.setText(Integer.toString(SessionManager.manager().getUser().getGamesPlayeds().size()));
        }
    }

    public void updateUserInFirebase(){


        if (HelperMethods.verifyName(editTextName, getActivity()) && !authUser.getDisplayName().equalsIgnoreCase(editTextName.getText().toString())) {
            editProfileContainer.setVisibility(View.GONE);
            containerProgressbar.setVisibility(View.VISIBLE);

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(editTextName.getText().toString())
                    // .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                    .build();

            authUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                containerProgressbar.setVisibility(View.GONE);
                                Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.user_updated), Toast.LENGTH_SHORT).show();
                                updateUIProfile();
                        /*            if (HelperMethods.verifyEmail(editTextEmail, getActivity()) && !authUser.getEmail().equalsIgnoreCase(editTextEmail.getText().toString())){
                                        authUser.updateEmail(editTextEmail.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            containerProgressbar.setVisibility(View.GONE);
                                                            Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.user_updated), Toast.LENGTH_SHORT).show();
                                                            updateUIProfile();
                                                        } else {
                                                            editProfileContainer.setVisibility(View.VISIBLE);
                                                            containerProgressbar.setVisibility(View.GONE);
                                                        }
                                                    }
                                                });
                                    } else {
                                        containerProgressbar.setVisibility(View.GONE);
                                        Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.user_updated), Toast.LENGTH_SHORT).show();
                                        updateUIProfile();
                                    }*/

                            } else {
                                editProfileContainer.setVisibility(View.VISIBLE);
                                containerProgressbar.setVisibility(View.GONE);
                            }
                        }
                    });
        }

    }


}
