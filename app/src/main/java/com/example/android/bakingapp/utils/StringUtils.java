package com.example.android.bakingapp.utils;

import android.text.Html;
import android.text.Spanned;

import com.example.android.bakingapp.data.Ingredient;
import com.example.android.bakingapp.data.Recipe;

public final class StringUtils {
    private StringUtils() { /* This class is not intended to be instantiated */ }

    public static Spanned getIngredients(Recipe recipe) {
        StringBuilder builder = new StringBuilder();

        builder.append("<h3>").append(recipe.getName()).append("</h3>").append("<ul>");

        for (Ingredient ingredient : recipe.getIngredients()) {
            builder.append("<li> <strong>").append(ingredient.getQuantity());

            if (!"unit".equalsIgnoreCase(ingredient.getUnit())) {
                builder.append(" ").append(ingredient.getUnit().toLowerCase());
            }

            builder.append("</strong> ").append(ingredient.getName()).append("</li>");
        }

        return Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
    }

}
