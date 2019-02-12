package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Ingredient;
import com.example.android.bakingapp.data.Recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class RecipeDetailFragment extends Fragment {
//    @BindView(R.id.ingredient_list) LinearLayout ingredients;
    @BindView(R.id.recipe_step_list) RecyclerView steps;
//    @BindView(R.id.ingredients_text_view) TextView ingredientsLabel;

    @Setter
    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recipe_detail, container, false);
        ButterKnife.bind(this, root);

        if (recipe != null) {
            if (recipe.hasIngredients()) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    LinearLayout ingredientLayout =
                            (LinearLayout) inflater.inflate(R.layout.ingredient_fragment, container, false);

                    TextView quantity = ingredientLayout.findViewById(R.id.ingredient_quantity_text_view);
                    TextView unit = ingredientLayout.findViewById(R.id.ingredient_unit_text_view);
                    TextView name = ingredientLayout.findViewById(R.id.ingredient_name_text_view);

                    quantity.setText(String.valueOf(ingredient.getQuantity()));
                    if ("unit".equalsIgnoreCase(ingredient.getUnit())) {
                        unit.setVisibility(View.GONE);
                    } else {
                        unit.setText(ingredient.getUnit().toLowerCase());
                    }
                    name.setText(ingredient.getName());

//                    ingredients.addView(ingredientLayout);
                }
            }
        } else {
//            ingredientsLabel.setText(R.string.no_recipe);
        }

        return root;
    }
}
