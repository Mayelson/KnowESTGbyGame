package pt.ipleiria.knowestgbygame.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import pt.ipleiria.knowestgbygame.Activities.AddChallengeActivity;
import pt.ipleiria.knowestgbygame.Activities.DashboardActivity;
import pt.ipleiria.knowestgbygame.Adapters.ChallengeViewAdapter;
import pt.ipleiria.knowestgbygame.Helpers.Constant;
import pt.ipleiria.knowestgbygame.Models.ChallengesManager;
import pt.ipleiria.knowestgbygame.R;


public class ChallengeFragment extends Fragment {


    private RecyclerView recyclerView;
    private ChallengeViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private DashboardActivity dashboardActivity;
    private static final int REQUEST_CODE_ADD = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_challenge, container, false);
        dashboardActivity = (DashboardActivity) getActivity();
        dashboardActivity.setTitle(R.string.challenges);
        setHasOptionsMenu(true);
        buildRecycleView();

        return view;
    }


    public void buildRecycleView() {
        recyclerView = view.findViewById(R.id.rv_list_challenges);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ChallengeFragment.this.getContext());
        adapter = new ChallengeViewAdapter(ChallengesManager.manager().getChallenges());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChallengeViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startAddActivity(position);
            }
        });

        adapter.setOnLongClickListener(new ChallengeViewAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                removeChallenge(position);
            }
        });
    }

    public void removeChallenge(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(ChallengeFragment.this.getContext());
        builder.setMessage(R.string.message_delete_challenge)
                .setTitle(R.string.delete_challenge_title);
        builder.setPositiveButton(R.string.cancel, null);
        builder.setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ChallengesManager.manager().removeChallengeAtPosition(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                startAddActivity(-1);
                return true;
            }
            case R.id.action_search: {
                //Write here what to do you on click
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


     @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == dashboardActivity.RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
    }

    public void startAddActivity(int position) {
        Intent addActivity = new Intent(dashboardActivity.getApplicationContext(), AddChallengeActivity.class);

        //if position is not -1, then we are editing
        if (position != -1){
            addActivity.putExtra(Constant.CHALLENGE_TO_EDIT, ChallengesManager.manager().getChallenges().get(position));
            addActivity.putExtra(Constant.POSITION, position);
        }
        startActivityForResult(addActivity, REQUEST_CODE_ADD);
    }

}