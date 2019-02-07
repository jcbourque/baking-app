package com.example.android.bakingapp.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Recipe {
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

    public void addIngredient(Ingredient ingredient) {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }

        ingredients.add(ingredient);
    }
    public boolean hasIngredients() {
        return ingredients != null && !ingredients.isEmpty();
    }

    public void addStep(Step step) {
        if (steps == null) {
            steps = new ArrayList<>();
        }

        steps.add(step);
    }

    public boolean hasSteps() {
        return steps != null && !steps.isEmpty();
    }
}
