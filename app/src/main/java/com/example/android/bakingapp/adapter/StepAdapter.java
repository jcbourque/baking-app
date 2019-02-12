package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    @BindView(R.id.step_image_view) ImageView stepImage;
    @BindView(R.id.step_short_description_text_view) TextView shortDescription;

    private final LayoutInflater layoutInflater;

    @Getter
    @Setter
    private List<Step> steps;

    public StepAdapter(Context context, List<Step> steps) {
        this.steps = steps;

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.step, parent, false);
        ButterKnife.bind(this, view);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = steps.get(position);

        if (!step.getThumbnailURL().isEmpty()) {
            Picasso.get().load(step.getThumbnailURL()).into(stepImage);
            stepImage.setVisibility(View.VISIBLE);
        }

        shortDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}
