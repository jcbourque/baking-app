package com.example.android.bakingapp.adapter;

import android.view.View;

import com.example.android.bakingapp.listeners.RecipeClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    final private RecipeClickListener recipeClickListener;

    RecipeViewHolder(@NonNull View itemView, RecipeClickListener recipeClickListener) {
        super(itemView);
        this.recipeClickListener = recipeClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (recipeClickListener != null) {
            recipeClickListener.onRecipeClick(v, getAdapterPosition());
        }
    }
}
