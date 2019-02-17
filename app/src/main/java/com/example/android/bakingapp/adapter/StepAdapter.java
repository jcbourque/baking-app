package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.listeners.StepSelectionListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lombok.Getter;
import lombok.Setter;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private final StepSelectionListener stepSelectionListener;
    private final LayoutInflater layoutInflater;

    @Getter
    @Setter
    private Recipe recipe;

    public StepAdapter(Context context, Recipe recipe, StepSelectionListener stepSelectionListener) {
        this.recipe = recipe;
        this.stepSelectionListener = stepSelectionListener;

        setHasStableIds(true);

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return recipe.getSteps().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.step, parent, false);

        return new StepViewHolder(view, stepSelectionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bindData(recipe, position);
    }
}
