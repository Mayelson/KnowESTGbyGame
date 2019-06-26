package pt.ipleiria.knowestgbygame.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.R;

public class ChallengeViewAdapter extends RecyclerView.Adapter <ChallengeViewAdapter.ChallengeViewHolder> {

    private ArrayList<Challenge> challengesList;


    public ChallengeViewAdapter(ArrayList<Challenge> challenges){
        challengesList = challenges;
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_challenge_row, parent, false);
        ChallengeViewHolder challengeViewHolder = new ChallengeViewHolder(view);
        return challengeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder gameViewHolder, int position) {
        Challenge currentChallenge = challengesList.get(position);
        gameViewHolder.gameImage.setImageResource(currentChallenge.getThumbnail());
        gameViewHolder.title.setText(currentChallenge.getTitle());
    }

    @Override
    public int getItemCount() {
        return challengesList.size();
    }


    public static class ChallengeViewHolder extends  RecyclerView.ViewHolder {

        public ImageView gameImage;
        public TextView title;


        public ChallengeViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.image_challenge_row);
            title = itemView.findViewById(R.id.textView_title_challenge);
        }
    }
}
