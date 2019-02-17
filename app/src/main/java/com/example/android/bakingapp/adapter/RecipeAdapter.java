package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.listeners.RecipeSelectionListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lombok.Getter;
import lombok.Setter;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private final RecipeSelectionListener recipeSelectionListener;
    private final LayoutInflater layoutInflater;

    @Getter
    @Setter
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes,
                         RecipeSelectionListener recipeSelectionListener) {
        this.recipes = recipes;
        this.recipeSelectionListener = recipeSelectionListener;

        setHasStableIds(true);

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recipe, parent, false);

        return new RecipeViewHolder(view, recipeSelectionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bindData(recipes.get(position));
    }
}
