package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.Step;
import com.example.android.bakingapp.listeners.StepClickListener;
import com.example.android.bakingapp.listeners.StepSelectionListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> implements StepClickListener {

    @BindView(R.id.step_image_view) ImageView stepImage;
    @BindView(R.id.step_short_description_text_view) TextView shortDescription;

    private final StepSelectionListener stepSelectionListener;
    private final LayoutInflater layoutInflater;

    @Getter
    @Setter
    private Recipe recipe;

    public StepAdapter(Context context, Recipe recipe, StepSelectionListener stepSelectionListener) {
        this.recipe = recipe;
        this.stepSelectionListener = stepSelectionListener;

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.step, parent, false);
        ButterKnife.bind(this, view);

        return new StepViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = recipe.getSteps().get(position);

        if (!step.getThumbnailURL().isEmpty()) {
            Picasso.get().load(step.getThumbnailURL()).into(stepImage);
            stepImage.setVisibility(View.VISIBLE);
        }

        shortDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return recipe.getSteps().size();
    }

    @Override
    public void onStepClick(View view, int position) {
        if (stepSelectionListener != null) {
            stepSelectionListener.onStepSelected(recipe, position);
        }
    }
}
