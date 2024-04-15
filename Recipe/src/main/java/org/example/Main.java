package org.example;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        Main main = new Main();
        RecipeList recipes = new RecipeList();

        while(true) {
            System.out.println("""
                    Welcome to the recipe manager!
                    Please select an option:
                        (V)iew your recipes
                        (A)dd a recipe
                        (D)elete a recipe
                        (Q)uit
                        """);
            String input = sc.next();
            sc.nextLine();
            switch (input.toUpperCase()) {
                case "V":
                    System.out.println(recipes);
                    while (true) {
                        System.out.println("Enter a category to filter by category, or simply hit enter to continue");
                        String in = sc.nextLine();
                        RecipeList filtered = recipes.filterByCategory(
                                RecipeCategory.valueOf(in.toUpperCase())
                        );
                        if (filtered.isEmpty()) { break; }
                        System.out.println(filtered);
                    }
                    break;
                case "A":
                    System.out.println("What is the name of the recipe? Enter \"cancel\" to cancel.");
                    String name = sc.nextLine();
                    if (name.equals("cancel")) { break; }
                    System.out.println("What category is the dish? Enter \"cancel\" to cancel.");
                    System.out.println("BREAKFAST | LUNCH | DINNER | DESSERT");
                    String catString = sc.nextLine();
                    if (catString.equals("cancel")) { break; }
                    RecipeCategory category;
                    while (true) {
                        try {
                            category = RecipeCategory.valueOf(catString.toUpperCase());
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid category");
                        }
                    }
                    ArrayList<String> ingredients = new ArrayList<>();
                    ArrayList<String> instructions = new ArrayList<>();
                    while(true) {
                        System.out.println("Please enter an ingredient, or enter \"done\" to finish.");
                        String ingredient = sc.nextLine();
                        if (ingredient.equalsIgnoreCase("done")) break;
                        ingredients.add(ingredient);
                    }
                    while (true) {
                        System.out.println("Please enter an instruction, or enter \"done\" to finish.");
                        String instruction = sc.nextLine();
                        if (instruction.equalsIgnoreCase("done")) break;
                        instructions.add(instruction);
                    }
                    recipes.add(new Recipe(
                            name,
                            ingredients, instructions,
                            category
                    ));
                    break;
                case "D":
                    while (true) {
                        System.out.println(recipes +
                                "\n\nPlease enter the name of the recipe you wish to delete (enter cancel to go back):");
                        String toDelete = sc.nextLine();
                        if (toDelete.equalsIgnoreCase("cancel")) break;
                        if (recipes.removeRecipe(toDelete))
                            System.out.println("Recipe deleted.");
                        else
                            System.out.println("Recipe not found.");
                    }
                    break;
                case "Q":
                    return;
                default:
                    System.out.println("Invalid input.\n");
            }
        }
    }
}