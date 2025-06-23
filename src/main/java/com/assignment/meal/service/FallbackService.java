package com.assignment.meal.service;

import com.assignment.meal.enums.CuisineType;
import com.assignment.meal.model.Recipe;
import com.assignment.meal.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FallbackService {
    public List<Recipe> getRecipes(int page, CuisineType cuisineType,  int[] recipesCount);
    public List<Restaurant> getRestaurants(int page,String city, int[] restaurantsCount);

}
