package org.example;

import java.io.Serializable;

public class Account implements Serializable {
    private MealPlan plan = new MealPlan();
    private RecipeList recipes = new RecipeList();
    private String username;

    public Account(String username) {
        this.username = username;
    }

    public boolean equals(Object o) {
            if (!(o instanceof Account)) return false;
        Account a = (Account) o;
        return plan.equals(a.plan) && recipes.equals(a.recipes);
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public boolean removeRecipe(Recipe recipe) {
        return recipes.remove(recipe);
    }

    public MealPlan getPlan() {
        return plan;
    }

    public RecipeList getRecipes() {
        return recipes;
    }

    public String getUsername() {
        return username;
    }
}
