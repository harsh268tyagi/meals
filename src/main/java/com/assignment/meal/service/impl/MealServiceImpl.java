package com.assignment.meal.service.impl;

import com.assignment.meal.client.DocumenuClient;
import com.assignment.meal.client.EdamamClient;
import com.assignment.meal.enums.CuisineType;
import com.assignment.meal.model.Recipe;
import com.assignment.meal.model.Restaurant;
import com.assignment.meal.model.RmdResponse;
import com.assignment.meal.service.FallbackService;
import com.assignment.meal.service.MealService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MealServiceImpl implements MealService {

    // Instantiated by lombok @Data annotation
    private FallbackService fallbackService;
    private EdamamClient edmamClient;
    private DocumenuClient documenuClient;

    @Override
    public RmdResponse getSuggestions(CuisineType cuisineType, String city, int page, boolean offline) {
        List<Recipe> recipes;
        List<Restaurant> restaurants;
        int[] recipesCountHolder = new int[1];
        int[] restaurantsCountHolder = new int[1];

        if(offline){
            recipes = fallbackService.getRecipes(page, cuisineType, recipesCountHolder);
            restaurants = fallbackService.getRestaurants(page, city,restaurantsCountHolder);

        }
        else {
            try {
                recipes = edmamClient.getRecipes(cuisineType,page,recipesCountHolder);
            } catch (Exception e) {
                recipes = fallbackService.getRecipes(page, cuisineType, recipesCountHolder);
            }
            try {
                restaurants = documenuClient.getRestaurants(cuisineType, city, restaurantsCountHolder);
            } catch (Exception e) {
                restaurants = fallbackService.getRestaurants(page, city, restaurantsCountHolder);
            }
        }
        int recipesCount = recipesCountHolder[0];
        int restaurantsCount = restaurantsCountHolder[0];
        return RmdResponse.builder()
                .recipes(recipes)
                .page(page)
                .restaurants(restaurants)
                .keyword(cuisineType.name() + " recipes in " + city)
                .totalResults(recipesCount + restaurantsCount)
                .prevPage( page > 1 ? page - 1 : 0)
                .nextPage(recipes.size() + restaurants.size() > 0 ? page + 1 : 0)
                .build();
    }
}
