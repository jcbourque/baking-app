package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Ingredient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class IngredientsCardFragment extends Fragment {

    @BindView(R.id.ingredients_list_linear_layout) LinearLayout ingredientsList;

    private Unbinder unbinder;

    @Setter
    private List<Ingredient> ingredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        CardView root = (CardView) inflater.inflate(R.layout.ingredients_card_fragment,
                container, false);

        unbinder = ButterKnife.bind(this, root);

        if (ingredients != null && !ingredients.isEmpty()) {
            for (Ingredient ingredient : ingredients) {
                LinearLayout ingredientLayout = (LinearLayout) inflater.inflate(
                        R.layout.ingredient_fragment, container, false);

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

                ingredientsList.addView(ingredientLayout);
            }
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
