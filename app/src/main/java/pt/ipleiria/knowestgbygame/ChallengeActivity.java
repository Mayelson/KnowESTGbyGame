package pt.ipleiria.knowestgbygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ChallengeActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewDesc;
    private ImageView imageViewThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        textViewTitle = (TextView) findViewById(R.id.challenge_item_title);
        textViewDesc = (TextView) findViewById(R.id.challenge_item_descr);
        imageViewThumb = (ImageView) findViewById(R.id.challenge_item_thumbnail);


        //receive data
        Intent intent = getIntent();
        String title = intent.getExtras().getString(Constant.CHALLENGE_TITLE);
        int thumbnail = intent.getExtras().getInt(Constant.CHALLENGE_THUMB);
        String description = intent.getExtras().getString(Constant.CHALLENGE_DESCRIPTION);

        //setting values
        textViewTitle.setText(title);
        imageViewThumb.setImageResource(thumbnail);
        textViewDesc.setText(description);


    }
}
