package org.example;

import java.util.ArrayList;

public class RecipeList extends ArrayList<Recipe> {

//    Undecided on whether filtering should be provided or simply the responsibility of the class user

//    public RecipeList filterByCategory(RecipeCategory category) {
//        RecipeList result = new RecipeList();
//        for (Recipe recipe : this) {
//            if (recipe.category() == category)
//                result.add(recipe);
//        }
//        return result;
//    }

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
        return this.stream().anyMatch(r -> r.dish().equals(dishName));
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
