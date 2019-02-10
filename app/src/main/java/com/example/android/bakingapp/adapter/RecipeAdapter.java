package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> implements RecipeClickListener {
    @BindView(R.id.recipe_image_view) ImageView recipeImage;
    @BindView(R.id.recipe_name_text_view) TextView recipeName;
    @BindView(R.id.recipe_servings_text_view) TextView servings;

    private final Context context;
    private final RecipeSelectionListener recipeSelectionListener;
    private final LayoutInflater layoutInflater;

    @Getter
    @Setter
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes, RecipeSelectionListener recipeSelectionListener) {
        this.context = context;
        this.recipes = recipes;
        this.recipeSelectionListener = recipeSelectionListener;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recipe, parent, false);
        ButterKnife.bind(this, view);

        return new RecipeViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        recipeName.setText(recipe.getName());
        servings.setText(context.getString(R.string.servings, recipe.getServings()));

        if (!recipe.getImage().isEmpty()) {
            Picasso.get().load(recipe.getImage()).into(recipeImage);
            recipeImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRecipeClick(View view, int position) {
        if (recipeSelectionListener != null) {
            recipeSelectionListener.onRecipeSelected(recipes.get(position));
        }
    }
}
