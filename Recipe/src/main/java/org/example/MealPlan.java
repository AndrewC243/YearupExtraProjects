package org.example;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;

public class MealPlan extends LinkedHashMap<DayOfWeek, RecipeList> {

    public MealPlan() {
        for (DayOfWeek day : DayOfWeek.values()) {
            put(day, new RecipeList());
        }
    }

    public void put(DayOfWeek day, Recipe recipe) {
        get(day).add(recipe);
    }

    public boolean remove(DayOfWeek day, String dishName) {
        return get(day).remove(dishName);
    }

    public void clearDay(DayOfWeek day) {
        get(day).clear();
    }

    public String toString() {
        String ret = "";
        for (DayOfWeek day : keySet()) {
            ret += day + ":\n" + get(day) + "\n";
        }
        return ret;
    }
}
