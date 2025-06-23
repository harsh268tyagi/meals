package com.assignment.meal.service.impl;

import com.assignment.meal.enums.CuisineType;
import com.assignment.meal.model.Recipe;
import com.assignment.meal.model.Restaurant;
import com.assignment.meal.service.FallbackService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FallbackServiceImpl implements FallbackService {

    private final ObjectMapper objectMapper;

    @Override
    public List<Recipe> getRecipes(int page, CuisineType cuisineType, int[] recipesCount) {
        List<Recipe> recipes = new ArrayList<>();
        try (InputStream is = new ClassPathResource("static/fallback/recipes.json").getInputStream()) {
            List<Recipe> recipeList = objectMapper.readValue(is, new TypeReference<List<Recipe>>() {});
            List<Recipe> filteredRecipes = recipeList.stream()
                    .filter(recipe -> recipe.getCuisineType() != null && recipe.getCuisineType().equalsIgnoreCase(cuisineType.name()))
                    .toList();
            int start = (page - 1) * 10;
            int end = start + 10;
            int index = 0;
            for(Recipe recipe : filteredRecipes) {
                if (index >= start && index < end) {
                    recipes.add(recipe);
                }
                index++;
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load fallback recipes", e);
        }
        recipesCount[0] = recipes.size();
        return recipes;
    }

    @Override
    public List<Restaurant> getRestaurants(int page, String city, int[] restaurantsCount) {
        List<Restaurant> restaurants = new ArrayList<>();
        try (InputStream is = new ClassPathResource("static/fallback/restaurants.json").getInputStream()) {
            List<Restaurant> restaurantList = objectMapper.readValue(is, new TypeReference<List<Restaurant>>() {});

            // Filter restaurants by city which is assumed to be the address field
            List <Restaurant> filteredRestaurants = restaurantList.stream()
                    .filter(restaurant -> restaurant.getAddress() != null && restaurant.getAddress().equalsIgnoreCase(city))
                    .toList();
            int start = (page - 1) * 10;
            int end = start + 10;
            int index = 0;
            for (Restaurant restaurant : filteredRestaurants) {
                if (index >= start && index < end) {
                    restaurants.add(restaurant);
                }
                index++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load fallback restaurants", e);
        }
        restaurantsCount[0] = restaurants.size();
        return restaurants;

    }
}
