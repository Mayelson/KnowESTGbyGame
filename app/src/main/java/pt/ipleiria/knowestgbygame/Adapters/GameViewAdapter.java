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

    public  GameViewAdapter(ArrayList<Game> games){
        gamesList = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_game_row, parent, false);
        GameViewHolder gameViewHolder = new GameViewHolder(view, itemClickListener, longClickListener);
        return gameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder gameViewHolder, int position) {
        Game currentGame = gamesList.get(position);
        gameViewHolder.gameImage.setImageResource(currentGame.getThumbnail());
        gameViewHolder.title.setText(currentGame.getTitle());
        gameViewHolder.created_by.setText(currentGame.getCreated_by());
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public void filterList(ArrayList<Game> filteredList){
        gamesList = filteredList;
        notifyDataSetChanged();
    }


    public static class GameViewHolder extends  RecyclerView.ViewHolder {

        public ImageView gameImage;
        public TextView title, created_by;

        public GameViewHolder(@NonNull View itemView, final OnItemClickListener clickListener, final OnLongClickListener longClickListener) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.image_game_row);
            title = itemView.findViewById(R.id.textView_title_game);
            created_by = itemView.findViewById(R.id.textView_createdBy_game);

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
