package pt.ipleiria.knowestgbygame.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pt.ipleiria.knowestgbygame.ChallengeActivity;
import pt.ipleiria.knowestgbygame.Constant;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.R;

public class ChallengeViewAdapter extends RecyclerView.Adapter<ChallengeViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Challenge> challenges;


    public ChallengeViewAdapter(Context mContext, List<Challenge> challenges) {
        this.mContext = mContext;
        this.challenges = challenges;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cardview_item_challenge, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        myViewHolder.title.setText(challenges.get(position).getTitle());
        myViewHolder.img_thumbnail.setImageResource(challenges.get(position).getThumbnail());


        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChallengeActivity.class);
                //passing data to the challenge activity
                intent.putExtra(Constant.CHALLENGE_TITLE, challenges.get(position).getTitle());
                intent.putExtra(Constant.CHALLENGE_THUMB, challenges.get(position).getThumbnail());
                intent.putExtra(Constant.CHALLENGE_DESCRIPTION, challenges.get(position).getDescription());
                //start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }



    public static class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView title;
        ImageView img_thumbnail;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView)itemView.findViewById(R.id.challenge_title);
            img_thumbnail = (ImageView)itemView.findViewById(R.id.challenge_img);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item);
        }
    }
}
