package org.example;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserInterface {
    private static Scanner scanner = new Scanner(System.in);
    private static AccountHandler accounts = new AccountHandler();

    public Account getAccount() throws IOException {
        System.out.println("""
                Welcome to your recipe and meal plan manager.
                Please enter a username to access your account or create a new one.
                """);
        String username = scanner.nextLine();
        if (accounts.getAccount(username) != null)
            return accounts.getAccount(username);
        System.out.println("Username not recognized. Creating new account");
        accounts.getAccountList().add(new Account(username));
        accounts.save();
        return accounts.getAccount(username);
    }


    public void recipeInterface(Account account) {
        while(true) {
            System.out.println("""
                    Welcome to your recipe manager!
                    Please select an option:
                        (V)iew your recipes
                        (A)dd a recipe
                        (D)elete a recipe
                        Access your (M)eal Plan
                        (Q)uit
                        """);
            String input = scanner.next();
            scanner.nextLine();
            switch (input.toUpperCase()) {
                case "V": // TODO: Add filtering by category to list of all recipes
                    System.out.println(account.getRecipes());
                    while (true) {
                        System.out.println(
                                "Enter a category to filter by category, enter \"all\" to view all recipes, or simply hit enter to continue"
                        );
                        String in = scanner.nextLine();
                        RecipeCategory category;
                        if (in.equalsIgnoreCase("all")) {
                            HashMap<String, Account> accounts = (HashMap<String, Account>) FileHandler.readFile("accounts.data");
                            accounts.forEach((k, v) -> System.out.println(k + "'s recipes:\n" + v.getRecipes()));
                            continue;
                        }
                        try {
                            category = RecipeCategory.valueOf(in.toUpperCase());
                        }
                        catch (IllegalArgumentException e) {
                            break;
                        }
                        RecipeList filtered = account.getRecipes().stream()
                                .filter(r -> r.category() == category)
                                .collect(Collectors.toCollection(RecipeList::new));
                        if (!filtered.isEmpty()) {
                            System.out.println(filtered);
                        }
                        else System.out.println("There are no recipes of this category.");
                    }
                    break;
                case "A":
                    addRecipeToList(account);
                    break;
                case "D":
                    deleteRecipeFromList(account);
                    break;
                case "M":
                    mealPlanInterface(account);
                    break;
                case "Q":
                    return;
                default:
                    System.out.println("Invalid input.\n");
            }
        }
    }

    private void addRecipeToList(Account account) {
        System.out.println("What is the name of the recipe? Enter \"cancel\" to cancel.");
        String name = scanner.nextLine();
        if (name.equalsIgnoreCase("cancel")) {
            return;
        }
        System.out.println("What category is the dish? Enter \"cancel\" to cancel.");
        System.out.println("BREAKFAST | LUNCH | DINNER | DESSERT");
        String catString = scanner.nextLine();
        if (catString.equalsIgnoreCase("cancel")) {
            return;
        }
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
            String ingredient = scanner.nextLine().trim();
            if (ingredient.equalsIgnoreCase("done")) break;
            ingredients.add(ingredient);
        }
        while (true) {
            System.out.println("Please enter an instruction, or enter \"done\" to finish.");
            String instruction = scanner.nextLine().trim();
            if (instruction.equalsIgnoreCase("done")) break;
            instructions.add(instruction);
        }
        account.getRecipes().add(
                new Recipe(
                        name,
                        ingredients,
                        instructions,
                        category
                ));
        accounts.save();
    }
    private void deleteRecipeFromList(Account account) {
        while (true) {
            System.out.println(account.getRecipes() +
                    "\n\nPlease enter the name of the recipe you wish to delete. Enter \"cancel\" to cancel.");
            String toDelete = scanner.nextLine();
            if (toDelete.equalsIgnoreCase("cancel")) break;
            if (account.getRecipes().remove(toDelete))
                System.out.println("Recipe deleted.");
            else
                System.out.println("Recipe not found.");
        }
        accounts.save();
    }

    public void mealPlanInterface(Account account) {
        while (true) {
            System.out.println("""
                    Welcome to your meal planner!
                    Please select an option:
                        (V)iew your meal plan
                        (A)dd a recipe to your meal plan
                        (D)elete a recipe from your meal plan
                        Go (B)ack
                    """);
            switch (scanner.nextLine().toUpperCase()) {
                case "V":
                    System.out.println(account.getPlan());
                    break;
                case "A":
                    addRecipeToMP(account);
                    break;
                case "D":
                    deleteRecipeFromMP(account);
                    break;
                case "B":
                    return;
            }
        }
    }

    private void addRecipeToMP(Account account) {
        System.out.println("What day of the week do you want to add a recipe to?");
        DayOfWeek dow;
        while (true) {
            try {
                dow = DayOfWeek.valueOf(scanner.nextLine().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid day of week");
            }
        }
        System.out.println("What is the name of the recipe would you like to add?");
        System.out.println(account.getRecipes());
        while (true) {
            try {
                account.getPlan()
                        .put(dow, account.getRecipes().get(scanner.nextLine()));
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Recipe not found.");
            }
        }
        accounts.save();
    }
    private void deleteRecipeFromMP(Account account) {
        System.out.println("Enter cancel at any time to go back.");
        DayOfWeek dow;
        String input = scanner.nextLine().toUpperCase();
        while (true) {
            System.out.println("What day of the week would you like to delete a recipe from?");
            if (input.equalsIgnoreCase("cancel")) return;
            try {
                dow = DayOfWeek.valueOf(input);
                break;
            } catch (Exception e) {
                System.out.println("Invalid day of week");
            }
        }
        while (true) {
            System.out.println("What is the name of the recipe would you like to delete?");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("cancel")) return;
            if (account.getPlan().remove(dow, input)) {
                accounts.save();
                return;
            }
            System.out.println("Invalid recipe name.");
        }
    }
}