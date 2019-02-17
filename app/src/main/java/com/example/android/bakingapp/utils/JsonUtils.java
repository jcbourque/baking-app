package com.example.android.bakingapp.utils;

import android.util.Log;

import com.example.android.bakingapp.data.Ingredient;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public final class JsonUtils {
    private static final String TAG = "JsonUtils";

    private JsonUtils() { /* This class is not intended to be instantiated */ }

    public static List<Recipe> parseRecipes(String json) {
        List<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray data = new JSONArray(json);

            for (int i = 0; i < data.length(); i++) {
                recipes.add(parseRecipe(data.optString(i)));
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseRecipes: Failed to parse", e);
        }

        return recipes;
    }

    private static Ingredient parseIngredient(String json) {
        Ingredient ingredient;

        try {
            JSONObject data = new JSONObject(json);

            ingredient = new Ingredient();

            ingredient.setName(data.optString("ingredient"));
            ingredient.setQuantity(data.optInt("quantity"));
            ingredient.setUnit(data.optString("measure"));
        } catch (JSONException e) {
            Log.e(TAG, "parseIngredient: Failed to parse", e);
            ingredient = null;
        }

        return ingredient;
    }

    private static Recipe parseRecipe(String json) {
        Recipe recipe;

        try {
            JSONObject data = new JSONObject(json);

            recipe = new Recipe();

            recipe.setId(data.optInt("id"));
            recipe.setName(data.optString("name"));
            recipe.setServings(data.getInt("servings"));
            recipe.setImage(data.optString("image"));

            JSONArray results = data.optJSONArray("ingredients");

            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    recipe.addIngredient(parseIngredient(results.optString(i)));
                }
            }

            results = data.optJSONArray("steps");

            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    recipe.addStep(parseStep(results.optString(i)));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseRecipe: Failed to parse", e);
            recipe = null;
        }

        return recipe;
    }

    private static Step parseStep(String json) {
        Step step;

        try {
            JSONObject data = new JSONObject(json);

            step = new Step();

            step.setId(data.optInt("id"));
            step.setShortDescription(data.optString("shortDescription"));
            step.setDescription(data.optString("description").replace('�', '°'));
            step.setVideoURL(data.optString("videoURL"));
            step.setThumbnailURL(data.optString("thumbnailURL"));

            if (step.getVideoURL().isEmpty() && !step.getThumbnailURL().isEmpty() &&
                    isVideo(step.getThumbnailURL())) {
                step.setVideoURL(step.getThumbnailURL());
                step.setThumbnailURL("");
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseStep: Failed to parse", e);
            step = null;
        }

        return step;
    }

    private static boolean isVideo(@NonNull String url) {
        return url.toLowerCase().endsWith(".mp4");
    }
}
