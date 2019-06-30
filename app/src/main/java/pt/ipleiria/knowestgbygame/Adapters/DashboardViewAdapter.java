package pt.ipleiria.knowestgbygame.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.knowestgbygame.Helpers.HelperMethods;
import pt.ipleiria.knowestgbygame.Models.Challenge;
import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.Activities.GameActivity;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Models.SessionManager;
import pt.ipleiria.knowestgbygame.R;

public class DashboardViewAdapter extends RecyclerView.Adapter<DashboardViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Game> games;


    public DashboardViewAdapter(Context mContext, List<Game> games) {
        this.mContext = mContext;
        this.games = games;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cardview_item_game, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        myViewHolder.title.setText(games.get(position).getTitle());
        myViewHolder.img_thumbnail.setImageResource(games.get(position).getThumbnail());
        if (SessionManager.manager().getUser().alreadyPlayed(games.get(position))){
            myViewHolder.img_thumbnail.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManager.manager().getUser().alreadyPlayed(games.get(position))){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Jogo concluído!").setMessage("Este jogo ja foi jogado!");
                    builder.setPositiveButton (R.string.close, null);
                    builder.show();
                } else {
                    Intent intent = new Intent(mContext, GameActivity.class);
                    intent.putExtra(Constant.GAME_UUID, games.get(position).getUuid());
                    //start the activity
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
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
