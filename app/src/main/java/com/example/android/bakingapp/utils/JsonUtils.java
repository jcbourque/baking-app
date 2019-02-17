package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;

public final class JsonUtils {

    private static final Type RECIPE_LIST_TYPE = new TypeToken<List<Recipe>>() {}.getType();

    private JsonUtils() { /* This class is not intended to be instantiated */ }

    public static String fromRecipe(@NonNull Recipe recipe) {
        return new Gson().toJson(recipe);
    }

    public static Recipe toRecipe(String json) {
        return new Gson().fromJson(json, Recipe.class);
    }

    public static List<Recipe> toRecipes(String json) {
        List<Recipe> recipes = new Gson().fromJson(json, RECIPE_LIST_TYPE);
        scrubData(recipes);

        return recipes;
    }

    private static boolean isVideo(@NonNull String url) {
        return url.toLowerCase().endsWith(".mp4");
    }

    private static void scrubData(@NonNull List<Recipe> recipes) {
        final char oldChar = '�', newChar = '°';

        for (Recipe recipe : recipes) {
            if (recipe.hasSteps()) {
                for (Step step : recipe.getSteps()) {
                    step.setDescription(step.getDescription().replace(oldChar, newChar));

                    if (step.getVideoURL().isEmpty() && !step.getThumbnailURL().isEmpty() &&
                            isVideo(step.getThumbnailURL())) {
                        step.setVideoURL(step.getThumbnailURL());
                        step.setThumbnailURL("");
                    }
                }
            }
        }
    }
}
