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
import pt.ipleiria.knowestgbygame.R;

public class ChallengeViewAdapter extends RecyclerView.Adapter <ChallengeViewAdapter.ChallengeViewHolder> {

    private ArrayList<Challenge> challengesList;
    private OnItemClickListener itemClickListener;
    private OnLongClickListener longClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnLongClickListener {
        void onItemLongClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        longClickListener = listener;
    }

    public ChallengeViewAdapter(ArrayList<Challenge> challenges){
        challengesList = challenges;
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_challenge_row, parent, false);
        ChallengeViewHolder challengeViewHolder = new ChallengeViewHolder(view, itemClickListener, longClickListener);
        return challengeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder challengeViewHolder, int position) {
        Challenge currentChallenge = challengesList.get(position);
        challengeViewHolder.challengeImage.setImageResource(currentChallenge.getThumbnail());
        challengeViewHolder.title.setText(currentChallenge.getTitle());

    }

    @Override
    public int getItemCount() {
        return challengesList.size();
    }


    public static class ChallengeViewHolder extends  RecyclerView.ViewHolder {

        public ImageView challengeImage;
        public TextView title;


        public ChallengeViewHolder(@NonNull View itemView, final OnItemClickListener clickListener, final OnLongClickListener longClickListener) {
            super(itemView);
            challengeImage = itemView.findViewById(R.id.image_challenge_row);
            title = itemView.findViewById(R.id.textView_title_challenge);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            longClickListener.onItemLongClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
}
