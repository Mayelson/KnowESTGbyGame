package pt.ipleiria.knowestgbygame.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import pt.ipleiria.knowestgbygame.R;

public class ProfileFragment extends Fragment {

    private EditText editTextEmail, editTextName;
    private TextView points, games_played, profileName, profileEmail;
    private View view;
    private ImageView img_profile, img_config, imgEdit;
    private RelativeLayout editProfileContainer;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle(R.string.my_account);
        setHasOptionsMenu(true);

        mAuth = FirebaseAuth.getInstance();


        editTextEmail = view.findViewById(R.id.editText_email_profile);
        editTextName = view.findViewById(R.id.editText_name_profile);

        profileEmail = view.findViewById(R.id.textView_email_address);
        profileName = view.findViewById(R.id.textView_profile_name);
        points = view.findViewById(R.id.user_total_points);
        games_played = view.findViewById(R.id.user_games_played);

        imgEdit = view.findViewById(R.id.img_edit_profile);
        img_profile = view.findViewById(R.id.img_profile);

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
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(img_profile);
            }

            if (user.getDisplayName() != null) {
                editTextName.setText(user.getDisplayName());
                profileName.setText(user.getDisplayName());
            }
            if (user.getEmail() != null) {
                editTextEmail.setText(user.getEmail());
                profileEmail.setText(user.getEmail());
            }

        }

    }


}
