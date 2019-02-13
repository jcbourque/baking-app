package com.example.android.bakingapp.adapter;

import android.view.View;

import com.example.android.bakingapp.listeners.StepClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final StepClickListener stepClickListener;

    public StepViewHolder(@NonNull View itemView, StepClickListener stepClickListener) {
        super(itemView);
        this.stepClickListener = stepClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (stepClickListener != null) {
            stepClickListener.onStepClick(v, getAdapterPosition());
        }
    }
}
