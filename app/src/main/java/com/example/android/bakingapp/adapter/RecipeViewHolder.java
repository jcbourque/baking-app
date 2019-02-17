package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.listeners.RecipeSelectionListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.recipe_image_view) ImageView recipeImage;
    @BindView(R.id.recipe_name_text_view) TextView recipeName;
    @BindView(R.id.recipe_servings_text_view) TextView servings;

    private final Context context;
    private final RecipeSelectionListener recipeSelectionListener;

    private Recipe recipe;

    RecipeViewHolder(@NonNull View itemView, RecipeSelectionListener recipeSelectionListener) {
        super(itemView);
        context = itemView.getContext();
        this.recipeSelectionListener = recipeSelectionListener;

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
    }

    void bindData(Recipe recipe) {
        this.recipe = recipe;

        recipeName.setText(recipe.getName());
        servings.setText(context.getString(R.string.servings, recipe.getServings()));

        if (!recipe.getImage().isEmpty()) {
            Picasso.get().load(recipe.getImage()).into(recipeImage);
            recipeImage.setVisibility(View.VISIBLE);
        } else {
            recipeImage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (recipeSelectionListener != null) {
            recipeSelectionListener.onRecipeSelected(recipe);
        }
    }
}
