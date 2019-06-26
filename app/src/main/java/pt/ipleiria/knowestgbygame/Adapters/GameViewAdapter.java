package pt.ipleiria.knowestgbygame.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.knowestgbygame.Models.Game;
import pt.ipleiria.knowestgbygame.R;

public class GameViewAdapter extends RecyclerView.Adapter <GameViewAdapter.GameViewHolder> {

    private ArrayList<Game> gamesList;


    public  GameViewAdapter(ArrayList<Game> games){
        gamesList = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_game_row, parent, false);
        GameViewHolder gameViewHolder = new GameViewHolder(view);
        return gameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder gameViewHolder, int position) {
        Game currentGame = gamesList.get(position);
        gameViewHolder.gameImage.setImageResource(currentGame.getThumbnail());
        gameViewHolder.title.setText(currentGame.getTitle());
//        gameViewHolder.pontuation.setText(currentGame.getScore());
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }


    public static class GameViewHolder extends  RecyclerView.ViewHolder {

        public ImageView gameImage;
        public TextView title;
        public TextView pontuation;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.image_game_row);
            title = itemView.findViewById(R.id.textView_title_game);
            pontuation = itemView.findViewById(R.id.textView_pontuation_game);
        }
    }
}
