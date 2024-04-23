package org.example;

import java.io.Serializable;
import java.util.ArrayList;

public record Recipe(
        String dish,
        ArrayList<String> ingredients,
        ArrayList<String> instructions,
        RecipeCategory category) implements Serializable {

    public String toString() {
        String ret = this.dish + " -- " + category + "\n";

        ret += "\nIngredients:\n";
        for(String ingredient : ingredients) {
            ret += "\t" + ingredient + "\n";
        }
        ret += "\nInstructions:\n";
        for(String instruction : instructions) {
            ret += "\t" + instruction + "\n";
        }
        return ret;
    }
}