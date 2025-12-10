package com.example.project02_group09_mohammed_diksha_fidel.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.data.Challenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder> {

    private final List<Challenge> challenges = new ArrayList<>();
    private final Set<Integer> joinedChallengeIds = new HashSet<>();
    private OnJoinLeaveClickListener listener;

    public void setOnJoinLeaveClickListener(OnJoinLeaveClickListener listener) {
        this.listener = listener;
    }

    public void setChallenges(List<Challenge> newChallenges) {
        challenges.clear();
        if (newChallenges != null) {
            challenges.addAll(newChallenges);
        }
        notifyDataSetChanged();
    }

    public void setJoinedChallengeIds(List<Integer> ids) {
        joinedChallengeIds.clear();
        if (ids != null) {
            joinedChallengeIds.addAll(ids);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_challenge, parent, false);
        return new ChallengeViewHolder(view);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {
        Challenge challenge = challenges.get(position);

        holder.title.setText(challenge.getTitle());
        holder.description.setText(challenge.getDescription());
        holder.duration.setText(
                holder.itemView.getContext().getString(
                        R.string.challenge_duration_days,
                        challenge.getDurationDays()
                )
        );

        boolean joined = joinedChallengeIds.contains(challenge.getChallengeId());
        holder.btnJoin.setText(joined ? "Leave" : "Join");

        holder.btnJoin.setOnClickListener(v -> {
            if (listener == null) return;
            if (joined) {
                listener.onLeaveClicked(challenge);
            } else {
                listener.onJoinClicked(challenge);
            }
        });
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    // Listener so the Activity can handle join/leave click.
    public interface OnJoinLeaveClickListener {
        void onJoinClicked(Challenge challenge);

        void onLeaveClicked(Challenge challenge);
    }

    static class ChallengeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView duration;
        Button btnJoin;

        ChallengeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvChallengeTitle);
            description = itemView.findViewById(R.id.tvChallengeDescription);
            duration = itemView.findViewById(R.id.tvChallengeDuration);
            btnJoin = itemView.findViewById(R.id.btnJoinChallenge);
        }
    }
}