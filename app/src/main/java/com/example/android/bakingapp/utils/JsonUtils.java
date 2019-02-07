package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.data.Ingredient;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {
    private JsonUtils() { /* This class is not intended to be instantiated */ }

    public static Ingredient parseIngredient(String json) {
        Ingredient ingredient;

        try {
            JSONObject data = new JSONObject(json);

            ingredient = new Ingredient();

            ingredient.setName(data.optString("ingredient"));
            ingredient.setQuantity(data.optInt("quantity"));
            ingredient.setUnit(data.optString("measure"));
        } catch (JSONException ignore) {
            ingredient = null;
        }

        return ingredient;
    }

    public static Recipe parseRecipe(String json) {
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
        } catch (JSONException ignore) {
            recipe = null;
        }

        return recipe;
    }

    public static Step parseStep(String json) {
        Step step;

        try {
            JSONObject data = new JSONObject(json);

            step = new Step();

            step.setId(data.optInt("id"));
            step.setShortDescription("shortDescription");
            step.setDescription("description");
            step.setVideoURL("videoURL");
            step.setThumbnailURL("thumbnailURL");
        } catch (JSONException ignore) {
            step = null;
        }

        return step;
    }
}
