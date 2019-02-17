package com.example.android.bakingapp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.Step;
import com.example.android.bakingapp.listeners.StepSelectionListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.step_image_view) ImageView stepImage;
    @BindView(R.id.step_short_description_text_view) TextView shortDescription;

    private Recipe recipe;
    private int stepIndex;
    private final StepSelectionListener stepSelectionListener;

    StepViewHolder(@NonNull View itemView, StepSelectionListener stepSelectionListener) {
        super(itemView);
        this.stepSelectionListener = stepSelectionListener;

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (stepSelectionListener != null) {
            stepSelectionListener.onStepSelected(recipe, stepIndex);
        }
    }

    void bindData(Recipe recipe, int stepIndex) {
        this.recipe = recipe;
        this.stepIndex = stepIndex;

        Step step = recipe.getSteps().get(stepIndex);

        shortDescription.setText(step.getShortDescription());

        if (!step.getThumbnailURL().isEmpty()) {
            Picasso.get().load(step.getThumbnailURL()).into(stepImage);
            stepImage.setVisibility(View.VISIBLE);
        } else {
            stepImage.setVisibility(View.GONE);
        }
    }
}
