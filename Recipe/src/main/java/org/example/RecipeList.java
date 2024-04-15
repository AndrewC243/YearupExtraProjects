package org.example;

import java.util.ArrayList;

public class RecipeList extends ArrayList<Recipe> {

    public RecipeList filterByCategory(RecipeCategory category) {
        RecipeList result = new RecipeList();
        for (Recipe recipe : this) {
            if (recipe.category() == category)
                result.add(recipe);
        }
        return result;
    }

    public boolean removeRecipe(String dishName) {
        if (!hasRecipe(dishName)) {
            return false;
        }
        else {
            for(int i = 0; i < size(); i++) {
                if (get(i).dish().equals(dishName)) {
                    remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean hasRecipe(String dishName) {
        for (Recipe recipe : this) {
            if (recipe.dish().equalsIgnoreCase(dishName))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String ret = "";
        int counter = 1;
        for(Recipe recipe : this) {
            ret += (counter++) + ": " + recipe + "\n";
        }
        return ret;
    }
}
