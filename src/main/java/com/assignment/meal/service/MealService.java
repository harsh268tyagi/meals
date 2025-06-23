package com.assignment.meal.service;

import com.assignment.meal.enums.CuisineType;
import com.assignment.meal.model.RmdResponse;
import org.springframework.stereotype.Service;

@Service
public interface MealService {
    public RmdResponse getSuggestions(CuisineType cuisineType, String city, int page, boolean offline);
}
