package com.example.project02_group09_mohammed_diksha_fidel.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.data.Challenge;

import java.util.ArrayList;
import java.util.List;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder> {

    private final List<Challenge> challenges = new ArrayList<>();

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
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public void setChallenges(List<Challenge> newChallenges) {
        challenges.clear();
        challenges.addAll(newChallenges);
        notifyDataSetChanged();
    }

    static class ChallengeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView duration;

        ChallengeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvChallengeTitle);
            description = itemView.findViewById(R.id.tvChallengeDescription);
            duration = itemView.findViewById(R.id.tvChallengeDuration);
        }
    }
}